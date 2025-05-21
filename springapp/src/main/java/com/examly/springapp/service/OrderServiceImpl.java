package com.examly.springapp.service;

import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Order;
import com.examly.springapp.model.Order.OrderStatus;
import com.examly.springapp.model.Product;
import com.examly.springapp.model.User;

import com.examly.springapp.dto.OrderDTO;
import com.examly.springapp.exception.DataNotFoundException;

import com.examly.springapp.repository.OrderRepo;
import com.examly.springapp.repository.ProductRepo;
import com.examly.springapp.repository.UserRepo;

import jakarta.validation.Valid;
@Service
public class OrderServiceImpl implements OrderService{
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;

     // Logger for tracking the application's flow
    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);    

    // Constructor for injecting dependencies
    public OrderServiceImpl(OrderRepo orderRepo, ProductRepo productRepo, UserRepo userRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }
    // Adds a new order by traversing the map in OrderDTO
    public @Valid Order createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setShippingAddress(orderDTO.getShippingAddress());

        List<Long> productIdList = orderDTO.getProductList();
        double totalAmount = 0.0;
        int totalQuantity = 0;

        List<Product> productList = new ArrayList<>();

        // Consolidate quantities for duplicate product IDs
        for(long prod:productIdList) {
            Product product = productRepo.findById(prod).orElse(null);
            if(product!=null){
                int productQuantity=product.getStock()-1;
                   if(productQuantity<0)
                        return null;
                product.setStock(productQuantity);
                productRepo.save(product);
                totalAmount+=product.getPrice();
                totalQuantity+=1;
                productList.add(product);
            }
            
        }
        // Round total amount to 2 decimal places using Math.round()
        totalAmount = Math.round(totalAmount * 100.0) / 100.0;

        order.setTotalAmount(totalAmount);
        order.setQuantity(totalQuantity);
        order.setProducts(productList); // Set the retrieved products

        // Fetch and validate user
        User user = userRepo.findById(orderDTO.getUserId())
            .orElseThrow(() -> new DataNotFoundException("User with ID " + orderDTO.getUserId() + " not found"));
        order.setUser(user);

        // Set default order status to PENDING
        order.setStatus(Order.OrderStatus.PENDING);

        return orderRepo.save(order);
    }
    
    //dummy methods
    public Order addOrder(Order order) {
        logger.info("Method addOrder started...");
        return orderRepo.save(order);
    }
    public List<Order> getAllOrders() {
        logger.info("Method getAllOrders started...");
        return orderRepo.findAll();  
    }

    public Order getOrderById(Long orderId) {
        logger.info("Method getOrderById started...");
       return orderRepo.findById(orderId).orElse(null);
    }

    public boolean deleteOrder(Long orderId) {
        logger.info("Method deleteOrder started...");
        Order order=orderRepo.findById(orderId).orElse(null);
        if(order==null){
            return false;
        }
        List<Product>product = order.getProducts();
        for(Product prod:product){
            prod.setStock(prod.getStock()+1);
            productRepo.save(prod);
        }
        orderRepo.delete(order);
        return true;
    }

    public Order updateOrderStatus(Long orderId, String order) {
        logger.info("Method UpdateOrder started...");
        Order existingOrder = orderRepo.findById(orderId).orElse(null);
    
        if (existingOrder != null) {
            if ("ACCEPTED".equals(order)) {
                existingOrder.setStatus(OrderStatus.SHIPPED);
            } else {
                existingOrder.setStatus(OrderStatus.REJECTED);
            }
            existingOrder.setOrderId(orderId);
            return orderRepo.save(existingOrder);
        } else {
            // Handle the case where existingOrder is null
            logger.warn("Order with ID {} not found.", orderId);
            return null; // or throw an exception, depending on your use case
        }
    }
    

    public List<Order> getOrdersByUserId(Long userId) {
        logger.info("Method getOrdersByUserId started...");
        return orderRepo.findByUserId(userId);
    }
    
}