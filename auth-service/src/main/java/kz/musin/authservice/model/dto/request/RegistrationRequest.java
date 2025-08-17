package kz.musin.authservice.model.dto.request;

public record RegistrationRequest (
        String userName,
        String email,
        String password
) {}
