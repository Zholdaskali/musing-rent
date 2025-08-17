package kz.musin.gatewayservice.dto.auth.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    /** username пользователя */
    private String username;

    /** email пользователя */
    private String email;

    /** password пользователя */
    private String password;
}
