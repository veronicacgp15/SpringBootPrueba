package com.vcgp.proyecto.infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcgp.proyecto.application.dto.WarehouseSimpleDTO;
import com.vcgp.proyecto.application.usecase.WarehouseService;
import com.vcgp.proyecto.infrastructure.utils.SuccessMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import javax.xml.validation.Validator;

import java.util.List;
import java.util.NoSuchElementException;

import static com.vcgp.proyecto.infrastructure.utils.Constans.WAREHOUSE;
import static com.vcgp.proyecto.infrastructure.utils.ConstansTest.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WarehouseController.class)
class WarehouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WarehouseService warehouseService;

    @MockBean
    private Validator validator;

    private WarehouseSimpleDTO warehouseDTO;

    @BeforeEach
    void setUp(){
        warehouseDTO = new WarehouseSimpleDTO(TEST_ID, WAREHOSE_TEST, TEST_CLIENT_ID,"Warehose Test",1000L);
    }

    @Test
    @DisplayName("GET /api/warehouses - Debe devolver una lista de almacenes y estado 200 OK")
    void shouldFindAllWarehouses() throws Exception {
        // GIVEN
        when(warehouseService.findAll()).thenReturn(List.of(warehouseDTO));

        // WHEN & THEN
        mockMvc.perform(get(WAREHOUSE_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(TEST_ID))
                .andExpect(jsonPath("$[0].name").value(WAREHOSE_TEST));
    }

    @Test
    @DisplayName("GET /api/warehouses - Debe devolver 500 si hay un error en el servicio")
    void shouldFindAllFails() throws Exception {
        // GIVEN
        when(warehouseService.findAll()).thenThrow(new RuntimeException("Error de base de datos simulado"));

        // WHEN & THEN
        mockMvc.perform(get(WAREHOUSE_BASE_URL))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("")); // Sin cuerpo en el error 500
    }


    @Test
    @DisplayName("POST /api/warehouses - Debe crear un almacén y devolver estado 201 Created")
    void shouldCreateWarehouse() throws Exception {
        // GIVEN
        when(warehouseService.create(any(WarehouseSimpleDTO.class))).thenReturn(warehouseDTO);

        // WHEN & THEN
        mockMvc.perform(post(WAREHOUSE_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(warehouseDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(TEST_ID))
                .andExpect(jsonPath("$.name").value(WAREHOSE_TEST));
    }

    @Test
    @DisplayName("POST /api/warehouses - Debe devolver 404 Not Found si falla por dependencia no existente")
    void shouldCreateFailsToDependency() throws Exception {
        // GIVEN
        String errorMessage = "Dependencia de cliente no encontrada";
        when(warehouseService.create(any(WarehouseSimpleDTO.class)))
                .thenThrow(new NoSuchElementException(errorMessage));

        // WHEN & THEN
        mockMvc.perform(post(WAREHOUSE_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(warehouseDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(errorMessage));
    }


    @Test
    @DisplayName("PUT /api/warehouses/{id} - Debe actualizar un almacén y devolver estado 200 OK")
    void shouldUpdateWarehouse() throws Exception {
        // GIVEN
        WarehouseSimpleDTO updatedDTO = new WarehouseSimpleDTO(TEST_ID, "Almacén Actualizado", 1L,"Prueba",1000L);
        when(warehouseService.edit(eq(TEST_ID), any(WarehouseSimpleDTO.class))).thenReturn(updatedDTO);

        // WHEN & THEN
        mockMvc.perform(put(WAREHOUSE_BASE_URL + "/" + TEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Almacén Actualizado"));
    }

    @Test
    @DisplayName("PUT /api/warehouses/{id} - Debe devolver 404 Not Found si el almacén no existe")
    void shouldUpdateDoesNotExist() throws Exception {
        // GIVEN
        String errorMessage = ALMACEN_NO_ENCONTRADO + "para actualizar";
        when(warehouseService.edit(eq(99L), any(WarehouseSimpleDTO.class)))
                .thenThrow(new NoSuchElementException(errorMessage));

        // WHEN & THEN
        mockMvc.perform(put(WAREHOUSE_BASE_URL + "/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(warehouseDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(errorMessage));
    }

    @Test
    @DisplayName("DELETE /api/warehouses/{id} - Debe eliminar un almacén y devolver estado 200 OK con mensaje de éxito")
    void shouldDeleteWarehouse() throws Exception {
        // GIVEN
        doNothing().when(warehouseService).delete(TEST_ID);
        String expectedMessage = SuccessMessages.entityDeleted(WAREHOUSE, TEST_ID);

        // WHEN & THEN
        mockMvc.perform(delete(WAREHOUSE_BASE_URL + "/" + TEST_ID))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));
    }

    @Test
    @DisplayName("DELETE /api/warehouses/{id} - Debe devolver 404 Not Found si el almacén a eliminar no existe")
    void shouldDeleteDoesNotExist() throws Exception {
        // GIVEN
        String errorMessage = ALMACEN_NO_ENCONTRADO + "para eliminar";
        doThrow(new NoSuchElementException(errorMessage)).when(warehouseService).delete(99L);

        // WHEN & THEN
        mockMvc.perform(delete(WAREHOUSE_BASE_URL + "/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(errorMessage));
    }
}
