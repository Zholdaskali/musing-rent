    package kz.musin.gatewayservice.util.jwt;

    import com.nimbusds.jose.jwk.source.RemoteJWKSet;
    import com.nimbusds.jose.jwk.source.JWKSource;
    import com.nimbusds.jose.proc.JWSVerificationKeySelector;
    import com.nimbusds.jose.proc.SecurityContext;
    import com.nimbusds.jose.JWSAlgorithm;
    import com.nimbusds.jwt.SignedJWT;
    import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
    import com.nimbusds.jwt.proc.DefaultJWTProcessor;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Component;

    import java.net.MalformedURLException;
    import java.net.URL;

    @Component
    public class JwtValidator {

        private final ConfigurableJWTProcessor<SecurityContext> jwtProcessor;

        public JwtValidator(@Value("${auth-service.jwks-uri}") String jwksUri) throws MalformedURLException {
            // Создаем RemoteJWKSet, который сам умеет подтягивать ключи по JWKS URL
            JWKSource<SecurityContext> keySource = new RemoteJWKSet<>(new URL(jwksUri));

            this.jwtProcessor = new DefaultJWTProcessor<>();
            JWSVerificationKeySelector<SecurityContext> keySelector =
                    new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, keySource);
            jwtProcessor.setJWSKeySelector(keySelector);
        }

        public boolean validateToken(String token) {
            try {
                SignedJWT jwt = SignedJWT.parse(token);
                jwtProcessor.process(jwt, null); // проверка подписи и срока жизни
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
