package com.vcgp.proyecto.infraestructure.entity;

import com.vcgp.proyecto.infrastructure.entity.IdentifiableEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTest {

    private static class ClientAll extends IdentifiableEntity {}

    @Test
    void testNameAll(){
        ClientAll client = new ClientAll();
        client.setName("Petra");
        String expected = "Petra";
        String actual = client.getName();
        assertEquals(expected, actual, "El nombre no coincide");
    }


}
