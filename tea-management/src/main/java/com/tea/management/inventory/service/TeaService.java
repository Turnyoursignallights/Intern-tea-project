package com.tea.management.inventory.service;

import com.tea.management.inventory.entity.Tea;
import com.tea.management.inventory.dto.TeaRequestDto;
import com.tea.management.inventory.dto.TeaResponseDto;

import java.util.List;


public interface TeaService {

    List<TeaResponseDto> getAllTeas();

    TeaResponseDto getByID(Integer id);

    Tea create(TeaRequestDto teaDto);

    Tea update(TeaRequestDto teaDto, Integer id);

    void delete(Integer id);
}