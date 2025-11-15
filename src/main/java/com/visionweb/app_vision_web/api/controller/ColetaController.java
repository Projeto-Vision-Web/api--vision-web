package com.visionweb.app_vision_web.api.controller;

import com.visionweb.app_vision_web.application.dto.ColetaResponseDto;
import com.visionweb.app_vision_web.application.dto.PublicarColetaDto;
import com.visionweb.app_vision_web.application.dto.RespostaColetaRequestDto;
import com.visionweb.app_vision_web.application.dto.RespostaColetaResponseDto;
import com.visionweb.app_vision_web.application.services.ColetaServiceImpl;
import com.visionweb.app_vision_web.domain.contracts.service.ColetaService;
import com.visionweb.app_vision_web.domain.core.entities.Coleta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/coleta")
public class ColetaController {

    private final ColetaService coletaService;

    public ColetaController(ColetaService coletaService) {
        this.coletaService = coletaService;
    }


    @GetMapping
    public ResponseEntity<List<ColetaResponseDto>> listarTodas() throws Exception{

            List<Coleta> coletas = coletaService.listarTodas();

            List<ColetaResponseDto> retorno = coletas.stream()
                    .map(ColetaResponseDto::fromEntity)
                    .toList();

            return ResponseEntity.ok(retorno);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ColetaResponseDto> buscarPorId(@PathVariable Integer id) throws Exception{
        Coleta coleta = coletaService.buscarPorId(id);
        return ResponseEntity.ok(ColetaResponseDto.fromEntity(coleta));
    }

    // -------------------------------------------------
    // DISPARAR COLETA (libera para respostas)
    // -------------------------------------------------

    @PostMapping("/{id}/disparar")
    public ResponseEntity<ColetaResponseDto> disparar(@PathVariable Integer id) throws Exception{
        Coleta coleta = coletaService.disparar(id);
        return ResponseEntity.ok(ColetaResponseDto.fromEntity(coleta));
    }

    // -------------------------------------------------
    // ENCERRAR COLETA (bloqueia novas respostas)
    // -------------------------------------------------

    @PostMapping("/{id}/encerrar")
    public ResponseEntity<ColetaResponseDto> encerrar(@PathVariable Integer id) throws Exception {
        Coleta coleta = coletaService.encerrar(id);
        return ResponseEntity.ok(ColetaResponseDto.fromEntity(coleta));
    }

    // -------------------------------------------------
    // REGISTRAR RESPOSTAS DO COLABORADOR
    // -------------------------------------------------

    /**
     * Endpoint para o colaborador responder a coleta.
     *
     * Exemplo de JSON:
     * {
     *   "idColaborador": 12,
     *   "respostas": [
     *     { "idPergunta": 101, "valor": "5" },
     *     { "idPergunta": 102, "valor": "Me sinto sobrecarregado..." }
     *   ]
     * }
     */
    @PostMapping("/{idColeta}/respostas")
    public ResponseEntity<RespostaColetaResponseDto> responderColeta(
            @PathVariable Integer idColeta,
            @RequestBody @Valid RespostaColetaRequestDto dto) throws Exception{

        RespostaColetaResponseDto resposta = coletaService.registrarRespostas(idColeta, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

}
