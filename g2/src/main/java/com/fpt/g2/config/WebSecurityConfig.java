package com.fpt.g2.config;

import com.fpt.g2.constant.URLConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        InMemoryTokenRepositoryImpl memory = new InMemoryTokenRepositoryImpl();
        return memory;
    }

    @Autowired
    public CustomLoginFailureHandler customLoginFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/permission-setting","/**").authenticated()
                .antMatchers("/resources/**","/**","/curriculum/electives/details/**","/home").permitAll()
                .and().formLogin()
                .loginPage(URLConstant.Authentication.INIT_LOGIN)
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl(URLConstant.Authentication.LOGIN_POST).permitAll()
                .defaultSuccessUrl(URLConstant.Authentication.INIT_LOGIN)
                .failureUrl(URLConstant.Authentication.INIT_LOGIN).failureHandler(customLoginFailureHandler)
                .and()
                .logout().logoutUrl(URLConstant.Authentication.LOGOUT).permitAll().logoutSuccessUrl(URLConstant.Authentication.INIT_LOGIN)
                .and()
                .rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(1 * 24 * 60 * 60)
                .and()
                .exceptionHandling().accessDeniedPage(URLConstant.Error.ACCESS_DENIED);

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
