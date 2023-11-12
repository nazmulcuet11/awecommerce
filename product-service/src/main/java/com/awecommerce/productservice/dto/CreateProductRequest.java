package com.awecommerce.productservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class CreateProductRequest {
    @NotBlank
    private String name;
    private String description;
    @Valid
    @NotEmpty
    private List<Variant> variants;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Variant {
        @NotNull
        @Min(0)
        private Double price;
        private Map<String, String> attributes;
    }
}
