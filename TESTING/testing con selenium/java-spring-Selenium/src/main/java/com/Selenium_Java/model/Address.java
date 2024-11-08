package com.Selenium_Java.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.UUID;

@Entity//Entidad JPA que permite mapear en la bBDD
@Getter
@Setter
@ToString//imprimir objeto de forma ordenada
@Builder//Crear Instancia
@NoArgsConstructor//Constructor vacio
@AllArgsConstructor//Constructor con todos los atributos

public class Address {
    //private Sting UUID id;
    @Id//PK de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY)//autoincremental id automático
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zipCode;

    //definir relacion inversa con Manufacturer, ya que Manufacturer es el owner de la relacion
    // y con cascade se cambia/borra en consecuencia
    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    @ToStringExclude//excluir este campo del metodo toString, para evitar bucles,
    // al estar relacionado con Manufacturer
    private Manufacturer manufacturer;
}
