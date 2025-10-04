package com.visionweb.app_vision_web.application.dto;

import com.visionweb.app_vision_web.domain.core.entities.Login;
import lombok.Data;

@Data
public class LoginDto {

    private String email;
    private String senha;

    public Login construirLogin() {
        return new Login();
    }
}
