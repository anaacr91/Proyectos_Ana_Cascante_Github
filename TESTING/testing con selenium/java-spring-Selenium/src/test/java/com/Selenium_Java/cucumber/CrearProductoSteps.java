package com.Selenium_Java.cucumber;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;

@Component
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/cucumber")
public class CrearProductoSteps extends CucumberSpringConfiguration{
    @Given("que estoy en la pantalla de ProductList")
    public void cargarProductList() {

    }

    @When("hago click en el botón de crear nuevo producto")
    public void hagoClickEnElBotonDeCrearNuevoProducto() {

    }

    @Then("debo ver la pantalla de creación de un nuevo producto")
    public void deboVerLaPantallaDeCreacionDeUnNuevoProducto() {
    }

}
