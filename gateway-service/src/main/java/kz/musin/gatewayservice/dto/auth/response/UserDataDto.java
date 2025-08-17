package kz.musin.gatewayservice.dto.auth.response;

import com.google.protobuf.Timestamp;
import kz.musin.proto.auth.UserData;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.ZoneId;

@Data
@Builder
public class UserDataDto {
    private String userName;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;
    private UserRolesDto roles;

    public static UserDataDto fromProto(UserData proto) {
        return UserDataDto.builder()
                .userName(proto.getUserName())
                .email(proto.getEmail())
                .createdAt(toInstant(proto.getCreateAt()))
                .updatedAt(toInstant(proto.getUpdateAt()))
                .roles(UserRolesDto.fromProto(proto.getRoles()))
                .build();
    }

    private static Instant toInstant(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos())
                .atZone(ZoneId.systemDefault())
                .toInstant();
    }
}
