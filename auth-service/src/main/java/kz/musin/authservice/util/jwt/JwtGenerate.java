package kz.musin.authservice.util.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kz.musin.authservice.model.entity.User;

import java.security.PrivateKey;
import java.util.Date;


/**
 *
 *
 *
 */
public class JwtGenerate {
    private static final long EXPIRATION_TIME = 36_000_000L;
    private final PrivateKey privateKey;

    public JwtGenerate(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    /**
     *
     * @param user
     * @param role
     * @return
     */
    public String generateToken(User user, String role) {
        return Jwts.builder()
                .setSubject(user.getUserName())
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
}