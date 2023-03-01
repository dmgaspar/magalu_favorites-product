package com.magalu.favorites.product.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

@Table(name="client")
public class Client {
    public Client() {
    }

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "client_id")

    private Set<Product> products = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public  synchronized Set<Product> getProducts() {
        return products;
    }

    public synchronized  void setProducts(Set<Product> products) {
        this.products = products;
    }



    public boolean contain(String productId) {
        for (Product product : products) {
            if (product.getProductid().equals(productId)) {
                return true;
            }
        }
        return false;
    }
}

