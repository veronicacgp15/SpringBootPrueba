package com.vcgp.proyecto.proyecto.application.services;

import com.vcgp.proyecto.proyecto.application.dto.ClientResponseDTO;
import com.vcgp.proyecto.proyecto.application.usecase.ClientService;
import com.vcgp.proyecto.proyecto.infrastructure.entity.Client;
import com.vcgp.proyecto.proyecto.infrastructure.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    //Inyeccion de dependencia utilizando el constructor
    private final ClientRepository clientRepository;


    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<ClientResponseDTO> findAll() {
         List<Client> clients = clientRepository.findAll();

         return clients.stream()
                 .map(ClientResponseDTO::fromEntity)
                 .collect(Collectors.toList());

    }

    @Override
    public ClientResponseDTO findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Client not found with ID: " + id));
        return ClientResponseDTO.fromEntity(client);
    }

    @Override
    public ClientResponseDTO create(ClientResponseDTO requestclient) {
        Client clientToSave = requestclient.toEntity();
        clientToSave.setId(null);
        Client savedClient = clientRepository.save(clientToSave);
        return ClientResponseDTO.fromEntity(savedClient);
    }

    @Override
    public ClientResponseDTO edit(Long id, ClientResponseDTO requestclient) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado: " + id));
                existingClient.setName(requestclient.name());

        Client updatedClient = clientRepository.save(existingClient);

        return ClientResponseDTO.fromEntity(updatedClient);
    }

    @Override
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new NoSuchElementException("Client not found with ID: " + id);
        }

        clientRepository.deleteById(id);
    }


}
