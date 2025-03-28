package com.tea.management.inventory.service;

import com.tea.management.inventory.entity.Tea;
import com.tea.management.inventory.dto.TeaRequestDto;
import com.tea.management.inventory.exception.TeaNotFoundException;
import com.tea.management.inventory.dto.TeaResponseDto;
import com.tea.management.inventory.repository.TeaRepositoryJpa;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeaServiceImp implements TeaService {

    private final TeaRepositoryJpa teaRepositoryJpa;

    public TeaServiceImp(TeaRepositoryJpa teaRepositoryJpa) {
        this.teaRepositoryJpa = teaRepositoryJpa;
    }

    @Override
    public List<TeaResponseDto> getAllTeas() {
        List<TeaResponseDto> teasDto = teaRepositoryJpa.findAll().stream().map(TeaMapper::toDto).collect(Collectors.toList());
        if (teasDto.isEmpty()) {
            throw new TeaNotFoundException();
        }
        return teasDto;
    }

    @Override
    public TeaResponseDto getByID(Integer id) {
        Optional<TeaResponseDto> teaDto = teaRepositoryJpa.findById(id).map(TeaMapper::toDto);
        if (teaDto.isEmpty()) {
            throw new TeaNotFoundException();
        } else {
            return teaDto.get();
        }
    }

    @Override
    public Tea create(TeaRequestDto teaDto) {
        Tea teaEntity = TeaMapper.toEntity(teaDto);
        Tea savedTea = teaRepositoryJpa.save(teaEntity);
        return savedTea;
    }

    @Override
    public Tea update(TeaRequestDto teaRequestDto, Integer id) {
        Optional<Tea> existingteaOpt = teaRepositoryJpa.findById(id);
        if (existingteaOpt.isPresent()) {
            Tea existingTea = existingteaOpt.get();
            TeaMapper.mergeFieldsForUpdate(existingTea, teaRequestDto);
            return teaRepositoryJpa.save(existingTea);
        } else {
            throw new TeaNotFoundException();
        }
    }

    @Override
    public void delete(Integer id) {
        Optional<Tea> tea = teaRepositoryJpa.findById(id);
        if (tea.isPresent()) {
            teaRepositoryJpa.delete(tea.get());
        } else {
            throw new TeaNotFoundException();
        }
    }
}
