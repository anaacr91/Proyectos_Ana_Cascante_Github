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
//CONTROLADOR API REST: PERMITE QUE 2 APPS SE COMUNIQUEN entre si a traves de la web utilizando protocolo http
//permite hacer peticiones http, solicitudes: GET, POST, PUT, DELETE , para obtener, enviar o modificar datos
//estos datos se envían y reciben en un formato como JSON y XML
//se usa @RestController en vez de @controller y se devuelve ->return ResponseEntity<> (respuestas/status http)
// en vez de String en cada metodo
public class CustomerController {

    private CustomerRepository customerRepository;

    // Metodos GET
    // Metodo que nos devuelva un saludo
    @GetMapping("welcome") // localhost:8080/welcome
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("Bienvenido a un controlador de Spring");
    }
    // Metodo que captura un parámetro de la URL
    @GetMapping("user") // localhost:8080/user?name=Daniela
    public ResponseEntity<String> getUserName(@RequestParam(required = false) String name) {
        return ResponseEntity.ok("Welcome user " + name);
    //devuelve una respuesta HTTP con el estado 200 OK y un mensaje personalizado en el cuerpo de la respuesta.
    }

    // Métodos CRUD
    // Metodo que nos devuelva todos los clientes
    @GetMapping("customers") // localhost:8080/customers
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(customerRepository.findAll());
    //devuelve una respuesta HTTP con el estado 200 OK y una lista de todos los clientes en el cuerpo de la respuesta.
    }

    // Metodo que devuelva un cliente por su id
    @GetMapping("customers/{id}") // localhost:8080/customers/2
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        return customerRepository.findById(id) // Optional<Customer>
                .map(customer -> {
                    return ResponseEntity.ok(customer);
    //devuelve una respuesta HTTP con el estado 200 OK y el objeto customer en el cuerpo de la respuesta.
                }) // Optional<ResponseEntity<Customer>>
               // .orElseThrow();
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    // expresión que se utiliza para manejar el caso en el que no se encuentra un recurso en una consulta que devuelve un Optional.
    // Esta línea de código está escrita en el contexto de una operación de búsqueda y, si el recurso no existe,
    // devuelve una respuesta HTTP con el estado 404 Not Found.
    }
    // Metodo que nos devuelve clientes con salario incrementado en un 10%
    @GetMapping("customers-salary-modified") // localhost:8080/customers-salary-modified
    public ResponseEntity<List<Customer>> findAllWithSalaryModified() {
        var customers = customerRepository.findAll();
        customers.forEach(c -> c.setSalary(c.getSalary() * 1.10));

        return ResponseEntity.ok(customers);
    }
    // Metodo POST
    // Metodo para crear nuevo cliente
    @PostMapping("customers")
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        if(customer.getId() != null)//si el id no es nulo
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        customerRepository.save(customer); //si obtiene id->guardar customer
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    // devuelve una respuesta HTTP con el estado 201 Created y el objeto customer en el cuerpo de la respuesta.
    // customer se guardará en json y la api lo guarda en la base de datos
    }
    // Metodo PUT
    // Metodo para actualizar un cliente
    @PutMapping("customers")
    public ResponseEntity<Customer> update(@RequestBody Customer customer) {
        if (customer.getId() == null) {//si el id es nulo
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    //lanza una excepción ResponseStatusException con el estado HTTP 400 Bad Request.
    // Esta excepción se utiliza para indicar que la solicitud enviada por el cliente es incorrecta
    // o no válida y no puede ser procesada por el servidor.
        }
    customerRepository.save(customer); // si obtine id->guardar customer
    //la api nos envia un customer con json y lo guardamos en la base de datos
    return ResponseEntity.ok(customer);
    // devuelve una respuesta HTTP con el estado 200 OK y el objeto customer en el cuerpo de la respuesta.
    }
    // Metodo DELETE
    // Metodo para eliminar un cliente
    @DeleteMapping("customers/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            customerRepository.deleteById(id);//metodo repo eliminar id
            return ResponseEntity.noContent().build();
            //devuelve una respuesta HTTP con el estado 204 No Content. Esto significa que la solicitud fue exitosa,
            // pero no hay contenido que devolver en el cuerpo de la respuesta.
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
            //indicar que hubo un conflicto en el procesamiento de la solicitud,
            // generalmente cuando se intenta crear o actualizar un recurso de manera
            // que no puede eliminarlo porque infringe alguna restricción de unicidad o integridad.
        }

    }

//AL SER UN CONTROLADOR DE API REST ->metodo SAVE YA ESTA EN EL CREATE UPDATE
// -> NO HACE FALTA CREARLO APARTE
//CREAMOS METODOS GET (FINDID, FINDALL), POST (CREATE) PUT (UPDATE), DELETE (DELETEBYID)

}
