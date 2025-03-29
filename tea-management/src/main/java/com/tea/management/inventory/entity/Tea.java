package com.tea.management.inventory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.Objects;


@Entity
public class Tea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NotEmpty
    String name;
    @NotEmpty
    String type;
    String description;
    @Positive
    Double sellPrice;
    String origin;
    Integer stockQuantity;
    LocalDate produceAt;
    @Positive
    Double costPrice;

    public Tea() {
    }

    public Tea(Double costPrice, LocalDate produceAt, Integer stockQuantity, String origin, Double sellPrice, String description, String type, String name, Integer id) {
        this.costPrice = costPrice;
        this.produceAt = produceAt;
        this.stockQuantity = stockQuantity;
        this.origin = origin;
        this.sellPrice = sellPrice;
        this.description = description;
        this.type = type;
        this.name = name;
        this.id = id;
    }

    //Setter and getter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDate getProduceAt() {
        return produceAt;
    }

    public void setProduceAt(LocalDate produceAt) {
        this.produceAt = produceAt;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
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
        Tea tea = (Tea) o;
        return Objects.equals(id, tea.id) && Objects.equals(name, tea.name) && Objects.equals(type, tea.type) && Objects.equals(description, tea.description) && Objects.equals(sellPrice, tea.sellPrice) && Objects.equals(origin, tea.origin) && Objects.equals(stockQuantity, tea.stockQuantity) && Objects.equals(produceAt, tea.produceAt) && Objects.equals(costPrice, tea.costPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, description, sellPrice, origin, stockQuantity, produceAt, costPrice);
    }

    @Override
    public String toString() {
        return "Tea{" +
                "id=" + id +
                ", name='" + name + '\'' +
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
