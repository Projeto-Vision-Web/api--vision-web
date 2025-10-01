package com.visionweb.app_vision_web.application.services;

import com.visionweb.app_vision_web.domain.contracts.repository.UsuarioRepository;
import com.visionweb.app_vision_web.domain.contracts.service.UsuarioService;
import com.visionweb.app_vision_web.domain.core.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
}
