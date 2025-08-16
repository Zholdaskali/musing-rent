package kz.musin.orderservice.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import kz.musin.proto.order.OrderServiceGrpc;
import kz.musin.proto.order.OrderRequest;
import kz.musin.proto.order.OrderResponse;

import java.util.UUID;

@GrpcService
public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    @Override
    public void createOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        String userId = request.getUserId();
        String productId = request.getProductId();

        // Логика создания заказа (пример)
        String orderId = UUID.randomUUID().toString();
        String status = "CREATED";

        OrderResponse response = OrderResponse.newBuilder()
                .setOrderId(orderId)
                .setStatus(status)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

