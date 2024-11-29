package com.Selenium_Java.SeleniumTestUI.manufacturerSeleniumTest;
import com.Selenium_Java.SeleniumTestUI.manufacturerSeleniumTest.PagePom.ManufacturerFormPage;
import com.Selenium_Java.SeleniumTestUI.manufacturerSeleniumTest.PagePom.ManufacturerListPage;
import com.Selenium_Java.model.Manufacturer;
import com.Selenium_Java.repository.ManufacturerRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

/*Test Selenium*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class manufacturerFormTest {
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    private WebDriver driver;
    private ManufacturerFormPage page;
    private WebDriverWait wait;
    @BeforeEach
    void setUp() {
        // Configurar el WebDriver (ChromeDriver en este caso)
        manufacturerRepository.deleteAllInBatch();
        driver = new ChromeDriver();//abre navegador, y va url
        driver.get("http://localhost:8080/manufacturers/new");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();//max pantalla
        page = new ManufacturerFormPage(driver);

        //page es un objeto de la clase ManufacturerFormPage
        // que se inicializa con el driver
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
    private void insertManufacturers() {
        manufacturerRepository.saveAll(List.of(
                Manufacturer.builder().name("fabricante 1").year(2019).description("man1").imageUrl("man1").build(),
                Manufacturer.builder().name("fabricante 2").year(2030).description("man2").imageUrl("man1").build(),
                Manufacturer.builder().name("fabricante 3").year(2015).description("man3").imageUrl("man1").build()
        ));
        driver.navigate().refresh();
    }
    @Test
    public void testCreateNewManufacturer() {
        // Verificar que el título sea "Crear nuevo fabricante"
        assertEquals("Crear nuevo fabricante", page.h1.getText());
        // Rellenar los campos del formulario
        page.fillInput(page.name, "Nuevo Fabricante");
        page.fillInput(page.description, "Descripción del fabricante");
        page.fillInput(page.imageUrl, "http://example.com/imagen.jpg");
        page.fillInput(page.year, "2023");
        page.fillInput(page.street, "Calle Falsa 123");
        page.fillInput(page.city, "Ciudad Ejemplo");
        page.fillInput(page.state, "Provincia Ejemplo");
        page.fillInput(page.zipcode, "12345");
        scrollToElement(page.saveNewButton);
        // Hacer clic en el botón "Guardar creación"
        page.saveNewButton.click();
        // Validar el resultado (esto dependerá de cómo se maneje la respuesta en tu aplicación)
        // Por ejemplo, podrías verificar si se muestra un mensaje de éxito o redirección
    }

    @Test
    public void testEditManufacturer() {
        // Aquí se asume que se navega a un formulario existente con manufacturer.id no nulo
        // Navegar a la página del formulario de edición de fabricante
        insertManufacturers();
        Manufacturer manufacturer = manufacturerRepository.findAll().getFirst();
        driver.get("http://localhost:8080/manufacturers/update/"+manufacturer.getId());
        // Verificar que el título sea "Editar fabricante"
        assertEquals("Editar fabricante", page.h1.getText());
        // Editar los campos del formulario
        page.fillInput(page.name, "Fabricante Editado");
        page.fillInput(page.description, "Descripción editada del fabricante");
        page.fillInput(page.imageUrl, "http://example.com/imagen_editada.jpg");
        page.fillInput(page.year, "2024");
        page.fillInput(page.street, "Avenida Siempre Viva 742");
        page.fillInput(page.city, "Ciudad Editada");
        page.fillInput(page.state, "Provincia Editada");
        page.fillInput(page.zipcode, "54321");
        scrollToElement(page.saveEditButton);
        // Hacer clic en el botón "Guardar edición"
        page.saveEditButton.click();
        // Validar el resultado (esto dependerá de cómo se maneje la respuesta en tu aplicación)
        // Por ejemplo, podrías verificar si se muestra un mensaje de éxito o redirección
    }
    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.visibilityOf(element));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

}

