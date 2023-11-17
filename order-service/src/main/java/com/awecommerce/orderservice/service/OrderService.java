package com.awecommerce.orderservice.service;

import com.awecommerce.orderservice.domain.Order;
import com.awecommerce.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    Order save(Order order) {
        orderRepository.save(order);
        return order;
    }
}
