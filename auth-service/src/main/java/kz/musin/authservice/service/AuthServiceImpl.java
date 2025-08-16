package kz.musin.authservice.service;

import io.grpc.stub.StreamObserver;
import kz.musin.authservice.entity.User;
import kz.musin.authservice.repository.UserRepository;
import kz.musin.authservice.util.encoder.PasswordEncoder;
import kz.musin.proto.auth.*;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.Instant;

@GrpcService
@RequiredArgsConstructor
public class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);


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
     * Регистрация пользователя в систему
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
        log.info("Микросервис Auth-service Начало регистрации");
        try {

            Timestamp sqlNow = Timestamp.from(Instant.now());

            User user = new User(
                    request.getUserName(),
                    request.getEmail(),
                    passwordEncoder.hash(request.getPassword()),
                    sqlNow,
                    sqlNow
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
}