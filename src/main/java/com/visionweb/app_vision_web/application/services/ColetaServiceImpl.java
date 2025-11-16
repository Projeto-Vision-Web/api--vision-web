package com.visionweb.app_vision_web.application.services;

import com.visionweb.app_vision_web.application.dto.ItemRespostaDto;
import com.visionweb.app_vision_web.application.dto.RespostaColetaRequestDto;
import com.visionweb.app_vision_web.application.dto.RespostaColetaResponseDto;
import com.visionweb.app_vision_web.domain.contracts.repository.*;
import com.visionweb.app_vision_web.domain.contracts.service.ColetaService;
import com.visionweb.app_vision_web.domain.core.entities.*;
import com.visionweb.app_vision_web.domain.core.entities.Enum.Canal;
import com.visionweb.app_vision_web.domain.core.entities.Enum.StatusColeta;
import com.visionweb.app_vision_web.domain.core.entities.Enum.TipoPergunta;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ColetaServiceImpl  implements ColetaService {

    private final ColetaRepository coletaRepository;
    private final FormularioRepository formularioRepository;
    private final EmpresaRepository empresaRepository;
    private final ColaboradorRepository colaboradorRepository;
    private final PerguntaRepository perguntaRepository;
    private final PerguntaOpcaoRepository perguntaOpcaoRepository;
    private final RespostaRepository respostaRepository;
    private final ColetaParticipanteRepository coletaParticipanteRepository;
    private final NlpService nlpService;
    private final FeedbackProcessadoRepository feedbackProcessadoRepository;

    public ColetaServiceImpl(ColetaRepository coletaRepository,
                             FormularioRepository formularioRepository,
                             EmpresaRepository empresaRepository,
                             ColaboradorRepository colaboradorRepository,
                             PerguntaRepository perguntaRepository,
                             PerguntaOpcaoRepository perguntaOpcaoRepository,
                             RespostaRepository respostaRepository, ColetaParticipanteRepository coletaParticipanteRepository, NlpService nlpService, FeedbackProcessadoRepository feedbackProcessadoRepository) {
        this.coletaRepository = coletaRepository;
        this.formularioRepository = formularioRepository;
        this.empresaRepository = empresaRepository;
        this.colaboradorRepository = colaboradorRepository;
        this.perguntaRepository = perguntaRepository;
        this.perguntaOpcaoRepository = perguntaOpcaoRepository;
        this.respostaRepository = respostaRepository;
        this.coletaParticipanteRepository = coletaParticipanteRepository;
        this.nlpService = nlpService;
        this.feedbackProcessadoRepository = feedbackProcessadoRepository;
    }

    // -------------------------------------------------
    // CONSULTAS BÁSICAS
    // -------------------------------------------------

    public List<Coleta> listarTodas() {
        return coletaRepository.findAll();
    }

    public Coleta buscarPorId(Integer id) {
        return coletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coleta não encontrada"));
    }

    // -------------------------------------------------
    // CRIAR COLETA (ao publicar formulário)
    // -------------------------------------------------


    @Transactional
    public Coleta criarColetaAoPublicar(Integer idFormulario,
                                        Integer idEmpresa,
                                        String periodoRef,
                                        String canalStr) {

        Formulario formulario = formularioRepository.findById(idFormulario)
                .orElseThrow(() -> new RuntimeException("Formulário não encontrado"));

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        Canal canal = (canalStr == null || canalStr.isBlank())
                ? Canal.APP
                : Canal.valueOf(canalStr.toUpperCase());

        Coleta coleta = Coleta.builder()
                .formulario(formulario)
                .empresa(empresa)
                .periodoRef(periodoRef)
                .canal(canal)
                .status(StatusColeta.CRIADA)
                .criadoEm(LocalDateTime.now())
                .build();

        return coletaRepository.save(coleta);
    }

    // -------------------------------------------------
    // DISPARAR COLETA
    // -------------------------------------------------

    /**
     * Marca a coleta como DISPARADA (libera para respostas).
     */
    @Transactional
    public Coleta disparar(Integer idColeta){
        Coleta coleta = buscarPorId(idColeta);

        if (coleta.getStatus() == StatusColeta.DISPARADA) {
            throw new RuntimeException("Coleta já está disparada");
        }
        if (coleta.getStatus() == StatusColeta.ENCERRADA) {
            throw new RuntimeException("Coleta já foi encerrada");
        }

        coleta.setStatus(StatusColeta.DISPARADA);
        // se quiser, pode ter um campo dataInicio na entidade:
        // coleta.setDataInicio(LocalDateTime.now());

        return coletaRepository.save(coleta);
    }

    // -------------------------------------------------
    // ENCERRAR COLETA (opcional, mas é legal ter)
    // -------------------------------------------------

    /**
     * Encerra a coleta, bloqueando novas respostas.
     */
    @Transactional
    public Coleta encerrar(Integer idColeta){
        Coleta coleta = buscarPorId(idColeta);

        if (coleta.getStatus() == StatusColeta.ENCERRADA) {
            throw new RuntimeException("Coleta já está encerrada");
        }

        coleta.setStatus(StatusColeta.ENCERRADA);

        return coletaRepository.save(coleta);
    }

    // -------------------------------------------------
    // REGISTRAR RESPOSTAS DE COLABORADORES
    // -------------------------------------------------

    /**
     * Registra as respostas de um colaborador para uma coleta.
     * Endpoint: POST /api/coletas/{idColeta}/respostas
     */
    @Transactional
    public RespostaColetaResponseDto registrarRespostas(Integer idColeta,
                                                        RespostaColetaRequestDto dto) {

        Coleta coleta = buscarPorId(idColeta);

        if (coleta.getStatus() != StatusColeta.DISPARADA) {
            throw new RuntimeException("Coleta não está aberta para respostas");
        }

        Colaborador colaborador = colaboradorRepository.findById(dto.idColaborador())
                .orElseThrow(() -> new RuntimeException("Colaborador não encontrado"));


        if (coletaParticipanteRepository.existsByColeta_IdAndColaborador_Id(idColeta, colaborador.getId())) {
             throw new RuntimeException("Colaborador já respondeu esta coleta");
        }

        int totalRespondidas = 0;

        for (ItemRespostaDto item : dto.respostas()) {

            Pergunta pergunta = perguntaRepository.findById(item.idPergunta())
                    .orElseThrow(() -> new RuntimeException("Pergunta não encontrada: " + item.idPergunta()));

            // valida o valor conforme o tipo da pergunta
            validarResposta(pergunta, item.valor());

            Resposta resposta = Resposta.builder()
                    .pergunta(pergunta)
                    .colaborador(colaborador)
                    .coleta(coleta)
                    .valorResposta(item.valor())
                    .dataResposta(LocalDateTime.now())
                    .build();

            respostaRepository.save(resposta);

            if (pergunta.getTipo() == TipoPergunta.TEXTO) {
                var analise = nlpService.analisarTexto(resposta.getValorResposta());

                FeedbackProcessado feedback = FeedbackProcessado.builder()
                        .resposta(resposta)
                        .sentimento(analise.sentimento().name())
                        .scoreSentimento(analise.scoreSentimento())
                        .cluster(analise.cluster())
                        .riscoTurnover(analise.riscoTurnover())
                        .build();

                feedbackProcessadoRepository.save(feedback);
            }
            totalRespondidas++;

        }

        ColetaParticipanteId cpId = new ColetaParticipanteId(
                coleta.getId(),
                colaborador.getId()
        );

        ColetaParticipante cp = ColetaParticipante.builder()
                .id(cpId)
                .coleta(coleta)
                .colaborador(colaborador)
                .enviadoEm(LocalDateTime.now())
                .respondidoEm(LocalDateTime.now())
                .build();

        coletaParticipanteRepository.save(cp);




        return new RespostaColetaResponseDto(
                idColeta,
                dto.idColaborador(),
                totalRespondidas,
                "RESPOSTAS_SALVAS"
        );
    }

    // -------------------------------------------------
    // VALIDAÇÃO POR TIPO DE PERGUNTA
    // -------------------------------------------------

    private void validarResposta(Pergunta pergunta, String valor) {

        TipoPergunta tipo = pergunta.getTipo(); // ajusta se o nome do getter estiver diferente

        switch (tipo) {
            case TEXTO -> {
                if (valor == null || valor.isBlank()) {
                    throw new RuntimeException("Resposta de texto não pode ser vazia");
                }
            }
            case LIKERT -> {
                int v;
                try {
                    v = Integer.parseInt(valor);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Resposta LIKERT deve ser numérica (ex: 1..5)");
                }
                if (v < 1 || v > 5) {
                    throw new RuntimeException("Resposta LIKERT fora do intervalo permitido (1..5)");
                }
            }
            case MULTIPLA -> {
                List<PerguntaOpcao> opcoes = perguntaOpcaoRepository.findByPergunta_Id(pergunta.getId());
                boolean existe = opcoes.stream().anyMatch(o ->
                        o.getTextoOpcao().equalsIgnoreCase(valor) ||
                                (o.getValorNum() != null && String.valueOf(o.getValorNum()).equals(valor))
                );
                if (!existe) {
                    throw new RuntimeException("Opção inválida para a pergunta " + pergunta.getId());
                }
            }
            default -> throw new RuntimeException("Tipo de pergunta não suportado: " + tipo);
        }
    }
}
