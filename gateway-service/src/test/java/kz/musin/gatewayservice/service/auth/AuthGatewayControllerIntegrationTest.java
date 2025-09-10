//package kz.musin.gatewayservice.service.auth;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import kz.musin.gatewayservice.controller.auth.AuthGatewayController;
//import kz.musin.gatewayservice.dto.auth.request.RegisterRequestDto;
//import kz.musin.gatewayservice.grpc.auth.AuthGrpcClient;
//import kz.musin.proto.auth.RegisterResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.reactive.server.WebTestClient;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@WebFluxTest(AuthGatewayController.class)
//class AuthGatewayControllerIntegrationTest {
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthGatewayControllerIntegrationTest.class);
//
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @MockBean
//    private AuthGrpcClient authGrpcClient;
//
//    @BeforeEach
//    void setUp() {
//        RegisterResponse mockResponse = RegisterResponse.newBuilder().setUserId("12345").build();
//        when(authGrpcClient.register(any(RegisterRequestDto.class))).thenReturn(mockResponse);
//    }
//
//    @Test
//    void testRegisterEndpointSuccess() throws Exception {
//        RegisterRequestDto requestDto = new RegisterRequestDto();
//        requestDto.setUsername("testuser");
//        requestDto.setEmail("test@example.com");
//        requestDto.setPassword("password123");
//
//        webTestClient.post()
//                .uri("/api/v1/auth/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(requestDto)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$.success").isEqualTo(true)
//                .jsonPath("$.data.userId").isEqualTo("12345");
//
//        ArgumentCaptor<RegisterRequestDto> captor = ArgumentCaptor.forClass(RegisterRequestDto.class);
//        verify(authGrpcClient, times(1)).register(captor.capture());
//
//        RegisterRequestDto capturedRequest = captor.getValue();
//        assertEquals("testuser", capturedRequest.getUsername());
//        assertEquals("test@example.com", capturedRequest.getEmail());
//        assertEquals("password123", capturedRequest.getPassword());
//
//        logger.info("Проверено: gRPC клиент получил запрос с username={}, email={}",
//                capturedRequest.getUsername(), capturedRequest.getEmail());
//    }
//
//    @Test
//    void testGrpcClientLogging() {
//        RegisterRequestDto testRequest = new RegisterRequestDto();
//        testRequest.setUsername("testuser");
//        testRequest.setEmail("test@example.com");
//        testRequest.setPassword("password123");
//
//        when(authGrpcClient.register(any(RegisterRequestDto.class)))
//                .thenAnswer(invocation -> {
//                    RegisterRequestDto arg = invocation.getArgument(0);
//                    logger.info("Мокированный AuthGrpcClient.register() вызван с: {}", arg);
//                    return RegisterResponse.newBuilder().setUserId("12345").build();
//                });
//
//        RegisterResponse response = authGrpcClient.register(testRequest);
//        assertEquals("12345", response.getUserId());
//    }
//}