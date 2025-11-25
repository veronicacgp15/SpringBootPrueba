package com.vcgp.proyecto.infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcgp.proyecto.application.dto.RackResponseDTO;
import com.vcgp.proyecto.application.usecase.RackService;
import com.vcgp.proyecto.infrastructure.enums.Section;
import com.vcgp.proyecto.infrastructure.utils.SuccessMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.validation.Validator;

import java.util.List;
import java.util.NoSuchElementException;

import static com.vcgp.proyecto.infrastructure.utils.Constans.RACK;
import static com.vcgp.proyecto.infrastructure.utils.ConstansTest.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RackController.class)
class RackControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RackService rackService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private Validator validator;

    private RackResponseDTO rackResponseDTO;


    @BeforeEach
    void setUp(){
        rackResponseDTO = new RackResponseDTO(TEST_CLIENT_ID, "Rack de Prueba", Section.A, 1L);
    }

    @Test
    @DisplayName("Get /api/racks - Debe devolver una lista de racks y estado 200 Ok")
    void shouldFindAllRacks() throws Exception{
        //Given
        when(rackService.findAll()).thenReturn(List.of(rackResponseDTO));

        //When & Then
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Rack de Prueba"));
    }


    @Test
    @DisplayName("GET /api/racks/{id} - Debe devolver un rack si existe y estado 200 OK")
    void shouldFindRackByIdExists() throws Exception {
        // GIVEN
        when(rackService.findById(TEST_ID)).thenReturn(rackResponseDTO);

        // WHEN & THEN
        mockMvc.perform(get(BASE_URL + "/" + TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_ID))
                .andExpect(jsonPath("$.name").value("Rack de Prueba"));
    }

    @Test
    @DisplayName("GET /api/racks/{id} - Debe devolver 404 Not Found si el rack no existe")
    void shouldNotFoundIdNotExist() throws Exception {
        // GIVEN
        String errorMessage = RACK_NO_ENCONTRADO_UP + "con ID: 99";
        when(rackService.findById(99L)).thenThrow(new NoSuchElementException(errorMessage));

        // WHEN & THEN
        mockMvc.perform(get(BASE_URL + "/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(errorMessage));
    }


    @Test
    @DisplayName("POST /api/racks - Debe crear un rack y devolver estado 201 Created")
    void shouldCreateRack() throws Exception {
        // GIVEN
        when(rackService.create(any(RackResponseDTO.class))).thenReturn(rackResponseDTO);

        // WHEN & THEN
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rackResponseDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(TEST_ID))
                .andExpect(jsonPath("$.name").value("Rack de Prueba"));
    }

    @Test
    @DisplayName("PUT /api/racks/{id} - Debe actualizar un rack y devolver estado 200 OK")
    void shouldUpdateRack() throws Exception {
        // GIVEN
        RackResponseDTO updatedRack = new RackResponseDTO(TEST_CLIENT_ID, "Rack Actualizado", Section.A, 200L);
        when(rackService.edit(eq(TEST_ID), any(RackResponseDTO.class))).thenReturn(updatedRack);

        // WHEN & THEN
        mockMvc.perform(put(BASE_URL + "/" + TEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRack)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rack Actualizado"))
                .andExpect(jsonPath("$.warehouseId").value(200L));

    }

    @Test
    @DisplayName("PUT /api/racks/{id} - Debe devolver 404 Not Found si el rack a actualizar no existe")
    void shouldUpdateDoesNotExist() throws Exception {
        // GIVEN
        String errorMessage = RACK_NO_ENCONTRADO_UP;
        RackResponseDTO requestBody = new RackResponseDTO(null, "Cambiado",Section.A, 0L);

        when(rackService.edit(eq(99L), any(RackResponseDTO.class)))
                .thenThrow(new NoSuchElementException(errorMessage));

        // WHEN & THEN
        mockMvc.perform(put(BASE_URL + "/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(errorMessage));
    }


    @Test
    @DisplayName("DELETE /api/racks/{id} - Debe eliminar un rack y devolver estado 200 OK con mensaje de éxito")
    void shouldDeleteRack() throws Exception {
        // GIVEN
        doNothing().when(rackService).delete(TEST_ID);
        String expectedMessage = SuccessMessages.entityDeleted(RACK, TEST_ID);

        // WHEN & THEN
        mockMvc.perform(delete(BASE_URL + "/" + TEST_ID))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));
    }

    @Test
    @DisplayName("DELETE /api/racks/{id} - Debe devolver 404 Not Found si el rack a eliminar no existe")
    void shouldDeleteDoesNotExist() throws Exception {
        // GIVEN
        String errorMessage = RACK_NO_ENCONTRADO_DL;
        doThrow(new NoSuchElementException(errorMessage)).when(rackService).delete(99L);


        // WHEN & THEN
        mockMvc.perform(delete(BASE_URL + "/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(errorMessage));
    }
}
