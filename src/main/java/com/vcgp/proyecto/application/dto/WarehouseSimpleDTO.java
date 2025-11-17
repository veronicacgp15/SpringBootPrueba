package com.vcgp.proyecto.application.dto;

import com.vcgp.proyecto.infrastructure.entity.Client;
import com.vcgp.proyecto.infrastructure.entity.Warehouse;

public record WarehouseSimpleDTO(
        Long id,
        String name,
        Long client_id,
        String family,
        Long size
        )

{
    public static WarehouseSimpleDTO fromEntity(Warehouse ware) {
        Long clientId = null;
        if (ware.getClient() != null) {
            clientId = ware.getClient().getId();
        }

        return new WarehouseSimpleDTO(
                ware.getId(),
                ware.getName(),
                clientId,
                ware.getFamily(),
                ware.getSize()
        );
    }


    public Warehouse toEntity() {
        Warehouse warehouse = new Warehouse();


        if (this.id != null) {
            warehouse.setId(this.id);
        }
        warehouse.setName(this.name);


        if (this.client_id != null) {
            Client clientReference = new Client();
            clientReference.setId(this.client_id);
            warehouse.setClient(clientReference);
        }

        return warehouse;
    }
}
