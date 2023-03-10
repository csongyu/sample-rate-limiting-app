package xyz.csongyu.ratelimiting;

import java.security.SecureRandom;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomNumberController {
    @GetMapping(value = "/api/random-number", produces = MediaType.TEXT_PLAIN_VALUE)
    public String generateRandomNumber() {
        return String.valueOf(new SecureRandom().nextLong());
    }
}