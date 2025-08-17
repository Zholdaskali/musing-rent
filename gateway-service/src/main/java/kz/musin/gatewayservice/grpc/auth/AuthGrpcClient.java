package kz.musin.gatewayservice.grpc.auth;

import kz.musin.gatewayservice.dto.auth.request.RegisterRequestDto;
import kz.musin.proto.auth.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * gRPC клиент для взаимодействия с Auth-сервисом.
 * Оборачивает вызовы AuthServiceGrpc и упрощает работу для Gateway.
 */
@Component
public class AuthGrpcClient {

    /** gRPC stub для синхронных вызовов Auth-сервиса */
    @GrpcClient("auth")
    private AuthServiceGrpc.AuthServiceBlockingStub authStub;
    private static final Logger log = LoggerFactory.getLogger(AuthGrpcClient.class);

    /**
     * Проверяет валидность JWT токена через Auth-микросервис.
     *
     * <p>Метод формирует TokenRequest и отправляет его на Auth-сервис.
     * Возвращает TokenResponse с информацией о токене.</p>
     *
     * @param token JWT токен пользователя
     * @return TokenResponse с результатом проверки токена
     * @throws io.grpc.StatusRuntimeException если Auth-сервис недоступен или произошла ошибка
     */
    public TokenResponse validateToken(String token) {
        // TODO: Рассмотреть добавление таймаута через withDeadlineAfter для защиты Gateway
        return authStub.validateToken(
                TokenRequest.newBuilder()
                        .setToken(token)
                        .build()
        );
    }

    /**
     * Проверяет валидность JWT токена через Auth-микросервис.
     *
     * <p>Метод формирует TokenRequest и отправляет его на Auth-сервис.
     * Возвращает TokenResponse с информацией о токене.</p>
     *
     * @param request JWT токен пользователя
     * @return RegisterResponseDto с результатом проверки токена
     * @throws io.grpc.StatusRuntimeException если Auth-сервис недоступен или произошла ошибка
     */
    public RegisterResponse register(RegisterRequestDto request) {
        log.info("Начало регистрации RegisterRequestDto:{}", request);

        RegisterRequest protoRequest = RegisterRequest.newBuilder()
                .setUserName(request.getUsername())
                .setEmail(request.getEmail())
                .setPassword(request.getPassword())
                .build();

        return authStub.register(protoRequest);
    }
}