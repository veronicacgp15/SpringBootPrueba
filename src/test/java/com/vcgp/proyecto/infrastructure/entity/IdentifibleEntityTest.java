package com.vcgp.proyecto.infrastructure.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class IdentifibleEntityTest {


    private static class ConcreteEntity extends IdentifiableEntity{}

    @Test
    @DisplayName("Debería asignar y obtener el nombre correctamente")
    void shouldAssignName() {
        //Given
        ConcreteEntity entity = new ConcreteEntity();
        String expectedName = "Nombre de Prueba";
        //When
        entity.setName(expectedName);
        String actualName = entity.getName();
        //Then
        assertEquals(expectedName, actualName);
    }
    @Test
    @DisplayName("Debería asignar y obtener el ID correctamente")
    void shouldAssignId() {
        //Given
        ConcreteEntity entity = new ConcreteEntity();
        Long expectedId = 123L;


        entity.setId(expectedId);
        Long actualId = entity.getId();

        //Then
        assertEquals(expectedId, actualId);
    }

}
