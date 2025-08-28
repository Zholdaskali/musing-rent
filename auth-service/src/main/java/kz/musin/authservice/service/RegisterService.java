//package kz.musin.authservice.service;
//
//import kz.musin.authservice.model.dto.request.RegistrationRequest;
//import kz.musin.authservice.model.entity.User;
//import kz.musin.authservice.repository.UserRepository;
//import kz.musin.authservice.service.validation.UserValidator;
//import kz.musin.authservice.util.encoder.PasswordEncoder;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import kz.musin.proto.auth.*;
//
//import java.time.Instant;
//
//@Service
//@RequiredArgsConstructor
//public class RegisterService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final UserValidator validator;
//
//    public User registerUser(RegistrationRequest request) {
//        validator.validate(request);
//
//        User user = new User(
//                request.userName(),
//                request.email(),
//                passwordEncoder.hash(request.password()),
//                Instant.now(),
//                Instant.now());
//
//        return userRepository.save(user);
//    }
//}
