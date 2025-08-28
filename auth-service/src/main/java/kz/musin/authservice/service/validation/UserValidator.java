//package kz.musin.authservice.service.validation;
//
//import jakarta.validation.ValidationException;
//import kz.musin.authservice.model.dto.request.RegistrationRequest;
//import kz.musin.authservice.model.entity.User;
//import kz.musin.authservice.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class UserValidator {
//    private final UserRepository userRepository;
//
//    public void validate(RegistrationRequest request)
//    {
//        if (request.userName() == null || request.userName().isBlank())
//        {
//            throw new ValidationException("Username is required");
//        }
//
//        if (userRepository.existsByEmail(request.email()))
//        {
//            throw new ValidationException("Email already exists");
//        }
//    }
//}
