package com.Selenium_Java.controllerTest.IntegrationTest;

import com.Selenium_Java.model.Manufacturer;
import com.Selenium_Java.model.Product;
import com.Selenium_Java.repository.ManufacturerRepository;
import com.Selenium_Java.repository.ProductRepository;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/*
Testing integración completa ProductController MVC con modelo y vista
 */

@AutoConfigureMockMvc//-> bean que funcione general todos los controladores
@SpringBootTest
@Transactional
//transaccional-> cada test una vez se ejecuta en 1 transaccion que se revierte al acabar para dejar bdd limpia
class ProductControllerIntegrationTest {//carga la app, el controlador y el repository

    @Autowired // inyectar el mock
    private MockMvc mockMvc;//-> simula peticiones http sin iniciar un servidor http

    @Autowired// en caso de no poner autowired en ambos, poner @AllArgsConstructor en la clase
    private ProductRepository productRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Test
    void findAll() throws Exception {

        productRepository.saveAll(List.of(
                Product.builder().name("1").price(15d).build(),
                Product.builder().name("2").price(20d).build()
        ));
        System.out.println("findAll num products" + productRepository.count());

    mockMvc.perform(get("/productos"))
        .andExpect(status().isOk())
            .andExpect(view().name("product-list"))
            .andExpect(model().attributeExists("productos"))
            .andExpect(model().attribute("productos", hasSize(2)));
    }

    @Test
    void findById() throws Exception {
       /* productRepository.saveAll(List.of(
                Product.builder().name("Microfono").price(30d).build(),
                Product.builder().name("Mesa").price(15d).build()));*/
        var product = productRepository.save(Product.builder().name("Microfono").price(30d).build());

        System.out.println("findById num products" + productRepository.count());
        System.out.println("findById id producto" + product.getId());
        mockMvc.perform(get("/productos/"+ product.getId()))//-> transaccional
                .andExpect(status().isOk())
                .andExpect(view().name("product-detail"))
                .andExpect(model().attributeExists("producto"));
    }

    @Test
    void findById_NotExist()  throws Exception {
        mockMvc.perform(get("/productos404/1"))
                //.andExpect(status().isBadRequest())//400
                .andExpect(status().is4xxClientError())//CLIENT_ERROR
                .andExpect(view().name("error"))
                .andExpect(model().attributeDoesNotExist("producto"));
    }

    @Test
    void obtenerFormularioParaNuevoProducto() throws Exception {
        manufacturerRepository.saveAll(List.of(
                Manufacturer.builder().name("Sony").build(),
                Manufacturer.builder().name("Samsung").build()
        ));
        mockMvc.perform(get("/productos/crear"))
                .andExpect(status().isOk())//-> comprobar que la respuesta es 200
                //-> comprobar que el modelo tiene los atributos product y manufacturers
                .andExpect(model().attributeExists("product", "manufacturers"))
                //-> comprobar que el modelo tiene un atributo product con un id y nombre nulos,
                // (porque es producto nuevo)
                .andExpect(model().attribute("product", allOf(
                        hasProperty("id", nullValue()),
                        hasProperty("name", nullValue())

                )))
                .andExpect(model().attribute("manufacturers", allOf(
                        hasSize(2),
                        //hasItem(allOf(hasProperty("name", is("Sony")))),
                        containsInAnyOrder(//-> comprobar que en cualquier lugar de la lista
                        hasProperty("name", is("SONY")),
                        hasProperty("name", is("SAMSUNG"))
                ))))  //-> comprobar que el modelo tiene un atributo manufacturers con 2 fabricantes

                .andExpect(view().name("product-form"));
        //-> comprobar que la vista es product-form
    }

    @Test
    void obtenerFormularioParaEditarProducto() throws Exception {
        // crear datos demo: product y manufacturers
        Product product = Product.builder()
                .name("Ratón Logitech").build();//creas un producto
        productRepository.save(product);//guardas producto

        manufacturerRepository.saveAll(List.of(
                Manufacturer.builder().name("Fabricante 1").build(),
                Manufacturer.builder().name("Fabricante 2").build()
        ));//guardas y creasfabricantes

        mockMvc.perform(get("/productos/editar/" + product.getId()))//habitual 1 solo perform
                .andExpect(status().isOk())//-> comprobar que la respuesta es 200
                .andExpect(view().name("product-form"))//-> comprobar que la vista es product-form
                .andExpect(model().attributeExists("product", "manufacturers"))
    //-> comprobar que el modelo tiene los atributos product y manufacturers

                .andExpect(status().isOk())//-> comprobar que la respuesta es 200
                .andExpect(view().name("product-form"))//-> comprobar que la vista es product-form
                .andExpect(model().attribute("product", allOf(
                        hasProperty("id", is(product.getId())),
                        hasProperty("name", is(product.getName()))
                ))) //-> comprobar que el modelo tiene un atributo product con un id y nombre
                .andExpect(model().attribute("manufacturers", allOf(
                        hasSize(2),
                                containsInAnyOrder(
                        hasProperty("name", is("Fabricante 1")),
                        hasProperty("name", is("Fabricante 2"))
                ))));//-> comprobar que el modelo tiene un atributo manufacturers con un id y nombre
    }

    @Test
    void guardarProducto() throws Exception {
        Product product = Product.builder().name("Ratón Logitech").build();
        productRepository.save(product);//crear y guardar producto
        Manufacturer manufacturer = Manufacturer.builder().name("ElGato").build();
        manufacturerRepository.save(manufacturer);//crear y guardar fabricante
        mockMvc.perform(post("/productos")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        //Datos que se envian al contralador, elcontrolador los lee y crea 1 producto con ellos
        //libreria que usa: Jackson
            .param("id", String.valueOf(product.getId()))
            .param("name", "Ratón Logitech Modificado")
            .param("price", "34.32")
            .param("quantity", "5")
        //enviar el id del manufacturer para asociar el manufacturer al producto
            .param("manufacturer", String.valueOf(manufacturer.getId()))
        ).andExpect(status().is3xxRedirection())//comprobar la redireccion
        .andExpect(redirectedUrl("/productos"));//a la url correcta
        //Primera manera de hacerlo
        //Product saveProduct = productRepository.findById(product.getId()).get();
        //getId(): obtener id(id optional)-> get(): (obtener objeto producto del optional)
        //assertEquals("Ratón Logitech Modificado", saveProduct.getName());

        //como no tenemos mocks no podemos hacer verify
        //pero tenemos el repositorio real podemos hacer consultas y asserts
        Optional<Product> savedProductOptional = productRepository.findById(product.getId());
        //obtener id del producto y guardarlo en un optional
        assertTrue(savedProductOptional.isPresent());// -> comprobar que el producto se ha guardado
        Product savedProduct = savedProductOptional.get();// -> obtener el producto del optional y guardarlo en producto
        assertEquals("Ratón Logitech Modificado", savedProduct.getName());
        assertEquals(34.32, savedProduct.getPrice());
        // -> comprobar que el nombre y el precio del producto guardado es el esperado
        assertEquals(manufacturer.getId(), savedProduct.getManufacturer().getId());
        assertEquals(manufacturer.getName(), savedProduct.getManufacturer().getName());
        // -> comprobar que el id y el nombre del fabricante coincide con el producto guardado
    }

    @Test
    void guardarProducto_notExists() throws Exception {
        Manufacturer manufacturer = Manufacturer.builder().name("ElGato").build();
        manufacturerRepository.save(manufacturer);

        // Simular que enviamos datos desde un formulario por POST
        mockMvc.perform(
                        post("/productos")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                // Estos son datos que se envían al controlador
                                // Y el controlador los lee y crea un objeto Product
                                // Para ello usa una librería que se llama Jackson
                                .param("name", "Nuevo producto")
                                .param("price", "34.32")
                                .param("quantity", "5")
                                // Enviar el id del manufacturer para asociar el producto
                                .param("manufacturer", String.valueOf(manufacturer.getId()))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/productos"));

        List<Product> products = productRepository.findAll();
        assertEquals(1, products.size());
        assertEquals("Nuevo producto", products.get(0).getName());
    }


    @Test
    void borrarProducto() throws Exception {
        // Preparación de datos: Crear y guardar un producto en el repositorio
        Product product = new Product();
        product.setName("Producto de prueba");
        product.setPrice(10.0);
        product.setQuantity(5);
        product = productRepository.save(product); // Guardar el producto y obtener su ID

        // Simulación de solicitud HTTP para borrar el producto
        mockMvc.perform(get("/productos/borrar/" + product.getId()))
                .andExpect(status().is3xxRedirection()) // Verificar que la respuesta es una redirección
                .andExpect(redirectedUrl("/productos")); // Verificar que redirige a /productos

        // Verificación: Comprobar que el producto ya no existe en el repositorio
        Optional<Product> deletedProduct = productRepository.findById(product.getId());
        assertFalse(deletedProduct.isPresent()); // Asegurarse de que el producto ha sido eliminado
    }

    @Test
    void borrarProducto_NoExiste() throws Exception {
        // Intentar borrar un producto con un ID que no existe
        mockMvc.perform(get("/productos/borrar/9999")) // Usar un ID inexistente
                .andExpect(status().isNotFound()) // Verificar que devuelve un código 200 OK
                .andExpect(view().name("error")); // Verificar que se muestra la vista de error
    }
}