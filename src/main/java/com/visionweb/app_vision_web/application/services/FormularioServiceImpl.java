package com.visionweb.app_vision_web.application.services;

import com.visionweb.app_vision_web.domain.contracts.repository.FormularioRepository;
import com.visionweb.app_vision_web.domain.contracts.service.FormularioService;
import com.visionweb.app_vision_web.domain.core.entities.Formulario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormularioServiceImpl implements FormularioService {

    private final FormularioRepository formularioRepository;

    public FormularioServiceImpl(FormularioRepository formularioRepository) {
        this.formularioRepository = formularioRepository;
    }

    @Override
    public List<Formulario> listarTodos() {
        return formularioRepository.findAll();
    }

    @Override
    public Formulario buscarPorId(Integer id) {
        return formularioRepository.findById(id).orElse(null);
    }

    @Override
    public Formulario buscarPorTitulo(String titulo) {
       return formularioRepository.findByTitulo(titulo);
    }

    @Override
    public Formulario salvar(Formulario form) {
        return formularioRepository.save(form);
    }

    @Override
    public Formulario atualizar(Integer id, Formulario form) {
        return formularioRepository.save(form);
    }

    @Override
    public void excluir(Integer id) {
        formularioRepository.deleteById(id);
    }
}
