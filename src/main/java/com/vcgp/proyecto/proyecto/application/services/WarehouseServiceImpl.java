package com.vcgp.proyecto.proyecto.application.services;

import com.vcgp.proyecto.proyecto.application.dto.WarehouseSimpleDTO;
import com.vcgp.proyecto.proyecto.application.usecase.WarehouseService;
import com.vcgp.proyecto.proyecto.infrastructure.entity.Client;
import com.vcgp.proyecto.proyecto.infrastructure.entity.Warehouse;
import com.vcgp.proyecto.proyecto.infrastructure.repository.ClientRepository;
import com.vcgp.proyecto.proyecto.infrastructure.repository.WarehouseRepository;
import com.vcgp.proyecto.proyecto.infrastructure.utils.ErrorMessages;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final ClientRepository clientRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository,
                                ClientRepository clientRepository) {
        this.warehouseRepository = warehouseRepository;
        this.clientRepository = clientRepository;
    }


    @Override
    public List<WarehouseSimpleDTO> findAll() {
        return this.warehouseRepository.findAll().stream()
                .map(WarehouseSimpleDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public WarehouseSimpleDTO findById(Long id) {
        Warehouse warehouse = this.warehouseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        ErrorMessages.notFoundById("Warehouse", id)
                ));
        return WarehouseSimpleDTO.fromEntity(warehouse);
    }

    @Override
    public WarehouseSimpleDTO create(WarehouseSimpleDTO requestWarehouse) {
        Client client = clientRepository.findById(requestWarehouse.client_id())
                .orElseThrow(() -> new NoSuchElementException(
                        ErrorMessages.associationNotFound("Client", requestWarehouse.client_id())
                ));

        Warehouse warehouseToSave = new Warehouse();
        warehouseToSave.setName(requestWarehouse.name());
        warehouseToSave.setFamily(requestWarehouse.family());
        warehouseToSave.setSize(requestWarehouse.size());
        warehouseToSave.setClient(client);


        Warehouse savedWarehouse = this.warehouseRepository.save(warehouseToSave);
        return WarehouseSimpleDTO.fromEntity(savedWarehouse);
    }

    @Override
    public WarehouseSimpleDTO edit(Long id, WarehouseSimpleDTO requestWarehouse) {
        Warehouse existingWarehouse = this.warehouseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        ErrorMessages.editNotFound("Warehouse", id)
                ));

        existingWarehouse.setName(requestWarehouse.name());
        existingWarehouse.setFamily(requestWarehouse.family());
        existingWarehouse.setSize(requestWarehouse.size());

        if (!existingWarehouse.getClient().getId().equals(requestWarehouse.client_id())) {
            Client newClient = clientRepository.findById(requestWarehouse.client_id())
                    .orElseThrow(() -> new NoSuchElementException(
                    ErrorMessages.clientNotFound(requestWarehouse.client_id())
            ));

            existingWarehouse.setClient(newClient);
        }
        Warehouse updatedWarehouse = this.warehouseRepository.save(existingWarehouse);

        return WarehouseSimpleDTO.fromEntity(updatedWarehouse);
    }

    @Override
    public void delete(Long id) {
        if (!this.warehouseRepository.existsById(id)) {
            throw new NoSuchElementException(ErrorMessages.deleteNotFound("Warehouse", id));
        }
        this.warehouseRepository.deleteById(id);
    }


}
