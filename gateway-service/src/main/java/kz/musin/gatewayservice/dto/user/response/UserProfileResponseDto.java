package kz.musin.gatewayservice.dto.user.response;

import kz.musin.proto.user.UserProfileResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class UserProfileResponseDto {
    private UUID authId;
    private String fullName;
    private String AvatarUrl;

    public static UserProfileResponseDto fromProto(UserProfileResponse proto) {
        return UserProfileResponseDto.builder()
                .authId(UUID.fromString(proto.getAuthId()))
                .AvatarUrl(proto.getAvatarUrl())
                .fullName(proto.getFullName()).build();
    }
}
