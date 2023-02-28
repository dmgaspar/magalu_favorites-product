package com.magalu.favorites.product.entity;


import javax.persistence.*;

@Entity
@Table(name="Products")
public class Product {
    public Product() {
    }

    public Product(String price, String brand, String title) {
        this.price = price;
        this.brand = brand;
        this.title = title;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="price")
    private String price;
    @Column(name="brand")
    private String brand;
    @Column(name="title")
    private String title;

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", price='" + price + '\'' +
                ", brand='" + brand + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
