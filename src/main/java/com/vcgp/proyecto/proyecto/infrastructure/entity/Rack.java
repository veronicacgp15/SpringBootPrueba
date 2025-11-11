package com.vcgp.proyecto.proyecto.infrastructure.entity;

/*
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id; */


import com.vcgp.proyecto.proyecto.infrastructure.enums.Section;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "racks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Rack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Section tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="warehouse_id", nullable = false)
    private Warehouse warehouse;


}
