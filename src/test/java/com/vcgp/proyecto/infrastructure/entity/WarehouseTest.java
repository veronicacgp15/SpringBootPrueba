package com.vcgp.proyecto.infrastructure.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.vcgp.proyecto.infrastructure.utils.ConstansTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WarehouseTest {


    private Warehouse warehouse;
    private Client client;

    @BeforeEach
    void setUp(){
        warehouse = new Warehouse();
        client = new Client();
        client.setId(1L);
        client.setName("Prueba de Almacen");
    }

    @Test
    @DisplayName("Debería asignar y obtener todas las propiedades de Warehouse")
    void testComplet(){
         //When
        warehouse.setFamily(Warehouse_ATRIBUTO_FAMILY);
        warehouse.setSize(EXPECTED);
        warehouse.setClient(client);

        //Then
        assertEquals("Electrónica", warehouse.getFamily());
        assertEquals(EXPECTED, warehouse.getSize());
        assertNotNull(warehouse.getClient(), EL_CLIENTE_NO_DEBERÍA_SER_NULO);
        assertEquals(1L, warehouse.getClient().getId(), EL_ID_DEL_CLIENTE_NO_COINCIDE);
    }
}
