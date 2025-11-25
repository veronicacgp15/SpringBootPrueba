package com.vcgp.proyecto.infrastructure.entity;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.vcgp.proyecto.infrastructure.utils.ConstansTest.EL_NOMBRE_NO_COINCIDE;
import static com.vcgp.proyecto.infrastructure.utils.ConstansTest.PETRA;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {

    IdentifiableEntity client = Mockito.mock(IdentifiableEntity.class, Mockito.CALLS_REAL_METHODS);

    @Test
    void shouldAssignName(){
        //Inyecto con mokito para no crear una instancia nueva
        client.setName(PETRA);
        //When
        String expected = PETRA;
        String actual = client.getName();
        //Then
        assertEquals(expected, actual, EL_NOMBRE_NO_COINCIDE);
    }


}
