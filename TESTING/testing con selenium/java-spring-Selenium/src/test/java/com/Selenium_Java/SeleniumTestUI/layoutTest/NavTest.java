package com.Selenium_Java.SeleniumTestUI.layoutTest;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//(webEnvironment)levanta app 8080 sin necesidad arrancar main, ya que no se puede tener esta app y la de test en el mismo puerto
public class NavTest {
    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/productos");
        driver.manage().window().maximize();//expandido modo escritorio para ver los elementos y que sean navigables
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
        driver.navigate().refresh();
        assertEquals("http://localhost:8080/manufacturers", driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Comprobar navbar colapsada en móvil con espera")
    void checkMobileNavbar() {
        driver.manage().window().setSize(new Dimension(390, 900));
        //adaptar el tamaño de la ventana para simular un móvil
        assertFalse(driver.findElement(By.id("manufacturersLink")).isDisplayed());
        driver.findElement(By.cssSelector("button.navbar-toggler")).click();
        //darle click a la hamburguesa(navbar colapsado)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        //espera 3 segundos antes de que el elemento sea verificado
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("manufacturersLink")));
        //ver los botones expandidos y hacer click
        assertTrue(driver.findElement(By.id("manufacturersLink")).isDisplayed());
        driver.findElement(By.id("manufacturersLink")).click();
        assertEquals("http://localhost:8080/manufacturers", driver.getCurrentUrl());
    }

}
