package com.api.inventory_control.repository;

import com.api.inventory_control.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    boolean existsByCnpj(String cnpj);
}