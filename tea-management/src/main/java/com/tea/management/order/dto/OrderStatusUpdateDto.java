package com.tea.management.order.dto;

import java.util.UUID;

public class OrderStatusUpdateDto {
    private UUID orderId;
    private String status;

    // Default constructor
    public OrderStatusUpdateDto() {
    }

    // Constructor with fields
    public OrderStatusUpdateDto(UUID orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    // Getters and setters
    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}