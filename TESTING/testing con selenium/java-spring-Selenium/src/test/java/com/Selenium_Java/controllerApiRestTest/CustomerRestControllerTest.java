package com.Selenium_Java.controllerApiRestTest;

import com.Selenium_Java.model.Customer;
import com.Selenium_Java.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional

class CustomerRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;//convertir objetos a json

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

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
    @DisplayName("Buscar todos los clientes con salario incrementado en un 10%")
    void findAllWithSalaryModified() throws Exception {
    customerRepository.deleteAll();
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
                .andExpect(jsonPath("$[0].salary").isNotEmpty())
                .andExpect(jsonPath("$[0].salary").value(1100d)) // primer cliente con salario incrementado un 10%
                .andExpect(jsonPath("$[1].salary").isNotEmpty())
                .andExpect(jsonPath("$[1].salary").value(2200d)); // segundo cliente con salario incrementado un 10%
    }
    @Test
    @DisplayName("Buscar Clientes por filtro")
    void findByFilter() throws Exception {
        // Crear datos de prueba en la base de datos
        customerRepository.saveAll(List.of(
                Customer.builder().name("Customer 1").email("customer1@gmail.com").salary(1000d).build(),
                Customer.builder().name("Customer 2").email("customer2@gmail.com").salary(2000d).build(),
                Customer.builder().name("Customer 3").email("customer3@gmail.com").salary(3000d).build()
        ));//crear en la base de datos

        // Definir el filtro de búsqueda en formato JSON
        // Filtro de Clientes con salario de 2000
        String filterJson = """
    {
        "salary": 2000.00
    }
    """;

        mockMvc.perform(post("/customers/filter")
                        .contentType(MediaType.APPLICATION_JSON)//establece el tipo de contenido de la solicitud.
                //MediaType.APPLICATION_JSON indica que el contenido de la solicitud está en formato JSON,
                // que es común para los datos enviados en una API RESTful.
                //Esto asegura que el endpoint interprete los datos de la solicitud como JSON.
                        .content(filterJson))//content(...) establece el cuerpo de la solicitud HTTP.
                //filterJson es una variable que contiene un JSON (probablemente una cadena de texto en formato JSON)
                // que se envía en el cuerpo de la solicitud.
                //Este JSON (filterJson) podría contener datos de filtros o criterios de
                // búsqueda para el endpoint /customers/filter.
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))//verificar que la respuesta JSON tenga un tamaño de 1
                .andExpect(jsonPath("$[0].name").value("Customer 2"))
                .andExpect(jsonPath("$[0].salary").value(2000d));
    }
    @Test
    @DisplayName("Buscar clientes por filtro sin resultados")
    void findByFilter_NotFound() throws Exception {
        // Crear datos de prueba en la base de datos
        customerRepository.saveAll(List.of(
                Customer.builder().name("Customer 1").email("customer1@gmail.com").salary(1000d).build(),
                Customer.builder().name("Customer 3").email("customer3@gmail.com").salary(3000d).build()
        ));

        // Definir el filtro de búsqueda en formato JSON
        // Filtro de clientes con salario de 2000
        String filterJson = """
            {
                "salary": 2000.00
            }
            """;

        mockMvc.perform(post("/customers/filter")
                        .contentType(MediaType.APPLICATION_JSON)//tipo de contenido JSON
                        .content(filterJson))//enviar solicitud POST a la url "/customers/filter" con el filtro de búsqueda en json
                .andExpect(status().isOk())//verificar que la respuesta HTTP sea 200
                .andExpect(jsonPath("$", hasSize(0))); // Se espera que la respuesta json no devuelva ningún cliente
    }
    @Test
    @DisplayName("Actualizar parcialmente un cliente")
    void partialUpdate_findById_OK() throws Exception {

        // Crear un cliente en la base de datos
        Customer customerFromDB = Customer.builder()
                .name("Juan Perez")
                .email("juan@mail.com")
                .phone("123456")
                .salary(1000d)
                .age(30)
                .active(true)
                .build();//crear cliente
        customerRepository.save(customerFromDB);//guardar
        //Datos que se quieren actualizar en el cliente
        String customerPatchJson = """
            {
                "name": "Juan Perez Editado",
                "salary": 1200.0,
                "active": false
            }
            """;
        //String customerPatchJson: Es una variable de tipo String que contiene una representación en formato JSON.
        //{...} forma de declarar una cadena de texto multilínea en Java, representan los datos json a actualizar
        // Realizar la solicitud PATCH para actualizar parcialmente el cliente
        mockMvc.perform(patch("/customers/" + customerFromDB.getId())
                        .contentType(MediaType.APPLICATION_JSON)//tipo contenido JSON
                        .content(customerPatchJson))//enviar solicitud PATCH a la url "/customers/{id}" con los datos de actualización en json
        //es una forma utilizada en las pruebas con MockMvc en Spring para especificar el cuerpo de una solicitud HTTP.
        // En este caso, customerPatchJson contiene un JSON que será enviado como el contenido de la solicitud
                .andExpect(status().isOk())//verificar que la respuesta HTTP sea 200
                .andExpect(jsonPath("$.name").value("Juan Perez Editado"))
        //verifica que el campo "name" en la respuesta JSON tenga el valor de "Juan Perez Editado"
                .andExpect(jsonPath("$.salary").value(1200.0))
        //verifica que el campo "salary" en la respuesta JSON tenga el valor de 1200.0
                .andExpect(jsonPath("$.active").value(false));
        //verifica que el campo "active" en la respuesta JSON tenga el valor de false

        //verifica que los datos del repositorio son los mismos que la bbdd, buscandolo por ID
        Customer updatedCustomer = customerRepository.findById(customerFromDB.getId())
                .orElseThrow(() -> new AssertionError("Cliente no encontrado en base de datos"));
        //si no lo encuentra, lanzar una excepcion si no se encuentra de tipo AssertionError
        assertEquals("Juan Perez Editado", updatedCustomer.getName());
        //verificar que los datos del cliente actualizado sean correctos en la base de datos
        assertEquals(1200.0, updatedCustomer.getSalary());
    }

    @Test
    @DisplayName("Intentar actualizar parcialmente un cliente inexistente")
    void partialUpdate_findById_NotFound() throws Exception {
        // No creamos cliente en la base de datos
        //Definir los datos de actualización (aunque el cliente existe)
        String customerPatchJson = """
            {
                "name": "Juan Perez Editado",
                "salary": 1200.0,
                "active": false
            }
            """;

        // Realizar la solicitud PATCH para actualizar parcialmente el cliente
        mockMvc.perform(patch("/customers/{id}", 9999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerPatchJson))//enviar solicitud PATCH a la url "/customers/{id}" con los datos de actualización en json
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Eliminar clientes por lista de IDs")
    void deleteAllCustomers_OK() throws Exception {
        List<Customer> customers = customerRepository.saveAll(List.of(
                Customer.builder().name("Customer 1").email("customer1@gmail.com").salary(1000d).build(),
                Customer.builder().name("Customer 2").email("customer2@gmail.com").salary(1000d).build(),
                Customer.builder().name("Customer 3").email("customer3@gmail.com").salary(1000d).build()
        ));//guarda y crea objetos en la bbdd


        //pasa a stream para realizar una operacion map, que mapea los objetos a sus ids
        //para obtener los IDs de los clientes guardados para eliminarlos
        List<Long> ids = customers.stream().map(Customer::getId).toList();

        // Convertir los IDs a JSON para enviarlo en la solicitud
        //ObjectMapper es una clase central en la biblioteca Jackson, utilizada para convertir datos entre objetos Java y formatos como JSON.
        //En este caso, se está utilizando para convertir un objeto Java (ids) a su representación en formato JSON.
        //writeValueAsString(...):Es un metodo de ObjectMapper que convierte un objeto Java en una cadena de texto en formato JSON
        //en este casp ids es un objeto de tipo List<Long> que contiene los IDs de los clientes a eliminar
        String idsJson = new ObjectMapper().writeValueAsString(ids);

        // Ejecutamos la solicitud DELETE para eliminar los clientes por lista de IDs
        mockMvc.perform(delete("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(idsJson))//enviar solicitud DELETE a la url "/customers" con los IDs de los clientes en formato JSON
                .andExpect(status().isNoContent()); // 204

        // Verificar que los clientes ya no existen en la base de datos
        List<Customer> remainingCustomers = customerRepository.findAllById(ids);
        assertTrue(remainingCustomers.isEmpty());//verificar que la lista de clientes restantes esté vacía
    }


    @Test
    @DisplayName("Intentar eliminar con una solicitud vacía")
    void deleteAllCustomers_NoIds() throws Exception {
        mockMvc.perform(delete("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))//enviar solicitud DELETE a la url "/customers" sin IDs
                .andExpect(status().isBadRequest());
    }
}