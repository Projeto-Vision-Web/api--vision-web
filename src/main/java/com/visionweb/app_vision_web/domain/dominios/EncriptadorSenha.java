package com.visionweb.app_vision_web.domain.dominios;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class EncriptadorSenha {

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();


    public static String hashPassword(String password) {
        return encoder.encode(password);
    }
}
