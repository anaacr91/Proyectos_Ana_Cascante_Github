package com.Selenium_Java.SeleniumTestUI.productSeleniumTest;

import com.Selenium_Java.model.Manufacturer;
import com.Selenium_Java.model.Product;
import com.Selenium_Java.repository.ManufacturerRepository;
import com.Selenium_Java.repository.ProductRepository;
import org.openqa.selenium.support.ui.Select;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductFormTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    WebDriver driver;

    @BeforeEach
    void setUp() {
        productRepository.deleteAllInBatch();//borra todos los productos de la bbdd en memoria
        manufacturerRepository.deleteAllInBatch();//borra todos los fabricantes de la bbdd en memoria
        driver = new ChromeDriver();//crea un driver de chrome
    }

    @AfterEach
    void tearDown() {
        driver.quit();//cierra el driver
    }

    @Test
    @DisplayName("Comprobar que form esta vacio de imputs, en modo crear")
    void checkCreation_EmptyInputs() {
        manufacturerRepository.saveAll(List.of(
                Manufacturer.builder().name("fabricante 1").build(),
                Manufacturer.builder().name("fabricante 2").build()
        ));//guarda dos fabricantes en la bbdd en memoria
        driver.get("http://localhost:8080/productos/crear");//navega a la url
        var h1 = driver.findElement(By.tagName("h1"));//encuentra el h1
        assertEquals("Crear producto", h1.getText());//comprueba que el texto del h1 sea Crear producto
        // comprobar inputs vacíos
        var inputName = driver.findElement(By.id("name"));//encuentra el input con id name
        assertTrue(inputName.getAttribute("value").isEmpty());//comprueba que el valor del input sea vacio
        var inputPrice = driver.findElement(By.id("price"));//encuentra el input con id price
        assertTrue(inputPrice.getAttribute("value").isEmpty());//comprueba que el valor del input sea vacio
        var inputQuantity = driver.findElement(By.id("quantity"));//encuentra el input con id quantity
        assertTrue(inputQuantity.getAttribute("value").isEmpty());//comprueba que el valor del input sea vacio
        var inputActive = driver.findElement(By.id("active"));//encuentra el input con id active
        assertEquals("true", inputActive.getAttribute("value"));//comprueba que el valor del input sea true
        //selector de manufacturer, convertimos de webElement a Select
        Select manufacturerSelect = new Select(driver.findElement(By.id("manufacturer")));//encuentra el select con id manufacturer
        assertFalse(manufacturerSelect.isMultiple());//comprueba que select sea multiple
        assertEquals(3, manufacturerSelect.getOptions().size());//comprueba que tenga 3 opciones
        //selector vacio+ options 2 fabricantes
        assertEquals("", manufacturerSelect.getOptions().get(0).getText());
        assertEquals("FABRICANTE 1", manufacturerSelect.getOptions().get(1).getText());
        //comprueba que la primera opcion sea fabricante 1;
        assertEquals("FABRICANTE 2", manufacturerSelect.getOptions().get(2).getText());
        }
    // TODO hacer many to many Book y Editorial
    // edicion formulario relleno
    // rellenar campos y enviar

    @Test
    @DisplayName("Comprobar que el formulario aparece relleno al editar un producto")
    void checkEdition_FilledInputs() {
        var manufacturers = manufacturerRepository.saveAll(List.of(
                Manufacturer.builder().name("fabricante 1").build(),
                Manufacturer.builder().name("fabricante 2").build()
        ));//guarda dos fabricantes en la bbdd en memoria
        Manufacturer manufacturer2 = manufacturers.getLast();
        Product product = Product.builder()
                .name("prod1").price(14.22).quantity(4).active(true).manufacturer(manufacturers.get(1))//fabricante2
                .build();
        productRepository.save(product);//guarda un producto en la bbdd en memoria
        driver.get("http://localhost:8080/productos/editar/" + product.getId());//navega a la url
        //comprobar imputs rellenos
        var inputName = driver.findElement(By.id("name"));//encuentra el input con id name
        assertEquals("prod1", inputName.getAttribute("value"));//comprueba que el valor del input sea prod1
        var inputPrice = driver.findElement(By.id("price"));//encuentra el input con id price
        assertEquals("14.22", inputPrice.getAttribute("value"));//comprueba que el valor del input sea 14.22
        var inputQuantity = driver.findElement(By.id("quantity"));//encuentra el input con id quantity
        assertEquals("4", inputQuantity.getAttribute("value"));//comprueba que el valor del input sea 4
        var inputActive = driver.findElement(By.id("active"));//encuentra el input con id active
        assertEquals("true", inputActive.getAttribute("value"));//comprueba que el valor del input sea true
        // selector de manufacturer, convertimos de WebElement a Select
        Select manufacturerSelect = new Select(driver.findElement(By.id("manufacturer")));
        assertFalse(manufacturerSelect.isMultiple());//comprueba que select de manufacturer sea multiple
        assertEquals(3, manufacturerSelect.getOptions().size());//num opciones seleccionables 3
        //assertEquals("fabricante 2", manufacturerSelect.getFirstSelectedOption().getText());
        assertEquals(
                String.valueOf(manufacturer2.getId()), // id del fabricante en string,
                manufacturerSelect.getFirstSelectedOption().getAttribute("value")
        );//comprueba que el valor del input sea el id del fabricante2
        //El metodo getFirstSelectedOption() devuelve el primer elemento <option> seleccionado dentro de un <select>
        //firstSelectedOption devuelve el primer elemento seleccionado
        assertEquals("fabricante 2", manufacturerSelect.getFirstSelectedOption().getText());
        //comprueba que el texto del primer elemento seleccionado sea fabricante 2
    }

}
