package com.tea.management.order.dto;

import com.tea.management.inventory.dto.TeaResponseDto;

import java.util.UUID;

public class OrderItemResponseDto {
    private UUID id;
    private UUID orderId;
    private TeaResponseDto tea;
    private Integer quantity;
    private double price;

    public OrderItemResponseDto() {
    }

    public OrderItemResponseDto(UUID id, UUID orderId, TeaResponseDto tea,
                                Integer quantity, double price) {
        this.id = id;
        this.orderId = orderId;
        this.tea = tea;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public TeaResponseDto getTea() {
        return tea;
    }

    public void setTea(TeaResponseDto tea) {
        this.tea = tea;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
