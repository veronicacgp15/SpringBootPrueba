package com.vcgp.proyecto.infrastructure.controllers;

import com.vcgp.proyecto.application.dto.ClientResponseDTO;
import com.vcgp.proyecto.application.usecase.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/clients")
public class ClienteleController {

    private final ClientService clientService;

    public ClienteleController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> clients(){
        List<ClientResponseDTO> clientList = clientService.findAll();
        return ResponseEntity.ok(clientList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            ClientResponseDTO client = clientService.findById(id);
            return ResponseEntity.ok(client);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@Validated @RequestBody ClientResponseDTO clientRequestDTO) {
        ClientResponseDTO createdClient = clientService.create(clientRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @Validated @RequestBody ClientResponseDTO clientRequestDTO) {
        try {
            ClientResponseDTO editedClient = clientService.edit(id, clientRequestDTO);
            return ResponseEntity.ok(editedClient);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            clientService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
