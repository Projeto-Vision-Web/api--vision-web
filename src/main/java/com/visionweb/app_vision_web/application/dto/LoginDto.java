package com.visionweb.app_vision_web.application.dto;

import com.visionweb.app_vision_web.domain.core.entities.Enum.TipoUsuario;
import com.visionweb.app_vision_web.domain.core.entities.Login;
import lombok.Data;

@Data
public class LoginDto {

    private String email;
    private String senha;
    private TipoUsuario tipoUsuario;

    public Login construirLogin() {
        return new Login();
    }
}
