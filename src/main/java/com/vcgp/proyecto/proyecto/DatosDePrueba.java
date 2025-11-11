package com.vcgp.proyecto.proyecto;

import com.vcgp.proyecto.proyecto.models.Client;
import com.vcgp.proyecto.proyecto.models.Rack;
import com.vcgp.proyecto.proyecto.models.Section;
import com.vcgp.proyecto.proyecto.models.Warehouse;

import java.util.Arrays;
import java.util.List;

public class DatosDePrueba {

    public static List<Client> crearClients(){
        return Arrays.asList(
                new Client(1L, "Cliente A - Supermercados DIA"),
                new Client(2L, "Cliente B - Logística Rápida S.L."),
                new Client(3L, "Cliente C - Almacenes del Sur")
        );
    }

    public static List<Warehouse> crearAlmacenes(List<Client> clients) {
        if (clients == null || clients.size() < 2) {
            throw new IllegalArgumentException("Se necesitan al menos 2 clientes para crear los almacenes de prueba.");
        }

        return Arrays.asList(
                new Warehouse(1L, "Almacén Central (Madrid)", clients.get(0)),
                new Warehouse(2L, "Almacén Norte (Barcelona)", clients.get(1)),
                new Warehouse(3L, "Almacén Sur (Sevilla)", clients.get(1))
        );
    }

    public static List<Rack> crearRacks(List<Warehouse> warehouses) {
        if (warehouses == null || warehouses.size() < 3) {
            throw new IllegalArgumentException("Se necesitan al menos 3 almacenes para crear " +
                    "los racks de prueba.");
        }

        return Arrays.asList(
                new Rack(1L, Section.A, warehouses.get(0)),
                new Rack(2L, Section.B, warehouses.get(0)),

                new Rack(3L, Section.A, warehouses.get(1)),
                new Rack(4L, Section.C, warehouses.get(1)),
                new Rack(5L, Section.D, warehouses.get(1)),

                new Rack(6L, Section.B, warehouses.get(2))
        );
    }
}
