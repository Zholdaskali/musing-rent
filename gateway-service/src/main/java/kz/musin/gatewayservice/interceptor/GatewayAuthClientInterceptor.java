//package kz.musin.gatewayservice.interceptor;
//
//import io.grpc.*;
//import org.springframework.security.core.token.TokenService;
//import org.springframework.stereotype.Component;
//
//// Gateway - gRPC Client Interceptor
//@Component
//public class GatewayAuthClientInterceptor implements ClientInterceptor {
//
//    private final TokenService tokenService;
//    private final SignatureService signatureService;
//
//    public GatewayAuthClientInterceptor(TokenService tokenService, SignatureService signatureService) {
//        this.tokenService = tokenService;
//        this.signatureService = signatureService;
//    }
//
//    @Override
//    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
//            MethodDescriptor<ReqT, RespT> method,
//            CallOptions callOptions,
//            Channel next) {
//
//        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
//                next.newCall(method, callOptions)) {
//
//            @Override
//            public void start(Listener<RespT> responseListener, Metadata headers) {
//                // Автоматически добавляем заголовки аутентификации
//                addAuthHeaders(headers);
//                super.start(responseListener, headers);
//            }
//        };
//    }
//
//    private void addAuthHeaders(Metadata headers) {
//        // Передаем оригинальный токен пользователя
//        String userToken = getCurrentUserToken();
//        if (userToken != null) {
//            headers.put(Metadata.Key.of("x-original-token", Metadata.ASCII_STRING_MARSHALLER),
//                    userToken);
//        }
//
//        // Добавляем подпись gateway
//        String signature = signatureService.createSignature();
//        headers.put(Metadata.Key.of("x-gateway-signature", Metadata.ASCII_STRING_MARSHALLER),
//                signature);
//
//        // Service-to-service токен
//        String serviceToken = tokenService.generateServiceToken();
//        headers.put(Metadata.Key.of("x-service-token", Metadata.ASCII_STRING_MARSHALLER),
//                serviceToken);
//    }
//}
