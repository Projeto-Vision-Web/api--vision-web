package com.visionweb.app_vision_web.domain.contracts.repository;

import com.visionweb.app_vision_web.domain.core.entities.Colaborador;
import com.visionweb.app_vision_web.domain.core.entities.GamificacaoPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface GamificacaoPerfilRepository extends JpaRepository<GamificacaoPerfil, Integer> {
    List<GamificacaoPerfil> findTop10ByOrderByPontosDesc();

    Optional<GamificacaoPerfil> findByColaborador(Colaborador colaborador);

    @Query("SELECT gp FROM GamificacaoPerfil gp " +
            "JOIN FETCH gp.colaborador c " +
            "JOIN FETCH c.usuario u " +
            "ORDER BY gp.pontos DESC")
    List<GamificacaoPerfil> findTop10WithColaboradorAndUsuario(Pageable pageable);

}
