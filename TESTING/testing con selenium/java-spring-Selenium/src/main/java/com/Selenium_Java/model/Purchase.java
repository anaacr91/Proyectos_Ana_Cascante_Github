package com.Selenium_Java.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor//objeto form vacio
@NoArgsConstructor
@ToString
@Builder

public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private Integer quantity;
    private Double totalPrice;
    private LocalDateTime purchaseDate;

    @ManyToOne
    private Product product;

}
