package com.vcgp.proyecto.proyecto;

import com.vcgp.proyecto.proyecto.models.Client;
import com.vcgp.proyecto.proyecto.models.Rack;
import com.vcgp.proyecto.proyecto.models.Warehouse;

import java.util.List;

public class TestEnMemoria {
    public static void main(String[] args) {
        System.out.println("--- Iniciando simulación en memoria ---");

        List<Client> clientes = DatosDePrueba.crearClients();
        List<Warehouse> almacenes = DatosDePrueba.crearAlmacenes(clientes);
        List<Rack> racks = DatosDePrueba.crearRacks(almacenes);

        System.out.println("\n--- Clientes Creados ---");
        clientes.forEach(System.out::println);

        System.out.println("\n--- Almacenes Creados ---");
        almacenes.forEach(System.out::println);

        System.out.println("\n--- Racks Creados ---");
        racks.forEach(System.out::println);

        System.out.println("\n--- Verificando la navegación entre objetos ---");
        Rack unRackCualquiera = racks.get(3);

        System.out.println("El rack seleccionado es: " + unRackCualquiera);
        System.out.println("Pertenece al almacén: " + unRackCualquiera.getWarehouse().getName());
        System.out.println("Que a su vez pertenece al cliente: " + unRackCualquiera.getWarehouse().getClient().getName());

        System.out.println("\n--- Simulación finalizada ---");

    }
}
