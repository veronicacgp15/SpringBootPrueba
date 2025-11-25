package com.vcgp.proyecto.infrastructure.entity;

import com.vcgp.proyecto.infrastructure.enums.Section;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RackTest {

    private static class RackAll extends IdentifiableEntity {}

    @Test
    @DisplayName("Debería asignar y obtener todas las propiedades de Rack")
    void shouldAssignAllProperties() {
        //Given
        Rack rack = new Rack();
        Warehouse warehouse = new Warehouse();
        warehouse.setId(10L);

        //When
        rack.setName("Rack A-01");
        rack.setTipo(Section.A);
        rack.setWarehouse(warehouse);

        //Then
        assertEquals("Rack A-01", rack.getName());
        assertEquals(Section.A, rack.getTipo());
        assertNotNull(rack.getWarehouse(), "El warehouse no debería ser nulo");
        assertEquals(10L, rack.getWarehouse().getId());
    }
}
