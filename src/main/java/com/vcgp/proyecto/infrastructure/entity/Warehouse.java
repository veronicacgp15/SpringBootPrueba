package com.vcgp.proyecto.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "warehouses")
@Getter // Usa Getter
@Setter // Usa Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true) // Importante para herencia
@EqualsAndHashCode(callSuper = true) // Importante para herencia
@SuperBuilder
public class Warehouse extends IdentifiableEntity {

    @ManyToOne
    @JoinColumn(name="client_id", nullable = false)
    private Client client;

    private String family;
    private Long size;
/*
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

 */
}
