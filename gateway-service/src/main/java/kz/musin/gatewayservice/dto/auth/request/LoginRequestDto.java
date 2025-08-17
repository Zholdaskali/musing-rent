package kz.musin.gatewayservice.dto.auth.request;

import kz.musin.proto.auth.LoginResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    /** email пользователя */
    private String email;

    /** password пользователя */
    private String password;

}
