package xyz.csongyu.ratelimiting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SampleRateLimitingAppApplication {
    public static void main(final String[] args) {
        SpringApplication.run(SampleRateLimitingAppApplication.class, args);
    }
}
