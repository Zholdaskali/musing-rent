package kz.musin.authservice.util.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kz.musin.authservice.model.entity.User;
import kz.musin.proto.auth.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtGenerate {

    private static final long EXPIRATION_TIME = 36_000_000L;

    private final KeyManager keyManager;

    public String generateToken(User user, String role) {

        KeyEntry key = keyManager.getActiveKey();

        return Jwts.builder()
                .setHeaderParam("kid", key.getKid())
                .setSubject(user.getUserName())
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }
}
