package com.visionweb.app_vision_web.domain.dominios;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

public class ValidadorSenha {

    public static boolean verificarSenha(String senhaDigitada, String senhaHashArmazenada) {
        try {
            // Divide em duas partes: salt e hash
            String[] partes = senhaHashArmazenada.split(":");
            if (partes.length != 2) {
                return false;
            }

            byte[] salt = Base64.getDecoder().decode(partes[0]);
            byte[] hashArmazenado = Base64.getDecoder().decode(partes[1]);

            // Recalcula o hash com a senha digitada e o mesmo salt
            PBEKeySpec spec = new PBEKeySpec(senhaDigitada.toCharArray(), salt, 100_000, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hashGerado = factory.generateSecret(spec).getEncoded();

            // Compara os dois arrays de forma segura
            return Arrays.equals(hashArmazenado, hashGerado);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Erro ao verificar senha", e);
        }
    }
}
