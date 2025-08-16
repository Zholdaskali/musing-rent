package kz.musin.authservice.util.encoder;

public interface PasswordEncoder {

    String hash(String password);

    boolean check(String password, String hash);

}