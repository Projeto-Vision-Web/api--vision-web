package com.visionweb.app_vision_web.domain.contracts.service;

import com.visionweb.app_vision_web.application.dto.RespostaColetaRequestDto;
import com.visionweb.app_vision_web.application.dto.RespostaColetaResponseDto;
import com.visionweb.app_vision_web.domain.core.entities.Coleta;

import java.util.List;

public interface ColetaService {

    // -------- CONSULTAS --------

    /**
     * Lista todas as coletas da base.
     */
    List<Coleta> listarTodas();

    /**
     * Busca uma coleta pelo ID.
     */
    Coleta buscarPorId(Integer id);

    // -------- FLUXO DA COLETA --------

    /**
     * Cria uma nova coleta (pulse) quando o formulário é publicado.
     *
     * @param idFormulario ID do formulário-base
     * @param idEmpresa    ID da empresa para a qual a coleta será aplicada
     * @param periodoRef   Período referente (ex: "2025-11")
     * @param canalStr     Canal (APP, EMAIL...) como string
     */
    Coleta criarColetaAoPublicar(Integer idFormulario,
                                 Integer idEmpresa,
                                 String periodoRef,
                                 String canalStr);

    /**
     * Dispara a coleta, mudando o status para DISPARADA
     * e permitindo que colaboradores enviem respostas.
     */
    Coleta disparar(Integer idColeta);

    /**
     * Encerra a coleta, bloqueando novas respostas.
     */
    Coleta encerrar(Integer idColeta);

    // -------- REGISTRO DE RESPOSTAS --------

    /**
     * Registra todas as respostas de um colaborador para uma coleta.
     *
     * @param idColeta ID da coleta
     * @param dto      Respostas enviadas
     */
    RespostaColetaResponseDto registrarRespostas(Integer idColeta,
                                                 RespostaColetaRequestDto dto) ;
}
