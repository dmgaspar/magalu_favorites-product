package com.magalu.favorites.product.model;

import javax.persistence.*;

@Entity
@Table(name="Products")
public class Product {

    public Product() {
    }

    public Product(String price, String image, String brand, String productid, String title) {
        this.price = price;
        this.image = image;
        this.brand = brand;
        this.productid = productid;
        this.title = title;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="price")
    private String price;
    @Column(name="image")
    private String image;
    @Column(name="brand")
    private String brand;
    @Column(name="productid")
    private String productid;
    @Column(name="title")
    private String title;


}
