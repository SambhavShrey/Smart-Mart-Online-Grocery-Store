package com.examly.springapp.model;


import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String category;

    @Lob
    @Column(length = 1000000000)
    private String photoImage;

    private LocalDate createdAt;
    private LocalDate updatedAt;
    @ManyToMany(mappedBy = "products",cascade=CascadeType.ALL)
    @JsonBackReference
    List<Order>orders;

    @ManyToOne
    private User user;
}