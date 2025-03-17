package com.tea.inventory.product.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

//@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class TeaResponseDto {
    Integer id;
    String name;
    String type;
    String description;
    double price;
    String origin;
    LocalDate produceAt;

    public TeaResponseDto() {
    }

    public TeaResponseDto(Integer id, String name, String type, String description, double price, String origin, LocalDate produceAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.origin = origin;
        this.produceAt = produceAt;
    }

    public LocalDate getProduceAt() {
        return produceAt;
    }

    public void setProduceAt(LocalDate produceAt) {
        this.produceAt = produceAt;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TeaResponseDto that = (TeaResponseDto) o;
        return Double.compare(price, that.price) == 0 && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(description, that.description) && Objects.equals(origin, that.origin) && Objects.equals(produceAt, that.produceAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, description, price, origin, produceAt);
    }

    @Override
    public String toString() {
        return "TeaResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", origin='" + origin + '\'' +
                ", produceAt=" + produceAt +
                '}';
    }
}
