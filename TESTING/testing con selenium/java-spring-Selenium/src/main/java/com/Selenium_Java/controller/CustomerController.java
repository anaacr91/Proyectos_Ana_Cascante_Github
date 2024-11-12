package com.Selenium_Java.controller;

import com.Selenium_Java.model.Customer;
import com.Selenium_Java.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
public class CustomerController {

    private CustomerRepository customerRepository;

    // Metodos GET
    // Metodo que nos devuelva un saludo
    @GetMapping("welcome") // localhost:8080/welcome
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("Bienvenido a un controlador de Spring");
    }
    // Métodos CRUD

    // Metodo que nos devuelva todos los clientes
    @GetMapping("customers") // localhost:8080/customers
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(customerRepository.findAll());
    }

    // Metodo que devuelva un cliente por su id
    @GetMapping("customers/{id}") // localhost:8080/customers/2
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        return customerRepository.findById(id) // Optional<Customer>
                .map(customer -> {
                    return ResponseEntity.ok(customer);
                }) // Optional<ResponseEntity<Customer>>
                .orElseThrow();
    }
    // Metodo POST
    // Metodo para crear nuevo cliente
    @PostMapping("customers")
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        if(customer.getId() != null)//si el id no es nulo
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        customerRepository.save(customer); //guardar

        return ResponseEntity.status(HttpStatus.CREATED).body(customer);//201//creado
    }
    // Metodo PUT
    // Metodo para actualizar un cliente
    @PutMapping("customers")
    public ResponseEntity<Customer> update(@RequestBody Customer customer) {
        if (customer.getId() == null) {//si el id es nulo
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        customerRepository.save(customer); // guardar

        return ResponseEntity.ok(customer);//200//ok
    }
    // Metodo DELETE
    // Metodo para eliminar un cliente
    @DeleteMapping("customers/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            customerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
            //204//no hay contenido//se ha eliminado//no hay nada que devolver
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
            //409//conflicto//no se ha podido eliminar
        }

    }


}
