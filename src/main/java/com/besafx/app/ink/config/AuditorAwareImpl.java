package com.besafx.app.ink.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        } catch (Exception ex) {
            return Optional.ofNullable("");
        }
    }
}