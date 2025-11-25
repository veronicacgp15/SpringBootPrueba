package com.vcgp.proyecto.infrastructure.entity;

import com.vcgp.proyecto.infrastructure.enums.Section;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "racks") // O como se llame tu tabla
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

public class Rack extends IdentifiableEntity {

    @Enumerated(EnumType.STRING)
    private Section tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="warehouse_id", nullable = false)
    private Warehouse warehouse;


}
