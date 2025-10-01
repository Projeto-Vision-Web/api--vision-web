package com.visionweb.app_vision_web.domain.contracts.service;

import com.visionweb.app_vision_web.domain.core.entities.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario salvar(Usuario usuario);
    List<Usuario> listarTodos();
}
