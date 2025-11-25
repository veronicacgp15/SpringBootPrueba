package com.vcgp.proyecto.application.service;

import com.vcgp.proyecto.application.dto.RackResponseDTO;
import com.vcgp.proyecto.application.services.RackServiceImpl;
import com.vcgp.proyecto.infrastructure.utils.ConstansTest;
import com.vcgp.proyecto.infrastructure.entity.Rack;
import com.vcgp.proyecto.infrastructure.entity.Warehouse;
import com.vcgp.proyecto.infrastructure.enums.Section;
import com.vcgp.proyecto.infrastructure.repository.RackRepository;
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

import static com.vcgp.proyecto.infrastructure.utils.ConstansTest.*;
import static com.vcgp.proyecto.infrastructure.utils.Constans.RACK;
import static com.vcgp.proyecto.infrastructure.utils.Constans.WAREHOUSE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RackSeriveImpTest {


    @Mock
    private RackRepository rackRepository;
    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private RackServiceImpl rackService;

    private Rack rack1;
    private Rack rack2;
    private Warehouse warehouse;
    private RackResponseDTO rackDto1;
    private RackResponseDTO rackDtoToCreate;

    @BeforeEach
    void setUp(){
        //Given
        warehouse = new Warehouse();
        warehouse.setId(100L);
        warehouse.setName(ConstansTest.WAREHOSE_TEST);

        rack1 = new Rack();
        rack1.setId(1L);
        rack1.setName("Rack A");
        rack1.setTipo(Section.A);
        rack1.setWarehouse(warehouse);

        rack2 = new Rack();
        rack2.setId(2L);
        rack2.setName("Rack B");
        rack2.setTipo(Section.B);
        rack2.setWarehouse(warehouse);

        rackDto1 = new RackResponseDTO(1L,"Rack A", Section.A, 100L);
        rackDtoToCreate = new RackResponseDTO(null, "New Rack", Section.B, 100L);

    }
    @Test
    @DisplayName("Debería devolver una lista de RackResponseDTO")
    void shouldReturnListOfRackResponseDTO(){
        //Given
        when(rackRepository.findAll()).thenReturn(List.of(rack1, rack2));

        //When
        List<RackResponseDTO> result = rackService.findAll();

        //Then
        assertNotNull(result, RACKS_NO_DEBE_SER_NULA);
        assertEquals(2, result.size(), DEBERIA_DEVOLVER_2);
        assertEquals("Rack A", result.get(0).name(),"Rack 1:"+ NOMBRE_RACK_NO_COINCIDE);
        assertEquals("Rack B", result.get(1).name(),"Rack2"+ NOMBRE_RACK_NO_COINCIDE);
        verify(rackRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería devolver una lista vacía de RackResponseDTO cuando no existen racks")
    void shouldReturnEmptyListOfRackResponseDTOWhenNoRacksExist() {
        // Given
        when(rackRepository.findAll()).thenReturn(List.of());

        // When
        List<RackResponseDTO> result = rackService.findAll();

        // Then
        assertNotNull(result, "La lista de racks no debería ser nula");
        assertTrue(result.isEmpty(), "La lista de racks debería estar vacía");
        verify(rackRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería devolver un RackResponseDTO cuando se encuentra por un ID válido")
    void shouldReturnRackIdValid(){
        //Given
        when(rackRepository.findById(1L)).thenReturn(java.util.Optional.of(rack1));

        //When
        RackResponseDTO result = rackService.findById(1L);

        //THEN
        assertNotNull(result, EL_RACK_ENCONTRADO_NULO);
        assertEquals(1L, result.id(), ID_RACK_NO_COINCIDE);
        assertEquals("Rack A", result.name(), NOMBRE_RACK_NO_COINCIDE);
        assertEquals(Section.A, result.tipo(), TIPO_RACK_NO_COINCIDE);
        assertEquals(100L, result.warehouseId(), ID_ALCAMEN_NO_COINCIDE);
        verify(rackRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería lanzar NoSuchElementException cuando el ID no se encuentra")
    void shoultThrowExceptionWhenIdNotFound(){
        // Given
        when(rackRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            rackService.findById(99L);
        }, NO_SUCH_ELEMENT_ID_NO_ENCONTRADO);


        assertEquals(ErrorMessages.notFoundById(RACK, 99L), thrown.getMessage());
        verify(rackRepository, times(1)).findById(99L);
    }


    @DisplayName("Debería crear un nuevo Rack")
    void shoulCreate(){
        //Given

        when(warehouseRepository.findById(rackDtoToCreate.warehouseId())).thenReturn(Optional.of(warehouse));

        when(rackRepository.save(any(Rack.class))).thenAnswer(invocation -> {
            Rack rack = invocation.getArgument(0);
            rack.setId(3L);
            return rack;
        });
        // When
        RackResponseDTO result = rackService.create(rackDtoToCreate);

        // Then
        assertNotNull(result, EL_RACK_ENCONTRADO_NULO);
        assertEquals(3L, result.id(), ID_RACK_NO_COINCIDE);
        assertEquals("New Rack", result.name(), NOMBRE_RACK_NO_COINCIDE);
        assertEquals(Section.B, result.tipo(), TIPO_RACK_NO_COINCIDE);
        assertEquals(100L, result.warehouseId(),  ID_ALCAMEN_NO_COINCIDE);


        verify(warehouseRepository, times(1)).findById(rackDtoToCreate.warehouseId());
        verify(rackRepository, times(1)).save(any(Rack.class));
    }

    @Test
    @DisplayName("Debería lanzar NoSuchElementException al editar si el nuevo Warehouse no existe")
    void shouldThrowExceptionWhenEditingRackIfNewWarehouseNotFound() {
        // Given
        Long existingRackId = 1L;
        Long nonExistentWarehouseId = 999L;
        RackResponseDTO updatedDto = new RackResponseDTO(existingRackId,
                                    "Rack with Bad Warehouse", Section.C,
                                        nonExistentWarehouseId);


        when(rackRepository.findById(existingRackId)).thenReturn(Optional.of(rack1));
        when(warehouseRepository.findById(nonExistentWarehouseId)).thenReturn(Optional.empty());

        // When & Then
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            rackService.edit(existingRackId, updatedDto);
        }, NUEVO_ALMACÉN_NO_EXISTE);

        assertEquals(ErrorMessages.associationNotFound(WAREHOUSE, nonExistentWarehouseId),
                    thrown.getMessage());


        verify(rackRepository, times(1)).findById(existingRackId);
        verify(warehouseRepository, times(1)).findById(nonExistentWarehouseId);
        verify(rackRepository, never()).save(any(Rack.class));
    }


    @Test
    @DisplayName("Debería eliminar un rack si existe")
    void shouldDeleteRackWhenExists() {
        // Given
        Long rackIdToDelete = 1L;

        when(rackRepository.existsById(rackIdToDelete)).thenReturn(true);
        doNothing().when(rackRepository).deleteById(rackIdToDelete);

        // When
        rackService.delete(rackIdToDelete);

        // Then
        verify(rackRepository, times(1)).existsById(rackIdToDelete);
        verify(rackRepository, times(1)).deleteById(rackIdToDelete);
    }

    @Test
    @DisplayName("Debería lanzar NoSuchElementException al intentar eliminar un rack que no existe")
    void shouldThrowExceptionWhenDeletingNonExistentRack() {
        // Given
        Long nonExistentRackId = 99L;
        when(rackRepository.existsById(nonExistentRackId)).thenReturn(false);

        // When & Then
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            rackService.delete(nonExistentRackId);
        }, "El mensaje de error debe ser descriptivo");

        assertEquals(ErrorMessages.deleteNotFound(RACK, nonExistentRackId), thrown.getMessage());
        verify(rackRepository, times(1)).existsById(nonExistentRackId);
        verify(rackRepository, never()).deleteById(nonExistentRackId);
    }

}
