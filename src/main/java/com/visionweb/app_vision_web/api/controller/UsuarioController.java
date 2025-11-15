package com.visionweb.app_vision_web.api.controller;

import com.visionweb.app_vision_web.domain.contracts.service.UsuarioService;
import com.visionweb.app_vision_web.domain.core.entities.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> get() {
        return usuarioService.listarTodos();
    }

}
