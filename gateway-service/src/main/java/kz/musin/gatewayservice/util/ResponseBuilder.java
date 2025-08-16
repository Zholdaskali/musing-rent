package kz.musin.gatewayservice.util;

import kz.musin.gatewayservice.dto.ApiResponse;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * 🔹 Утилитарный класс для построения единых ответов API
 *
 * Используйте этот класс в Gateway для формирования одинаковой структуры
 * ответов для всех клиентов (REST API).
 */
public class ResponseBuilder {

    // ==================================
    // ✅ Успешные ответы
    // ==================================

    /**
     * 200 OK — стандартный успешный ответ с телом
     */
    public static <T> ApiResponse<T> success(T data) {
        return build(data, HttpStatus.OK, true);
    }

    /**
     * Успешный ответ с кастомным HttpStatus
     */
    public static <T> ApiResponse<T> success(T data, HttpStatus status) {
        return build(data, status, true);
    }

    /**
     * 201 Created — объект успешно создан
     */
    public static <T> ApiResponse<T> created(T data) {
        return build(data, HttpStatus.CREATED, true);
    }

    /**
     * 202 Accepted — для асинхронных операций, которые обрабатываются позже
     */
    public static <T> ApiResponse<T> accepted(T data) {
        return build(data, HttpStatus.ACCEPTED, true);
    }

    /**
     * 204 No Content — операция успешна, тело ответа отсутствует
     */
    public static ApiResponse<Void> noContent() {
        return build(null, HttpStatus.NO_CONTENT, true);
    }

    // ==================================
    // ❌ Ошибочные ответы
    // ==================================

    /**
     * Универсальный метод для всех ошибок
     */
    public static ApiResponse<?> error(HttpStatus status, String message) {
        return build(message, status, false);
    }

    public static ApiResponse<?> badRequest(String message) {
        return error(HttpStatus.BAD_REQUEST, message);
    }

    public static ApiResponse<?> unauthorized(String message) {
        return error(HttpStatus.UNAUTHORIZED, message);
    }

    public static ApiResponse<?> forbidden(String message) {
        return error(HttpStatus.FORBIDDEN, message);
    }

    public static ApiResponse<?> notFound(String message) {
        return error(HttpStatus.NOT_FOUND, message);
    }

    public static ApiResponse<?> conflict(String message) {
        return error(HttpStatus.CONFLICT, message);
    }

    public static ApiResponse<?> internalError(String message) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    // ==================================
    // 🔹 Внутренний метод построения
    // ==================================

    private static <T> ApiResponse<T> build(T data, HttpStatus status, boolean success) {
        return ApiResponse.<T>builder()
                .success(success)
                .code(status.value())
                .message(status.getReasonPhrase())
                .data(data)
                .timestamp(Instant.now())
                .build();
    }
}
