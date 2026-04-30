package com.api.inventory_control.service;

import com.api.inventory_control.dto.product.ProductRequestDTO;
import com.api.inventory_control.exception.BusinessException;
import com.api.inventory_control.exception.ResourceNotFoundException;
import com.api.inventory_control.model.Category;
import com.api.inventory_control.model.Product;
import com.api.inventory_control.model.Supplier;
import com.api.inventory_control.repository.CategoryRepository;
import com.api.inventory_control.repository.ProductRepository;
import com.api.inventory_control.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private ProductService productService;

    private ProductRequestDTO buildRequest(String sku) {
        return new ProductRequestDTO(
                "Notebook",
                sku,
                "A laptop",
                new BigDecimal("2500.00"),
                10,
                2,
                1L,
                1L
        );
    }

    @Test
    void shouldThrowExceptionWhenSkuAlreadyExists() {
        // ARRANGE
        ProductRequestDTO request = buildRequest("SKU-001");
        when(productRepository.existsBySku("SKU-001")).thenReturn(true);

        // ACT + ASSERT
        assertThrows(BusinessException.class, () -> productService.create(request));
        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        // ARRANGE
        ProductRequestDTO request = buildRequest("SKU-002");
        when(productRepository.existsBySku("SKU-002")).thenReturn(false);
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(ResourceNotFoundException.class, () -> productService.create(request));
    }

    @Test
    void shouldThrowExceptionWhenSupplierNotFound() {
        // ARRANGE
        ProductRequestDTO request = buildRequest("SKU-003");
        Category category = new Category();
        category.setId(1L);

        when(productRepository.existsBySku("SKU-003")).thenReturn(false);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(ResourceNotFoundException.class, () -> productService.create(request));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // ARRANGE
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(ResourceNotFoundException.class, () -> productService.findById(99L));
    }
}