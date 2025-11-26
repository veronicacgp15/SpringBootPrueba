package com.vcgp.proyecto.infrastructure.controllers;

import com.vcgp.proyecto.application.dto.RackResponseDTO;
import com.vcgp.proyecto.application.usecase.RackService;
import com.vcgp.proyecto.infrastructure.utils.SuccessMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static com.vcgp.proyecto.infrastructure.utils.Constans.RACK;

@RestController
@RequestMapping("/api/racks")
public class RackController {

    private final RackService rackService;

    public RackController(RackService rackService) {
        this.rackService = rackService;
    }
    @GetMapping
    public ResponseEntity<List<RackResponseDTO>> getAllRacks(){
        List<RackResponseDTO> racks = rackService.findAll();
        return ResponseEntity.ok(racks);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getRackById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(rackService.findById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createRack(@RequestBody RackResponseDTO requestRack) {
        try {
            RackResponseDTO createdRack = rackService.create(requestRack);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRack);
        } catch (NoSuchElementException e) {
            String errorMsg = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRack(@PathVariable Long id, @RequestBody RackResponseDTO requestRack) {
        try {
            RackResponseDTO updatedRack = rackService.edit(id, requestRack);
            return ResponseEntity.ok(updatedRack);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRack(@PathVariable Long id) {
        try {
            rackService.delete(id);
            String successMessage = SuccessMessages.entityDeleted(RACK, id);
            return ResponseEntity.ok(successMessage);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
