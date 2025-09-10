package kz.musin.authservice.controller.jwks;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.JSONObjectUtils;
import kz.musin.authservice.util.jwt.KeyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class JwksController {

    private final KeyManager keyManager;

    @GetMapping("/.well-known/jwks.json")
    public String getJwks() {
        var jwkSet = new JWKSet(
                keyManager.getAllKeys().values().stream()
                        .map(k -> new RSAKey.Builder((java.security.interfaces.RSAPublicKey) k.getPublicKey())
                                .keyID(k.getKid())
                                .build())
                        .collect(Collectors.toList())
        );

        return JSONObjectUtils.toJSONString(jwkSet.toJSONObject());
    }
}
