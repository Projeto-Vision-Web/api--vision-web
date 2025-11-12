package com.visionweb.app_vision_web.api.controller;

import com.visionweb.app_vision_web.application.dto.CadastroDto;
import com.visionweb.app_vision_web.application.dto.LoginDto;
import com.visionweb.app_vision_web.application.dto.TokenDto;
import com.visionweb.app_vision_web.domain.contracts.service.AutenticacaoService;
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


    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody CadastroDto cadastroDto) throws Exception {
        try {
            var result = autenticacaoService.cadastrarUsuario(cadastroDto);

            if (result != null) {
                return ResponseEntity
                        .status(201) // HTTP 201 Created
                        .body("Usuário criado com sucesso!");
            }

            return ResponseEntity
                    .status(400)
                    .body("Não foi possível criar um cadastro para o usuário.");
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping("/logar")
    public ResponseEntity<?> registrar(@RequestBody LoginDto loginDto) throws Exception {
        try {
                TokenDto tokenDto = autenticacaoService.autenticar(loginDto);

                if (tokenDto != null && tokenDto.getToken() != null) {
                    return ResponseEntity.ok(tokenDto);
                } else {
                    // Falha na autenticação
                    return ResponseEntity.status(400)
                            .body("Não foi possível autenticar o usuário.");
                }

            } catch (Exception ex) {
                // Erro inesperado
                return ResponseEntity.status(500)
                        .body("Erro interno: " + ex.getMessage());
            }
        }

}
