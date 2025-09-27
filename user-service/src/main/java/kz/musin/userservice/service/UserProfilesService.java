package kz.musin.userservice.service;

import io.grpc.Status;
import kz.musin.proto.user.GetUserProfileRequest;
import kz.musin.proto.user.UserProfileRequest;
import kz.musin.proto.user.UserProfileResponse;
import kz.musin.userservice.model.entity.UserProfiles;
import kz.musin.userservice.repository.UserProfilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserProfilesService {

    private final UserProfilesRepository userProfilesRepository;

    public UserProfileResponse createUserProfile(UserProfileRequest userProfileRequest) {

        try {

            UserProfiles userProfiles = new UserProfiles(
                    UUID.fromString(userProfileRequest.getAuthId())
                    , userProfileRequest.getFullName()
                    , userProfileRequest.getAvatarUrl()
            );

            userProfilesRepository.save(userProfiles);

            return UserProfileResponse.newBuilder()
                    .setAuthId(String.valueOf(userProfiles.getId()))
                    .setAvatarUrl(userProfiles.getAvatarUrl())
                    .setFullName(userProfiles.getFullName())
                    .build() ;

        }catch (Exception e){
            throw Status.INVALID_ARGUMENT
                    .withDescription("Ошибка при сохранении профиля пользователя: " + e.getMessage()).asRuntimeException();
//                    new RuntimeException("Ошибка при сохранении профиля пользователя");
        }
    }

    public UserProfileResponse getUserProfile(GetUserProfileRequest getUserProfileRequest) {
        try {
            UUID authId = UUID.fromString(getUserProfileRequest.getAuthId());

            UserProfiles userProfiles = userProfilesRepository.findByAuthId(authId).orElseThrow(() ->
                    Status.NOT_FOUND
                            .withDescription("Пользователь с authId " + authId + "не найден")
                            .asRuntimeException());

            return UserProfileResponse.newBuilder()
                    .setAuthId(String.valueOf(userProfiles.getId()))
                    .setAvatarUrl(userProfiles.getAvatarUrl())
                    .setFullName(userProfiles.getFullName())
                    .build() ;

        }catch (Exception e){
            throw Status.FAILED_PRECONDITION.withDescription("Ошибка при получении профиля пользователя").asRuntimeException();
//                    new RuntimeException("Ошибка при получении профиля пользователя");
        }
    }

}
