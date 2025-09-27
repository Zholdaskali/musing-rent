package kz.musin.gatewayservice.grpc.user;

import kz.musin.gatewayservice.dto.user.request.GetUserProfileRequestDto;
import kz.musin.gatewayservice.dto.user.request.UserProfileRequestDto;
import kz.musin.gatewayservice.exception.UserServiceConnectionException;
import kz.musin.gatewayservice.util.ValidationUtils;
import kz.musin.proto.user.GetUserProfileRequest;
import kz.musin.proto.user.UserProfileRequest;
import kz.musin.proto.user.UserProfileResponse;
import kz.musin.proto.user.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class UserGrpcClient {
    @GrpcClient("user")
    private UserServiceGrpc.UserServiceBlockingStub userStub;

//    private static final Logger log =

    /**
     * Логика передачи в Auth-service создание профиля пользователя
     *
     * @param userProfileRequestDto
     * @return
     */
    public UserProfileResponse saveUserProfile(UserProfileRequestDto userProfileRequestDto) {

        ValidationUtils.validate(userProfileRequestDto);

        try {

            UserProfileRequest userProfileRequest = UserProfileRequest.newBuilder()
                    .setAuthId(userProfileRequestDto.getAuthId().toString())
                    .setFullName(userProfileRequestDto.getFullName())
                    .setAvatarUrl(userProfileRequestDto.getAvatarUrl())
                    .build();
            return userStub.createUserProfile(userProfileRequest);

        }catch (Exception e){

            throw new UserServiceConnectionException("Ошибка соединения с User-service " + e);

        }
    }

    public UserProfileResponse getUserProfile(GetUserProfileRequestDto getUserProfileRequestDto) {

        ValidationUtils.validate(getUserProfileRequestDto);

        try {
            GetUserProfileRequest getUserProfileRequest = GetUserProfileRequest.newBuilder()
                    .setAuthId(getUserProfileRequestDto.getAuthId().toString()).build();

            return userStub.getUserProfile(getUserProfileRequest);

        }catch (Exception e){
            throw new UserServiceConnectionException("Ошибка соединения с User-service " + e);
        }

    }

}
