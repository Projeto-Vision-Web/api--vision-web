package com.visionweb.app_vision_web.api.controller;

import com.visionweb.app_vision_web.application.dto.FormularioCreateDto;
import com.visionweb.app_vision_web.application.dto.FormularioResponseDto;
import com.visionweb.app_vision_web.domain.contracts.service.FormularioService;
import com.visionweb.app_vision_web.domain.core.entities.Empresa;
import com.visionweb.app_vision_web.domain.core.entities.Formulario;
import com.visionweb.app_vision_web.domain.core.entities.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/formulario")
public class FormularioController {

    private final FormularioService formularioService;

    public FormularioController(FormularioService formularioService) {
        this.formularioService = formularioService;
    }

    @PostMapping
    public ResponseEntity<FormularioResponseDto> criar(@RequestBody @Valid FormularioCreateDto dto) {
        Formulario form = Formulario.builder()
                .titulo(dto.titulo())
                .descricao(dto.descricao())
                .ativo(dto.ativo())
                .empresa(Empresa.builder().id_empresa(dto.idEmpresa()).build()) // referencia por id
                .criadoPor(dto.idUsuarioCriador() != null ? Usuario.builder().id_usuario(dto.idUsuarioCriador()).build() : null)
                .build();

        form.setDataCriacao(LocalDateTime.now());
        Formulario formSalvo = formularioService.salvar(form);

        var body = new FormularioResponseDto(
                formSalvo.getId(), formSalvo.getTitulo(), formSalvo.getDescricao(), formSalvo.getAtivo(),
                formSalvo.getEmpresa() != null ? formSalvo.getEmpresa().getId_empresa() : null
        );
        return ResponseEntity.created(URI.create("/api/formularios/" + formSalvo.getId())).body(body);
    }


}
