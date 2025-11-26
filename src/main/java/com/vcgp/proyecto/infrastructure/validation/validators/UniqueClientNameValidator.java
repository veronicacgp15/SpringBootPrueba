package com.vcgp.proyecto.infrastructure.validation.validators;

import com.vcgp.proyecto.application.dto.ClientRequestDTO;
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
    public boolean isValid(ClientRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.name() == null) {
            return true;
        }

        Optional<Client> existingClient = clientRepository.findByNameAndIdNot(dto.name(), null);

        return existingClient.isEmpty();
    }
}
