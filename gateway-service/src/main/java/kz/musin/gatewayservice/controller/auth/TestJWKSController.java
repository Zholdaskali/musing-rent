package kz.musin.gatewayservice.controller.auth;

import kz.musin.gatewayservice.dto.ApiResponse;
import kz.musin.gatewayservice.util.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestJWKSController {

    private static final Logger log = LoggerFactory.getLogger(TestJWKSController.class);

    @GetMapping("/public/images")
    public ApiResponse<?> getImages() {
        log.info("getImages");
        return ResponseBuilder.success("List images", HttpStatus.OK);
    }

    @GetMapping("/private/profile/{id}")
    public ApiResponse<?> getProfile(@PathVariable("id") String id) {
        log.info("getProfile");
        return ResponseBuilder.success("profile " + id, HttpStatus.OK);
    }
}
