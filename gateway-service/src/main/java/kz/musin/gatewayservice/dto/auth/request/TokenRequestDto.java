package kz.musin.gatewayservice.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenRequestDto {

    /** token пользователя */
    @NotBlank(message = "Token не может быть пустым")
    private String token;

}