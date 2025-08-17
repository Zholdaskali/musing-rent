package kz.musin.gatewayservice.dto.auth.response;

import kz.musin.gatewayservice.dto.auth.request.LoginRequestDto;
import kz.musin.proto.auth.LoginResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

    private final String accessToken;
    private final String refreshToken;
    private final UserDataDto data;

    public static LoginResponseDto fromProto(LoginResponse proto) {
        return LoginResponseDto.builder()
                .accessToken(proto.getAccessToken())
                .refreshToken(proto.getRefreshToken())
                .data(UserDataDto.fromProto(proto.getData()))
                .build();
    }
}
