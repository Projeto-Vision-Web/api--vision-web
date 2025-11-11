package com.visionweb.app_vision_web.application.services;

import com.visionweb.app_vision_web.application.dto.PerguntaCreateDto;
import com.visionweb.app_vision_web.application.dto.PerguntaOpcaoResponseDto;
import com.visionweb.app_vision_web.application.dto.PerguntaResponseDto;
import com.visionweb.app_vision_web.domain.contracts.repository.FormularioRepository;
import com.visionweb.app_vision_web.domain.contracts.repository.PerguntaRepository;
import com.visionweb.app_vision_web.domain.core.entities.Enum.TipoPergunta;
import com.visionweb.app_vision_web.domain.core.entities.Formulario;
import com.visionweb.app_vision_web.domain.core.entities.Pergunta;
import com.visionweb.app_vision_web.domain.core.entities.PerguntaOpcao;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerguntaServiceImpl {

    private final PerguntaRepository perguntaRepository;
    private final FormularioRepository formularioRepository;

    public PerguntaServiceImpl(PerguntaRepository perguntaRepository, FormularioRepository formularioRepository) {
        this.perguntaRepository = perguntaRepository;
        this.formularioRepository = formularioRepository;
    }

    @Transactional
    public Pergunta criar(PerguntaCreateDto dto) {
        Formulario form = formularioRepository.findById(dto.idFormulario())
                .orElseThrow(() -> new RuntimeException("Formulário não encontrado"));

        Pergunta pergunta = Pergunta.builder()
                .formulario(form)
                .textoPergunta(dto.textoPergunta())
                .tipo(TipoPergunta.valueOf(dto.tipo()))
                .build();

        if (dto.opcoes() != null && !dto.opcoes().isEmpty()) {
            List<PerguntaOpcao> opcoes = dto.opcoes().stream()
                    .map(o -> PerguntaOpcao.builder()
                            .pergunta(pergunta)
                            .textoOpcao(o.textoOpcao())
                            .valorNum(o.valorNum())
                            .build())
                    .toList();
            pergunta.setOpcoes(opcoes);
        }

        return perguntaRepository.save(pergunta);
    }

    public List<PerguntaResponseDto> listarPorFormulario(int idFormulario) {
        var perguntas =  perguntaRepository.findByFormulario_Id(idFormulario);

        var listaPerguntas = perguntas.stream()
                .map(p -> new PerguntaResponseDto(
                        p.getId(),
                        p.getTextoPergunta(),
                        p.getTipo().name(),
                        p.getFormulario().getId(),
                        p.getOpcoes() != null
                                ? p.getOpcoes().stream()
                                .map(o -> new PerguntaOpcaoResponseDto(o.getId(), o.getTextoOpcao(), o.getValorNum()))
                                .toList()
                                : null
                ))
                .toList();

        return listaPerguntas;
    }
}
