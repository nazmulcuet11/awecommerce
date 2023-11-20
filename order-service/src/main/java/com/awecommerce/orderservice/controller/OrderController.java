package com.awecommerce.orderservice.controller;

import com.awecommerce.orderservice.domain.Order;
import com.awecommerce.orderservice.dto.CreateOrderRequest;
import com.awecommerce.orderservice.dto.CreateOrderResponse;
import com.awecommerce.orderservice.dto.InventoryDto;
import com.awecommerce.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderResponse createOrder(@Valid  @RequestBody CreateOrderRequest request) {
        validateRequest(request);
        Order order = modelMapper.map(request, Order.class);
        orderService.save(order);
        return modelMapper.map(order, CreateOrderResponse.class);
    }

    private void validateRequest(CreateOrderRequest request) {
        var skus = request
            .getOrderLineItems()
            .stream()
            .map(CreateOrderRequest.LineItem::getSku)
            .toList();

        var inventories = webClientBuilder
            .build()
            .get()
            .uri(
                "http://inventory-service/api/inventory",
                uriBuilder -> uriBuilder.queryParam("sku", skus).build()
            )
            .retrieve()
            .bodyToMono(InventoryDto[].class)
            .block();

        assert inventories != null;

        Map<String, InventoryDto> inventoriesMap = Arrays.stream(inventories)
            .collect(Collectors.toMap(InventoryDto::getSku, Function.identity()));

        request.getOrderLineItems().forEach(lineItem -> {
            var inventory = inventoriesMap.get(lineItem.getSku());
            if (inventory == null) {
                var message = "Product sku: \"%s\" is not in stock".formatted(lineItem.getSku());
                throw new IllegalArgumentException(message);
            }

            if (lineItem.getCount() > inventory.getCount()) {
                var message = "Product sku: \"%s\" does not have quantity: %d"
                    .formatted(lineItem.getSku(), lineItem.getCount());
                throw new IllegalArgumentException(message);
            }
        });
    }
}
