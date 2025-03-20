package com.tea.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tea.management.inventory.entity.Tea;
import com.tea.management.inventory.controller.TeaController;
import com.tea.management.inventory.dto.TeaRequestDto;
import com.tea.management.inventory.dto.TeaResponseDto;
import com.tea.management.inventory.service.TeaMapper;
import com.tea.management.inventory.service.TeaService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = TeaController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TeaControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeaService teaService;

    @Autowired
    private ObjectMapper objectMapper;
    private Tea tea;


    @BeforeEach
    public void init() {
        tea = new Tea();
        tea.setName("new tea");
        tea.setType("new tea type");
    }

    @Test
    public void TeaController_CreateTea_ReturnCreated() throws Exception {
        //invoke the first argument which is the tea as the response, the actual logic of create not invoked
        TeaRequestDto teaUpdated =new TeaRequestDto();
        teaUpdated.setName("New Nea DTO");
        teaUpdated.setType("new tea type DTO");
        when(teaService.create(teaUpdated)).thenReturn(TeaMapper.toEntity(teaUpdated));

        ResultActions response = mockMvc.perform(post("/api/tea/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teaUpdated)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(teaUpdated.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(teaUpdated.getType())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void TeaController_GetAllTea_ReturnResponseDto() throws Exception {
        TeaResponseDto responseDto = new TeaResponseDto();
        responseDto.setName("New Tea");
        responseDto.setType("New Type");
        List<TeaResponseDto> list = Arrays.asList(responseDto);
        when(teaService.getAllTeas()).thenReturn(list);

        ResultActions response = mockMvc.perform(get("/api/tea/getAll"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void TeaController_GetTeaByID_ReturnCreated() throws Exception {
        TeaResponseDto responseDto = new TeaResponseDto();
        responseDto.setName("new tea");
        responseDto.setType("new tea type");

        when(teaService.getByID(1)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/api/tea/1"));


        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(tea.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(tea.getType())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void TeaController_UpdateTea_ReturnTea() throws Exception {
        TeaRequestDto teaUpdated = new TeaRequestDto();
        teaUpdated.setName("New Nea DTO");
        teaUpdated.setType("new tea type DTO");
        when(teaService.update(teaUpdated, 1)).thenReturn(TeaMapper.toEntity(teaUpdated));

        //simulates sending an HTTP PUT request to /api/tea/update/1.
        ResultActions response = mockMvc.perform(put("/api/tea/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teaUpdated)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(teaUpdated.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(teaUpdated.getType())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void TeaController_DeleteTea_ReturnString() throws Exception {
        doNothing().when(teaService).delete(1);

        ResultActions response = mockMvc.perform(delete("/api/tea/delete/1"));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
