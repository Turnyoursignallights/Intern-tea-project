package com.tea.demo.product;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/teas")
public class TeaController {

    private final TeaRepository teaRepository;

    public TeaController(TeaRepository teaRepository) {
        this.teaRepository = teaRepository;
    }

//    @GetMapping
//    List<Tea> getProducts(){
//        return teaRepository.findAll();
//    }

    @GetMapping
    public List<TeaResponseDto> getProducts(){
        return teaRepository.findAll().stream().map(TeaMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    Optional<Tea> findById(@PathVariable("id") Integer id){
        Optional<Tea> tea = teaRepository.findById(id);
        if(tea.isEmpty()){
            throw new TeaNotFoundException();
        }
        return tea;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    void create(@Valid @RequestBody Tea tea){
        teaRepository.create(tea);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Tea tea, @PathVariable Integer id){
        teaRepository.update(tea,id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        teaRepository.delete(id);
    }




}
