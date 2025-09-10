package kz.musin.gatewayservice.grpc.auth;

import kz.musin.gatewayservice.dto.auth.request.LoginRequestDto;
import kz.musin.gatewayservice.dto.auth.request.RefreshTokenRequestDto;
import kz.musin.gatewayservice.dto.auth.request.RegisterRequestDto;
import kz.musin.gatewayservice.util.ValidationUtils;
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
     * <p>Метод формирует protoRequest и отправляет его на Auth-сервис.
     * Возвращает RegisterResponse с информацией о регистрацией.</p>
     *
     * @param request регистрационные данные пользователя
     * @return RegisterResponse с результатом проверки токена
     * @throws io.grpc.StatusRuntimeException если Auth-сервис недоступен или произошла ошибка
     */
    public RegisterResponse register(RegisterRequestDto request) {
        log.info("Начало регистрации RegisterRequestDto:{}", request);

        ValidationUtils.validate(request);

        RegisterRequest protoRequest = RegisterRequest.newBuilder()
                .setUserName(request.getUsername())
                .setEmail(request.getEmail())
                .setPassword(request.getPassword())
                .build();

        return authStub.register(protoRequest);
    }

    /**
     * <p>Метод формирует LoginRequest и отправляет его на Auth-сервис.
     * Возвращает LoginResponse с информацией о токене.</p>
     *
     * @param request Данные пользователя для входа
     * @return LoginResponse с результатом проверки токена
     * @throws io.grpc.StatusRuntimeException если Auth-сервис недоступен или произошла ошибка
     */
    public LoginResponse login(LoginRequestDto request) {
        log.info("Начало регистрации RegisterRequestDto:{}", request);

        LoginRequest loginRequest = LoginRequest.newBuilder()
                .setEmail(request.getEmail())
                .setPassword(request.getPassword())
                .build();
        return authStub.login(loginRequest);
    }

    public LoginResponse refreshToken(RefreshTokenRequestDto request) {
        log.info("Начало обновление токена");

        RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.newBuilder()
                .setRefreshToken(request.getRefreshToken())
                .build();
        return authStub.refreshToken(refreshTokenRequest);
    }
}