package com.visionweb.app_vision_web.api.controller;

import com.visionweb.app_vision_web.application.dto.CadastroDto;
import com.visionweb.app_vision_web.domain.contracts.service.AutenticacaoService;
import com.visionweb.app_vision_web.domain.core.entities.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AutenticacaoService autenticacaoService;

    public LoginController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

//    @PostMapping("/registrar")
//    public ResponseEntity<?> registrar(@RequestBody CadastroDto cadastroDto) throws Exception {
//        try{
//            autenticacaoService.cadastrarUsuario(cadastroDto);
//            return ResponseEntity
//                                .status(201) // Created
//                    .body(new ApiResponse<>(cadastroDto, "Usuário criado com sucesso!"));
//
//        }
//        return
//    }
}
