package kz.musin.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    // -------------------------
    // Public endpoints
    // -------------------------

    /**
     * Публичные API
     * @param http
     * @return
     */
    @Bean
    @Order(1)
    public SecurityWebFilterChain publicEndpoints(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .securityMatcher(ServerWebExchangeMatchers.pathMatchers(
                        "/webjars/swagger-ui/index.html#/",
                        "/api-docs/**",
                        "/v3/api-docs/**",
                        "/webjars/swagger-ui/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**", // И ЭТУ!
                        "/api/v1/auth/**",
                        "/public/**",
                        "/.well-known/jwks.json",
                        "/actuator/**"
                ))
                .authorizeExchange(ex -> ex.anyExchange().permitAll())
                .build();
    }

    // -------------------------
    // Secured endpoints
    // -------------------------

    /**
     * Приватные API проверка jwt токена
     * @param http
     * @return
     */
    @Bean
    @Order(2)
    public SecurityWebFilterChain securedEndpoints(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter()))
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((exchange, e) -> {
                            var response = exchange.getResponse();
                            response.setRawStatusCode(401);
                            var buffer = response.bufferFactory()
                                    .wrap("{\"error\":\"Неавторизован\"}".getBytes(StandardCharsets.UTF_8));
                            return response.writeWith(Mono.just(buffer));
                        })
                        .accessDeniedHandler((exchange, e) -> {
                            var response = exchange.getResponse();
                            response.setRawStatusCode(403);
                            var buffer = response.bufferFactory()
                                    .wrap("{\"error\":\"Доступ запрещён\"}".getBytes(StandardCharsets.UTF_8));
                            return response.writeWith(Mono.just(buffer));
                        })
                )
                .build();
    }

    // -------------------------
    // Конвертер JWT -> Authentication с ролями
    // -------------------------
    @Bean
    public Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthConverter() {
        return jwt -> {
            String role = jwt.getClaimAsString("role");
            Collection<GrantedAuthority> authorities = Collections.emptyList();
            if (role != null && !role.isBlank()) {
                authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
            }
            return Mono.just(new JwtAuthenticationToken(jwt, authorities));
        };
    }
}