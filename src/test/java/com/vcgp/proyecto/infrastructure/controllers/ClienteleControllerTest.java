package com.vcgp.proyecto.infrastructure.controllers;

import com.vcgp.proyecto.application.dto.ClientRequestDTO;
import com.vcgp.proyecto.infrastructure.repository.ClientRepository;
import com.vcgp.proyecto.infrastructure.validation.validators.UniqueClientNameValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcgp.proyecto.application.dto.ClientResponseDTO;
import com.vcgp.proyecto.application.usecase.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import jakarta.validation.Validator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteleController.class)
class ClienteleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;


    @MockBean
    private UniqueClientNameValidator uniqueClientNameValidator;

    @MockBean
    private Validator validator;

    private ClientResponseDTO clientResponseDTO;
    private ClientRequestDTO clientRequestDTO;

    @BeforeEach
    void setUp() {
        clientResponseDTO = new ClientResponseDTO(1L, "Cliente de Prueba");
        clientRequestDTO = new ClientRequestDTO("Nuevo Cliente");
    }


    @Test
    @DisplayName("GET /api/clients - Debe devolver una lista de clientes y estado 200 OK")
    void shouldFindAllClients() throws Exception {
        //Given
        when(clientService.findAll()).thenReturn(List.of(clientResponseDTO));

        //When & Then
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Cliente de Prueba"));
    }

    @Test
    @DisplayName("GET /api/clients/{id} - Debe devolver un cliente si existe y estado 200 OK")
    void shouldFindClientById_whenExists() throws Exception {
        //Given
        when(clientService.findById(1L)).thenReturn(clientResponseDTO);

        //When & Then
        mockMvc.perform(get("/api/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Cliente de Prueba"));
    }

    @Test
    @DisplayName("GET /api/clients/{id} - Debe devolver 404 Not Found si el cliente no existe")
    void shouldReturnNotFound_whenFindByIdDoesNotExist() throws Exception {
        //Given
        when(clientService.findById(99L)).thenThrow(new NoSuchElementException("Cliente no encontrado"));
        //When & Then
        mockMvc.perform(get("/api/clients/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente no encontrado"));
    }

    @Test
    @DisplayName("POST /api/clients - Debe crear un cliente y devolverlo con estado 201 Created")
    void shouldCreateClient() throws Exception {
        //Given
        when(clientService.create(any(ClientRequestDTO.class))).thenReturn(clientResponseDTO);

        //When & Then
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Cliente de Prueba"));
    }


    @Test
    @DisplayName("PUT /api/clients/{id} - Debe actualizar un cliente y devolverlo con estado 200 OK")
    void shouldUpdateClient() throws Exception {
        //Given
        ClientResponseDTO updatedClient = new ClientResponseDTO(1L, "Cliente Actualizado");

        //When & Then
        when(clientService.edit(eq(1L), any(ClientRequestDTO.class))).thenReturn(updatedClient);


        mockMvc.perform(put("/api/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ClientRequestDTO("Cliente Actualizado"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cliente Actualizado"));
    }


    @Test
    @DisplayName("DELETE /api/clients/{id} - Debe eliminar un cliente y devolver estado 204 No Content")
    void shouldDeleteClient() throws Exception {

        //Given
        doNothing().when(clientService).delete(1L);

        //When & Then
        mockMvc.perform(delete("/api/clients/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/clients/{id} - Debe devolver 404 Not Found si el cliente a eliminar no existe")
    void shouldReturnNotFound_whenDeleteDoesNotExist() throws Exception {
        //When & Then
        doThrow(new NoSuchElementException("No se puede eliminar, cliente no encontrado")).when(clientService).delete(99L);
        mockMvc.perform(delete("/api/clients/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se puede eliminar, cliente no encontrado"));
    }
}