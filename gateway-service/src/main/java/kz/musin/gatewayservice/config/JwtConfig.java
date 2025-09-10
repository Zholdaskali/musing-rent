package kz.musin.gatewayservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

@Configuration
public class JwtConfig {

    @Bean
    public ReactiveJwtDecoder jwtDecoder(@Value("${auth-service.jwks-uri}") String jwksUri) {
        return NimbusReactiveJwtDecoder.withJwkSetUri(jwksUri).build();
    }
}
