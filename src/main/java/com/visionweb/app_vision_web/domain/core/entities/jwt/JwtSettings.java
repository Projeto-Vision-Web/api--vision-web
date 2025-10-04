package com.visionweb.app_vision_web.domain.core.entities.jwt;

import lombok.Data;

@Data
public class JwtSettings {

    private String SecretKey;
    private String Issuer;
    private String Audience;
    private int ExpiryHours;
}
