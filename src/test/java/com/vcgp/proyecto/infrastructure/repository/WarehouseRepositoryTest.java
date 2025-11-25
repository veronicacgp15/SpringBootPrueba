package com.vcgp.proyecto.infrastructure.repository;

import com.vcgp.proyecto.infrastructure.entity.Warehouse;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class WarehouseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Test
    @DisplayName("Debe guardar y encontrar un almacén por su ID")
    void shouldSaveAndFindWarehouse() {
        // GIVEN
        Warehouse newWarehouse = new Warehouse();
        newWarehouse.setName("Almacén Central");

        // WHEN
        Warehouse savedWarehouse = warehouseRepository.save(newWarehouse);
        entityManager.flush();
        entityManager.clear();

        // THEN
        Optional<Warehouse> foundWarehouse = warehouseRepository.findById(savedWarehouse.getId());

        assertThat(foundWarehouse).isPresent();
        assertThat(foundWarehouse.get().getName()).isEqualTo("Almacén Central");
    }

    @Test
    @DisplayName("Debe devolver todos los almacenes guardados")
    void shouldFindAllWarehouses() {
        // GIVEN
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setName("Almacén Norte");
        entityManager.persist(warehouse1);

        Warehouse warehouse2 = new Warehouse();
        warehouse2.setName("Almacén Sur");
        entityManager.persist(warehouse2);

        entityManager.flush();

        // WHEN
        List<Warehouse> warehouses = warehouseRepository.findAll();

        // THEN
        assertThat(warehouses).hasSize(2);
    }

    @Test
    @DisplayName("Debe eliminar un almacén correctamente")
    void shouldDeleteWarehouse() {
        // GIVEN
        Warehouse warehouse = new Warehouse();
        warehouse.setName("Almacén a Eliminar");
        Warehouse savedWarehouse = entityManager.persistAndFlush(warehouse);

        // WHEN
        warehouseRepository.deleteById(savedWarehouse.getId());
        entityManager.flush();
        entityManager.clear();

        // THEN
        Optional<Warehouse> foundWarehouse = warehouseRepository.findById(savedWarehouse.getId());
        assertThat(foundWarehouse).isNotPresent();
    }
}
