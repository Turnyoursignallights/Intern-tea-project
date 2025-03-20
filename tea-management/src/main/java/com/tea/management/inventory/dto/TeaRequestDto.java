package com.tea.management.inventory.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

//@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class TeaRequestDto {
    String name;
    String type;
    String description;
    Double sellPrice;
    String origin;
    Integer stockQuantity;
    LocalDate produceAt;
    Double costPrice;

    public TeaRequestDto(String type, String description, String name, Double sellPrice, String origin, Integer stockQuantity, LocalDate produceAt, Double costPrice) {
        this.type = type;
        this.description = description;
        this.name = name;
        this.sellPrice = sellPrice;
        this.origin = origin;
        this.stockQuantity = stockQuantity;
        this.produceAt = produceAt;
        this.costPrice = costPrice;
    }

    public TeaRequestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public LocalDate getProduceAt() {
        return produceAt;
    }

    public void setProduceAt(LocalDate produceAt) {
        this.produceAt = produceAt;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TeaRequestDto that = (TeaRequestDto) o;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(description, that.description) && Objects.equals(sellPrice, that.sellPrice) && Objects.equals(origin, that.origin) && Objects.equals(stockQuantity, that.stockQuantity) && Objects.equals(produceAt, that.produceAt) && Objects.equals(costPrice, that.costPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, description, sellPrice, origin, stockQuantity, produceAt, costPrice);
    }

    @Override
    public String toString() {
        return "TeaRequestDto{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", sellPrice=" + sellPrice +
                ", origin='" + origin + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", produceAt=" + produceAt +
                ", costPrice=" + costPrice +
                '}';
    }
}



