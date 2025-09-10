package kz.musin.authservice.util.jwt;

import com.nimbusds.jose.jwk.RSAKey;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class KeyManager {

    private final Map<String, KeyEntry> keys = new HashMap<>();
    @Getter
    private KeyEntry activeKey;

    public KeyManager() {
        generateNewKey();
    }

    public Map<String, KeyEntry> getAllKeys() {
        return Collections.unmodifiableMap(keys);
    }

    public void generateNewKey() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.generateKeyPair();

            String kid = UUID.randomUUID().toString();
            KeyEntry entry = new KeyEntry(kid, kp.getPrivate(), kp.getPublic());
            keys.put(kid, entry);
            activeKey = entry;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate RSA key", e);
        }
    }

    public RSAKey getActiveRSAJWK() {
        return new RSAKey.Builder((RSAPublicKey) activeKey.getPublicKey())
                .keyID(activeKey.getKid())
                .build();
    }
}
