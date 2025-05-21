package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Product;

// Marks this interface as a Repository to interact with the database
@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    // Custom query to fetch all products based on their category
    @Query("select p from Product p where p.category=:category")
    public List<Product> findAllProductsByCategory(String category);
    //Custom query to fetch all products by userId
    @Query("select p from Product p where p.user.userId=:userId")
    public List<Product> findProductsByUserId(Long userId);
}
