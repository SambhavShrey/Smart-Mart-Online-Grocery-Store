package com.examly.springapp.dto;

import java.time.LocalDate;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data // Generates getters, setters, toString, equals, and hashCode methods
public class ProductDTO {

    private Long productId;

    @NotBlank(message = "Name cannot be blank.") //Ensures name is not null or empty
    @Size(max = 50, message = "Name must not exceed 50 characters.") //Limits description to 50 characters
    private String name;

    @NotBlank(message = "Description cannot be blank.") //Ensures description is not null or empty
    @Size(max = 100, message = "Description must not exceed 100 characters.") //Limits description to 100 characters
    private String description;

    @NotNull(message = "Price cannot be null.") //Ensures price is not null or empty
    @Positive(message = "Price must be greater than zero.") //Ensures price is not greater than zero by using positive validation
    private Double price;

    @NotNull(message = "Stock cannot be null.") //Ensures stock is not null or empty
    @Min(value = 0, message = "Stock cannot be negative.") //Ensures stock is not negative by using min validation
    private Integer stock;

    @NotBlank(message = "Category cannot be blank.") //Ensures category is not null or empty
    @Size(max = 100, message = "Category must not exceed 100 characters.") //Limits description to 100 characters
    private String category;
    
    @Lob
    private String photoImage;

    private Long userId;

    private LocalDate createdAt;
    private LocalDate updatedAt;
}
