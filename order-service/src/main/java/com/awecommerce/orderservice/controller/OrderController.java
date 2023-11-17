package com.awecommerce.orderservice.controller;

import com.awecommerce.orderservice.domain.Order;
import com.awecommerce.orderservice.dto.CreateOrderRequest;
import com.awecommerce.orderservice.dto.CreateOrderResponse;
import com.awecommerce.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderResponse createOrder(@Valid  @RequestBody CreateOrderRequest request) {
        Order order = modelMapper.map(request, Order.class);
        orderService.save(order);
        CreateOrderResponse response = modelMapper.map(order, CreateOrderResponse.class);
        return response;
    }
}
