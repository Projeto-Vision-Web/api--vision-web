package com.visionweb.app_vision_web.domain.contracts.repository;

import com.visionweb.app_vision_web.domain.core.entities.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Colaborador c WHERE c.usuario.id_usuario = :idUsuario")
    boolean existsByUsuarioIdUsuario(@Param("idUsuario") int idUsuario);

    @Query("""
           SELECT COUNT(c)
           FROM Colaborador c
           WHERE c.empresa.id_empresa = :empresaId
           """)
    long countByEmpresa(@Param("empresaId") Integer empresaId);

}
