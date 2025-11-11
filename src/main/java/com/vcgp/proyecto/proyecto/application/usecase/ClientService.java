package com.vcgp.proyecto.proyecto.application.usecase;

import com.vcgp.proyecto.proyecto.application.dto.ClientResponseDTO;

import java.util.List;

public interface ClientService {

     List<ClientResponseDTO> findAll();
     ClientResponseDTO findById(Long id);
     ClientResponseDTO create(ClientResponseDTO requestclient);
     ClientResponseDTO edit(Long id, ClientResponseDTO requestclient);
     void delete(Long id);

     //ClientResponseDTO save(ClientResponseDTO client);
}
