package kz.musin.authservice.util.encoder;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptPasswordEncoder implements PasswordEncoder {
    @Override
    public String hash(String password) {
        int saltLength = 10;
        String salt = BCrypt.gensalt(saltLength);
        return BCrypt.hashpw(password, salt);
    }

    @Override
    public boolean check(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
