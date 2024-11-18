package com.Selenium_Java.controllerTest.UnitTest;

import com.Selenium_Java.controller.ProductController;
import com.Selenium_Java.model.Manufacturer;
import com.Selenium_Java.model.Product;
import com.Selenium_Java.repository.ManufacturerRepository;
import com.Selenium_Java.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
//carga la libreria moquito en junit-> sino mocks no funcionan

class ProductControllerUTest {

    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ManufacturerRepository manufacturerRepository;
    @Mock
    Model model;

    @Test
    void findAll() {
            // 1. configurar mocks
            when(productRepository.findAll()).thenReturn(List.of(
                    Product.builder().id(1L).build(),
                    Product.builder().id(2L).build(),
                    Product.builder().id(3L).build()
            ));
        //integrado-> en lugar de hacer un mock de model, se llama al metodo real
        //doCallRealMethod().when(model).addAttribute(any(), any());
        //doCallRealMethod().when(model).containsAttribute(any());

            // 2. invocar metodo a testear
            String view = productController.findAll(model);
        //verificaciones
            verify(productRepository).findAll();
            assertEquals("product-list", view);

    }

    @Test
    @DisplayName("Caso en el que el producto sí existe")
    void findById_productExist() {
 // 1. configurar respuestas mocks
 Product producto = Product.builder().id(1L).name("Producto 1").build();//crea un producto
    when(productRepository.findById(1L)).thenReturn(Optional.of(producto));
    //cuando se llame al metodo findById, devolvera el producto

// 2. ejecutar metodo a testear
 String view = productController.findById(1L, model);
 //comprobar que se llama al metodo findById, y devolvera la vista detalle en la string vista del id 1L

// 3. assert y verify
 assertEquals("product-detail", view);//comprobar que la vista es la correcta
verify(productRepository).findById(1L);//comprobar que se llama al metodo findById
verify(model).addAttribute("producto", producto);
//comprobar que se llama al metodo addAttribute con el producto correcto
}

    @Test
    void findById_productNotExist() {

        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        //cuando se llame al metodo findById, devolvera un optional vacio

        String view = productController.findById(1L, model);
        //comprobar que se llama al metodo findById, y devolvera la vista detalle en la string vista del id 1L

        assertEquals("product-detail", view);//comprobar que la vista es la correcta
        verify(productRepository).findById(1L);//comprobar que se llama al metodo findById
        verify(model, never()).addAttribute(anyString(), any());
        //comprobar que no se llama al metodo addAttribute
    }

    @Test
    void findById_NotExist() {
        when(productRepository.findById(1L))//cuando se llame al metodo findById
                .thenReturn(Optional.empty());//devolvera un optional vacio

       // String view =
        //cuando se llame al metodo findById_NotExist, devolvera la vista product-not-found
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
                    productController.findById_NotExist(1L, model);
                });//dentro de la lambda, lanza una excepcion con assertThrows de tipo nosuchelementexception

        verify(productRepository).findById(1L);//comprobar que se llama al metodo findById con id 1L
        verify(model, never()).addAttribute(eq("producto"), any());
        //comprobar que nunca se llama al metodo addAttribute con el modelo product

    }
    @Test
    void obtenerFormularioParaNuevoProducto() {
        List<Manufacturer> manufacturers = List.of(
                new Manufacturer().builder().name("Manufacturer 1").build(),
                new Manufacturer().builder().name("Manufacturer 2").build()
        );//crea una lista de manufacturers

        when(manufacturerRepository.findAll()).thenReturn(manufacturers);
        //cuando se llame al metodo findAll, devolvera la lista de manufacturers

        String view = productController.obtenerFormularioParaNuevoProducto(model);
        //cuando se llame al metodo obtenerFormularioParaNuevoProducto, devolvera la vista product-form
        //y se guarda en la variable para poder hacer las aserciones luego

        assertEquals("product-form", view);//comprobar que la vista es la correcta
        verify(model).addAttribute(eq("product"), any());
        //comprobar que se llama al metodo addAttribute con el modelo product con cualquier objeto de producto

        verify(model).addAttribute("manufacturers", manufacturers);
        //comprobar que se llama al metodo addAttribute con la lista de manufacturers
        }

    @Test
    void obtenerFormularioParaEditarProducto() {

            Product producto = Product.builder().id(1L).name("Producto 1").build();
            //crea un producto
            when(productRepository.findById(1L)).thenReturn(Optional.of(producto));
            //cuando se llame al metodo findById, devolvera el producto
            String view = productController.obtenerFormularioParaEditarProducto(1L, model);
            //cuando se llame al metodo obtenerFormularioParaEditarProducto, devolvera la vista product-form
            assertEquals("product-form", view);
            //verificar que la vista es la correcta
            verify(productRepository).findById(1L);
            //verificar que se llama al metodo findById con el id 1L
            verify(model).addAttribute("product", producto);
            //verificar que se llama al metodo addAttribute con el modelo product y el producto
    }
    @Test
    void obtenerFormularioParaEditarProducto_NotExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        List<Manufacturer> manufacturers = List.of(
                new Manufacturer(),
                new Manufacturer()
        );//crea una lista de manufacturers
        when(manufacturerRepository.findAll()).thenReturn(manufacturers);
        //cuando se llame al metodo findAll, devolvera la lista de manufacturers
        String view = productController.obtenerFormularioParaEditarProducto(1L, model);
        //cuando se llame al metodo obtenerFormularioParaEditarProducto, devolvera la vista product-form
        assertEquals("product-form", view);//verificar que la vista es la correcta
        verify(model, never()).addAttribute(eq("product"), any());
        //verificar que nunca se llama al metodo addAttribute con el modelo product
        verify(model).addAttribute("manufacturers", manufacturers);
        //verificar que se llama al metodo addAttribute con la lista de manufacturers
    }


    @Test
    void guardarProducto_CrearProductoNuevo() {
        Product product = new Product();
        //crea un producto

        String view = productController.guardarProducto(product);
        //cuando se llame al metodo guardarProducto, devolvera la vista redirect:/productos
        assertEquals("redirect:/productos", view);
        //verificar que la vista es la correcta
        verify(productRepository).save(product);
        //verificar que se llama al metodo save con el producto
    }
    @Test
    void guardarProducto_EditarProductoExistente() {

        Product product = Product.builder()
                .id(1L).name("Xiaomi 13").build();
        Product productEdited = Product.builder()
                .id(1L).name("Xiaomi 13 PRO").build();
        //crea un producto y un producto editado
        when(productRepository.existsById(1L))
                .thenReturn(true);
        //cuando se llame al metodo existsById, devolvera true
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));
        //cuando se llame al metodo findById, devolvera el producto
        String view = productController
                .guardarProducto(productEdited);
        //cuando se llame al metodo guardarProducto, devolvera la vista redirect:/productos

        assertEquals("redirect:/productos", view);
        //verificar que la vista es la correcta
        verify(productRepository).existsById(1L);
        //verificar que se llama al metodo existsById con el id 1L;
        verify(productRepository).findById(1L);
        //verificar que se llama al metodo findById con el id 1L
        verify(productRepository).save(product);
        //verificar que se llama al metodo save con el producto
        assertEquals(productEdited.getName(), product.getName());
        //verificar que el nombre del producto editado es el mismo que el del producto
    }


    @Test
    void borrarProducto_OK() {
        String view = productController.borrarProducto(1L);
        //cuando se llame al metodo borrarProducto, devolvera la vista redirect:/productos
        assertEquals("redirect:/productos", view);
        //verificar que la vista es la correcta
        verify(productRepository).deleteById(1L);
        //verificar que se llama al metodo deleteById con el id 1L

    }

    @Test
    void borrarProducto_ErrorCapturado() {
        doThrow(new RuntimeException("Error al borrar"))
                //cuando se llame al metodo deleteById, lanzara una excepcion
                .when(productRepository).deleteById(1L);
        //cuando se llame al metodo deleteById, lanzara una excepcion

        String view = productController.borrarProducto(1L);
        //cuando se llame al metodo borrarProducto, devolvera la vista error

        assertEquals("error", view);
        //verificar que la vista es la correcta
        verify(productRepository).deleteById(1L);
        //verificar que se llama al metodo deleteById con el id 1L
    }
}