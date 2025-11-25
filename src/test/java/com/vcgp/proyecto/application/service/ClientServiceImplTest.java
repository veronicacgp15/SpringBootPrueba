package com.vcgp.proyecto.application.service;

import com.vcgp.proyecto.application.dto.ClientRequestDTO;
import com.vcgp.proyecto.application.dto.ClientResponseDTO;
import com.vcgp.proyecto.application.services.ClientServiceImpl;
import com.vcgp.proyecto.infrastructure.entity.Client;
import com.vcgp.proyecto.infrastructure.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.vcgp.proyecto.infrastructure.utils.ConstansTest.TEST_CLIENT;
import static com.vcgp.proyecto.infrastructure.utils.ConstansTest.UPDATED_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;
    private ClientResponseDTO clientDTO;

    @BeforeEach
    void setUp(){
        //Given
        client = new Client();

        //When
        client.setId(1L);
        client.setName(TEST_CLIENT);

        //Given
        clientDTO = new ClientResponseDTO(1L, TEST_CLIENT);
    }

    @Test
    @DisplayName("Debería devolver un cliente cuando se encuentra por un ID válido")
    void shouldReturListOfClients(){

        //Given
        when(clientRepository.findAll()).thenReturn(List.of(client));

        //When
        List<ClientResponseDTO> result = clientService.findAll();

        //Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TEST_CLIENT, result.get(0).name());
        verify(clientRepository,times(1)).findAll();

    }

    @Test
    @DisplayName("Debería devolver un cliente cuando se encuentra por un ID válido")
    void shouldThrowExceptionWhenIdNotFound(){
        //Given
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        //When        //Then
        assertThrows(NoSuchElementException.class, () -> {
            clientService.findById(99L);
        });

        verify(clientRepository, times(1)).findById(99L);

    }

    @Test
    @DisplayName("Debería crear y devolver un nuevo cliente")
    void shouldCreateAndReturnNewClient() {
        // Given: Un DTO de PETICIÓN para crear el cliente
        ClientRequestDTO requestDTO = new ClientRequestDTO(TEST_CLIENT);

        // El método toEntity() del DTO de petición crea un cliente sin ID
        Client clientToSave = requestDTO.toEntity();

        // El repositorio devuelve el cliente ya guardado, con ID
        Client savedClient = new Client();
        savedClient.setId(1L);
        savedClient.setName(TEST_CLIENT);

        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);

        // When: Llamamos al servicio con el DTO de PETICIÓN
        ClientResponseDTO result = clientService.create(requestDTO);

        // Then: El resultado es un DTO de RESPUESTA con los datos correctos
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(TEST_CLIENT, result.name());
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("Debería editar un cliente existente")
    void shouldEditExistingClient() {
        // Given: Un DTO de PETICIÓN con los datos actualizados
        ClientRequestDTO updatedRequestDto = new ClientRequestDTO(UPDATED_NAME);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When: Llamamos al servicio con el DTO de PETICIÓN
        ClientResponseDTO result = clientService.edit(1L, updatedRequestDto);

        // Then
        assertNotNull(result);
        assertEquals(UPDATED_NAME, result.name());
        verify(clientRepository).findById(1L);
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("Debería eliminar un cliente si existe")
    void shouldDeleteClientWhenExists() {
        // Given
        when(clientRepository.existsById(1L)).thenReturn(true);

        doNothing().when(clientRepository).deleteById(1L); // Para métodos void (que no devuelven nada), usamos doNothing()

        // When
        clientService.delete(1L);

        //Then
        verify(clientRepository).existsById(1L);
        verify(clientRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debería lanzar una excepción al intentar eliminar un cliente que no existe")
    void shouldThrowExceptionWhenDeletingNonExistentClient() {
        // Given
        when(clientRepository.existsById(99L)).thenReturn(false);

        // When
        assertThrows(NoSuchElementException.class, () -> {
            clientService.delete(99L);
        });
        // Then
        verify(clientRepository, never()).deleteById(99L);
    }


}
