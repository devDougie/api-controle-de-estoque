package com.api.inventory_control.controller;

import com.api.inventory_control.dto.stockmovement.StockMovementRequestDTO;
import com.api.inventory_control.dto.stockmovement.StockMovementResponseDTO;
import com.api.inventory_control.service.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @GetMapping
    public ResponseEntity<List<StockMovementResponseDTO>> findAll() {
        return ResponseEntity.ok(stockMovementService.findAll());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<StockMovementResponseDTO>> findByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(stockMovementService.findByProduct(productId));
    }

    @PostMapping
    public ResponseEntity<StockMovementResponseDTO> create(@RequestBody @Valid StockMovementRequestDTO dto) {
        return ResponseEntity.status(201).body(stockMovementService.create(dto));
    }
}