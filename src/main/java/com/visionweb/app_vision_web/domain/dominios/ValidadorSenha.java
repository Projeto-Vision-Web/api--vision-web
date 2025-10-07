package com.visionweb.app_vision_web.domain.dominios;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class ValidadorSenha {

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();


    public static boolean verificarSenha(String senhaDigitada, String senhaHashArmazenada) {
        return encoder.matches(senhaDigitada, senhaHashArmazenada);
    }
}
