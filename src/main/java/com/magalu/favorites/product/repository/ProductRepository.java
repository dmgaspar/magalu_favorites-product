package com.magalu.favorites.product.repository;

import com.magalu.favorites.product.model.Client;
import com.magalu.favorites.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}

