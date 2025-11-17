package com.vcgp.proyecto.infrastructure.validation.validators;

import com.vcgp.proyecto.application.dto.ClientResponseDTO;
import com.vcgp.proyecto.infrastructure.entity.Client;
import com.vcgp.proyecto.infrastructure.repository.ClientRepository;
import com.vcgp.proyecto.infrastructure.validation.annotations.UniqueClientName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueClientNameValidator implements ConstraintValidator<UniqueClientName, ClientResponseDTO> {

    private final ClientRepository clientRepository;

    public UniqueClientNameValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    // 👈 3. El método ahora recibe un DTO
    public boolean isValid(ClientResponseDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.name() == null) {
            return true;
        }

        Optional<Client> existingClient = clientRepository.findByNameAndIdNot(dto.name(), dto.id());

        return existingClient.isEmpty();
    }
}
