package com.visionweb.app_vision_web.domain.contracts.service;

import com.visionweb.app_vision_web.domain.core.entities.Formulario;

import java.util.List;

public interface FormularioService {
    List<Formulario> listarTodos();
    Formulario buscarPorId(Integer id);
    Formulario buscarPorTitulo(String titulo);
    Formulario salvar(Formulario form);
    Formulario atualizar(Integer id, Formulario form);
    void excluir(Integer id);
}
