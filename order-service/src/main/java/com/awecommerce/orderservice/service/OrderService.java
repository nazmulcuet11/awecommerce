package com.awecommerce.orderservice.service;

import com.awecommerce.orderservice.domain.Order;
import com.awecommerce.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order save(Order order) {
        order.getOrderLineItems()
            .forEach(orderLineItem -> orderLineItem.setOrder(order));
        orderRepository.save(order);
        log.info("Order saved: {}.", order);
        return order;
    }
}
