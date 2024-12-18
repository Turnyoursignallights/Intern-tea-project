package com.tea.demo.product;

import java.time.LocalDate;

public record TeaResponseDto(
        String name,
        String type,
        String description,
        double price,
        String origin,
        LocalDate produceAt
) {
}
