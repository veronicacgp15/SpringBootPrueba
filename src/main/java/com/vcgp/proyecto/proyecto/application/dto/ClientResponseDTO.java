package com.vcgp.proyecto.proyecto.application.dto;

import com.vcgp.proyecto.proyecto.infrastructure.entity.Client;

public record ClientResponseDTO (Long id,
                                 String name)

{

    public static ClientResponseDTO fromEntity(Client clientEntity) {
        return new ClientResponseDTO(
                clientEntity.getId(),
                clientEntity.getName()
        );
    }
    public Client toEntity() {
        Client client = new Client();
        if (this.id != null) {
            client.setId(this.id);
        }
        client.setName(this.name);
        return client;
    }

}

