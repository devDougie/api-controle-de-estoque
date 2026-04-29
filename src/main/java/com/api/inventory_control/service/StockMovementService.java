package com.api.inventory_control.service;

import com.api.inventory_control.dto.stockmovement.StockMovementRequestDTO;
import com.api.inventory_control.dto.stockmovement.StockMovementResponseDTO;
import com.api.inventory_control.exception.BusinessException;
import com.api.inventory_control.exception.ResourceNotFoundException;
import com.api.inventory_control.model.MovementType;
import com.api.inventory_control.model.Product;
import com.api.inventory_control.model.StockMovement;
import com.api.inventory_control.model.User;
import com.api.inventory_control.repository.ProductRepository;
import com.api.inventory_control.repository.StockMovementRepository;
import com.api.inventory_control.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public StockMovementService(StockMovementRepository stockMovementRepository,
                                ProductRepository productRepository,
                                UserRepository userRepository) {
        this.stockMovementRepository = stockMovementRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<StockMovementResponseDTO> findAll() {
        return stockMovementRepository.findAll()
                .stream()
                .map(StockMovementResponseDTO::from)
                .toList();
    }

    public List<StockMovementResponseDTO> findByProduct(Long productId) {
        return stockMovementRepository.findByProductId(productId)
                .stream()
                .map(StockMovementResponseDTO::from)
                .toList();
    }

    public StockMovementResponseDTO create(StockMovementRequestDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (dto.getType() == MovementType.OUT) {
            if (product.getQuantity() < dto.getQuantity()) {
                throw new BusinessException("Insufficient stock for this operation");
            }
            product.setQuantity(product.getQuantity() - dto.getQuantity());
        } else {
            product.setQuantity(product.getQuantity() + dto.getQuantity());
        }

        productRepository.save(product);

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setUser(user);
        movement.setType(dto.getType());
        movement.setQuantity(dto.getQuantity());
        movement.setDateTime(LocalDateTime.now());

        return StockMovementResponseDTO.from(stockMovementRepository.save(movement));
    }
}