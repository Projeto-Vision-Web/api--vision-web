package com.visionweb.app_vision_web.application.mapper;

import com.visionweb.app_vision_web.application.dto.CadastroDto;
import com.visionweb.app_vision_web.domain.core.entities.Usuario;

public class UsuarioMapper {

    public static Usuario fromCadastroDto(CadastroDto dto) {
        return Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .tipo_usuario(dto.getTipo_usuario())
                .ativo(true)
                .build();
    }
}
