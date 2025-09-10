package kz.musin.gatewayservice.dto.auth.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequestDto {

    /** refreshToken пользователя */
    @NotNull(message = "refreshToken не может быть пустым")
    private String refreshToken;

}
