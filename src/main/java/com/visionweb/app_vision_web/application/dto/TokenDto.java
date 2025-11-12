package com.visionweb.app_vision_web.application.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TokenDto {

    private String token;
    private Date expirationTime;
}
