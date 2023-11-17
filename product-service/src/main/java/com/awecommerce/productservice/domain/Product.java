package com.awecommerce.productservice.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;
import java.util.Map;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
    @Id
    private String id;
    @NotBlank
    private String name;
    private String description;
    @DocumentReference(lazy = true)
    private Category category;
    @NotEmpty
    private List<Variant> variants;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Variant {
        private Map<String, String> attributes;
        @NotNull
        @Min(0)
        private Double price;
    }
}
