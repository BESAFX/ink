package com.besafx.app.ink.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.besafx.app.ink.exception.ApiError;
import com.besafx.app.ink.util.JsonConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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
public class JwtTokenFilter extends BasicAuthenticationFilter {

    private JwtConfig jwtConfig;

    public JwtTokenFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
        super(authenticationManager);
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Read the Authorization header, where the JWT token should be
        String header = request.getHeader(this.jwtConfig.getHeader());

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(this.jwtConfig.getPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        // If header is present, try grab user principal from database and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            final ApiError apiError = new ApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage(),
                    e.getMessage(),
                    "Exception from request: " + request.getRequestURL().toString()
            );
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json");
            try {
                response.getWriter().write(JsonConverter.toString(apiError));
                log.error(e.getMessage());
            } catch (IOException ex) {
                log.error(ExceptionUtils.getMessage(ex));
            }
        }
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(this.jwtConfig.getHeader())
                .replace(this.jwtConfig.getPrefix(), "");

        if (token != null) {
            // parse the token and validate it
            DecodedJWT decodedJWT = JWT.require(HMAC512(this.jwtConfig.getSecret().getBytes()))
                    .build()
                    .verify(token);

            String authoritiesString = decodedJWT.getClaim("Authorities").asString();

            List<GrantedAuthority> authorities = new ArrayList<>();

            Optional.ofNullable(authoritiesString)
                    .ifPresent(value -> Arrays.asList(value.split(",")).stream()
                            .forEach(s -> authorities.add(new SimpleGrantedAuthority(s))));

            String userName = decodedJWT.getSubject();

            // Search in the DB if we find the user by token subject (username)
            // If so, then grab user details and create spring auth token using username, pass, authorities/roles
            if (userName != null) {
                return new UsernamePasswordAuthenticationToken(userName, request.getHeader(this.jwtConfig.getHeader()), authorities);
            }
            return null;
        }
        return null;
    }
}
