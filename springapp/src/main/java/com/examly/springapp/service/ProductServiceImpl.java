package com.examly.springapp.service;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.ProductDTO;
import com.examly.springapp.exception.DataNotFoundException;
import com.examly.springapp.mapper.ProductMapper;
import com.examly.springapp.model.Product;
import com.examly.springapp.repository.ProductRepo;

import jakarta.validation.Valid;

// Marks this class as a Service component to define business logic for product operations
@Service
public class ProductServiceImpl implements ProductService {
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepo productRepo;
    // Constructor injection of the Product Repository
    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    // Adds a new product to the database
    public @Valid ProductDTO createProduct(ProductDTO productDTO) {
        logger.info("Method addProduct started...");
        // Converts the incoming DTO to a Product entity using the mapper
        Product product = ProductMapper.mapToProductEntity(productDTO);
        // Saves the Product entity in the database
        LocalDate currentDate = LocalDate.now();
        product.setCreatedAt(currentDate);
        product.setUpdatedAt(currentDate);
        Product saved = productRepo.save(product);
        logger.info("Method addProduct ended...");
        // Converts the saved Product entity back to DTO and returns it
        return ProductMapper.mapToProductDTO(saved);
    }

    // Fetches all products from the database
    public List<ProductDTO> getAllProducts() {
        logger.info("Method getAllProducts started...");
        // Retrieves all Product entities from the database
        List<Product> products = productRepo.findAll();
        logger.info("Method getAllProducts ended...");
        // Converts each Product entity to a DTO and collects them into a list
        return products.stream().map(ProductMapper::mapToProductDTO).toList();
    }

    // Fetches a product by its ID
    public Product getProductById(Long productId) {
        logger.info("Method getProductById started...");
        // Finds the Product entity by its ID (or returns null if not found)
        return productRepo.findById(productId).orElse(null);
    }

    // Fetches all products by category
    public List<Product> getProductsByCategory(String category) {
        logger.info("Method getProductsByCategory started...");
        // Finds all Product entities matching the given category
        List<Product> list = productRepo.findAllProductsByCategory(category);
        logger.info("Method getProductsByCategory ended...");
        return list;
    }

    // Fetches all products by user ID
    public List<ProductDTO> getProductsByUserId(Long userId) {
        logger.info("Method getProductsByUserId started...");
        // Finds all Product entities associated with the given user ID
        List<Product> saved = productRepo.findProductsByUserId(userId);
        logger.info("Method getProductsByUserId ended...");
        // Converts each Product entity to a DTO and collects them into a list
        return saved.stream().map(ProductMapper::mapToProductDTO).toList();
    }

    // Deletes a product by its ID
    public boolean deleteProduct(Long productId) {
        logger.info("Method deleteProduct started...");
        Product product = productRepo.findById(productId).orElse(null);
        if (product == null) {
            return false;
        }
        productRepo.delete(product);
        logger.info("Method deleteProduct ended...");
        return true;
    }

    // Updates a product by its ID
    public ProductDTO editProduct(Long productId, ProductDTO productDTO) {
        logger.info("Method updateProduct started...");
            //Find the existing Product entity by ID
            Product existingProduct = productRepo.findById(productId).orElse(null);
            if (existingProduct == null) {
                throw new DataNotFoundException("Product with ID"+ productId +"not found.");
            }
            //Update fields only if DTO fields are not null
            if (productDTO.getName() != null)
                existingProduct.setName(productDTO.getName());
            if (productDTO.getDescription() != null)
                existingProduct.setDescription(productDTO.getDescription());
            if (productDTO.getPrice() != null && productDTO.getPrice() > 0)
                existingProduct.setPrice(productDTO.getPrice());
            if (productDTO.getStock() != null)
                existingProduct.setStock(productDTO.getStock());
            if (productDTO.getCategory() != null)
                existingProduct.setCategory(productDTO.getCategory());
            if (productDTO.getPhotoImage() != null)
                existingProduct.setPhotoImage(productDTO.getPhotoImage());
        
            //Ensure createdAt is set only once during creation
            if (existingProduct.getCreatedAt() == null) {
                existingProduct.setCreatedAt(LocalDate.now());
            }
        
            //Always update updatedAt
            existingProduct.setUpdatedAt(LocalDate.now());
        
            //Save the updated Product entity to the database
            productRepo.save(existingProduct);
        
            //Populate and return the updated ProductDTO
            productDTO.setProductId(existingProduct.getProductId());
            productDTO.setCreatedAt(existingProduct.getCreatedAt());
            productDTO.setUpdatedAt(existingProduct.getUpdatedAt());
        
            logger.info("Method updateProduct ended...");
            return productDTO;
    }


    //Dummy methods
    public Product addProduct(Product product) {
        return productRepo.save(product);
    }
    public Product updateProduct(Long productId, Product product) {
        product.setProductId(productId);
        return productRepo.save(product);
    }
}