package com.awecommerce.orderservice.dto;

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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateOrderRequest {
    @Valid
    @NotEmpty
    private List<LineItem> orderLineItems;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class LineItem {
        @NotBlank
        private String productSku;
        @NotNull
        @Min(0)
        private Double unitPrice;
        @NotNull
        @Min(1)
        private Integer count;
    }
}
