package com.tea.management.service;


import com.tea.management.inventory.entity.Tea;
import com.tea.management.inventory.dto.TeaRequestDto;
import com.tea.management.inventory.dto.TeaResponseDto;
import com.tea.management.inventory.repository.TeaRepositoryJpa;
import com.tea.management.inventory.service.TeaServiceImp;
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
    public void TeaService_CreatTea_ReturnTeaDto() {
        Tea tea = new Tea();
        tea.setName("new tea");
        tea.setType("new tea type");

        TeaRequestDto teaDto = new TeaRequestDto();
        teaDto.setName("new tea");
        teaDto.setType("new tea type");

        when(teaJpa.save(Mockito.any(Tea.class))).thenReturn(tea);

        //Act
        Tea result = teaService.create(teaDto);

        //Assert
        Assertions.assertThat(result).isNotNull();
        // verify that the repository's save method was called once
        verify(teaJpa, times(1)).save(Mockito.any(Tea.class));
    }

    @Test
    public void TeaService_GetAllTeas_ReturnsResponseDto() {
        Tea tea1 = new Tea();
        tea1.setName("new tea1");
        tea1.setType("new tea type1");
        tea1.setSellPrice(100.1);
        
        Tea tea2 = new Tea();
        tea2.setName("new tea2");
        tea2.setType("new tea type2");
        tea2.setSellPrice(100.1);
        
        List<Tea> teas = Arrays.asList(tea1, tea2);
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
    public void TeaService_GetTeaByID_ReturnTeaDto() {
        Tea tea = new Tea();
        tea.setName("new tea");
        tea.setType("new tea type");
        tea.setSellPrice(100.0);
        
        when(teaJpa.findById(1)).thenReturn(Optional.ofNullable(tea));

        //Act
        TeaResponseDto result = teaService.getByID(1);

        //Assert
        Assertions.assertThat(result).isNotNull();
        // verify that the repository's findById method was called once
        verify(teaJpa, times(1)).findById(1);
    }

    @Test
    public void TeaService_UpdateTea_ReturnTea() {
        Tea tea = new Tea();
        tea.setName("tea");
        tea.setType("tea type");
        
        TeaRequestDto teaDto = new TeaRequestDto();
        teaDto.setName("updated tea");
        teaDto.setType("updated tea type");
        
        when(teaJpa.save(Mockito.any(Tea.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(teaJpa.findById(1)).thenReturn(Optional.ofNullable(tea));

        //Act
        Tea result = teaService.update(teaDto, 1);

        //Assert
        Assertions.assertThat(result.getName()).isEqualTo("updated tea");
        Assertions.assertThat(result.getType()).isEqualTo("updated tea type");
        verify(teaJpa, times(1)).save(Mockito.any(Tea.class));

    }

    @Test
    public void TeaService_UpdatePartialTea_ReturnTeaWithUnchangedFields() {
        // Arrange
        Tea existingTea = new Tea();
        existingTea.setName("Original Tea");
        existingTea.setType("Original Type");
        existingTea.setDescription("Original Description");
        existingTea.setSellPrice(10.0);

        TeaRequestDto teaDto = new TeaRequestDto();
        teaDto.setName("Updated Tea");

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
    public void TeaService_DeleteTeaByID_ReturnTeaDto() {
        Tea tea = new Tea();
        tea.setName("new tea");
        tea.setType("new tea type");
        
        when(teaJpa.findById(1)).thenReturn(Optional.ofNullable(tea));

        //Act

        //Assert
        //?? not Sure if it is tested?
        assertAll(() -> teaService.delete(1));
        // Verify that findById() was called
        verify(teaJpa, times(1)).findById(1);
//        // Verify that delete() was not called
        verify(teaJpa, times(1)).delete(any(Tea.class));
    }

}
