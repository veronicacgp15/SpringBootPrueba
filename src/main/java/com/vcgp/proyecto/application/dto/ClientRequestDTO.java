package com.vcgp.proyecto.application.dto;

import com.vcgp.proyecto.infrastructure.entity.Client;
import com.vcgp.proyecto.infrastructure.validation.annotations.UniqueClientName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@UniqueClientName
public record ClientRequestDTO(
        @NotBlank(message = "El nombre no puede estar vacío.")
        @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ .]+$", message = "El nombre solo puede contener letras, espacios y puntos.")
        String name
) {

    public Client toEntity() {
        Client client = new Client();
        client.setName(this.name);
        return client;
    }
}

