package com.api.inventory_control.dto.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequestDTO(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "SKU is required")
        String sku,

        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
        BigDecimal price,

        @NotNull(message = "Quantity is required")
        @Min(value = 0, message = "Quantity must be zero or greater")
        Integer quantity,

        @NotNull(message = "Minimum stock is required")
        @Min(value = 0, message = "Minimum stock must be zero or greater")
        Integer minimumStock,

        @NotNull(message = "Category ID is required")
        Long categoryId,

        @NotNull(message = "Supplier ID is required")
        Long supplierId
) {}