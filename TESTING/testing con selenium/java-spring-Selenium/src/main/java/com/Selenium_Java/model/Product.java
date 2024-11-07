package com.Selenium_Java.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity


public class Product {
    @Id// indica la llave primaria de la tabla de la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //GV. poner como se generan los ids de la tabla de la base de datos, que seran autoincremental
    //estrategia permite que la base de datos genere automáticamente el valor de la clave primaria
    // en cada inserción de una nueva fila.
    private Long id;//columnas de datos
    private String name;
    private Double price;
    private Integer quantity;
    @Column(columnDefinition = "boolean default true")//por defecto true
    private Boolean active;



    // asociacion: un manufacturer tiene muchos productos
    //muchos objetos de la clase proucto pueden estar asociados a 1 objeto de la clase manufacturer
    @ManyToOne
    @JoinColumn(name = "manufacturer.id")
    private Manufacturer manufacturer;

}
