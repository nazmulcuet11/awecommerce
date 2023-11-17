package com.awecommerce.orderservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String productSku;
    @NotNull
    @Min(0)
    private Double unitPrice;
    @NotNull
    @Min(1)
    private Integer count;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
