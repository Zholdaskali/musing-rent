package kz.musin.gatewayservice.controller.user;

import jakarta.validation.Valid;
import kz.musin.gatewayservice.dto.ApiResponse;
import kz.musin.gatewayservice.dto.user.request.GetUserProfileRequestDto;
import kz.musin.gatewayservice.dto.user.request.UserProfileRequestDto;
import kz.musin.gatewayservice.dto.user.response.UserProfileResponseDto;
import kz.musin.gatewayservice.grpc.user.UserGrpcClient;
import kz.musin.gatewayservice.util.ResponseBuilder;
import kz.musin.proto.user.GetUserProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserGrpcClient userGrpcClient;

    /**
     *
     *
     *
     * @param userProfileRequestDto
     * @return
     */
    @PostMapping
    public ApiResponse<?> saveUserProfile(@Valid @RequestBody UserProfileRequestDto userProfileRequestDto) {
        return ResponseBuilder.created(
                UserProfileResponseDto.fromProto(userGrpcClient.saveUserProfile(userProfileRequestDto)
        ));
    }

    /**
     *
     *
     *
     * @param getUserProfileRequestDto
     * @return
     */
    @GetMapping
    public ApiResponse<?> getUserProfiles(@Valid @RequestBody GetUserProfileRequestDto getUserProfileRequestDto) {
        return ResponseBuilder.success(
                UserProfileResponseDto.fromProto(userGrpcClient.getUserProfile(getUserProfileRequestDto)
                ));
    }
}
