package com.Selenium_Java.SeleniumTestUI.layoutTest;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class NavTest {
    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/productos");
    }
    @AfterEach
    void tearDown(){
        driver.quit();
    }
    @Test
    @DisplayName("Comprobar logo navbar con link a products")
    void checkLogoWithLink() {
        var logo = driver.findElement(By.cssSelector("a.navbar-brand > img"));
        assertTrue(logo.isDisplayed());
    //verificar si el logo está visible
        driver.findElement(By.id("homeLink")).click();
        assertEquals("http://localhost:8080/productos", driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Comprobar menú de fabricantes")
    void navigateToManufacturers() {
        driver.findElement(By.id("manufacturersLink")).click();
        //TODO: BUG, ME DICE ELEMENTO NO NAVEGABLE?
        driver.navigate().refresh();
        assertEquals("http://localhost:8080/manufacturers", driver.getCurrentUrl());

    }

    @Test
    @DisplayName("Comprobar navbar colapsada en móvil")
    void checkMobileNavbar() {
        driver.manage().window().setSize(new Dimension(390, 900));
        //adaptar el tamaño de la ventana para simular un móvil
        System.out.println("fin");
    }

}
