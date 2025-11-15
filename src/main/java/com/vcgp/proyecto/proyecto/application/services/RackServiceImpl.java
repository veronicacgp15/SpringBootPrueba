package com.vcgp.proyecto.proyecto.application.services;

import com.vcgp.proyecto.proyecto.application.dto.RackResponseDTO;
import com.vcgp.proyecto.proyecto.application.usecase.RackService;
import com.vcgp.proyecto.proyecto.infrastructure.entity.Rack;
import com.vcgp.proyecto.proyecto.infrastructure.entity.Warehouse;
import com.vcgp.proyecto.proyecto.infrastructure.repository.RackRepository;
import com.vcgp.proyecto.proyecto.infrastructure.repository.WarehouseRepository;
import com.vcgp.proyecto.proyecto.infrastructure.utils.ErrorMessages;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.vcgp.proyecto.proyecto.infrastructure.utils.Constans.RACK;
import static com.vcgp.proyecto.proyecto.infrastructure.utils.Constans.WAREHOUSE;

@Service
public class RackServiceImpl implements RackService {

    private final RackRepository rackRepository;
    private final WarehouseRepository warehouseRepository;

    public RackServiceImpl(RackRepository rackRepository, WarehouseRepository warehouseRepository) {
        this.rackRepository = rackRepository;
        this.warehouseRepository = warehouseRepository;
    }


    @Override
    @Transactional
    public List<RackResponseDTO> findAll() {
        return this.rackRepository.findAll().stream()
                .map(RackResponseDTO::fromEntity)
                .collect(Collectors.toList());

    }

    @Override
    public RackResponseDTO findById(Long aLong) {
        return null;
    }

    @Override
    @Transactional // Necesario porque interactúa con dos repositorios
    public RackResponseDTO create(RackResponseDTO requestRack) {
        Warehouse warehouse = warehouseRepository.findById(requestRack.warehouseId())
                .orElseThrow(() -> new NoSuchElementException(
                        ErrorMessages.associationNotFound(WAREHOUSE, requestRack.warehouseId())));

        Rack rackToSave = requestRack.toEntity();
        rackToSave.setWarehouse(warehouse);
        Rack savedRack = this.rackRepository.save(rackToSave);
        return RackResponseDTO.fromEntity(savedRack);
    }

    @Override
    @Transactional
    public RackResponseDTO edit(Long id, RackResponseDTO requestRack) {
        Rack existingRack = this.rackRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessages.editNotFound(RACK, id)));

        existingRack.setTipo(requestRack.tipo());
        existingRack.setName(requestRack.name());

        Long existingWarehouseId = existingRack.getWarehouse() != null ? existingRack.getWarehouse().getId() : null;
        if (requestRack.warehouseId() != null && !requestRack.warehouseId().equals(existingWarehouseId)) {
            Warehouse newWarehouse = warehouseRepository.findById(requestRack.warehouseId())
                    .orElseThrow(() -> new NoSuchElementException(
                            ErrorMessages.associationNotFound(WAREHOUSE, requestRack.warehouseId())
                    ));
            existingRack.setWarehouse(newWarehouse);
        }

        Rack updatedRack = this.rackRepository.save(existingRack);
        return RackResponseDTO.fromEntity(updatedRack);
    }

    @Override
    public void delete(Long id) {
        if (!this.rackRepository.existsById(id)) {
            throw new NoSuchElementException(ErrorMessages.deleteNotFound(RACK, id));
        }
        this.rackRepository.deleteById(id);
    }


}
