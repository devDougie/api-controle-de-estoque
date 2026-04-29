package com.api.inventory_control.repository;

import com.api.inventory_control.model.MovementType;
import com.api.inventory_control.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findByProductId(Long productId);

    List<StockMovement> findByProductIdAndType(Long productId, MovementType type);
}