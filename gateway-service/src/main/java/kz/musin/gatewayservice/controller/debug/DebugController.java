package kz.musin.gatewayservice.controller.debug;

import net.devh.boot.grpc.client.inject.GrpcClient;
import kz.musin.proto.auth.AuthServiceGrpc;
import kz.musin.proto.order.OrderServiceGrpc;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/debug")
public class DebugController {

    private final DiscoveryClient discoveryClient;

    @GrpcClient("auth")
    private AuthServiceGrpc.AuthServiceBlockingStub authStub;

    @GrpcClient("order")
    private OrderServiceGrpc.OrderServiceBlockingStub orderStub;

    public DebugController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    /**
     * Получить список всех сервисов и их инстансов.
     */
    @GetMapping("/services")
    public Map<String, List<Map<String, Object>>> services() {
        Map<String, List<Map<String, Object>>> result = new LinkedHashMap<>();

        discoveryClient.getServices().forEach(serviceName -> {
            List<Map<String, Object>> instances = discoveryClient.getInstances(serviceName).stream()
                    .map(instance -> Map.of(
                            "host", instance.getHost(),
                            "port", instance.getPort(),
                            "uri", instance.getUri().toString(),
                            "grpcPort", instance.getMetadata().getOrDefault("gRPC_port", "N/A"),
                            "metadata", instance.getMetadata()
                    ))
                    .toList();
            result.put(serviceName, instances);
        });

        return result;
    }

    /**
     * Проверка доступности gRPC-сервисов.
     */
    @GetMapping("/grpc-status")
    public Map<String, String> grpcStatus() {
        Map<String, String> status = new LinkedHashMap<>();

        // Проверка auth-service
        try {
            authStub.withDeadlineAfter(1, java.util.concurrent.TimeUnit.SECONDS);
            status.put("auth-service", "UP");
        } catch (Exception e) {
            status.put("auth-service", "DOWN: " + e.getMessage());
        }

        // Проверка order-service
        try {
            orderStub.withDeadlineAfter(1, java.util.concurrent.TimeUnit.SECONDS);
            status.put("order-service", "UP");
        } catch (Exception e) {
            status.put("order-service", "DOWN: " + e.getMessage());
        }

        return status;
    }

    /**
     * Информация о приложении.
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> info() {
        return ResponseEntity.ok(Map.of(
                "app", "Gateway Service",
                "version", "1.0.0",
                "time", new Date().toString()
        ));
    }
}