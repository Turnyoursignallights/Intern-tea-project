package com.tea.management.inventory.service;

import com.tea.management.inventory.entity.Tea;
import com.tea.management.inventory.dto.TeaRequestDto;
import com.tea.management.inventory.dto.TeaResponseDto;

import java.util.Optional;

public class TeaMapper {
    public static TeaResponseDto toDto(Tea tea) {
        return new TeaResponseDto(
                tea.getId(),
                tea.getName(),
                tea.getType(),
                tea.getDescription(),
                tea.getSellPrice(),
                tea.getOrigin(),
                tea.getProduceAt()
        );
    }

    public static Tea toEntity(TeaRequestDto teaRequireDto) {
        Tea tea = new Tea();
        tea.setName(teaRequireDto.getName());
        tea.setType(teaRequireDto.getType());
        tea.setDescription(Optional.ofNullable(teaRequireDto.getDescription()).orElse("No description provided"));
        tea.setSellPrice(Optional.ofNullable(teaRequireDto.getSellPrice()).orElse(0.0));
        tea.setOrigin(Optional.ofNullable(teaRequireDto.getOrigin()).orElse("Unknown origin"));
        tea.setStockQuantity(Optional.ofNullable(teaRequireDto.getStockQuantity()).orElse(0));
        tea.setProduceAt(Optional.ofNullable(teaRequireDto.getProduceAt()).orElse(null));
        tea.setCostPrice(Optional.ofNullable(teaRequireDto.getCostPrice()).orElse(0.0));
        return tea;

    }

    public static void mergeFieldsForUpdate(Tea existingTea, TeaRequestDto teaRequestDto) {
        if (teaRequestDto.getName() != null) {
            existingTea.setName(teaRequestDto.getName());
        }
        if (teaRequestDto.getType() != null) {
            existingTea.setType(teaRequestDto.getType());
        }
        if (teaRequestDto.getDescription() != null) {
            existingTea.setDescription(teaRequestDto.getDescription());
        }
        if (teaRequestDto.getSellPrice() != null) {
            existingTea.setSellPrice(teaRequestDto.getSellPrice());
        }
        if (teaRequestDto.getOrigin() != null) {
            existingTea.setOrigin(teaRequestDto.getOrigin());
        }
        if (teaRequestDto.getStockQuantity() != null) {
            existingTea.setStockQuantity(teaRequestDto.getStockQuantity());
        }
        if (teaRequestDto.getProduceAt() != null) {
            existingTea.setProduceAt(teaRequestDto.getProduceAt());
        }
        if (teaRequestDto.getCostPrice() != null) {
            existingTea.setCostPrice(teaRequestDto.getCostPrice());
        }
    }
}
