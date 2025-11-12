package com.vcgp.proyecto.proyecto.infrastructure.controllers;

import com.vcgp.proyecto.proyecto.application.dto.ClientResponseDTO;
import com.vcgp.proyecto.proyecto.application.usecase.ClientService;
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
    @ResponseStatus(HttpStatus.OK)
    public List<ClientResponseDTO> clients(){
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientResponseDTO obtenerPorId(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponseDTO create(@Validated @RequestBody ClientResponseDTO clientRequestDTO) {
        return clientService.create(clientRequestDTO);
    }



    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> edit(@PathVariable Long id, @Validated @RequestBody ClientResponseDTO clientRequestDTO) {
        ClientResponseDTO editedClient = clientService.edit(id, clientRequestDTO);
        return ResponseEntity.ok(editedClient);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFoundException(NoSuchElementException ex) {
        return ex.getMessage();
    }



}
