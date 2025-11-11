package com.vcgp.proyecto.proyecto.models;

/*
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id; */


import jakarta.persistence.*;

@Entity
@Table(name = "racks")
public class Rack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Section tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="warehouse_id", nullable = false)
    private Warehouse warehouse;

    public Rack() {
    }

    public Rack(Long id, Section tipo, Warehouse warehouse) {
        this.id = id;
        this.tipo = tipo;
        this.warehouse = warehouse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Rack{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", warehouse=" + (warehouse != null ? warehouse.getName() : "null") +
                '}';
    }
}
