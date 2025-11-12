package com.vcgp.proyecto.proyecto.application.services;

import com.vcgp.proyecto.proyecto.application.dto.ClientResponseDTO;
import com.vcgp.proyecto.proyecto.application.usecase.ClientService;
import com.vcgp.proyecto.proyecto.infrastructure.entity.Client;
import com.vcgp.proyecto.proyecto.infrastructure.repository.ClientRepository;
import com.vcgp.proyecto.proyecto.infrastructure.utils.ErrorMessages;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.vcgp.proyecto.proyecto.infrastructure.utils.Constans.CLIENT;

@Service
public class ClientServiceImpl implements ClientService {

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
        .orElseThrow(() -> new NoSuchElementException(ErrorMessages.notFoundById(CLIENT, id)));


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
    public ClientResponseDTO edit(Long id, ClientResponseDTO clientRequestDTO) {
        Client existingClient = clientRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException(ErrorMessages.notFoundById(CLIENT, id)));

        existingClient.setName(clientRequestDTO.toEntity().getName());
        Client updatedClient = clientRepository.save(existingClient);
        return ClientResponseDTO.fromEntity(updatedClient);
    }
    @Override
    public void delete(Long id) {
        if (!this.clientRepository.existsById(id)) {
            throw new NoSuchElementException(ErrorMessages.deleteNotFound(CLIENT, id));
        }
        this.clientRepository.deleteById(id);
    }







}
