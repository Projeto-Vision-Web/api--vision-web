package com.visionweb.app_vision_web.domain.contracts.service;

import com.visionweb.app_vision_web.application.dto.CadastroDto;
import com.visionweb.app_vision_web.application.dto.LoginDto;
import com.visionweb.app_vision_web.application.dto.TokenDto;

import java.util.concurrent.CompletableFuture;

public interface AutenticacaoService {

   TokenDto autenticar(LoginDto loginDto) throws Exception;

    Boolean cadastrarUsuario(CadastroDto cadastroDto) throws Exception;
}
