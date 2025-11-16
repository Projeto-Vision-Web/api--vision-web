package com.visionweb.app_vision_web.domain.contracts.repository;

import com.visionweb.app_vision_web.domain.core.entities.FeedbackProcessado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackProcessadoRepository extends JpaRepository<FeedbackProcessado, Integer> {

    @Query("""
           SELECT f
           FROM FeedbackProcessado f
           WHERE f.resposta.coleta.id = :idColeta
           """)
    List<FeedbackProcessado> findByColetaId(@Param("idColeta") Integer idColeta);

}
