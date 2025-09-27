package kz.musin.gatewayservice.exception.handler;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import kz.musin.gatewayservice.util.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<?> handleGrpcException(StatusRuntimeException ex) {
        // Логируем полную ошибку для разработчиков
        log.error("gRPC error: {}", ex.getMessage(), ex);

        HttpStatus httpStatus = convertGrpcToHttpStatus(ex.getStatus().getCode());
        String clientMessage = getClientFriendlyMessage(ex);

        return ResponseEntity
                .status(httpStatus)
                .body(ResponseBuilder.error(httpStatus, clientMessage));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.warn("Validation error: {}", errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseBuilder.badRequest(errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        // Логируем полную ошибку для разработчиков
        log.error("Internal error: {}", ex.getMessage(), ex);

        // ⚠️ СОХРАНИЛ ТВОЮ ЛОГИКУ: возвращаем сообщение ошибки клиенту
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseBuilder.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    // ==================================
    // 🔧 Вспомогательные методы
    // ==================================

    private HttpStatus convertGrpcToHttpStatus(Status.Code grpcCode) {
        return switch (grpcCode) {
            case OK -> HttpStatus.OK;
            case INVALID_ARGUMENT, FAILED_PRECONDITION, OUT_OF_RANGE -> HttpStatus.BAD_REQUEST;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case PERMISSION_DENIED -> HttpStatus.FORBIDDEN;
            case UNAUTHENTICATED -> HttpStatus.UNAUTHORIZED;
            case UNAVAILABLE -> HttpStatus.SERVICE_UNAVAILABLE;
            case RESOURCE_EXHAUSTED -> HttpStatus.TOO_MANY_REQUESTS;
            case UNIMPLEMENTED -> HttpStatus.NOT_IMPLEMENTED;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    /**
     * Понятные сообщения для клиента вместо технических gRPC ошибок
     */
    private String getClientFriendlyMessage(StatusRuntimeException ex) {
        return switch (ex.getStatus().getCode()) {
            case UNAVAILABLE -> "Сервис временно недоступен";
            case NOT_FOUND -> "Запрашиваемый ресурс не найден";
            case ALREADY_EXISTS -> "Ресурс уже существует";
            case INVALID_ARGUMENT -> "Неверные параметры запроса";
            case PERMISSION_DENIED -> "Доступ запрещен";
            case UNAUTHENTICATED -> "Требуется авторизация";
            case UNIMPLEMENTED -> "Функционал временно недоступен";
            default -> "Внутренняя ошибка сервера";
        };
    }
}