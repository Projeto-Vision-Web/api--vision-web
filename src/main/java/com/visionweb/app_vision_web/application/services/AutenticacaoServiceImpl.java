package com.visionweb.app_vision_web.application.services;

import com.visionweb.app_vision_web.application.dto.CadastroDto;
import com.visionweb.app_vision_web.application.dto.LoginDto;
import com.visionweb.app_vision_web.application.dto.TokenDto;
import com.visionweb.app_vision_web.application.mapper.UsuarioMapper;
import com.visionweb.app_vision_web.domain.contracts.repository.LoginRepository;
import com.visionweb.app_vision_web.domain.contracts.repository.UsuarioRepository;
import com.visionweb.app_vision_web.domain.contracts.service.AutenticacaoService;
import com.visionweb.app_vision_web.domain.contracts.service.GeradorTokenJwtService;
import com.visionweb.app_vision_web.domain.core.entities.Login;
import com.visionweb.app_vision_web.domain.dominios.EncriptadorSenha;
import com.visionweb.app_vision_web.domain.dominios.ValidadorSenha;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {

    private final UsuarioRepository usuarioRepository;

    private final LoginRepository loginRepository;

    private final GeradorTokenJwtService  geradorTokenJwtService;

    public AutenticacaoServiceImpl(UsuarioRepository usuarioRepository, LoginRepository loginRepository, GeradorTokenJwtService geradorTokenJwtService) {
        this.usuarioRepository = usuarioRepository;
        this.loginRepository = loginRepository;
        this.geradorTokenJwtService = geradorTokenJwtService;
    }

    @Override
    public TokenDto autenticar(LoginDto loginDto) throws Exception{
        Optional<Login> loginExistente = loginRepository.findByEmail(loginDto.getEmail());

        var senhaValida = ValidadorSenha.verificarSenha(loginDto.getSenha(), loginExistente.get().getSenha());

        if (loginExistente.isPresent() && senhaValida){
            return geradorTokenJwtService.gerarToken(loginDto);
        }
        else{
            throw new Exception("E-mail ou senha incorretos");
        }

    }

    @Override
    public Boolean cadastrarUsuario(CadastroDto cadastroDto) throws Exception{
        Optional<Login> loginExistente = loginRepository.findByEmail(cadastroDto.getEmail());

        if (loginExistente.isPresent()){
            throw new Exception("E-mail: " + loginExistente.get().getEmail() + "já cadastrado!!");
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
