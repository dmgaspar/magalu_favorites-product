package com.magalu.favorites.product.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name="Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="price")
    private String price;
    @Column(name="image")
    private String image;
    @Column(name="brand")
    private String brand;
    @Column(name="product_id")
    private String productId;
    @Column(name="title")
    private String title;
}
