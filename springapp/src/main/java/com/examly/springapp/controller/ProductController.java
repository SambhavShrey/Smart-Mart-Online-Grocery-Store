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

import com.examly.springapp.dto.ProductDTO;
import com.examly.springapp.model.Product;
import com.examly.springapp.service.ProductServiceImpl;

import jakarta.validation.Valid;

//REST controller to handle HTTP requests
@RestController
// Specifies the base URL for the API endpoints in this controller
@RequestMapping("/api/products")
public class ProductController {
    private final ProductServiceImpl service;
    // Constructor injection of the service
    public ProductController(ProductServiceImpl service) {
        this.service = service;
    }
    // Handles POST requests to add a new product
    //@Valid Ensures validation of object fields
    @PostMapping("/add-product")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        productDTO = service.createProduct(productDTO);
        return ResponseEntity.status(201).body(productDTO);
    }
    // Handles GET requests to fetch all products
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> list = service.getAllProducts();
        return ResponseEntity.status(200).body(list);
    }
    // Handles GET requests to fetch a product by its ID
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = service.getProductById(productId);
        return ResponseEntity.status(200).body(product);
    }
    // Handles GET requests to fetch a product by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> list = service.getProductsByCategory(category);
        return ResponseEntity.status(200).body(list);
    }
    //Handles GET requests to fetch a product by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductDTO>> getProductsByUserId(@PathVariable Long userId) {
        List<ProductDTO> list = service.getProductsByUserId(userId);
        return ResponseEntity.status(200).body(list);
    }
    //Handles PUT requests to update product by its id
    @PutMapping("/edit-product/{productId}")
    public ResponseEntity<ProductDTO> editProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        productDTO = service.editProduct(productId,productDTO);
        return ResponseEntity.status(200).body(productDTO);
    }
   

    // Handles DELETE requests to remove a product by its ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        service.deleteProduct(productId);
        return ResponseEntity.status(200).body(null);
    }


    //Dummy controller...
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product createdProduct = service.addProduct(product);
        return ResponseEntity.status(201).body(createdProduct);
    }
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        Product updatedProduct = service.updateProduct(productId, product);
        return ResponseEntity.status(200).body(updatedProduct);
    }
}
