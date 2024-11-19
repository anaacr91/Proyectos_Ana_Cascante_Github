package com.Selenium_Java.SeleniumTestUI.productSeleniumTest;

import com.Selenium_Java.model.Product;
import com.Selenium_Java.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
Test funcional/UI de selenium del listado de productos product-list.html
Requiere la dependencia selenium-java
https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java/4.26.0

Al poner DEFINED_PORT el propio test inicia la aplicación de spring boot y ejecuta los tests con navegador
NO HACE FALTA INICIAR LA APLICACIÓN MANUALMENTE DESDE EL MAIN
 */
// arranca en el puerto de application.properties para poder testear
/*selenium test interactuar con el navegador, crea un objeto y eso crea metodos para interaccionar con el navegador
 * llamando a etiquetas, botones, divs, parrafos del html o elementos de cualquier front end*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)//arranque puerto 8080, no p. random
public class ProductListTest {

    @Autowired
    private ProductRepository productRepository;

    WebDriver driver;

    @BeforeEach
// setUp se ejecuta antes de cada test, crea un driver para navegar a la url de test
    void setUp() {
        driver = new ChromeDriver();//crear instancia de ChromeDriver
        //declars objeto driver, navegar por la pantalla y hacer aserciones q
        // entrar a la pantalla productos
        //webdriver es una clase que representa el navegador
        driver.get("http://localhost:8080/productos");//abrir navegador en la dirección especificada
    }

    @AfterEach
        // Se ejecuta al final de cada test
    void tearDown() {
        driver.quit();//cerrar navegador para que no quede el navegador abierto
    }

    @Test
    @DisplayName("Comprobar etiqueta <title>")
    void title() {
        // comprobar título
        String title = driver.getTitle();//obtener título de la página web
        System.out.println(title);
        assertEquals("Product List", title);
        //comparar si es igual a Productos (título de la página)
    }

    @Test
    @DisplayName("Comprobar la etiqueta <h1>")
    void h1() {
        WebElement h1 = driver.findElement(By.tagName("h1"));
        //by es una clase que tiene metodos para buscar elementos por nombre, id, clase, etc
        //webelement es una clase que representa un elemento de la página web
        assertEquals("Lista de productos", h1.getText());
    }

    @Test
    @DisplayName("Comprobar que existe el enlace de Crear nuevo producto y su texto")
    void buttonCreateProduct() {
        WebElement createButton = driver.findElement(By.id("btnCreateProduct"));
        assertEquals("Crear nuevo producto", createButton.getText());
        createButton.click(); // pulsa botón crear nuevo producto
        assertEquals("http://localhost:8080/productos/crear", driver.getCurrentUrl());
    }

    //comprobar cuando la tabla no tiene elementos
    @Test
    @DisplayName("comprobar que la tabla vacía, no tiene elementos")
    void tableEmpty() {
        //comprobar que el mensaje de no hay productos es igual a productsEmpty
        WebElement noProductsMessage = driver.findElement(By.id("productsEmpty"));
        //comprobar que el texto del mensaje es igual a no hay productos
        assertEquals("No hay productos", noProductsMessage.getText());
        //comprobar que la tabla de productos no está visible
        //WebElement productsTable = driver.findElement(By.id("productList"));
        //lanza una excepcion si no encuentra el elemento productList
        assertThrows(
                NoSuchElementException.class,
                () -> driver.findElement(By.id("productList"))
        );
    }

    @Test
    @DisplayName("Comprobar que la tabla tiene elementos")
    void tableWithProducts() {
        productRepository.saveAll(List.of(
                Product.builder().name("prod1").price(10d).active(true).quantity(1).build(),
                Product.builder().name("prod2").price(20d).active(false).quantity(2).build(),
                Product.builder().name("prod3").price(30d).active(true).quantity(3).build()
        ));
    //Al insertar nuevos productos debemos refrescar la pantalla para que se muestren
        driver.navigate().refresh();//refrescar la página, simular F5
        WebElement productList = driver.findElement(By.id("productList"));
        assertTrue(productList.isDisplayed());
    // obtener columnas
    // obtener filas, comprobar que hay 3 filas
    // comprobar datos de las filas
    // comprobar los botones de los productos
    }

    @Test
    @DisplayName("Comprobar las columnas de la tabla")
    void tableWithProducts_columns() {
        productRepository.saveAll(List.of(
                Product.builder().name("prod1").price(10d).active(true).quantity(1).build(),
                Product.builder().name("prod2").price(20d).active(false).quantity(2).build(),
                Product.builder().name("prod3").price(30d).active(true).quantity(3).build()
        ));//guardar productos en la base de datos

        // Al insertar nuevos productos debemos refrescar la pantalla para que los traiga
        driver.navigate().refresh(); // Simular F5

        WebElement productList = driver.findElement(By.id("productList"));//obtener la tabla productList

        // obtener los encabezados de la tabla productList
        List<WebElement> headers = productList.findElements(By.tagName("th"));//obtener los encabezados de la tabla

        // comprobar que los encabezados son los esperados
        assertEquals(7, headers.size());//comprobar que hay 7 encabezados
        assertEquals("ID", headers.get(0).getText());
        assertEquals("TÍTULO", headers.get(1).getText());
        assertEquals("PRECIO (€)", headers.get(2).getText());
        assertEquals("CANTIDAD", headers.get(3).getText());
        assertEquals("ACTIVO", headers.get(4).getText());
        assertEquals("FABRICANTE", headers.get(5).getText());
        assertEquals("ACCIONES", headers.get(6).getText());
    // comprobar que la tabla tiene elementos
    // comprobar columnas de la tabla
    // comprobar botones de la tabla
    }
    @Test
    @DisplayName("Comprobar filas de la tabla y sus datos - sin poner ids en el HTML en los <td>")
    void tableWithProducts_rows() {
        productRepository.saveAll(List.of(
                Product.builder().name("prod1").price(10d).active(true).quantity(1).build(),
                Product.builder().name("prod2").price(20d).active(false).quantity(2).build(),
                Product.builder().name("prod3").price(30d).active(true).quantity(3).build()
        ));//guardar productos en la base de datos

        // Al insertar nuevos productos debemos refrescar la pantalla para que los traiga
        driver.navigate().refresh(); // Simular F5

        WebElement productList = driver.findElement(By.id("productList"));//obtener la tabla productList
        List<WebElement> rows = productList.findElements(By.tagName("tr"));//obtener las filas de la tabla
        assertEquals(4, rows.size());//comprobar que hay 4 filas

        // Utilizando un selector más específico para obtener las filas del tbody
        List<WebElement> tableRows = driver.findElements(By.cssSelector("#productList tbody tr"));
        //obtener las filas de la tabla productList tbody
        assertEquals(3, tableRows.size());//comprobar que hay 3 filas

        WebElement firstRow = tableRows.getFirst();//obtener la primera fila
        var firstRowColumns = firstRow.findElements(By.tagName("td"));//obtener las columnas de la primera fila
        assertEquals("prod1", firstRowColumns.get(1).getText());//comprobar que el id es 1
        //
    }
    @Test
    @DisplayName("Comprobar filas de la tabla y sus datos - con ids dinámicos en el HTML")
    void tableWithProducts_rows_ids() {
        Product product = productRepository.save(
                Product.builder().name("prod1").price(10d).active(true).quantity(1).build()
        );//guardar producto en la base de datos

        // Al insertar nuevos productos debemos refrescar la pantalla para que los traiga
        driver.navigate().refresh(); // Simular F5
        //obtener el id del producto de la base de datos que se ha guardado
        WebElement id = driver.findElement(By.id("productId_" + product.getId()));
        assertEquals(product.getId(), Long.valueOf(id.getText()));//comprobar que el id es igual al id del producto

        //obtener el nombre del producto de la base de datos que se ha guardado
        WebElement name = driver.findElement(By.id("productName_" + product.getId()));
        assertEquals("prod1", name.getText());//comprobar que el nombre es igual al nombre del producto

        //obtener el precio del producto de la base de datos que se ha guardado
        WebElement price = driver.findElement(By.id("productPrice_" + product.getId()));
        assertEquals("10,0 €", price.getText());//comprobar que el precio es igual al precio del producto
    }

}
