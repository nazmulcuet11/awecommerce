package com.awecommerce.productservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private String id;
    private String name;
    private String description;
    private Double price;
    private CategoryDTO category;
    private List<Variant> variants;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Variant {
        private String id;
        private Map<String, String> attributes;
        private Double price;
    }
}
