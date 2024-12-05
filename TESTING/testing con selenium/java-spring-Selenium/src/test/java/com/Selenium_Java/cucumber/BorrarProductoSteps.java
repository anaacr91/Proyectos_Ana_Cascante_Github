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


public class BorrarProductoSteps {
    private WebDriver driver;
    private WebDriverWait wait;

    @When("hago click en el botón de borrar producto")
    public void hagoClickEnElBotonDeBorrarProducto() {
    }


    @Then("debo ver la pantalla de ProductList")
    public void deboVerLaPantallaDeProductList() {
    }


    @And("debo ver la lista de productos sin el producto borrado")
    public void deboVerLaListaDeProductosSinElProductoBorrado() {
    }
}
