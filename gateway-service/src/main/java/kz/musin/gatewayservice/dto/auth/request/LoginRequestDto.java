package kz.musin.gatewayservice.dto.auth.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    /** email пользователя */
    @NotNull(message = "email не может быть пустым")
    private String email;

    /** password пользователя */
    @NotNull(message = "password не может быть пустым")
    private String password;

}
