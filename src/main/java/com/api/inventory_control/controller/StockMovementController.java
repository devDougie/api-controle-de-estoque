package com.api.inventory_control.controller;

import com.api.inventory_control.dto.stockmovement.StockMovementRequestDTO;
import com.api.inventory_control.dto.stockmovement.StockMovementResponseDTO;
import com.api.inventory_control.service.StockMovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
@Tag(name = "Stock Movements", description = "Stock movement management")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @Operation(summary = "List all stock movements")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public ResponseEntity<List<StockMovementResponseDTO>> findAll() {
        return ResponseEntity.ok(stockMovementService.findAll());
    }

    @Operation(summary = "List stock movements by product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<StockMovementResponseDTO>> findByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(stockMovementService.findByProduct(productId));
    }

    @Operation(summary = "Register a stock movement (IN or OUT)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "409", description = "Insufficient stock for this operation")
    })
    @PostMapping
    public ResponseEntity<StockMovementResponseDTO> create(@RequestBody @Valid StockMovementRequestDTO dto) {
        return ResponseEntity.status(201).body(stockMovementService.create(dto));
    }
}