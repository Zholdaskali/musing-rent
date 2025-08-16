package kz.musin.gatewayservice.dto.auth.response;

import jakarta.validation.constraints.NotBlank;
import kz.musin.proto.auth.TokenResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponseDto {

    /** userId пользователя */
    @NotBlank(message = "Token не может быть пустым")
    private String userId;

    /** valid пользователя */
    private boolean valid;

    public static TokenResponseDto fromProto(TokenResponse proto) {
        return new TokenResponseDto(
                proto.getUserId(),
                proto.getValid()
        );
    }

}