package com.vcgp.proyecto.infrastructure.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WarehouseTest {

    public static final long EXPECTED = 500L;
    public static final String EL_CLIENTE_NO_DEBERÍA_SER_NULO = "El cliente no debería ser nulo";
    public static final String EL_ID_DEL_CLIENTE_NO_COINCIDE = "El ID del cliente no coincide";

    @Test
    @DisplayName("Debería asignar y obtener todas las propiedades de Warehouse")
    void testComplet(){
        //Given
        Warehouse warehouse = new Warehouse();
        Client client = new Client();
        client.setId(1L);
        client.setName("Prueba de Almacen");

        //When
        warehouse.setFamily("Electrónica");
        warehouse.setSize(EXPECTED);
        warehouse.setClient(client);

        //Then
        assertEquals("Electrónica", warehouse.getFamily());
        assertEquals(EXPECTED, warehouse.getSize());
        assertNotNull(warehouse.getClient(), EL_CLIENTE_NO_DEBERÍA_SER_NULO);
        assertEquals(1L, warehouse.getClient().getId(), EL_ID_DEL_CLIENTE_NO_COINCIDE);



    }
}
