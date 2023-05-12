package com.fpt.g2.config;

import com.fpt.g2.constant.URLConstant;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebRoutingConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {

        return container -> {
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, URLConstant.Error.PAGE_NOT_FOUND));
            container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, URLConstant.Error.ACCESS_DENIED));
            container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, URLConstant.Error.UNAUTHORIZED));
            container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, URLConstant.Error.INTERNAL_SERVER_ERROR));
        };
    }

}