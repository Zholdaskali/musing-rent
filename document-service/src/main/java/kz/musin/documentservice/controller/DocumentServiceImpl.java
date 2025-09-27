package kz.musin.documentservice.controller;

import kz.musin.document.service.grpc.DocumentServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class DocumentServiceImpl extends DocumentServiceGrpc.DocumentServiceImplBase {

}
