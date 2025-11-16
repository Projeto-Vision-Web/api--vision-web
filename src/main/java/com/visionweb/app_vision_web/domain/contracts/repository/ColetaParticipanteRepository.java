package com.visionweb.app_vision_web.domain.contracts.repository;

import com.visionweb.app_vision_web.domain.core.entities.ColetaParticipante;
import com.visionweb.app_vision_web.domain.core.entities.ColetaParticipanteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ColetaParticipanteRepository extends JpaRepository<ColetaParticipante, ColetaParticipanteId> {

    @Query("""
           SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END 
           FROM ColetaParticipante c 
           WHERE c.coleta.id = :idColeta 
             AND c.colaborador.id = :idColaborador
           """)
    boolean existsByColeta_IdAndColaborador_Id(@Param("idColeta") int idColeta,
                                               @Param("idColaborador") int idColaborador);
}

