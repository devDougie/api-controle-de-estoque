package com.api.inventory_control.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(nullable = false)
    private String tradeName;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false, unique = true)
    private String cnpj;

    private String phone;

    private String email;

    @OneToMany(mappedBy = "supplier")
    private List<Product> products;
}