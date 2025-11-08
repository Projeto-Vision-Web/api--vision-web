package com.visionweb.app_vision_web.domain.contracts.repository;

import com.visionweb.app_vision_web.domain.core.entities.Formulario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormularioRepository extends JpaRepository<Formulario,Integer> {

    public Formulario findByTitulo(String titulo);

    @Query("SELECT f FROM Formulario f WHERE f.empresa.id_empresa = :idEmpresa")
    List<Formulario> findByEmpresa(@Param("idEmpresa") Integer idEmpresa);

}
