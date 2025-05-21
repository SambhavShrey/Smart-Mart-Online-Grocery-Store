package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.OrderDTO;
import com.examly.springapp.model.Order;

import jakarta.validation.Valid;

public interface OrderService {

    public Order addOrder(Order order);

    public Order getOrderById(Long orderId);

    public List<Order> getAllOrders();

    public List<Order> getOrdersByUserId(Long userId);

    public Order updateOrderStatus(Long id, String order);

    public boolean deleteOrder(Long orderId);

    public @Valid Order createOrder(OrderDTO orderDTO);
}


