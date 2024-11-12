package com.Selenium_Java.controller;

import com.Selenium_Java.model.Customer;
import com.Selenium_Java.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional

class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void findAll() throws Exception {//perform siempre lanza excepcion
        customerRepository.saveAll(List.of(
                Customer.builder().name("Customer 1").email("customer1@gmail.com").salary(1000d).build(),
                Customer.builder().name("Customer 2").email("customer2@gmail.com").salary(1000d).build(),
                Customer.builder().name("Customer 3").email("customer3@gmail.com").salary(1000d).build()
        ));

        mockMvc.perform(get("/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Customer 1"))
                //$->indice [0], accede primer elemanto array json, verifica valor nombre Customer1
                .andExpect(jsonPath("$[1].name").value("Customer 2"));
    }

    @Test
    void findById() throws Exception {
        var customer = Customer.builder().name("Customer 1").email("customer3@gmail.com")
                .salary(1000d).build();

        customerRepository.save(customer); // obtiene un id de base de datos

        mockMvc.perform(get("/customers/" + customer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Customer 1"))
                //no usa [] porque no es una lista, solo $, verifica que el nombre sea Customer 1
                .andExpect(jsonPath("$.salary").value(1000d));
    }
    @Test
    void findById_NotFound() throws Exception {
        mockMvc.perform(get("/customers/{id}", 9999)
    //verificar el comportamiento de la aplicación cuando se intenta acceder a un cliente con un id inexistente (9999).
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // 404

    }
    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}