package com.api.inventory_control.controller;

import com.api.inventory_control.dto.stockmovement.StockMovementResponseDTO;
import com.api.inventory_control.model.MovementType;
import com.api.inventory_control.service.StockMovementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = StockMovementController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        com.api.inventory_control.security.JwtAuthenticationFilter.class,
                        com.api.inventory_control.security.JwtTokenProvider.class
                }
        )
)
@AutoConfigureMockMvc(addFilters = false)
class StockMovementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StockMovementService stockMovementService;

    @Test
    void shouldReturnMovementListOnGetAll() throws Exception {
        when(stockMovementService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/stock-movements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldReturnMovementListByProduct() throws Exception {
        when(stockMovementService.findByProduct(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/stock-movements/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldReturn201OnCreateMovement() throws Exception {
        when(stockMovementService.create(any())).thenReturn(new StockMovementResponseDTO());

        String requestBody = """
                {
                    "productId": 1,
                    "type": "IN",
                    "quantity": 5
                }
                """;

        mockMvc.perform(post("/api/stock-movements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }
}