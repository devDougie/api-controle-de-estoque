package com.api.inventory_control.service;

import com.api.inventory_control.dto.product.ProductRequestDTO;
import com.api.inventory_control.dto.product.ProductResponseDTO;
import com.api.inventory_control.exception.BusinessException;
import com.api.inventory_control.exception.ResourceNotFoundException;
import com.api.inventory_control.model.Category;
import com.api.inventory_control.model.Product;
import com.api.inventory_control.model.Supplier;
import com.api.inventory_control.repository.CategoryRepository;
import com.api.inventory_control.repository.ProductRepository;
import com.api.inventory_control.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDTO::from)
                .toList();
    }

    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return ProductResponseDTO.from(product);
    }

    public List<ProductResponseDTO> findLowStock() {
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getQuantity() <= p.getMinimumStock())
                .map(ProductResponseDTO::from)
                .toList();
    }

    public ProductResponseDTO create(ProductRequestDTO dto) {
        if (productRepository.existsBySku(dto.sku())) {
            throw new BusinessException("SKU already in use: " + dto.sku());
        }

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.categoryId()));

        Supplier supplier = supplierRepository.findById(dto.supplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + dto.supplierId()));

        Product product = new Product();
        product.setName(dto.name());
        product.setSku(dto.sku());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());
        product.setMinimumStock(dto.minimumStock());
        product.setCategory(category);
        product.setSupplier(supplier);

        return ProductResponseDTO.from(productRepository.save(product));
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Permite manter o mesmo SKU ao atualizar o próprio produto
        if (!product.getSku().equals(dto.sku()) && productRepository.existsBySku(dto.sku())) {
            throw new BusinessException("SKU already in use: " + dto.sku());
        }

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.categoryId()));

        Supplier supplier = supplierRepository.findById(dto.supplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + dto.supplierId()));

        product.setName(dto.name());
        product.setSku(dto.sku());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());
        product.setMinimumStock(dto.minimumStock());
        product.setCategory(category);
        product.setSupplier(supplier);

        return ProductResponseDTO.from(productRepository.save(product));
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}