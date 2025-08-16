package kz.musin.gatewayservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.musin.gatewayservice.controller.auth.AuthGatewayController;
import kz.musin.gatewayservice.dto.auth.request.RegisterRequestDto;
import kz.musin.gatewayservice.dto.auth.response.RegisterResponseDto;
import kz.musin.gatewayservice.grpc.auth.AuthGrpcClient;
import kz.musin.proto.auth.RegisterResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Тестирование GateWay связь с Auth-service
 * registration
 */
@WebMvcTest(AuthGatewayController.class)
@AutoConfigureMockMvc
class AuthGatewayControllerIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(AuthGatewayControllerIntegrationTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthGrpcClient authGrpcClient;

    @BeforeEach
    void setUp() {
        // Настройка мока для успешного ответа
        RegisterResponse mockResponse = new RegisterResponse("12345");
        when(authGrpcClient.register(any(RegisterRequestDto.class))).thenReturn(mockResponse);
    }

    /**
     * Тестирование Endpoint регистрации
     * @throws Exception
     */
    @Test
    void testRegisterEndpointSuccess() throws Exception {
        // 1. Подготовка тестовых данных
        RegisterRequestDto requestDto = new RegisterRequestDto();
        requestDto.setUsername("testuser");
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("password123");

        // 2. Выполнение запроса и проверка результата
        mockMvc.perform(post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.userId").value("12345"));

        // 3. Проверка вызова gRPC клиента
        ArgumentCaptor<RegisterRequestDto> captor = ArgumentCaptor.forClass(RegisterRequestDto.class);
        verify(authGrpcClient, times(1)).register(captor.capture());

        RegisterRequestDto capturedRequest = captor.getValue();
        assertEquals("testuser", capturedRequest.getUsername());
        assertEquals("test@example.com", capturedRequest.getEmail());
        assertEquals("password123", capturedRequest.getPassword());

        // 4. Логирование для отладки
        logger.info("Проверено: gRPC клиент получил запрос с username={}, email={}",
                capturedRequest.getUsername(), capturedRequest.getEmail());
    }


    /**
     * Тест для gRPC клиента в GateWay
     */
    @Test
    void testGrpcClientLogging() {
        // 1. Создаем тестовый запрос
        RegisterRequestDto testRequest = new RegisterRequestDto();
        testRequest.setUsername("testuser");
        testRequest.setEmail("test@example.com");
        testRequest.setPassword("password123");

        // 2. Настраиваем мок для логирования
        when(authGrpcClient.register(any(RegisterRequestDto.class)))
                .thenAnswer(invocation -> {
                    RegisterRequestDto arg = invocation.getArgument(0);
                    logger.info("Мокированный AuthGrpcClient.register() вызван с: {}", arg);
                    return new RegisterResponseDto("12345");
                });

        // 3. Вызываем метод
        RegisterResponse response = authGrpcClient.register(testRequest);

        // 4. Проверяем результат
        assertEquals("12345", response.getUserId());
    }
}