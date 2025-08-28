package kz.musin.gatewayservice.util;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.spec.X509EncodedKeySpec;

@Component
public class JwtValidate {

//    private static final Key SECRET_KEY;
//

    public static boolean validate(String jwtToken) {
        if (jwtToken.startsWith("jwt-")) {
            return true;
        } else {
            return false;
        }
    }
}
