package com.vcgp.proyecto.infrastructure.repository;

import com.vcgp.proyecto.infrastructure.entity.Client;
import com.vcgp.proyecto.infrastructure.entity.Rack;
import com.vcgp.proyecto.infrastructure.entity.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class RackRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RackRepository rackRepository;

    private Warehouse savedWarehouse;

    @BeforeEach
    void setUp() {
       //GIVEN
        Client client = new Client();
        client.setName("Cliente de Prueba");
        Client savedClient = entityManager.persistAndFlush(client);


        Warehouse warehouse = new Warehouse();
        warehouse.setName("Almacén Principal");
        warehouse.setClient(savedClient);

        //THEN
        savedWarehouse = entityManager.persistAndFlush(warehouse);
    }

    @Test
    @DisplayName("Debe guardar y encontrar un rack asociado a un almacén")
    void shouldSaveAndFindRack() {
        // GIVEN
        Rack newRack = new Rack();
        newRack.setName("RACK UNO");
        newRack.setWarehouse(savedWarehouse);

        // WHEN
        Rack savedRack = rackRepository.save(newRack);
        entityManager.flush();
        entityManager.clear();

        // THEN
        Optional<Rack> foundRack = rackRepository.findById(savedRack.getId());

        assertThat(foundRack).isPresent();
        assertThat(foundRack.get().getName()).isEqualTo("RACK UNO");
        assertThat(foundRack.get().getWarehouse()).isNotNull();
        assertThat(foundRack.get().getWarehouse().getId()).isEqualTo(savedWarehouse.getId());
    }

    @Test
    @DisplayName("Debe actualizar el nombre de un rack")
    void shouldUpdateRackCode() {
        // GIVEN
        Rack rack = new Rack();
        rack.setName("OLD CODE");
        rack.setWarehouse(savedWarehouse);
        Rack savedRack = entityManager.persistAndFlush(rack);

        // WHEN
        Optional<Rack> rackToUpdateOpt = rackRepository.findById(savedRack.getId());
        assertThat(rackToUpdateOpt).isPresent();

        Rack rackToUpdate = rackToUpdateOpt.get();
        rackToUpdate.setName("NEW CODE");
        rackRepository.save(rackToUpdate);
        entityManager.flush();
        entityManager.clear();

        // THEN
        Optional<Rack> updatedRack = rackRepository.findById(savedRack.getId());
        assertThat(updatedRack).isPresent();
        assertThat(updatedRack.get().getName()).isEqualTo("NEW CODE");
    }
}
