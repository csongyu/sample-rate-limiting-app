package xyz.csongyu.ratelimiting;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

public class BasicUsageTest {
    @Test
    void givenCapacity13PerSecondsAndIntervalRefill_whenRequest14AtMoment_thenFailed()
        throws ExecutionException, InterruptedException {
        final long capacity = 13L;
        final Refill refill = Refill.intervally(capacity, Duration.ofSeconds(1L));
        final Bandwidth bandwidth = Bandwidth.classic(capacity, refill);
        final Bucket bucket = Bucket.builder().addLimit(bandwidth).build();
        assertTrue(bucket.tryConsume(capacity));
        assertEquals(0L, bucket.getAvailableTokens());
        final ScheduledFuture<Boolean> scheduledFuture =
            Executors.newScheduledThreadPool(1).schedule(() -> bucket.tryConsume(capacity + 1), 2, TimeUnit.SECONDS);
        assertFalse(scheduledFuture.get());
    }

    @Test
    void givenCapacity780PerMinutesAndInternalRefill_whenRequest26AtMoment_thenSuccess()
        throws ExecutionException, InterruptedException {
        final long capacity = 13L * 60;
        final Refill refill = Refill.intervally(capacity, Duration.ofMinutes(1L));
        final Bandwidth bandwidth = Bandwidth.classic(capacity, refill);
        final Bucket bucket = Bucket.builder().addLimit(bandwidth).build();
        assertTrue(bucket.tryConsume(capacity));
        assertEquals(0L, bucket.getAvailableTokens());
        final ScheduledFuture<Boolean> scheduledFuture = Executors.newScheduledThreadPool(1)
            .schedule(() -> bucket.tryConsume(capacity / 60 * 2), 2, TimeUnit.SECONDS);
        assertFalse(scheduledFuture.get());
        assertEquals(0L, bucket.getAvailableTokens());
    }

    @Test
    void givenCapacity780PerMinutesAndGreedyRefill_whenRequest26AtMoment_thenSuccess()
        throws ExecutionException, InterruptedException {
        final long capacity = 13L * 60;
        final Refill refill = Refill.greedy(capacity, Duration.ofMinutes(1L));
        final Bandwidth bandwidth = Bandwidth.classic(capacity, refill);
        final Bucket bucket = Bucket.builder().addLimit(bandwidth).build();
        assertTrue(bucket.tryConsume(capacity));
        assertEquals(0L, bucket.getAvailableTokens());
        final ScheduledFuture<Boolean> scheduledFuture = Executors.newScheduledThreadPool(1)
            .schedule(() -> bucket.tryConsume(capacity / 60 * 2), 2, TimeUnit.SECONDS);
        assertTrue(scheduledFuture.get());
        assertEquals(0L, bucket.getAvailableTokens());
    }
}
