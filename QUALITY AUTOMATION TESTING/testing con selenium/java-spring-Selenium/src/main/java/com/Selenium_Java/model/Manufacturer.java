package com.Selenium_Java.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    @Column(name = "manufacturer_year")//para que h2 no fallen los test
    private Integer year;

    @OneToOne(cascade =CascadeType.ALL)//operaciones crud-> si borro/modifico un fabricante, borro/modifico la direccion
    @JoinColumn(name = "address_id")//FK, name= nombre columna en comunica con la otra tabla
    private Address address;

}
