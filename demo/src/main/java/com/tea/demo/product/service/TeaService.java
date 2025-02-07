package com.tea.demo.product.service;

import com.tea.demo.product.Entity.Tea;
import com.tea.demo.product.dto.TeaRequestDto;
import com.tea.demo.product.dto.TeaResponseDto;

import java.util.List;

public interface TeaService {

         List<TeaResponseDto> getAllTeas();
         TeaResponseDto getByID(Integer id);
         Tea create(TeaRequestDto teaDto);
         Tea update(TeaRequestDto teaDto, Integer id);
         void delete(Integer id);
}