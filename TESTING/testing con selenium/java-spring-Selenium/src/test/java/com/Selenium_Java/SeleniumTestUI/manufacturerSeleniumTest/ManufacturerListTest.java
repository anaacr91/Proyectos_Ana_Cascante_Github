package com.Selenium_Java.SeleniumTestUI.manufacturerSeleniumTest;

import com.Selenium_Java.model.Manufacturer;
import com.Selenium_Java.repository.ManufacturerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
        driver.manage().window().maximize();
        page = new ManufacturerListPagePom(driver);
    }//page es un objeto de la clase ManufacturerListPagePom
    // Esta clase representa una página específica, en este caso, una lista de fabricantes, como un "objeto".
    //ManufacturerListPagePom tiene métodos y propiedades que permiten interactuar con los elementos de la página, como botones, cuadros de texto, etc.
   //driver: Es el objeto WebDriver, que controla el navegador web. Este driver se pasa al constructor de la clase ManufacturerListPagePom
    // para que la clase pueda interactuar con el navegador.
  //  En resumen, esta línea crea una nueva instancia de la página ManufacturerListPagePom y la almacena en la variable page.
    //  Luego, puedes usar page para interactuar con la página de la lista de fabricantes a través de métodos que encapsulan acciones como
    //  hacer clic en botones, obtener texto, verificar elementos, etc., siguiendo el patrón Page Object Model para mantener el código más organizado y reutilizable.
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
    @Test
    @DisplayName("Comprobar que la tabla de fabricantes se muestra correctamente")
    void checkManufacturersTable() {
        manufacturerRepo.saveAll(List.of(
                Manufacturer.builder().name("fabricante 1").year(2019).build(),
                Manufacturer.builder().name("fabricante 2").year(2030).build(),
                Manufacturer.builder().name("fabricante 3").year(2015).build()
        ));

        driver.navigate().refresh();
        assertTrue(page.manufacturersTable.isDisplayed());
        assertThrows(
                NoSuchElementException.class,
                () -> page.manufacturersEmpty.isDisplayed()
        );
    }
    @Test
    @DisplayName("Comprobar los datos de cada fabricante")
    void checkManufacturersData() {
        insertManufacturers();//insertar fabricantes
        assertEquals("fabricante 1", page.getManufacturerName(1L).getText());
        assertEquals("fabricante 2", page.getManufacturerName(2L).getText());
        assertEquals("fabricante 3", page.getManufacturerName(3L).getText());
        // Forma flexible comprobar: se obtienen todos los nombres de fabricantes sin importar el id:
        List<WebElement> names = page.getManufacturerNames();
        assertEquals(3, names.size());
        assertEquals("fabricante 1", names.getFirst().getText());//getFirst= get(0)
        names.forEach((WebElement name) -> assertTrue(name.getText().startsWith("fabricante")));
        // Otra opción: traerlos de base de datos:
        List<Manufacturer> manufacturers = manufacturerRepo.findAll();
        Long manufacturerId = manufacturers.get(0).getId();
        assertEquals("fabricante 1", page.getManufacturerName(manufacturerId).getText());
    }
    /**
     * Método para insertar fabricante demo y refrescar la pantalla
     */
    private void insertManufacturers() {
        manufacturerRepo.saveAll(List.of(
                Manufacturer.builder().name("fabricante 1").year(2019).description("man1").imageUrl("man1").build(),
                Manufacturer.builder().name("fabricante 2").year(2030).description("man2").imageUrl("man1").build(),
                Manufacturer.builder().name("fabricante 3").year(2015).description("man3").imageUrl("man1").build()
        ));
        driver.navigate().refresh();
    }
    @Test
    @DisplayName("Comprobar que el botón de ver lleva a detalle fabricante")
    void checkActionViewButton() {
        insertManufacturers();//insertar datos
        Manufacturer manufacturer = manufacturerRepo.findAll().getFirst();
        page.clickViewButton(manufacturer.getId());

        assertEquals(
                "http://localhost:8080/manufacturers/" + manufacturer.getId(),
                driver.getCurrentUrl()
        );
    }
    @Test
    @DisplayName("Comprobar todos los datos de un fabricante")
    void checkManufacturerAllData() {
        insertManufacturers();
        Manufacturer manufacturer = manufacturerRepo.findAll().getFirst();
        var manufacturerCard = page.getManufacturer(manufacturer.getId());
        assertEquals(manufacturer.getName(), manufacturerCard.getName().getText());
        assertEquals("Año de fundación: " + manufacturer.getYear(), manufacturerCard.getYear().getText());
        assertEquals(manufacturer.getDescription(), manufacturerCard.getDescr().getText());
        assertEquals("http://localhost:8080/" + manufacturer.getImageUrl(), manufacturerCard.getImage().getAttribute("src"));
// Acción Ver
        manufacturerCard.getViewButton().click();
        assertEquals("http://localhost:8080/manufacturers/" + manufacturer.getId(), driver.getCurrentUrl());
        driver.navigate().back();
// Acción Editar
        manufacturerCard.getEditButton().click();
        assertEquals("http://localhost:8080/manufacturers/update/" + manufacturer.getId(), driver.getCurrentUrl());
        driver.navigate().back();
// Acción Borrar
        manufacturerCard.getDeleteButton().click();
        assertThrows(NoSuchElementException.class, () -> page.getManufacturerName(manufacturer.getId()));

    }
}
