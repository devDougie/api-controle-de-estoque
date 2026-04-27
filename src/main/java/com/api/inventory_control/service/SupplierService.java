package com.api.inventory_control.service;

import com.api.inventory_control.dto.supplier.SupplierRequestDTO;
import com.api.inventory_control.dto.supplier.SupplierResponseDTO;
import com.api.inventory_control.exception.BusinessException;
import com.api.inventory_control.exception.ResourceNotFoundException;
import com.api.inventory_control.model.Supplier;
import com.api.inventory_control.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierResponseDTO create(SupplierRequestDTO dto) {
        if (supplierRepository.existsByCnpj(dto.getCnpj())) {
            throw new BusinessException("Supplier with this CNPJ already exists");
        }
        Supplier supplier = new Supplier();
        supplier.setTradeName(dto.getTradeName());
        supplier.setCompanyName(dto.getCompanyName());
        supplier.setCnpj(dto.getCnpj());
        supplier.setPhone(dto.getPhone());
        supplier.setEmail(dto.getEmail());

        Supplier saved = supplierRepository.save(supplier);
        return toResponseDTO(saved);
    }

    public List<SupplierResponseDTO> findAll() {
        return supplierRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public SupplierResponseDTO findById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        return toResponseDTO(supplier);
    }

    public SupplierResponseDTO update(Long id, SupplierRequestDTO dto) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));

        if (!supplier.getCnpj().equals(dto.getCnpj()) && supplierRepository.existsByCnpj(dto.getCnpj())) {
            throw new BusinessException("Supplier with this CNPJ already exists");
        }

        supplier.setTradeName(dto.getTradeName());
        supplier.setCompanyName(dto.getCompanyName());
        supplier.setCnpj(dto.getCnpj());
        supplier.setPhone(dto.getPhone());
        supplier.setEmail(dto.getEmail());

        Supplier saved = supplierRepository.save(supplier);
        return toResponseDTO(saved);
    }

    public void delete(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new ResourceNotFoundException("Supplier not found with id: " + id);
        }
        supplierRepository.deleteById(id);
    }

    private SupplierResponseDTO toResponseDTO(Supplier supplier) {
        return new SupplierResponseDTO(
                supplier.getId(),
                supplier.getTradeName(),
                supplier.getCompanyName(),
                supplier.getCnpj(),
                supplier.getPhone(),
                supplier.getEmail()
        );
    }
}