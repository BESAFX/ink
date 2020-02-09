package com.besafx.app.ink.jwt;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Getter
@ToString
@Slf4j
public class JwtConfig {

    @Value("${ink.security.jwt.url:/api/login}")
    private String url;

    @Value("${ink.security.jwt.header:Authorization}")
    private String header;

    @Value("${ink.security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${ink.security.jwt.expiration}")
    private int expiration;

    @Value("${ink.security.jwt.secret}")
    private String secret;
}
