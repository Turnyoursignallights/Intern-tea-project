package com.tea.demo.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record Tea(
        Integer id,
        @NotEmpty
        String name,
        @NotEmpty
        String type,
        String description,
        @Positive
        Double sellPrice,
        String origin,
        Integer stockQuantity,
        LocalDate produceAt,
        @Positive
        Double costPrice
) {
}
