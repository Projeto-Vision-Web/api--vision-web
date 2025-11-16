package com.visionweb.app_vision_web.domain.contracts.repository;

import com.visionweb.app_vision_web.domain.core.entities.Coleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColetaRepository extends JpaRepository<Coleta,Integer> {

    @Query("""
           SELECT c 
           FROM Coleta c
           WHERE c.empresa.id_empresa = :empresaId
             AND c.periodoRef = :periodoRef
           """)
    Optional<Coleta> findByEmpresaAndPeriodoRef(@Param("empresaId") Integer empresaId,
                                                @Param("periodoRef") String periodoRef);

}
