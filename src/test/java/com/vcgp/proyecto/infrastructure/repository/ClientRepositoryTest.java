package com.vcgp.proyecto.infrastructure.repository;

import com.vcgp.proyecto.infrastructure.entity.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ClientRepositoryTest {

   @Autowired
    private TestEntityManager entityManager;

   @Autowired
    private ClientRepository clientRepository;

    @Test
    @DisplayName("Debe encontrar un cliente por nombre cuando no se ignora ningún ID")
    void shouldNameIgnoreIsNull() {
        // GIVEN
        Client client = new Client();
        client.setName("John Doe");
        entityManager.persistAndFlush(client);

        // WHEN
        Optional<Client> foundClient = clientRepository.findByNameAndIdNot("John Doe", null);

        // THEN
        assertThat(foundClient).isPresent();
        assertThat(foundClient.get().getName()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("NO debe encontrar un cliente si su propio ID es ignorado")
    void shouldNameWhenItsIdIsIgnored() {
        // GIVEN
        Client client = new Client();
        client.setName("Jane Doe");
        Client savedClient = entityManager.persistAndFlush(client);

        // WHEN
        Optional<Client> foundClient = clientRepository.findByNameAndIdNot("Jane Doe", savedClient.getId());

        // THEN
        assertThat(foundClient).isNotPresent();
    }


    @Test
    @DisplayName("Debe encontrar un cliente aunque se ignore el ID de OTRO cliente")
    void shouldIgnoringAnotherClientsId() {
        // GIVEN
        Client clientToFind = new Client();
        clientToFind.setName("Client A");
        entityManager.persistAndFlush(clientToFind);

        Client clientToIgnore = new Client();
        clientToIgnore.setName("Client B");
        Client savedClientToIgnore = entityManager.persistAndFlush(clientToIgnore);

        // WHEN:
        Optional<Client> foundClient = clientRepository.findByNameAndIdNot("Client A", savedClientToIgnore.getId());

        // THEN:
        //assertThat(foundClient).isPresent();
        //assertThat(foundClient.get().getName()).isEqualTo("Client A");
        // THEN: esta es una manera mas eficiente de comparar
        assertThat(foundClient).isPresent()
                .get()
                .extracting(Client::getName)
                .isEqualTo("Client A");
    }

    @Test
   @DisplayName("Deberia devolver un Optinal vacio si el nombre no existe")
    void shouldNameNotExist(){
       //When
       Optional<Client> foundClient = clientRepository.findByNameAndIdNot("Unknown Person", null);
       //Then
       assertThat(foundClient).isEmpty();
   }

}
