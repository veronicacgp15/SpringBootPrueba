package com.vcgp.proyecto.application.service;

import com.vcgp.proyecto.application.dto.WarehouseSimpleDTO;
import com.vcgp.proyecto.application.services.WarehouseServiceImpl;
import com.vcgp.proyecto.infrastructure.entity.Client;
import com.vcgp.proyecto.infrastructure.entity.Warehouse;
import com.vcgp.proyecto.infrastructure.repository.ClientRepository;
import com.vcgp.proyecto.infrastructure.repository.WarehouseRepository;
import com.vcgp.proyecto.infrastructure.utils.ErrorMessages;
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

import static com.vcgp.proyecto.infrastructure.utils.Constans.CLIENT;
import static com.vcgp.proyecto.infrastructure.utils.Constans.WAREHOUSE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceImpTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    private Client testClient;
    private Warehouse warehouse1;
    private Warehouse warehouse2;
    private WarehouseSimpleDTO dtoToCreate;


    @BeforeEach
    void setUp(){
        testClient = new Client();
        testClient.setId(10L);
        testClient.setName("Test Client");

        warehouse1 = new Warehouse();
        warehouse1.setId(1L);
        warehouse1.setName("Main Warehouse");
        warehouse1.setFamily("Electronics");
        warehouse1.setSize(1000L);
        warehouse1.setClient(testClient);

        warehouse2 = new Warehouse();
        warehouse2.setId(2L);
        warehouse2.setName("Secondary Warehouse");
        warehouse2.setFamily("Groceries");
        warehouse2.setSize(500L);
        warehouse2.setClient(testClient);

        dtoToCreate = new WarehouseSimpleDTO(null, "New Warehouse", 10L,"Furniture", 2000L);


    }

    @Test
    @DisplayName("Debería devolver una lista de WarehouseSimpleDTO")
    void shouldReturnListOfWarehouseDTOs(){
        //Given
        when(warehouseRepository.findAll()).thenReturn(List.of(warehouse1, warehouse2));

        //When
        List<WarehouseSimpleDTO> result = warehouseService.findAll();

        //Then
        assertNotNull(result);
        assertEquals(2, result.size(), "La lista debería contener 2 almacenes");
        assertEquals("Main Warehouse", result.get(0).name());
        assertEquals(10L, result.get(1).client_id(), "El ID del cliente del segundo almacén no coincide");

        // Verificamos que el método del repositorio fue llamado
        verify(warehouseRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería devolver una lista vacía de WarehouseSimpleDTO cuando no existen almacenes")
    void shouldListNoWarehousesExist() {
        // Given
        when(warehouseRepository.findAll()).thenReturn(List.of());

        // When
        List<WarehouseSimpleDTO> result = warehouseService.findAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty(), "La lista debería estar vacía");
        verify(warehouseRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería devolver un DTO de almacén para un ID válido")
    void shouldIdIsValid() {
        // Given
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse1));

        // When
        WarehouseSimpleDTO result = warehouseService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Main Warehouse", result.name());
        assertEquals(10L, result.client_id());
        verify(warehouseRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería lanzar una excepción si el ID del almacén no se encuentra")
    void shouldIdNotFound() {
        // Given
        Long nonExistentId = 99L;
        when(warehouseRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // When & Then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            warehouseService.findById(nonExistentId);
        });

        assertEquals(ErrorMessages.notFoundById(WAREHOUSE, nonExistentId), exception.getMessage());
        verify(warehouseRepository, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("Debería crear un nuevo almacén exitosamente")
    void shouldCreateSuccessfully() {
        // Given
        when(clientRepository.findById(dtoToCreate.client_id())).thenReturn(Optional.of(testClient));

        // 2. Simulamos la operación de guardado en la base de datos
        when(warehouseRepository.save(any(Warehouse.class))).thenAnswer(invocation -> {
            Warehouse warehouse = invocation.getArgument(0);
            warehouse.setId(3L); // Simulamos que la BD le asigna un ID
            return warehouse;
        });

        // When
        WarehouseSimpleDTO result = warehouseService.create(dtoToCreate);

        // Then
        assertNotNull(result);
        assertEquals(3L, result.id(), "El ID del nuevo almacén no es el esperado");
        assertEquals("New Warehouse", result.name());
        assertEquals(10L, result.client_id());

        // Verificamos las interacciones con los mocks
        verify(clientRepository, times(1)).findById(10L);
        verify(warehouseRepository, times(1)).save(any(Warehouse.class));
    }

    @Test
    @DisplayName("Debería lanzar una excepción al crear si el cliente asociado no existe")
    void shouldExceptionCreateClientNotFound() {
        // Given
        Long nonExistentClientId = 99L;
        WarehouseSimpleDTO dtoWithInvalidClient = new WarehouseSimpleDTO(null, "Test", nonExistentClientId, "Test", 100L);
        when(clientRepository.findById(nonExistentClientId)).thenReturn(Optional.empty());

        // When & Then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            warehouseService.create(dtoWithInvalidClient);
        });

        assertEquals(ErrorMessages.associationNotFound(CLIENT, nonExistentClientId), exception.getMessage());

        // Verificamos que el guardado nunca se intentó
        verify(warehouseRepository, never()).save(any(Warehouse.class));
    }


    @Test
    @DisplayName("Debería editar un almacén existente sin cambiar el cliente")
    void shouldEdit() {
        // Given
        Long existingId = 1L;
        WarehouseSimpleDTO updatedDto = new WarehouseSimpleDTO(existingId, "Updated Name", 10L, "Updated Family", 1500L);

        when(warehouseRepository.findById(existingId)).thenReturn(Optional.of(warehouse1));
        when(warehouseRepository.save(any(Warehouse.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        WarehouseSimpleDTO result = warehouseService.edit(existingId, updatedDto);

        // Then
        assertNotNull(result);
        assertEquals("Updated Name", result.name());
        assertEquals("Updated Family", result.family());
        assertEquals(1500L, result.size());
        assertEquals(10L, result.client_id());

        verify(warehouseRepository, times(1)).findById(existingId);
        verify(warehouseRepository, times(1)).save(any(Warehouse.class));

        verify(clientRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("Debería lanzar una excepción al editar si el almacén no existe")
    void shouldEditNotFound() {
        // Given
        Long nonExistentId = 99L;
        WarehouseSimpleDTO updatedDto = new WarehouseSimpleDTO(nonExistentId, "Test", 10L, "Test", 100L);
        when(warehouseRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // When & Then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            warehouseService.edit(nonExistentId, updatedDto);
        });

        assertEquals(ErrorMessages.editNotFound(WAREHOUSE, nonExistentId), exception.getMessage());
        verify(warehouseRepository, never()).save(any(Warehouse.class));
    }


    @Test
    @DisplayName("Debería eliminar un almacén si existe")
    void shouldDeleteWarehouseWhenExists() {
        // Given
        Long idToDelete = 1L;
        when(warehouseRepository.existsById(idToDelete)).thenReturn(true);
        doNothing().when(warehouseRepository).deleteById(idToDelete); // Para métodos void

        // When
        warehouseService.delete(idToDelete);

        // Then

        verify(warehouseRepository, times(1)).existsById(idToDelete);
        verify(warehouseRepository, times(1)).deleteById(idToDelete);
    }

    @Test
    @DisplayName("Debería lanzar una excepción al eliminar si el almacén no existe")
    void shouldThrowExceptionOnDeleteWhenWarehouseNotFound() {
        // Given
        Long nonExistentId = 99L;
        when(warehouseRepository.existsById(nonExistentId)).thenReturn(false);

        // When & Then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            warehouseService.delete(nonExistentId);
        });

        assertEquals(ErrorMessages.deleteNotFound(WAREHOUSE, nonExistentId), exception.getMessage());


        verify(warehouseRepository, never()).deleteById(nonExistentId);
    }





}
