package com.vcgp.proyecto.application.dto;

import com.vcgp.proyecto.infrastructure.entity.Rack;
import com.vcgp.proyecto.infrastructure.entity.Warehouse;
import com.vcgp.proyecto.infrastructure.enums.Section;

public record RackResponseDTO(Long id,
                              String name,
                              Section tipo,
                              Long warehouseId)

{
    public static RackResponseDTO fromEntity(Rack rack) {
        Long  warehouseId= null;
        if (rack.getWarehouse() != null) {
            warehouseId = rack.getWarehouse().getId();
        }

        return new RackResponseDTO(
                rack.getId(),
                rack.getName(),
                rack.getTipo(),
                warehouseId
        );
    }

    public Rack toEntity() {
        Rack rack = new Rack();
        if (this.id != null) {
            rack.setId(this.id);
        }
        rack.setName(this.name);
        rack.setTipo(this.tipo);

        if (this.warehouseId != null) {
            Warehouse warehouseReference = new Warehouse();
            warehouseReference.setId(this.warehouseId);
            rack.setWarehouse(warehouseReference);
        }
        return rack;
    }
    
}


