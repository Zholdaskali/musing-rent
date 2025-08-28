package kz.musin.authservice.config;

import kz.musin.authservice.util.encoder.BCryptPasswordEncoder;
import kz.musin.authservice.util.jwt.JwtGenerate;
import kz.musin.authservice.util.jwt.KeyUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyPair;
import java.security.PrivateKey;

@Configuration
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public KeyPair keyPair() {
        return KeyUtil.generateRsaKey(); // создаём пару ключей один раз
    }

    @Bean
    public JwtGenerate jwtGenerate(KeyPair keyPair) {
        return new JwtGenerate(keyPair.getPrivate()); // передаём приватный ключ
    }

}
