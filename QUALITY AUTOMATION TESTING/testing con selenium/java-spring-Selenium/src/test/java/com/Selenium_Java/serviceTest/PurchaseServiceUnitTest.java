package com.Selenium_Java.serviceTest;

import com.Selenium_Java.model.Product;
import com.Selenium_Java.model.Purchase;
import com.Selenium_Java.repository.ProductRepository;
import com.Selenium_Java.repository.PurchaseRepository;
import com.Selenium_Java.service.PurchaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Anotación para habilitar Mockito en JUnit 5
class PurchaseServiceUnitTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks // Inyecta los mocks en el servicio purchaseService
    private PurchaseService purchaseService;

    private Product product;
    private Purchase purchase;


    @BeforeEach
        // Metodo que se ejecuta antes de cada test
    void setUp() {
        // Inicializar un producto de prueba
        product = Product.builder()
                .id(1L)
                .name("Producto Test")
                .price(100.0)
                .quantity(20)
                .build();

        // Inicializar una compra de prueba
        purchase = Purchase.builder()
                .id(1L)
                .email("test@example.com")
                .product(product)
                .quantity(2)
                .totalPrice(200.0)
                .purchaseDate(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Prueba del método getPurchaseById - Compra encontrada")
    void purchaseById_Found() {
        // Configurar el mock para devolver la compra cuando se busque por ID
        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));//llama al repo+ metodo findById
        //Ejecutar el metodo
        Purchase result = purchaseService.getPurchaseById(1L);//llama metodo servicio
        //verificar el rdo-> aserciones
        assertNotNull(result, "la compra no debería ser nula");
        assertEquals(1L, result.getId(), "el id de la compra debería ser 1");
        assertEquals("test@example.com", result.getEmail(), "el email de la compra debería ser test@example.com");

        //verificar que el metodo findBYId del repo haya sido invocado/ llamado alguna vez
        verify(purchaseRepository, times(1)).findById(1L);

    }

    @Test
    @DisplayName("Prueba del método getPurchaseById - Compra no encontrada")
    void testGetPurchaseById_NotFound() {
        // Configurar el mock para devolver vacío cuando se busque por ID
        when(purchaseRepository.findById(1L)).thenReturn(Optional.empty()); // Mock findById vacío

        // Ejecutar y verificar excepción
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            purchaseService.getPurchaseById(1L); // Llama al metodo que debería lanzar excepción
        }, "Se debería lanzar una excepción cuando la compra no es encontrada.");
        // Verificar el mensaje de la excepción-> verifica que el servicio manda una aserción al ser id_notfound
        assertEquals("Compra no encontrada.", exception.getMessage(), "El mensaje de la excepción debería ser 'Compra no encontrada.'.");
        // Verificar que el metodo findById haya sido llamado
        verify(purchaseRepository).findById(1L); // Verifica interacción con el mock-> que el metodo haya sido invocado

    }

    @Test
    @DisplayName("Prueba del método getAllPurchases")
    void testGetAllPurchases() {
        // Configurar el mock para devolver una lista de compras
        when(purchaseRepository.findAll()).thenReturn(Arrays.asList(purchase));//llama al repo+ metodo findByAll

        // Ejecutar el metodo del servicio
        List<Purchase> result = purchaseService.getAllPurchases();

        // Verificar el resultado
        assertNotNull(result, "La lista de compras no debería ser nula.");
        assertEquals(1, result.size(), "Debería haber una compra en la lista.");
        assertEquals(purchase, result.get(0), "la compra de la lista deberia ser igual que la compra de prueba.");
        //verificar que el metodo findAll haya sido llamado/invocado alguna vez
        // verify(purchaseRepository, times(1)).findAll();
        verify(purchaseRepository).findAll();//verifica interaccion con el mock
    }

    //asList-> trabajar con Listas , no con ArrayList
    @Test
    @DisplayName("Prueba del método getPurchasesByEmail")
    void testGetPurchasesByEmail() {
        // Configurar el mock para devolver una lista de compras filtradas por email
        when(purchaseRepository.findByEmail("test@example.com")).thenReturn(Arrays.asList(purchase));

        // Ejecutar el método
        List<Purchase> result = purchaseService.getPurchasesByEmail("test@example.com");
        // Verificar el resultado
        assertNotNull(result, "La lista de compras no debería ser nula.");
        assertEquals(1, result.size(), "Debería haber una compra en la lista.");
        assertEquals(purchase, result.get(0), "la compra de la lista debería ser igual que la compra de prueba.");
        // Verificar que el metodo findByEmail haya sido llamado/invocado alguna vez
        verify(purchaseRepository).findByEmail("test@example.com");
    }

    @Test
    @DisplayName("Prueba del método makePurchase - Compra con descuento")
    void testMakePurchase_WithDiscount() {
        // Definir los parámetros de entrada-> crear el producto
        String email = "test@example.com"; // Email del comprador
        Long productId = 1L; // ID del producto
        Integer quantity = 10; // Cantidad a comprar

        // Configurar el mock para devolver el producto cuando se busque por ID
        //invocar metodo repositorio-> findById y devolver el producto
        when(productRepository.findById(productId)).thenReturn(Optional.of(product)); // Mock findById

        // Configurar el mock para guardar la compra y devolver la misma compra
        //invocar metodo repositorio-> save y devolver la compra
        when(purchaseRepository.save(any(Purchase.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //configurar el mock para guardar producto actualizado
        //invocar metodo repositorio-> save y devolver el producto
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //ejecutar el metodo del servicio
        //invocar metodo servicio-> makePurchase: parte central: test servicio-> testear metodo servicio
        Purchase result = purchaseService.makePurchase(email, productId, quantity);

        //verificar el resultados equivalgan a los datos guardados en BBDD
        assertNotNull(result, "La compra no debería ser nula.");
        assertEquals(email, result.getEmail(), "El email de la compra debería ser 'test@example.com'");
        assertEquals(product, result.getProduct(), "El producto de la compra debería ser igual al producto de la prueba.");
        assertEquals(quantity, result.getQuantity(), "La cantidad de la compra debería ser 10.");
        assertEquals(900.0, result.getTotalPrice(), 0.001, "El precio total debería ser 900.0 (10 * 90.0 con descuento).");
        assertNotNull(result.getPurchaseDate(), "La fecha de la compra no debería ser nula.");

        //verificar que el stock del producto se haya actualizado correctamente
        assertEquals(10, product.getQuantity(), "El stock del producto debería ser 10.");
        //verificar que los métodos del repositorio hayan sido llamados
        verify(productRepository).findById(productId);//verificar que el metodo findById haya sido invocado
        verify(purchaseRepository).save(any(Purchase.class));//verificar que el metodo save haya sido invocado
        verify(productRepository).save(any(Product.class));//verificar que el metodo save haya sido invocado
    }

    @Test
    @DisplayName("Prueba del método makePurchase - Compra sin descuento")
    void testMakePurchase_WithoutDiscount() {
        // Definir los parámetros de entrada
        String email = "test@example.com";
        Long productId = 1L;
        Integer quantity = 5;
        // Configurar el mock para devolver el producto cuando se busque por ID
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        // Configurar el mock para guardar la compra y devolver la misma compra
        when(purchaseRepository.save(any(Purchase.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Configurar el mock para guardar el producto actualizado
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Ejecutar el metodo
        Purchase result = purchaseService.makePurchase(email, productId, quantity);

        // Verificar el resultado
        assertNotNull(result, "La compra no debería ser nula.");
        assertEquals(email, result.getEmail(), "El email de la compra debería ser 'test@example.com'");
        assertEquals(product, result.getProduct(), "El producto de la compra debería ser igual al producto de la prueba.");
        assertEquals(quantity, result.getQuantity(), "El producto de la compra debería ser 5, = que el producto de la prueba.");
        assertEquals(500, result.getTotalPrice(), "El producto de la compra debería ser '500' .");
        assertNotNull(result.getPurchaseDate(), "La fecha de la compra no debería ser nula.");

        //comprobar que el stock se haya actualizado correctamente
        assertEquals(15, product.getQuantity(), "El stock del producto debería ser 15. (20: beforeEach -5 (compra este test))");
        // Verificar que los métodos del repositorio hayan sido llamados
        verify(productRepository).findById(productId); // Verifica que findById ha sido llamado
        verify(purchaseRepository).save(any(Purchase.class)); // Verifica que save de compra ha sido llamado
        verify(productRepository).save(any(Product.class)); // Verifica que save de producto ha sido llamado
    }

    @Test
    @DisplayName("Prueba del método makePurchase - Stock insuficiente")
    void testMakePurchase_InsufficientStock() {
        // Definir los parámetros de entrada
        String email = "test@example.com";
        Long productId = 1L;
        Integer quantity = 25; // Cantidad que excede el stock actual de 20
        // Configurar el mock para devolver el producto cuando se busque por ID
        //mock que devuelve el metodo del repositorio, findById y devuelve un optional
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Ejecutar y verificar excepción-> que lanza el metodo cuando no hay stock
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            purchaseService.makePurchase(email, productId, quantity); // Llama al metodo que debería lanzar excepción
        }, "Se debería lanzar una excepción cuando el stock es insuficiente.");

        //verificar mensaje excepción
        assertEquals("Stock insuficiente para el producto seleccionado.", exception.getMessage(),
                "El mensaje de la excepción debería ser 'Stock insuficiente para el producto seleccionado.'.");
        //verificar metodos find by id y save hayan sido llamados
        verify(productRepository).findById(productId); // Verifica que findById ha sido llamado
        //verificar que al no haber stock, los demás métodos no se llaman
        verify(purchaseRepository, never()).save(any(Purchase.class)); // Verifica que save de compra no ha sido llamado
        verify(productRepository, never()).save(any(Product.class)); // Verifica que save de producto no ha sido llamado
    }

    @Test
    @DisplayName("Prueba del método cancelPurchase")
    void testCancelPurchase() {
        // Configurar el mock para devolver la compra cuando se busque por ID
        //invocar metodo repositorio-> findById y devolver la compra
        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));

        //ejecutar el metodo del servicio
        //invocar metodo servicio-> cancelPurchase: parte central: test servicio-> testear metodo servicio
        purchaseService.cancelPurchase(1L);

        //verificar que el stock del producto se haya restaurado correctamente en la BD
        assertEquals(22, product.getQuantity(), "El stock del producto debería ser 22. (20: beforeEach +2 (compra))");

        // Verificar que la compra haya sido eliminada de la BD
        verify(purchaseRepository).delete(purchase); // Verifica que delete ha sido llamado
    }

    @Test
    @DisplayName("Prueba del método getPurchasesBetweenDates")
    void testGetPurchasesBetweenDates() {
        // Definir el rango de fechas para la prueba
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59);

        // Configurar el mock para devolver la lista de compras dentro del rango de fechas
        //invocar metodo repositorio-> findByPurchaseDateBetween y devolver la lista de compras
        when(purchaseRepository.findByPurchaseDateBetween(startDate, endDate)).thenReturn(Arrays.asList(purchase));

        //ejecutar el metodo del servicio
        List<Purchase> result = purchaseService.getPurchasesBetweenDates(startDate, endDate);

        //verificar el resultado
        assertNotNull(result, "la lista no deberia ser nula");
        assertEquals(1, result.size(), "Debería haber una compra en la lista.");
        assertEquals(purchase, result.get(0), "La compra en la lista debería ser igual a la compra de prueba.");

        // Verificar que el metodo findByPurchaseDateBetween haya sido llamado con las fechas correctas
        //verificar que el metodo del repositorio haya sido invocado y que los datos coinciden con la BBDD
        verify(purchaseRepository).findByPurchaseDateBetween(startDate, endDate);
    }
}