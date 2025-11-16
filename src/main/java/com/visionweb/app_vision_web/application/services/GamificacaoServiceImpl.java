package com.visionweb.app_vision_web.application.services;

import com.visionweb.app_vision_web.application.dto.RankingDto;
import com.visionweb.app_vision_web.domain.contracts.repository.ColaboradorRepository;
import com.visionweb.app_vision_web.domain.contracts.repository.GamificacaoEventoRepository;
import com.visionweb.app_vision_web.domain.contracts.repository.GamificacaoPerfilRepository;
import com.visionweb.app_vision_web.domain.contracts.service.GamificacaoService;
import com.visionweb.app_vision_web.domain.core.entities.Colaborador;
import com.visionweb.app_vision_web.domain.core.entities.GamificacaoEvento;
import com.visionweb.app_vision_web.domain.core.entities.GamificacaoPerfil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GamificacaoServiceImpl implements GamificacaoService {

    private final GamificacaoPerfilRepository gamificacaoPerfilRepository;
    private final GamificacaoEventoRepository gamificacaoEventoRepository;
    private final ColaboradorRepository colaboradorRepository;

    @Override
    @Transactional
    public void registrarEventoResposta(Integer idColaborador) {
        // Garante que o colaborador existe
        Colaborador colaborador = colaboradorRepository.findById(idColaborador)
                .orElseThrow(() -> new RuntimeException("Colaborador não encontrado com id " + idColaborador));

        // Busca o perfil de gamificação pelo colaborador (não pelo ID direto)
        GamificacaoPerfil perfil = gamificacaoPerfilRepository.findByColaborador(colaborador)
                .orElseGet(() -> {
                    GamificacaoPerfil novo = GamificacaoPerfil.builder()
                            .colaborador(colaborador)
                            .pontos(0)
                            .nivel(1)
                            .atualizadoEm(LocalDateTime.now())
                            .build();
                    return gamificacaoPerfilRepository.save(novo);
                });

        // Atualiza pontos
        perfil.setPontos(perfil.getPontos() + 10);
        perfil.setAtualizadoEm(LocalDateTime.now());
        gamificacaoPerfilRepository.saveAndFlush(perfil);

        // Registra o evento
        GamificacaoEvento evento = GamificacaoEvento.builder()
                .colaborador(colaborador)
                .tipo("RESPOSTA")
                .pontuacao(10)
                .dataAtribuicao(LocalDateTime.now())
                .build();
        gamificacaoEventoRepository.save(evento);
    }

    @Override
    public List<RankingDto> listarRankingTop10() {
        return gamificacaoPerfilRepository.findTop10ByOrderByPontosDesc()
                .stream()
                .map(p -> {
                    String nome = null;
                    if (p.getColaborador() != null && p.getColaborador().getUsuario() != null) {
                        nome = p.getColaborador().getUsuario().getNome();
                    }
                    return new RankingDto(
                            nome,
                            p.getPontos(),
                            p.getNivel()
                    );
                })
                .toList();
    }
    }


