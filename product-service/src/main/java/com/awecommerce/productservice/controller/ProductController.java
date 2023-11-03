package com.awecommerce.productservice.controller;

import com.awecommerce.productservice.domain.Product;
import com.awecommerce.productservice.dto.CreateProductRequest;
import com.awecommerce.productservice.dto.ProductDTO;
import com.awecommerce.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController  {
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        Product product = modelMapper.map(createProductRequest, Product.class);
        product = productService.createProduct(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getAllProducts() {
        return productService
                .getAllProducts()
                .stream()
                .map(product -> modelMapper.map(this, ProductDTO.class))
                .toList();
    }
}
