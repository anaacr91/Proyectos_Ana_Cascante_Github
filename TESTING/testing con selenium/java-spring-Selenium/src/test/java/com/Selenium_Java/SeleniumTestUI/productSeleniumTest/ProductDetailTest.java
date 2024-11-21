package com.Selenium_Java.SeleniumTestUI.productSeleniumTest;

import com.Selenium_Java.model.Manufacturer;
import com.Selenium_Java.model.Product;
import com.Selenium_Java.repository.ManufacturerRepository;
import com.Selenium_Java.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/*
Test de selenium para probar: product-detail.html
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductDetailTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    WebDriver driver;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        driver = new ChromeDriver();
    }
    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Comprobar producto OK con todos los datos correctos")
    void productExistWithAllDetails() {
        Manufacturer manufacturer = manufacturerRepository.save(Manufacturer.builder().name("fabricante 1").year(2024).build());
        Product product = productRepository.save(Product.builder().name("prod1").price(40.43).quantity(3).active(true).manufacturer(manufacturer).build());

        // navegar al product-detail
        driver.get("http://localhost:8080/productos/" + product.getId());

        WebElement h1 = driver.findElement(By.tagName("h1"));
        assertEquals("Detalle producto " + product.getId(), h1.getText());

        assertEquals("prod1", driver.findElement(By.id("productTitle")).getText());
        assertEquals(
                product.getId().toString(),
                driver.findElement(By.id("product-id")).getText()
        );

        assertEquals(
                product.getPrice().toString(),
                driver.findElement(By.id("product-price")).getText()
        );

        WebElement active = driver.findElement(By.id("product-active"));
        assertEquals("Disponible", active.getText());
        assertEquals("rgba(0, 128, 0, 1)", active.getCssValue("color"));
        WebElement manufacturerLink = driver.findElement(By.id("manufacturer-link"));
        assertEquals("http://localhost:8080/manufacturers/" + manufacturer.getId(),
                manufacturerLink.getAttribute("href"));
        assertEquals("fabricante 1", manufacturerLink.getText());

    }
    @Test
    @DisplayName("Comprobar active false y manufacturer null")
    void checkFalseAndNullValues() {
            Product product = productRepository.save(Product.builder().name("prod1").price(40.43).quantity(3).active(false).build());
            driver.get("http://localhost:8080/productos/" + product.getId());

            // active
            WebElement active = driver.findElement(By.id("product-active"));//id del elemento
            assertEquals("No disponible", active.getText());//texto del elemento
            assertEquals("rgba(255, 0, 0, 1)", active.getCssValue("color"));//color del elemento

            // manufacturer
        WebElement manufacturerEmpty = driver.findElement(By.id("manufacturerEmpty"));//id del elemento
        assertEquals("Sin fabricante", manufacturerEmpty.getText());//texto del elemento
        }

    }



