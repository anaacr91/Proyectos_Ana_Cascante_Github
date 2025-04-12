package com.Selenium_Java.repository;

import com.Selenium_Java.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

