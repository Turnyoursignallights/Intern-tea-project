package com.tea.management.order.dto;

public class OrderItemRequestDto {
    private Integer teaId;
    private Integer quantity;


    public OrderItemRequestDto() {
    }

    public OrderItemRequestDto(Integer teaId, Integer quantity) {
        this.teaId = teaId;
        this.quantity = quantity;
    }

    // Getters and setters
    public Integer getTeaId() {
        return teaId;
    }

    public void setTeaId(Integer teaId) {
        this.teaId = teaId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}