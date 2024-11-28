package com.Selenium_Java.SeleniumTestUI.manufacturerSeleniumTest;

import com.Selenium_Java.repository.ManufacturerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/*
Test de Selenium utilizando Page Object Model
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ManufacturerListTest {
    @Autowired
    private ManufacturerRepository manufacturerRepo;
    private WebDriver driver;
    private ManufacturerListPagePom page;
//importamos la clase ManufacturerListPagePom
    @BeforeEach
    void setUp() {
        manufacturerRepo.deleteAllInBatch();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/manufacturers");
        page = new ManufacturerListPagePom(driver);
    }//page es un objeto de la clase ManufacturerListPagePom
    @AfterEach
    void tearDown() {
        driver.quit();
    }
    @Test
    void h1Test() {
        assertEquals("Listado fabricantes/Marcas", page.h1.getText());
    }
    @Test
    void createButton() {//usamos page porque es un metodo de la clase ManufacturerListPagePom
        page.createButton.click();//click en el botón de crear
        assertEquals(
                "http://localhost:8080/manufacturers/new",
                driver.getCurrentUrl()
        );
    }
    @Test
    @DisplayName("Comprobar que si no hay datos no sale la tabla, sale mensaje")
    void checkEmpty() {//comprobamos que el mensaje de que no hay datos está visible
        assertTrue(page.manufacturersEmpty.isDisplayed());
        assertThrows(
                NoSuchElementException.class,
                () -> page.manufacturersTable.isDisplayed()
        );
    }
}
