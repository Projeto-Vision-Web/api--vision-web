package com.visionweb.app_vision_web.application.services;

import com.visionweb.app_vision_web.application.dto.CadastroDto;
import com.visionweb.app_vision_web.application.dto.LoginDto;
import com.visionweb.app_vision_web.application.dto.TokenDto;
import com.visionweb.app_vision_web.application.mapper.UsuarioMapper;
import com.visionweb.app_vision_web.domain.contracts.repository.LoginRepository;
import com.visionweb.app_vision_web.domain.contracts.repository.UsuarioRepository;
import com.visionweb.app_vision_web.domain.contracts.service.AutenticacaoService;
import com.visionweb.app_vision_web.domain.core.entities.Login;
import com.visionweb.app_vision_web.domain.dominios.EncriptadorSenha;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {

    private final UsuarioRepository usuarioRepository;

    private final LoginRepository loginRepository;

    public AutenticacaoServiceImpl(UsuarioRepository usuarioRepository, LoginRepository loginRepository) {
        this.usuarioRepository = usuarioRepository;
        this.loginRepository = loginRepository;
    }

    @Override
    public TokenDto autenticar(LoginDto loginDto) throws Exception{
        return null;
    }

    @Override
    public Boolean cadastrarUsuario(CadastroDto cadastroDto) throws Exception{
        var loginExistente = loginRepository.findByEmail(cadastroDto.getEmail());

        if (loginExistente){
            throw new Exception("E-mail já cadastrado!!");
        }

        var usuario = UsuarioMapper.fromCadastroDto(cadastroDto);

        var usuarioInserido  = usuarioRepository.save(usuario);

        var senhaEncriptada = EncriptadorSenha.hashPassword(cadastroDto.getSenha());

        var login = new Login(cadastroDto.getEmail(), senhaEncriptada, usuarioInserido);

        var loginInserido = loginRepository.save(login);

        if (loginInserido != null){
            return true;
        }

        return false;
    }
}
