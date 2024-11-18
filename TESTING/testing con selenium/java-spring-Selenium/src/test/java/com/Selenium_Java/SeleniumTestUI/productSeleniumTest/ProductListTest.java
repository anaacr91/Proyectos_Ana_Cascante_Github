package com.Selenium_Java.SeleniumTestUI.productSeleniumTest;

import com.Selenium_Java.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @BeforeEach
    void setUp() {

    }
    @Test
    @DisplayName("Comprobar etiqueta <title>")
    void title() {
        WebDriver driver = new ChromeDriver();//crear instancia de ChromeDriver
        //declars objeto driver, navegar por la pantalla y hacer aserciones q
        // entrar a la pantalla productos
        //webdriver es una clase que representa el navegador
        driver.get("http://localhost:8080/productos");//abrir navegador en la dirección especificada

        // comprobar título
        String title = driver.getTitle();//obtener título de la página web
        System.out.println(title);
        assertEquals("Product List", title);;//comparar si es igual a Productos (título de la página)

        driver.quit();//cerrar navegador para que no quede el navegador abierto
    }
    @Test
    @DisplayName("Comprobar la etiqueta <h1>")
    void h1() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/productos");
        WebElement h1 = driver.findElement(By.tagName("h1"));
        //by es una clase que tiene metodos para buscar elementos por nombre, id, clase, etc
        //webelement es una clase que representa un elemento de la página web
        assertEquals("Lista de productos", h1.getText());
        driver.quit();
    }
    @Test
    @DisplayName("Comprobar que existe el enlace de Crear nuevo producto y su texto")
    void buttonCreateProduct() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/productos");
        WebElement createButton = driver.findElement(By.id("btnCreateProduct"));
        assertEquals("Crear nuevo producto", createButton.getText());
        createButton.click(); // pulsa botón crear nuevo producto
        assertEquals("http://localhost:8080/productos/crear", driver.getCurrentUrl());
        driver.quit();
    }



}

