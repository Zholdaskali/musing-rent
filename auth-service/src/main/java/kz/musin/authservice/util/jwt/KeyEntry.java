package kz.musin.authservice.util.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.security.PrivateKey;
import java.security.PublicKey;

@Getter
@RequiredArgsConstructor
public class KeyEntry {
    private final String kid;
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
}
