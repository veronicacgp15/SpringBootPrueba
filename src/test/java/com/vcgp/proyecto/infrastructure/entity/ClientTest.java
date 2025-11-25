package com.vcgp.proyecto.infrastructure.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {

    public static final String PETRA = "Petra";
    public static final String EL_NOMBRE_NO_COINCIDE = "El nombre no coincide";

    private static class ClientAll extends IdentifiableEntity {}

    @Test
    void shouldAssignName(){
        //Given
        ClientAll client = new ClientAll();
        client.setName(PETRA);
        //When
        String expected = PETRA;
        String actual = client.getName();
        //Then
        assertEquals(expected, actual, EL_NOMBRE_NO_COINCIDE);
    }


}
