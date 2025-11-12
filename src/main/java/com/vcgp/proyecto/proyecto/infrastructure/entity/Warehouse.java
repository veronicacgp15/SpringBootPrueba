package com.vcgp.proyecto.proyecto.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "warehouses")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse extends IdentifiableEntity {

    @ManyToOne
    @JoinColumn(name="client_id", nullable = false)
    private Client client;

    private String family;
    private Long size;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
