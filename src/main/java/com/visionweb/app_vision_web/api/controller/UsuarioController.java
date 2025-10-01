package com.visionweb.app_vision_web.api.controller;

import com.visionweb.app_vision_web.domain.contracts.service.UsuarioService;
import com.visionweb.app_vision_web.domain.core.entities.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public Usuario post(@RequestBody Usuario usuario) {
        try{
            var result = usuarioService.salvar(usuario);
            return result;
        }
        catch (Exception ex){
            log.error("Exception: "+ ex.getMessage());
            return null;
        }
    }
}
