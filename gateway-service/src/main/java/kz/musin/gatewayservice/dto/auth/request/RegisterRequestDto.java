package kz.musin.gatewayservice.dto.auth.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RegisterRequestDto {
    /** username пользователя */
    private String username;

    /** email пользователя */
    private String email;

    /** password пользователя */
    private String password;
}
