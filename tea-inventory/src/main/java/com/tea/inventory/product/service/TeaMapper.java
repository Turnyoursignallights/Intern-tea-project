package com.tea.inventory.product.service;

import com.tea.inventory.product.entity.Tea;
import com.tea.inventory.product.dto.TeaRequestDto;
import com.tea.inventory.product.dto.TeaResponseDto;

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
        return Tea.builder()
                .name(teaRequireDto.getName())  // Mandatory field
                .type(teaRequireDto.getType())  // Mandatory field
                .description(Optional.ofNullable(teaRequireDto.getDescription()).orElse("No description provided"))
                .sellPrice(Optional.ofNullable(teaRequireDto.getSellPrice()).orElse(0.0))
                .origin(Optional.ofNullable(teaRequireDto.getOrigin()).orElse("Unknown origin"))
                .stockQuantity(Optional.ofNullable(teaRequireDto.getStockQuantity()).orElse(0))
                .produceAt(Optional.ofNullable(teaRequireDto.getProduceAt()).orElse(null))
                .costPrice(Optional.ofNullable(teaRequireDto.getCostPrice()).orElse(0.0))
                .build();

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
