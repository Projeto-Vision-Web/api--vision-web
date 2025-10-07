package com.visionweb.app_vision_web.domain.contracts.service;

import com.visionweb.app_vision_web.application.dto.LoginDto;
import com.visionweb.app_vision_web.application.dto.TokenDto;

public interface GeradorTokenJwtService {

    TokenDto gerarToken(LoginDto loginDto) throws Exception;
}
