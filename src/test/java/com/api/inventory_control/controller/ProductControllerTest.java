package com.api.inventory_control.controller;

import com.api.inventory_control.dto.product.ProductResponseDTO;
import com.api.inventory_control.exception.ResourceNotFoundException;
import com.api.inventory_control.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = ProductController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        com.api.inventory_control.security.JwtAuthenticationFilter.class,
                        com.api.inventory_control.security.JwtTokenProvider.class
                }
        )
)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    private ProductResponseDTO buildResponse() {
        return new ProductResponseDTO(
                1L,
                "Notebook",
                "SKU-001",
                "A laptop",
                new BigDecimal("2500.00"),
                10,
                2,
                1L,
                "Electronics",
                1L,
                "TechSupplier"
        );
    }

    @Test
    void shouldReturnProductListOnGetAll() throws Exception {
        when(productService.findAll()).thenReturn(List.of(buildResponse()));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Notebook"))
                .andExpect(jsonPath("$[0].sku").value("SKU-001"));
    }

    @Test
    void shouldReturnProductOnGetById() throws Exception {
        when(productService.findById(1L)).thenReturn(buildResponse());

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Notebook"));
    }

    @Test
    void shouldReturn404WhenProductNotFound() throws Exception {
        when(productService.findById(99L)).thenThrow(new ResourceNotFoundException("Product not found"));

        mockMvc.perform(get("/api/products/99"))
                .andExpect(status().isNotFound());
    }
}