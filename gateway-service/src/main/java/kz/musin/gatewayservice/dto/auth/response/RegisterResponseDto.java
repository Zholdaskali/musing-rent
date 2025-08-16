package kz.musin.gatewayservice.dto.auth.response;

import kz.musin.proto.auth.RegisterResponse;
import kz.musin.proto.auth.TokenResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDto {
    /** userId RegisterResponseDto */
    private String userId;

    public static RegisterResponseDto fromProto(RegisterResponse proto) {
        return new RegisterResponseDto(
                proto.getUserId()
        );
    }
}
