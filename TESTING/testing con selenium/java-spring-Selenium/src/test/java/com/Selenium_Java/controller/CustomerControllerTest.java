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
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private ObjectMapper objectMapper;//convertir objetos a json

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
    void create() throws Exception{
        //Crear un cliente de prueba utilizando el patrón de builder
        var customer = Customer.builder()
                .name("Customer 1")
                .email("customer1@gmail.com")
                .salary(1000d)
                .build(); // Construye el objeto Customer

        // Ejecutar una solicitud POST para crear un nuevo cliente
        mockMvc.perform(post("/customers")
            .contentType(MediaType.APPLICATION_JSON)//tipo de contenido
            .content(objectMapper.writeValueAsString(customer))//convertir objeto a 1-cadena json->2-cadena string
            )//ObjectMapper es su clase principal para la conversión entre objetos Java y datos JSON de la libreria Jackson
        //1-Es un metodo de ObjectMapper-> que convierte un objeto Java en una cadena JSON.
        //2-es el metodo writeValueAsString()->que convierte el objeto en un JSON String.
                .andExpect(status().isCreated()) // verificar que la respuesta HTTP sea 201, created
                // Verificamos que el campo "name" en la respuesta JSON tenga el valor de "Customer 1"
                .andExpect(jsonPath("$.name").value("Customer 1"));
        //jsonpath verifica el contenido de respuestas JSON en pruebas de controladores de APIs REST. Forma parte de la biblioteca Spring MVC Test,
        // que permite probar de manera sencilla las respuestas de los controladores simulando solicitudes HTTP.
        //jsonPath:Es una forma de hacer una consulta sobre un JSON para acceder a sus elementos.
        // Utiliza una sintaxis de notación de puntos ($.name) para navegar en la estructura del JSON y verificar los valores esperados.
        //$.name es una expresión de jsonPath que significa "en la raíz del JSON, busca la clave name".
    }
    @Test
    void create_BadRequest() throws Exception {
        // Crear un cliente con un ID especificado para forzar el BadRequest
        var customer = Customer.builder()
                .id(1L) //Especificar ID para simular el error
                .name("Customer 1")
                .email("customer1@gmail.com")
                .salary(1000d)
                .build(); // Construye el objeto Customer

        // Realizar la solicitud POST y esperar un error BadRequest
        mockMvc.perform(post("/customers")//enviar solicitud POST a la url "/customers"
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer))
                )
                .andExpect(status().isBadRequest()); // Verificar que la respuesta HTTP sea 400, BadRequest
    }
    @Test
    void update()throws Exception {
        //Crear un cliente de prueba utilizando el patrón de builder
        var customer = Customer.builder()
                .name("Customer 1")
                .email("customer1@gmail.com")
                .salary(1000d)
                .build(); // Construye el objeto Customer
        customerRepository.save(customer); // Guardar el cliente en la base de datos

        // Simulación de modificación de datos de un cliente
        customer.setName(customer.getName() + " Modificado"); // "Customer 1 Modificado"
        customer.setSalary(2000d);

        mockMvc.perform(put("/customers") // Envía la solicitud PUT a la url "/customers"
                        .contentType(MediaType.APPLICATION_JSON) // Especifica el tipo de contenido JSON
                        .content(objectMapper.writeValueAsString(customer)) // Convierte el cliente a JSON
                )
                .andExpect(status().isOk()) // verificar que la respuesta HTTP sea 200
                // Verificamos que el campo "name" en la respuesta JSON tenga el valor de "Customer 1"
                .andExpect(jsonPath("$.name").value("Customer 1 Modificado"));

        // Validación extra, no necesaria
        // Recuperar el cliente de la base de datos para confirmar los cambios
        var customerDB = customerRepository.findById(customer.getId()).orElseThrow();
        // Verificar que el nombre guardado en la base de datos es el nombre actualizado
        assertEquals("Customer 1 Modificado", customerDB.getName());
    }
    @Test
    void update_BadRequest() throws Exception {
        // Crear un cliente sin guardar en base de datos para simular que no tiene Id
        var customer = Customer.builder()
                .name("Customer 1")
                .email("customer1@gmail.com")
                .salary(1000d)
                .build(); // Construye el objeto Customer

        // Realizar la solicitud de tipo PUT sin incluir un ID en el objeto del cliente
        mockMvc.perform(put("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer))
        ).andExpect(status().isBadRequest()); // Comprobar que el status de la respuesta sea 400
    }


    @Test
    void deleteById() throws Exception {
        //Crear un cliente de prueba utilizando el patrón de builder
        var customer = Customer.builder()
                .name("Customer 1")
                .email("customer1@gmail.com")
                .salary(1000d)
                .build(); // Construye el objeto Customer
        customerRepository.save(customer); // Guardar el cliente en la base de datos

        // Ejecutar la solicitud DELETE para eliminar un cliente por su ID
        mockMvc.perform(delete("/customers/{id}", customer.getId())//enviar solicitud DELETE a la url "/customers/{id}"
                //Especificar el tipo de contenido
                .contentType(MediaType.APPLICATION_JSON)//tipo de contenido JSON
        ).andExpect(status().isNoContent()); // Verificamos que el estado de la respuesta HTTP sea 204 No Content
    }
    @Test
    void findAllWithSalaryModified() throws Exception {

    // Crear y guardar clientes en la base de datos
        customerRepository.saveAll(List.of(
                Customer.builder().id(1L).name("Customer 1").email("customer1@gmail.com").salary(1000d).build(),
                Customer.builder().id(2L).name("Customer 2").email("customer2@gmail.com").salary(2000d).build()
        ));

    // Simular una solicitud GET al endpoint /customers-salary-modified
        mockMvc.perform(get(
                        "/customers-salary-modified")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].salary").value(1100d)) // primer cliente con salario incrementado un 10%
                .andExpect(jsonPath("$[1].salary").value(2200d)); // segundo cliente con salario incrementado un 10%
    }
}