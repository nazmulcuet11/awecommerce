package com.awecommerce.productservice.service;

import com.awecommerce.productservice.domain.Product;
import com.awecommerce.productservice.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(@Valid Product product) {
        productRepository.save(product);
        log.info("Product {} saved.", product);
        return product;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
