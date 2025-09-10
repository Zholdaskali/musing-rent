package kz.musin.gatewayservice.controller.auth;

import jakarta.validation.Valid;
import kz.musin.gatewayservice.dto.auth.request.LoginRequestDto;
import kz.musin.gatewayservice.dto.auth.request.RefreshTokenRequestDto;
import kz.musin.gatewayservice.dto.auth.request.RegisterRequestDto;
import kz.musin.gatewayservice.dto.auth.request.TokenRequestDto;
import kz.musin.gatewayservice.dto.ApiResponse;
import kz.musin.gatewayservice.dto.auth.response.LoginResponseDto;
import kz.musin.gatewayservice.dto.auth.response.RegisterResponseDto;
import kz.musin.gatewayservice.dto.auth.response.TokenResponseDto;
import kz.musin.gatewayservice.grpc.auth.AuthGrpcClient;
import kz.musin.gatewayservice.util.ResponseBuilder;
import kz.musin.proto.auth.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с авторизацией через Gateway.
 * Обрабатывает запросы клиентов и делегирует проверку токена Auth-сервису через gRPC.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthGatewayController {

    /** gRPC клиент для взаимодействия с Auth-сервисом */
    private final AuthGrpcClient authGrpcClient;
    private static final Logger log = LoggerFactory.getLogger(AuthGatewayController.class);

    /**
     * Тестовый метод
     * Валидирует JWT токен пользователя.
     *
     * <p>Метод принимает токен от клиента, передает его Auth-сервису через gRPC.
     * Если токен валиден, возвращает данные токена.
     * Если токен невалидный или gRPC сервис недоступен — возвращает ошибку 401 Unauthorized.</p>
     *
     * @param request DTO с токеном пользователя
     * @return ApiResponse с данными токена или ошибкой
     */
    @PostMapping("/validate-token")
    public ApiResponse<?> validateToken(@Valid @RequestBody TokenRequestDto request) {
        TokenResponse response = authGrpcClient.validateToken(request.getToken());
        if (response.getValid()) {
            return ResponseBuilder.success(TokenResponseDto.fromProto(response));
        } else {
            return ResponseBuilder.unauthorized("Invalid Token");
        }
    }

    @PostMapping("/validate-token2")
    public ApiResponse<?> validateToken2(@Valid @RequestBody TokenRequestDto request) {
        TokenResponse response = authGrpcClient.validateToken(request.getToken());
        if (response.getValid()) {
            return ResponseBuilder.success(TokenResponseDto.fromProto(response));
        } else {
            return ResponseBuilder.unauthorized("Invalid Token");
        }
    }


    /**
     * Регистрация пользователя.
     *
     * <p>Метод принимает регистрационные данные от клиента, передает его Auth-сервису через gRPC.
     * Если регистрация успешна, возвращает uuid id user.
     * Если токен невалидный или gRPC сервис недоступен — возвращает ошибку 401 Unauthorized.</p>
     *
     * @param request DTO с токеном пользователя
     * @return ApiResponse с данными токена или ошибкой
     */
    @PostMapping("/register")
    public ApiResponse<?> register(@Valid @RequestBody RegisterRequestDto request) {
        log.info("RestController контроллер GateWay -> RegisterRequestDto:{}", request);
        return ResponseBuilder.success(RegisterResponseDto.fromProto(authGrpcClient.register(request)));
    }

    /**
     * Логинизация пользователя.
     *
     * <p>Метод принимает данные для входа от клиента, передает его Auth-сервису через gRPC.
     * Если вход успешна, возвращает данные user и 2 вида токена.</p>
     *
     * @param request DTO с токеном пользователя
     * @return ApiResponse с данными токена или ошибкой
     */
    @PostMapping("/login")
    public ApiResponse<?> login(@Valid @RequestBody LoginRequestDto request) {
        log.info("RestController контроллер GateWay -> LoginRequestDto:{}", request);
        return ResponseBuilder.success(LoginResponseDto.fromProto(authGrpcClient.login(request)));
    }


    /**
     * Обновление токена возвращает новый подписанный jwt и refresh token
     *
     * @param request
     * @return ApiResponse с данными
     */
    @PostMapping("/refresh-token")
    public ApiResponse<?> refreshToken(@Valid @RequestBody RefreshTokenRequestDto request) {
        log.info("RestController контроллер GateWay -> RefreshTokenRequestDto");
        return ResponseBuilder.success(LoginResponseDto.fromProto(authGrpcClient.refreshToken(request)));
    }

}