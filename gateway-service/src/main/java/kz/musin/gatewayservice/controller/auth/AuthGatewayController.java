package kz.musin.gatewayservice.controller.auth;

import jakarta.validation.Valid;
import kz.musin.gatewayservice.dto.auth.request.RegisterRequestDto;
import kz.musin.gatewayservice.dto.auth.request.TokenRequestDto;
import kz.musin.gatewayservice.dto.ApiResponse;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthGatewayController {

    /** gRPC клиент для взаимодействия с Auth-сервисом */
    private final AuthGrpcClient authGrpcClient;
    private static final Logger log = LoggerFactory.getLogger(AuthGatewayController.class);

    /**
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

    /**
     * Регистрация пользователя.
     *
     * <p>Метод принимает регистрационные данные от клиента, передает его Auth-сервису через gRPC.
     * Если регистрация успешна, возвращает uuid id user.
     * Если токен невалидный или gRPC сервис недоступен — возвращает ошибку 401 Unauthorized.</p>
     *
     * @param jsonRequest DTO с токеном пользователя
     * @return ApiResponse с данными токена или ошибкой
     */
    @PostMapping("/register")
    public ApiResponse<?> register(@Valid @RequestBody RegisterRequestDto request) {
        log.info("RestController контроллер GateWay -> RegisterRequestDto:{}", request);
        return ResponseBuilder.success(RegisterResponseDto.fromProto(authGrpcClient.register(request)));
    }

}