package com.api.inventory_control.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trade_name", nullable = false)
    private String tradeName;

    @Column(name = "company_name")
    private String companyName;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    private String phone;

    private String email;
}