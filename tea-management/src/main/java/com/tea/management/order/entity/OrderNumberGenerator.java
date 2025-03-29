package com.tea.management.order.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderNumberGenerator {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final AtomicInteger SEQUENCE = new AtomicInteger(1);
    private static volatile String lastDateUsed = LocalDateTime.now().format(DATE_FORMAT);

    /**
     * Generates a unique order number with format: ORD-{date}-{sequence}-{random}
     * The combination of date, sequence, and random digits makes collisions extremely unlikely.
     *
     * @return A unique order number string
     */
    public static synchronized String generateOrderNumber() {
        String currentDate = LocalDateTime.now().format(DATE_FORMAT);

        // Reset sequence if it's a new day
        if (!currentDate.equals(lastDateUsed)) {
            SEQUENCE.set(1);
            lastDateUsed = currentDate;
        }

        int sequence = SEQUENCE.getAndIncrement();
        // Add random digits for additional uniqueness
        String randomPart = String.format("%03d", RANDOM.nextInt(1000));

        return String.format("ORD-%s-%04d-%s", currentDate, sequence, randomPart);
    }
}