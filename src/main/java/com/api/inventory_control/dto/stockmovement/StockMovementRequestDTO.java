package com.api.inventory_control.dto.stockmovement;

import com.api.inventory_control.model.MovementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class StockMovementRequestDTO {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Movement type is required")
    private MovementType type;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public MovementType getType() { return type; }
    public void setType(MovementType type) { this.type = type; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}