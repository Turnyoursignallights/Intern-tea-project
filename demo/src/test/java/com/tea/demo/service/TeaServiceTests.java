package com.tea.demo.service;


import com.tea.demo.product.Entity.Tea;
import com.tea.demo.product.dto.TeaRequestDto;
import com.tea.demo.product.dto.TeaResponseDto;
import com.tea.demo.product.repository.TeaRepositoryJpa;
import com.tea.demo.product.service.TeaServiceImp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeaServiceTests {

    private static final Logger logger = LoggerFactory.getLogger(TeaServiceTests.class);

    @Mock
    private TeaRepositoryJpa teaJpa;

    @InjectMocks
    private TeaServiceImp teaService;

    @Test
    public void TeaService_CreatTea_ReturnTeaDto(){
        Tea tea = Tea.builder()
                .name("new tea")
                .type("new tea type")
                .build();
        TeaRequestDto teaDto = TeaRequestDto.builder()
                .name("new tea")
                .type("new tea type")
                .build();
        when(teaJpa.save(Mockito.any(Tea.class))).thenReturn(tea);

        //Act
        Tea result = teaService.create(teaDto);

        //Assert
        Assertions.assertThat(result).isNotNull();
        // verify that the repository's save method was called once
        verify(teaJpa, times(1)).save(Mockito.any(Tea.class));
    }

    @Test
    public void TeaService_GetAllTeas_ReturnsResponseDto(){
        Tea tea1 = Tea.builder().name("new tea1").type("new tea type1").sellPrice(100.1).build();
        Tea tea2 = Tea.builder().name("new tea2").type("new tea type2").sellPrice(100.1).build();
        List<Tea> teas = Arrays.asList(tea1,tea2);
        // Mock the repository behavior
        when(teaJpa.findAll()).thenReturn(teas);

        // Act: Call the service method
        List<TeaResponseDto> teaResponseDtos = teaService.getAllTeas();

        // Assert: Verify the result
        Assertions.assertThat(teaResponseDtos).isNotNull();
        Assertions.assertThat(teaResponseDtos).hasSize(2);
        Assertions.assertThat(teaResponseDtos.get(0).getName()).isEqualTo("new tea1");
        // Verify that the repository method was called exactly once
        verify(teaJpa, times(1)).findAll();
    }

    @Test
    // The id is not generated in fact,the id=1 haven't been attached to the object
    public void TeaService_GetTeaByID_ReturnTeaDto(){
        Tea tea = Tea.builder()
                .name("new tea")
                .type("new tea type")
                .sellPrice(100.0)
                .build();
        when(teaJpa.findById(1)).thenReturn(Optional.ofNullable(tea));

        //Act
        TeaResponseDto result = teaService.getByID(1);

        //Assert
        Assertions.assertThat(result).isNotNull();
        // verify that the repository's findById method was called once
        verify(teaJpa, times(1)).findById(1);
    }

    @Test
    public void TeaService_UpdateTea_ReturnTea(){
        Tea tea = Tea.builder()
                .name("tea")
                .type("tea type")
                .build();
        TeaRequestDto teaDto = TeaRequestDto.builder()
                .name("updated tea")
                .type("updated tea type")
                .build();
        when(teaJpa.save(Mockito.any(Tea.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(teaJpa.findById(1)).thenReturn(Optional.ofNullable(tea));

        //Act
        Tea result = teaService.update(teaDto,1);

        //Assert
        Assertions.assertThat(result.getName()).isEqualTo("updated tea");
        Assertions.assertThat(result.getType()).isEqualTo("updated tea type");
        verify(teaJpa, times(1)).save(Mockito.any(Tea.class));

    }

    @Test
    public void TeaService_UpdatePartialTea_ReturnTeaWithUnchangedFields() {
        // Arrange
        Tea existingTea = Tea.builder()
                .name("Original Tea")
                .type("Original Type")
                .description("Original Description")
                .sellPrice(10.0)
                .build();

        TeaRequestDto teaDto = TeaRequestDto.builder()
                .name("Updated Tea")
                .build();  // Only 'name' is provided

        when(teaJpa.findById(1)).thenReturn(Optional.of(existingTea));
        when(teaJpa.save(Mockito.any(Tea.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Tea updatedTea = teaService.update(teaDto, 1);

        // Assert
        Assertions.assertThat(updatedTea.getName()).isEqualTo("Updated Tea");
        Assertions.assertThat(updatedTea.getType()).isEqualTo("Original Type");  // Should remain unchanged
        Assertions.assertThat(updatedTea.getDescription()).isEqualTo("Original Description");  // Should remain unchanged
        Assertions.assertThat(updatedTea.getSellPrice()).isEqualTo(10.0);  // Should remain unchanged
    }

    @Test
    // The id is not generated in fact,the id=1 haven't been attached to the object
    public void TeaService_DeleteTeaByID_ReturnTeaDto(){
        Tea tea = Tea.builder()
                .name("new tea")
                .type("new tea type")
                .build();
        when(teaJpa.findById(1)).thenReturn(Optional.ofNullable(tea));

        //Act

        //Assert
        //?? not Sure if it is tested?
        assertAll(()->teaService.delete(1));
        // Verify that findById() was called
        verify(teaJpa, times(1)).findById(1);
//        // Verify that delete() was not called
        verify(teaJpa, times(1)).delete(any(Tea.class));
    }

}
