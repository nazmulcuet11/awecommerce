package com.awecommerce.orderservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    private String sku;
    @NotNull
    @Min(1)
    private Integer count;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
