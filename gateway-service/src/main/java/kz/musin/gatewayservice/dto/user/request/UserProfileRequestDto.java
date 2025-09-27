package kz.musin.gatewayservice.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserProfileRequestDto {

    private UUID authId;

    @NotNull(message = "fullName не может быть пустыым")
    private String fullName;

    @NotNull(message = "fullName не может быть пустыым")
    private String AvatarUrl;
}
