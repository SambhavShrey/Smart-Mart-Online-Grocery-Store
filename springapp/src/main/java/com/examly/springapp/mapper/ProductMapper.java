package com.examly.springapp.mapper;

import com.examly.springapp.dto.ProductDTO;
import com.examly.springapp.model.Product;
import com.examly.springapp.model.User;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    ProductMapper(){}
    // Maps ProductDTO to Product
    public static Product mapToProductEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setCategory(productDTO.getCategory());
        product.setPhotoImage(productDTO.getPhotoImage());

        product.setCreatedAt(productDTO.getCreatedAt());
        product.setUpdatedAt(productDTO.getUpdatedAt());

        // Map userId to User entity

        if (productDTO.getUserId() != null) {
            User user = new User(); // Create a User entity and set its ID
            user.setUserId(productDTO.getUserId());
            product.setUser(user);
        }

        return product;
    }

    // Maps Product to ProductDTO
    public static ProductDTO mapToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setCategory(product.getCategory());
        productDTO.setPhotoImage(product.getPhotoImage());

        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setUpdatedAt(product.getUpdatedAt());

        // Map User entity to userId

        if (product.getUser() != null) {
            productDTO.setUserId(product.getUser().getUserId());
        }

        return productDTO;
    }
}
