package com.Selenium_Java.repository;

import com.Selenium_Java.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByEmail(String email);//encontrar compras por email->usar metodo en el servicio

    List<com.Selenium_Java.model.Purchase> findByPurchaseDateBetween(java.time.LocalDateTime purchaseDateStart, java.time.LocalDateTime purchaseDateEnd);
    //lista de compras por fechas->usar metodo en el servicio
}