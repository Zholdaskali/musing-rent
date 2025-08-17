package kz.musin.gatewayservice.dto.auth.response;

import kz.musin.proto.auth.LoginResponse;
import kz.musin.proto.auth.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRolesDto {
    private List<String> names;

    public static UserRolesDto fromProto(UserRoles proto) {
        return UserRolesDto.builder()
                .names(proto.getNameList())
                .build();
    }
}
