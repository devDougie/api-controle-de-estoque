package com.api.inventory_control.dto.stockmovement;

import com.api.inventory_control.model.MovementType;
import com.api.inventory_control.model.StockMovement;

import java.time.LocalDateTime;

public class StockMovementResponseDTO {

    private Long id;
    private Long productId;
    private String productName;
    private String userEmail;
    private MovementType type;
    private Integer quantity;
    private LocalDateTime dateTime;

    public static StockMovementResponseDTO from(StockMovement movement) {
        StockMovementResponseDTO dto = new StockMovementResponseDTO();
        dto.id = movement.getId();
        dto.productId = movement.getProduct().getId();
        dto.productName = movement.getProduct().getName();
        dto.userEmail = movement.getUser().getEmail();
        dto.type = movement.getType();
        dto.quantity = movement.getQuantity();
        dto.dateTime = movement.getDateTime();
        return dto;
    }

    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getUserEmail() { return userEmail; }
    public MovementType getType() { return type; }
    public Integer getQuantity() { return quantity; }
    public LocalDateTime getDateTime() { return dateTime; }
}