package com.vcgp.proyecto.infraestructure.entity;

import com.vcgp.proyecto.infrastructure.entity.Client;
import com.vcgp.proyecto.infrastructure.entity.Warehouse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WarehouseTest {

    @Test
    @DisplayName("Debería asignar y obtener todas las propiedades de Warehouse")
    void testComplet(){

        Warehouse warehouse = new Warehouse();
        Client client = new Client();
        client.setId(1L);
        client.setName("Prueba de Almacen");

        warehouse.setFamily("Electrónica");
        warehouse.setSize(500L);
        warehouse.setClient(client);

        assertEquals("Electrónica", warehouse.getFamily());
        assertEquals(500L, warehouse.getSize());
        assertNotNull(warehouse.getClient(), "El cliente no debería ser nulo");
        assertEquals(1L, warehouse.getClient().getId(), "El ID del cliente no coincide");



    }
}
