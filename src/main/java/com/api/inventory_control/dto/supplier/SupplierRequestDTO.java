package com.api.inventory_control.dto.supplier;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierRequestDTO {

    @NotBlank(message = "Trade name is required")
    private String tradeName;

    private String companyName;

    @NotBlank(message = "CNPJ is required")
    @Size(min = 14, max = 14, message = "CNPJ must be 14 characters")
    private String cnpj;

    private String phone;

    private String email;
}