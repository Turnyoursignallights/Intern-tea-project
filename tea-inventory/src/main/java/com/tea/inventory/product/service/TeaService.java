package com.tea.inventory.product.service;

import com.tea.inventory.product.entity.Tea;
import com.tea.inventory.product.dto.TeaRequestDto;
import com.tea.inventory.product.dto.TeaResponseDto;

import java.util.List;

public interface TeaService {

    List<TeaResponseDto> getAllTeas();

    TeaResponseDto getByID(Integer id);

    Tea create(TeaRequestDto teaDto);

    Tea update(TeaRequestDto teaDto, Integer id);

    void delete(Integer id);
}