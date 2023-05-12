package com.fpt.g2.utils;

import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class UsernameAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return Optional.of(((User) authentication.getPrincipal()).getUsername());
    }

    @Bean
    public AuditorAware<String> auditorAware(){
        return new UsernameAuditorAware();
    }
}
