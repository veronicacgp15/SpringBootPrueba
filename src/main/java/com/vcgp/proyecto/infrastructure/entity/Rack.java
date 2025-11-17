package com.vcgp.proyecto.infrastructure.entity;

import com.vcgp.proyecto.infrastructure.enums.Section;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "racks")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class Rack extends IdentifiableEntity {

    @Enumerated(EnumType.STRING)
    private Section tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="warehouse_id", nullable = false)
    private Warehouse warehouse;

    public Section getTipo() {
        return tipo;
    }

    public void setTipo(Section tipo) {
        this.tipo = tipo;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

}
