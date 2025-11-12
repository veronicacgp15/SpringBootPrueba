package com.vcgp.proyecto.proyecto.infrastructure.controllers;

import com.vcgp.proyecto.proyecto.application.dto.WarehouseSimpleDTO;
import com.vcgp.proyecto.proyecto.application.usecase.WarehouseService;
import com.vcgp.proyecto.proyecto.infrastructure.utils.SuccessMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static com.vcgp.proyecto.proyecto.infrastructure.utils.Constans.WAREHOUSE;


@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public ResponseEntity<List<WarehouseSimpleDTO>> getAllWarehouses() {
        try {
            List<WarehouseSimpleDTO> warehouses = warehouseService.findAll();
            return ResponseEntity.ok(warehouses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createWarehouse(@RequestBody WarehouseSimpleDTO requestWarehouse) {
        try {
            WarehouseSimpleDTO createdWarehouse = warehouseService.create(requestWarehouse);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdWarehouse);
        } catch (NoSuchElementException e) {
            String errorMsg = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWarehouse(@PathVariable Long id, @RequestBody WarehouseSimpleDTO requestWarehouse) {
        try {
            WarehouseSimpleDTO updatedWarehouse = warehouseService.edit(id, requestWarehouse);
            return ResponseEntity.ok(updatedWarehouse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable Long id) {
          try {warehouseService.delete(id);
              String successMessage = SuccessMessages.entityDeleted(WAREHOUSE, id);
              return ResponseEntity.ok(successMessage);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}
