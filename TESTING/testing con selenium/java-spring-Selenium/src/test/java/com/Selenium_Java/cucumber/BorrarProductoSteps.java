package com.Selenium_Java.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

@Component
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/cucumber")
public class BorrarProductoSteps extends CucumberSpringConfiguration {
    private WebDriver driver;
    private WebDriverWait wait;

    @Given("que estoy en la pantalla de ProductList")
    public void queEstoyEnLaPantallaDeProductList() {
        // Configuramos el driver (asegúrate de tener el driver de Chrome en la ruta indicada)
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navegamos a la pantalla de ProductList
        driver.get("http://localhost:8080/productlist");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("product-list")));
    }

    @When("hago click en el botón de borrar producto")
    public void hagoClickEnElBotonDeBorrarProducto() {
        // Localizamos el botón de borrar producto y hacemos click
        WebElement deleteButton = driver.findElement(By.cssSelector("button.delete-product"));
        deleteButton.click();
    }

    @Then("debo ver la pantalla de ProductList")
    public void deboVerLaPantallaDeProductList() {
        // Esperamos que la pantalla de ProductList esté visible nuevamente
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("product-list")));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/productlist"));
    }

    @And("debo ver la lista de productos sin el producto borrado")
    public void deboVerLaListaDeProductosSinElProductoBorrado() {
        // Verificamos que el producto ya no esté en la lista
        // Aquí se asume que el producto tiene un identificador específico que podemos verificar
        boolean isProductPresent = driver.findElements(By.id("deleted-product-id")).isEmpty();
        Assert.assertTrue("El producto no debería estar en la lista", isProductPresent);

        // Cerramos el navegador
        driver.quit();
    }
}
