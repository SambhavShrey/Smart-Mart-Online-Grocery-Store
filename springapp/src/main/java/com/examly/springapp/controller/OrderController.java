package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Order;
import com.examly.springapp.service.OrderServiceImpl;
import com.examly.springapp.dto.OrderDTO;


import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderServiceImpl service;
    //Constructor injection of the service
    public OrderController(OrderServiceImpl service) {
        this.service = service;
    }
    // Handles POST requests to add a new order
    @PostMapping("/add")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Order order = service.createOrder(orderDTO);
        return ResponseEntity.status(200).body(order);
    }
   // Handles GET requests to fetch all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> list = service.getAllOrders();
        return ResponseEntity.status(200).body(list);
    }
    // Handles GET requests to fetch a order by its ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = service.getOrderById(orderId);
        return ResponseEntity.status(200).body(order);
    }
    // Handles DELETE requests to remove a order by its ID
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        service.deleteOrder(orderId);
        return ResponseEntity.status(200).body(null);
    }
    @PutMapping("/{orderId}")
    public ResponseEntity<Order>updateOrder(@PathVariable Long orderId, @RequestBody String status){
        Order order=service.updateOrderStatus(orderId,status);
        return ResponseEntity.status(200).body(order);

    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order>list= service.getOrdersByUserId(userId);
        return ResponseEntity.status(200).body(list);
    }


    //dummy controller
    @PostMapping
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        order = service.addOrder(order);
        return ResponseEntity.status(200).body(order);
    }
}