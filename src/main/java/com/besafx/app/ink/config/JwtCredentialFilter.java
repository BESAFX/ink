package com.besafx.app.ink.config;

import com.auth0.jwt.JWT;
import com.besafx.app.ink.dto.JwtResponse;
import com.besafx.app.ink.jwt.JwtConfig;
import com.besafx.app.ink.util.JsonConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Slf4j
public class JwtCredentialFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;

    public JwtCredentialFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        setFilterProcessesUrl(this.jwtConfig.getUrl());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        LoginForm credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginForm.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                new ArrayList<>());

        Authentication auth = authenticationManager.authenticate(authenticationToken);

        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        PersonUserDetails principal = (PersonUserDetails) authResult.getPrincipal();

        String authorities = principal.getPerson().getTeam().getAuthorities();

        DateTime dateTime = new DateTime().plusMinutes(this.jwtConfig.getExpiration());

        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withClaim("Authorities", authorities)
                .withExpiresAt(dateTime.toDate())
                .sign(HMAC512(this.jwtConfig.getSecret().getBytes()));

        String authHeader = this.jwtConfig.getPrefix() + token;
        response.addHeader(this.jwtConfig.getHeader(), authHeader);

        List<String> authList = new ArrayList<>();

        Optional.ofNullable(authorities)
                .ifPresent(value -> Arrays.asList(value.split(",")).stream()
                        .forEach(s -> authList.add(s)));

        response.setContentType("application/json");
        try {
            response.getWriter().write(JsonConverter
                    .toString(JwtResponse.builder()
                            .accessToken(token)
                            .authorities(authList)
                            .type(this.jwtConfig.getPrefix())
                            .username(principal.getUsername())
                    ));
        } catch (IOException ex) {
            log.error(ExceptionUtils.getMessage(ex));
        }
    }

    @Getter
    @Setter
    private static class LoginForm {
        private String username;
        private String password;
    }
}

