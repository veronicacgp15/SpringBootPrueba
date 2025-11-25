package com.vcgp.proyecto.application.usecase;

import com.vcgp.proyecto.application.dto.ClientRequestDTO;
import com.vcgp.proyecto.application.dto.ClientResponseDTO;

import java.util.List;


public interface ClientService {
    List<ClientResponseDTO> findAll();

    ClientResponseDTO findById(Long id);


    ClientResponseDTO create(ClientRequestDTO clientRequestDTO);


    ClientResponseDTO edit(Long id, ClientRequestDTO clientRequestDTO);

    void delete(Long id);
}
