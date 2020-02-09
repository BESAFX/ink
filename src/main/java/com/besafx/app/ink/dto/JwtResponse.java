package com.besafx.app.ink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JwtResponse {

    private String accessToken;

    private String type;

    private String username;

    private List<String> authorities;
}
