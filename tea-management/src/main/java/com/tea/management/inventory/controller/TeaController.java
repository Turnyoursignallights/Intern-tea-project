package com.tea.management.inventory.controller;

import com.tea.management.inventory.entity.Tea;
import com.tea.management.inventory.dto.TeaRequestDto;
import com.tea.management.inventory.dto.TeaResponseDto;
import com.tea.management.inventory.service.TeaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tea/")
public class TeaController {
    private final TeaService teaService;

    public TeaController(TeaService teaService) {
        this.teaService = teaService;
    }

    @GetMapping("/getAll")
    public List<TeaResponseDto> getProducts() {
        return teaService.getAllTeas();
    }

    @GetMapping("/{id}")
    public TeaResponseDto findById(@PathVariable("id") Integer id) {
        TeaResponseDto tea = teaService.getByID(id);
        return tea;
    }

    //Create the tea
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public Tea create(@Valid @RequestBody TeaRequestDto teaDto) {
        return teaService.create(teaDto);
    }

    //Update the tea
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update/{id}")
    public Tea update(@Valid @RequestBody TeaRequestDto teaDto, @PathVariable Integer id) {
        return teaService.update(teaDto, id);
    }

    //Delete the tea
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        teaService.delete(id);
    }


}
