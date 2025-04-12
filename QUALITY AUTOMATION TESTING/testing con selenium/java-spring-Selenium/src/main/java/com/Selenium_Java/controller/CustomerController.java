package com.Selenium_Java.controller;

import com.Selenium_Java.model.Customer;
import com.Selenium_Java.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Slf4j//para logs en consola de errores y mensajes de info y debug en consola
@AllArgsConstructor
@RestController//se usa @RestController en vez de @controller
// y se devuelve ResponseEntity<> (respuestas/status http), en lugar de strings
//CONTROLADOR API REST: PERMITE QUE 2 APPS SE COMUNIQUEN entre si a traves de la web utilizando protocolo http
//permite hacer peticiones http, solicitudes: GET, POST, PUT, DELETE , para obtener, enviar o modificar datos
//estos datos se envían y reciben en un formato como JSON y XML

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
    @Operation(
            summary = "Obtener detalles de un recurso",
            description = "Esta operación devuelve información detallada sobre un recurso específico"
    )
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
    @PostMapping("customers/filter")
    public ResponseEntity<List<Customer>> findByFilter(@RequestBody Customer customer) {
        var customers = customerRepository.findAll(Example.of(customer));

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

    // Metodo PATCH
    // Actualización parcial de un cliente
    @PatchMapping(value = "customers/{id}", consumes = {"application/json", "application/merge-patch+json"})
    //@PatchMapping en Spring se utiliza para manejar solicitudes HTTP PATCH en un controlador. En este caso,
    // la anotación se está usando para definir un endpoint que permite actualizar parcialmente un recurso
    // (en este ejemplo, un "customer" o cliente) especificado por su id.
    //consumes especifica el tipo de contenido (Content-Type) que este endpoint acepta en el cuerpo de la solicitud.
    //En este caso, el endpoint acepta dos tipos de contenido:
    //application/json: Indica que puede recibir datos JSON estándar.
    //application/merge-patch+json: Este es un tipo de contenido específico para actualizaciones parciales,
    // definido en la especificación RFC 7396.Permite enviar solo las propiedades del recurso que necesitan ser actualizadas,
    // en lugar de enviar el recurso completo. Este tipo es ideal para operaciones de PATCH, ya que indica que el
    // JSON contiene solo los campos que deben cambiar.
    public ResponseEntity<Customer> partialUpdate(
            @PathVariable Long id, @RequestBody Customer customer
    //@RequestBody en Spring Boot se utiliza para mapear el cuerpo de una solicitud HTTP
            // (generalmente en formato JSON) directamente a un objeto Java en el controlador.
            // En este caso,  indica que el cuerpo de la solicitud HTTP debe convertirse en un objeto de tipo Customer
            // y ser inyectado en el parámetro customer del metodo.
    ) {
        // Validación inicial
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);//si el id es nulo lanza excepcion
        }
        // Buscar el cliente por ID y realizar la actualización parcial
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    // el map nos devuelve un Optional, hace que podamos o no tener un objeto
                    // existingCustomer es un cliente que existe en base de datos
                    // customer es el cliente que viene en el body de la petición
                    if (customer.getName() != null) existingCustomer.setName(customer.getName());
                    if (customer.getEmail() != null) existingCustomer.setEmail(customer.getEmail());
                    if (customer.getPhone() != null) existingCustomer.setPhone(customer.getPhone());
                    if (customer.getAge() != null) existingCustomer.setAge(customer.getAge());
                    if (customer.getSalary() != null) existingCustomer.setSalary(customer.getSalary());
                    if (customer.getActive() != null) existingCustomer.setActive(customer.getActive());
                    //si los datos de customer no son null, coge el customer existente y actualizalo
                    customerRepository.save(existingCustomer);//guarda el customer actualizado en la base de datos
                    return ResponseEntity.ok(existingCustomer); // 200
                })// si este Optional está vacío se pasa el orElse
                // Si no se encuentra el cliente, se devuelve un 404
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
    // Metodo para eliminar múltiples clientes cuyo ID esté en la lista
    @DeleteMapping("customers")
    public ResponseEntity<Void> deleteAll(@RequestBody List<Long> ids) {
        try {
            customerRepository.deleteAllByIdInBatch(ids);
    //deleteAllByIdInBatch(ids): Este metodo es parte de la interfaz JpaRepository. Permite eliminar
    // de forma eficiente múltiples registros de la base de datos, en una sola transacción, utilizando un lote (batch).
            return ResponseEntity.noContent().build();
    //se utiliza en Spring Boot para construir y devolver una respuesta HTTP con
    //Código de estado: 204 No Content, y cuerpo vacío: No se devuelve ningún contenido en el cuerpo de la respuesta.
        } catch (Exception e) {
            log.error("Error al eliminar un cliente", e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error al eliminar un cliente");
            //lanza excepción con respuesta http que indica que hubo un conflicto en el procesamiento de la solicitud
        }
    }

//AL SER UN CONTROLADOR DE API REST ->metodo SAVE YA ESTA EN EL CREATE UPDATE
// -> NO HACE FALTA CREARLO APARTE
//CREAMOS METODOS GET (FINDID, FINDALL), POST (CREATE) PUT (UPDATE), DELETE (DELETEBYID)


}
