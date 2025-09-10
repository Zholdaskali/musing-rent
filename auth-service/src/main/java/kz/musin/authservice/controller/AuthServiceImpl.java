package kz.musin.authservice.controller;

import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.micrometer.common.util.StringUtils;
import kz.musin.authservice.exception.AuthenticationException;
import kz.musin.authservice.exception.UserNotFoundException;
import kz.musin.authservice.model.entity.RefreshToken;
import kz.musin.authservice.model.entity.User;
import kz.musin.authservice.repository.RefreshTokenRepository;
import kz.musin.authservice.repository.UserRepository;
import kz.musin.authservice.util.encoder.PasswordEncoder;
import kz.musin.authservice.util.jwt.JwtGenerate;
import kz.musin.proto.auth.*;
import kz.musin.proto.auth.AuthServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtGenerate jwtGenerate;
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Тестирование grpc подключения
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void validateToken(TokenRequest request, StreamObserver<TokenResponse> responseObserver) {
        String token = request.getToken();

        // Простейшая проверка токена (для примера)
        boolean valid = token != null && token.startsWith("jwt-");
        String userId = valid ? "user123" : "";

        TokenResponse response = TokenResponse.newBuilder()
                .setValid(valid)
                .setUserId(userId)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * Аутентификация пользователя в системе
     *
     * @param request Запрос с учетными данными
     * @param responseObserver Потоковый наблюдатель для ответа
     */
    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        try {
            // 1. Валидация входных данных
            validateLoginRequest(request);

            // 2. Поиск пользователя
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

            // 3. Проверка пароля
            if (!passwordEncoder.check(request.getPassword(), user.getPasswordHash())) {
                throw new AuthenticationException("Не верный пароль");
            }

            // 4. Генерация токенов
            // TokenPair tokenPair = tokenService.generateTokenPair(user);

            String token = jwtGenerate.generateToken(user, "ADMIN");

            // 5. Построение ответа
            LoginResponse response = buildLoginResponse(user, token);

            // 6. Отправка ответа
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            log.error("Login failed: {}", e.getMessage());
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        }
    }

    // Вспомогательные методы

    private void validateLoginRequest(LoginRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        if (StringUtils.isBlank(request.getEmail())) {
            throw new IllegalArgumentException("Email is required");
        }
        if (StringUtils.isBlank(request.getPassword())) {
            throw new IllegalArgumentException("Password is required");
        }
    }

    private LoginResponse buildLoginResponse(User user, String token) {

        String refreshToken = createRefreshToken(user);

        return LoginResponse.newBuilder()
                .setAccessToken(token)
                .setRefreshToken(refreshToken)
                .build();
    }

    private String createRefreshToken(User user) {
        try {
            String refreshToken = UUID.randomUUID().toString();
            UUID userId = user.getId();
            RefreshToken refreshTokenEntity = new RefreshToken(userId, refreshToken);
            refreshTokenRepository.save(refreshTokenEntity);
            return refreshToken;
        } catch (RuntimeException e) {
            throw new RuntimeException(e + " Ошибка при создании токена обновления");
        }
    }


    /**
     * Регистрация пользователя в систему
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
        log.info("Микросервис Auth-service Начало регистрации");
        try {

            User user = new User(
                    request.getUserName(),
                    request.getEmail(),
                    passwordEncoder.hash(request.getPassword()),
                    Instant.now(),
                    Instant.now()
            );

            log.info("Сохранение user -> userRepository");
            userRepository.save(user);
            RegisterResponse response = RegisterResponse.newBuilder()
                    .setUserId(user.getId().toString())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                    .withDescription("Ошибка при регистрации пользователя: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException()
            );

            log.error(e.getMessage());
        }
    }


    /**
     *  Обновление токена
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void refreshToken(RefreshTokenRequest request, StreamObserver<LoginResponse> responseObserver) {
        log.info("Микросервис Auth-service Начало обновления токена");

        try {
            String refreshToken = request.getRefreshToken();

            RefreshToken refreshToken1 = refreshTokenRepository.findRefreshTokenByToken(refreshToken).orElseThrow(() -> new RuntimeException("RefreshToken not found"));

            refreshTokenRepository.delete(refreshToken1);

            User user = userRepository.findById(refreshToken1.getAuthId()).orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtGenerate.generateToken(user, "ADMIN");

            // 5. Построение ответа
            LoginResponse response = buildLoginResponse(user, token);

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (RuntimeException e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription("Ошибка при обновлении токена: " + e.getMessage())
                            .withCause(e)
                            .asRuntimeException()
            );

            log.error(e.getMessage());
        }

    }
}