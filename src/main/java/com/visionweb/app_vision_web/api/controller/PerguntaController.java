package com.visionweb.app_vision_web.api.controller;

import com.visionweb.app_vision_web.application.dto.PerguntaCreateDto;
import com.visionweb.app_vision_web.application.dto.PerguntaOpcaoResponseDto;
import com.visionweb.app_vision_web.application.dto.PerguntaResponseDto;
import com.visionweb.app_vision_web.application.services.PerguntaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pergunta")
public class PerguntaController {

    private final PerguntaServiceImpl perguntaService;

    public PerguntaController(PerguntaServiceImpl perguntaService) {
        this.perguntaService = perguntaService;
    }

    @PostMapping
    public ResponseEntity<PerguntaResponseDto> criar(@RequestBody @Valid PerguntaCreateDto dto) {
        var pergunta = perguntaService.criar(dto);

        var retorno = new PerguntaResponseDto(
                pergunta.getId(),
                pergunta.getTextoPergunta(),
                pergunta.getTipo().name(),
                pergunta.getFormulario().getId(),
                pergunta.getOpcoes() != null
                        ? pergunta.getOpcoes().stream()
                        .map(o -> new PerguntaOpcaoResponseDto(o.getId(), o.getTextoOpcao(), o.getValorNum()))
                        .toList()
                        : null
        );

        return ResponseEntity
                .created(URI.create("/api/perguntas/" + pergunta.getId()))
                .body(retorno);
    }

    @GetMapping("/formulario/{idFormulario}")
    public ResponseEntity<List<PerguntaResponseDto>> listarPorFormulario(@PathVariable Integer idFormulario) {
        var perguntas = perguntaService.listarPorFormulario(idFormulario);

        return ResponseEntity.ok(perguntas);
    }



}
