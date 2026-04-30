package com.api.inventory_control.service;

import com.api.inventory_control.dto.stockmovement.StockMovementRequestDTO;
import com.api.inventory_control.exception.BusinessException;
import com.api.inventory_control.exception.ResourceNotFoundException;
import com.api.inventory_control.model.MovementType;
import com.api.inventory_control.model.Product;
import com.api.inventory_control.model.User;
import com.api.inventory_control.repository.ProductRepository;
import com.api.inventory_control.repository.StockMovementRepository;
import com.api.inventory_control.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockMovementServiceTest {

    @Mock
    private StockMovementRepository stockMovementRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StockMovementService stockMovementService;

    private Product product;
    private User user;

    @BeforeEach
    void setUp() {
        // Simula usuário autenticado no SecurityContext
        var auth = new UsernamePasswordAuthenticationToken("user@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);

        product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        product.setQuantity(10);

        user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");
    }

    @Test
    void shouldThrowExceptionWhenInsufficientStock() {
        // ARRANGE
        StockMovementRequestDTO request = new StockMovementRequestDTO();
        request.setProductId(1L);
        request.setType(MovementType.OUT);
        request.setQuantity(99);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.of(user));

        // ACT + ASSERT
        assertThrows(BusinessException.class, () -> stockMovementService.create(request));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // ARRANGE
        StockMovementRequestDTO request = new StockMovementRequestDTO();
        request.setProductId(99L);
        request.setType(MovementType.OUT);
        request.setQuantity(1);

        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(ResourceNotFoundException.class, () -> stockMovementService.create(request));
    }

    @Test
    void shouldIncreaseStockOnInMovement() {
        // ARRANGE
        StockMovementRequestDTO request = new StockMovementRequestDTO();
        request.setProductId(1L);
        request.setType(MovementType.IN);
        request.setQuantity(5);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.of(user));
        when(stockMovementRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        // ACT
        stockMovementService.create(request);

        // ASSERT
        assertEquals(15, product.getQuantity());
    }

    @Test
    void shouldDecreaseStockOnOutMovement() {
        // ARRANGE
        StockMovementRequestDTO request = new StockMovementRequestDTO();
        request.setProductId(1L);
        request.setType(MovementType.OUT);
        request.setQuantity(3);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.of(user));
        when(stockMovementRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        // ACT
        stockMovementService.create(request);

        // ASSERT
        assertEquals(7, product.getQuantity());
    }
}