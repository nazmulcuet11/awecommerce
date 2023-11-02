package com.awecommerce.productservice.service;

import com.awecommerce.productservice.domain.Product;
import com.awecommerce.productservice.dto.ProductRequest;
import com.awecommerce.productservice.dto.ProductResponse;
import com.awecommerce.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = mapToProduct(productRequest);
        productRepository.save(product);
        log.info("Product {} saved.", product);
        return mapToProductResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    private Product mapToProduct(ProductRequest productRequest) {
        return Product
                .builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
