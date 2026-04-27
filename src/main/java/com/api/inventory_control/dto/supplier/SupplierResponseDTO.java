package com.api.inventory_control.dto.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SupplierResponseDTO {

    private Long id;
    private String tradeName;
    private String companyName;
    private String cnpj;
    private String phone;
    private String email;
}