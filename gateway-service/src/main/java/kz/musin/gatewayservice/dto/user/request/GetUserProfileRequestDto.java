package kz.musin.gatewayservice.dto.user.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GetUserProfileRequestDto {
    private UUID authId;
}
