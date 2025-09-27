package kz.musin.gatewayservice.exception;

public class UserServiceConnectionException extends RuntimeException {
    public UserServiceConnectionException(String message) {
        super(message);
    }
}
