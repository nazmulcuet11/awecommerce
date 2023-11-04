package com.awecommerce.productservice.controller;

import com.awecommerce.productservice.domain.Product;
import com.awecommerce.productservice.dto.CreateProductRequest;
import com.awecommerce.productservice.dto.ProductDTO;
import com.awecommerce.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        Product product = modelMapper.map(createProductRequest, Product.class);
        Product savedProduct = productService.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductDTO> getProducts(Pageable pageable) {
        return productService
            .findAll(pageable)
            .map(product -> modelMapper.map(product, ProductDTO.class));
    }
}
