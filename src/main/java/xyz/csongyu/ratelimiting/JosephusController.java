package xyz.csongyu.ratelimiting;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JosephusController {
    @GetMapping(value = "/api/josephus", produces = MediaType.TEXT_PLAIN_VALUE)
    public String gcd(@RequestParam("n") final int n, @RequestParam("m") final int m) {
        return String.valueOf(this.f(n, m));
    }

    private int f(final int n, final int m) {
        if (n == 1) {
            return n;
        }
        return (this.f(n - 1, m) + m - 1) % n + 1;
    }
}