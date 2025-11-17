package com.vcgp.proyecto.infraestructure.entity;

import com.vcgp.proyecto.infrastructure.entity.IdentifiableEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentifibleEntityTest {


    private static class ConcreteEntity extends IdentifiableEntity{}

    @Test
    @DisplayName("Debería asignar y obtener el nombre correctamente")
    void testName() {
        ConcreteEntity entity = new ConcreteEntity();
        String expectedName = "Nombre de Prueba";

        entity.setName(expectedName);
        String actualName = entity.getName();

        assertEquals(expectedName, actualName);
    }
    @Test
    @DisplayName("Debería asignar y obtener el ID correctamente")
    void testId() {

        ConcreteEntity entity = new ConcreteEntity();
        Long expectedId = 123L;


        entity.setId(expectedId);
        Long actualId = entity.getId();

        assertEquals(expectedId, actualId);
    }

}
