package com.visionweb.app_vision_web.domain.contracts.repository;

import com.visionweb.app_vision_web.domain.core.entities.PerguntaOpcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerguntaOpcaoRepository extends JpaRepository<PerguntaOpcao,Integer> {

    List<PerguntaOpcao> findByPergunta_Id(Integer id);
}
