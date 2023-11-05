package com.awecommerce.productservice.dto;

import com.awecommerce.productservice.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductDTO {
    private String id;
    private String name;
    private String description;
    private Double price;
    private CategoryDTO category;
}
