package com.visionweb.app_vision_web.application.dto;

import com.visionweb.app_vision_web.domain.core.entities.Enum.TipoUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class CadastroDto {

    private String nome;
    private String email;
    private String departamento;
    private String cargo;
    private TipoUsuario tipo_usuario;
    private String senha;
}
