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


}

