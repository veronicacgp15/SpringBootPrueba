package com.vcgp.proyecto.application.services;

import com.vcgp.proyecto.application.dto.ClientRequestDTO;
import com.vcgp.proyecto.application.dto.ClientResponseDTO;
import com.vcgp.proyecto.application.usecase.ClientService;
import com.vcgp.proyecto.infrastructure.entity.Client;
import com.vcgp.proyecto.infrastructure.repository.ClientRepository;
import com.vcgp.proyecto.infrastructure.utils.ErrorMessages;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.vcgp.proyecto.infrastructure.utils.Constans.CLIENT;

@Service
public class ClientServiceImpl implements ClientService {
/*
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

        //existingClient.setName(clientRequestDTO.toEntity().getName());
        existingClient.setName(clientRequestDTO.name());


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



*/
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
    public ClientResponseDTO create(ClientRequestDTO clientRequestDTO) {
        // Usa el método toEntity() del DTO de petición
        Client clientToSave = clientRequestDTO.toEntity();
        Client savedClient = clientRepository.save(clientToSave);
        return ClientResponseDTO.fromEntity(savedClient);
    }

    @Override
    public ClientResponseDTO edit(Long id, ClientRequestDTO clientRequestDTO) {
        // 1. Busca el cliente existente o lanza una excepción si no lo encuentra.
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessages.notFoundById(CLIENT, id)));

        // 2. Actualiza las propiedades del cliente encontrado con los datos del DTO de petición.
        existingClient.setName(clientRequestDTO.name());

        // 3. Guarda el cliente actualizado en la base de datos.
        Client updatedClient = clientRepository.save(existingClient);

        // 4. Devuelve un DTO de respuesta con los datos actualizados.
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
