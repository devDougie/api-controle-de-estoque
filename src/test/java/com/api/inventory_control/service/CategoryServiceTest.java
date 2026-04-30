package com.api.inventory_control.service;

import com.api.inventory_control.dto.category.CategoryRequestDTO;
import com.api.inventory_control.exception.BusinessException;
import com.api.inventory_control.exception.ResourceNotFoundException;
import com.api.inventory_control.model.Category;
import com.api.inventory_control.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void shouldThrowExceptionWhenCategoryNameAlreadyExists() {
        // ARRANGE
        CategoryRequestDTO request = new CategoryRequestDTO();
        request.setName("Electronics");
        request.setDescription("Electronic products");

        when(categoryRepository.existsByName("Electronics")).thenReturn(true);

        // ACT + ASSERT
        assertThrows(BusinessException.class, () -> categoryService.create(request));
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        // ARRANGE
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(ResourceNotFoundException.class, () -> categoryService.findById(99L));
    }
}