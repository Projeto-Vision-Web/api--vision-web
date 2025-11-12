package com.visionweb.app_vision_web.domain.core.entities.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtSettings {

    private String SecretKey;
    private String Issuer;
    private String Audience;
    private int ExpiryHours;
}
