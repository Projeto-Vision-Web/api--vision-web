package com.visionweb.app_vision_web.domain.contracts.repository;

import com.visionweb.app_vision_web.domain.core.entities.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta,Integer> {
}
