package com.api.inventory_control.dto.product;

import com.api.inventory_control.model.Product;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String name,
        String sku,
        String description,
        BigDecimal price,
        Integer quantity,
        Integer minimumStock,
        Long categoryId,
        String categoryName,
        Long supplierId,
        String supplierTradeName
) {
    public static ProductResponseDTO from(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getMinimumStock(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getSupplier().getId(),
                product.getSupplier().getTradeName()
        );
    }
}