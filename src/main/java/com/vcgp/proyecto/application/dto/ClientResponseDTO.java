package com.vcgp.proyecto.application.dto;

import com.vcgp.proyecto.infrastructure.entity.Client;
import com.vcgp.proyecto.infrastructure.validation.annotations.UniqueClientName;

public record ClientResponseDTO (Long id,
                                 String name)

{

    public static ClientResponseDTO fromEntity(Client clientEntity) {
        return new ClientResponseDTO(
                clientEntity.getId(),
                clientEntity.getName()
        );
    }
//    public Client toEntity() {
//        Client client = new Client();
//        if (this.id != null) {
//            client.setId(this.id);
//        }
//        client.setName(this.name);
//        return client;
//    }

}

