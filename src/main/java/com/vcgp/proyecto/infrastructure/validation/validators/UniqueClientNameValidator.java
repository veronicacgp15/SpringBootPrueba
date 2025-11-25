package com.vcgp.proyecto.infrastructure.validation.validators;

import com.vcgp.proyecto.application.dto.ClientRequestDTO;
import com.vcgp.proyecto.application.dto.ClientResponseDTO;
import com.vcgp.proyecto.infrastructure.entity.Client;
import com.vcgp.proyecto.infrastructure.repository.ClientRepository;
import com.vcgp.proyecto.infrastructure.validation.annotations.UniqueClientName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueClientNameValidator implements
        ConstraintValidator<UniqueClientName, ClientRequestDTO> {

    private final ClientRepository clientRepository;

    public UniqueClientNameValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    // El método ahora es consistente con la declaración de la clase.
    public boolean isValid(ClientRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.name() == null) {
            return true; // La validación de nulos la hace @NotBlank.
        }

        // 2. CORRECCIÓN: ClientRequestDTO no tiene ID. Para una creación, el ID a ignorar es siempre null.
        // Tu consulta `findByNameAndIdNot` ya maneja correctamente el caso de un ID nulo.
        Optional<Client> existingClient = clientRepository.findByNameAndIdNot(dto.name(), null);

        // La validación es exitosa si NO se encuentra un cliente con ese nombre.
        return existingClient.isEmpty();
    }
}
