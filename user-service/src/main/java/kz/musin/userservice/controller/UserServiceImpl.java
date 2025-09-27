package kz.musin.userservice.controller;

import io.grpc.stub.StreamObserver;
import kz.musin.proto.auth.LoginResponse;
import kz.musin.proto.user.GetUserProfileRequest;
import kz.musin.proto.user.UserProfileRequest;
import kz.musin.proto.user.UserProfileResponse;
import kz.musin.proto.user.UserServiceGrpc;
import kz.musin.userservice.service.UserProfilesService;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    private final UserProfilesService userProfilesService;

    @Override
    public void createUserProfile(UserProfileRequest userProfileRequest,  StreamObserver<UserProfileResponse> responseObserver) {
        responseObserver.onNext(userProfilesService.createUserProfile(userProfileRequest));
        responseObserver.onCompleted();
    }

    @Override
    public void getUserProfile(GetUserProfileRequest request,  StreamObserver<UserProfileResponse> responseObserver) {
        responseObserver.onNext(userProfilesService.getUserProfile(request ));
        responseObserver.onCompleted();
    }

}
