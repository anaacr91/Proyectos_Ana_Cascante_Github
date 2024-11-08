package com.Selenium_Java;

import com.Selenium_Java.model.Address;
import com.Selenium_Java.model.Manufacturer;
import com.Selenium_Java.model.Product;
import com.Selenium_Java.repository.AddressRepository;
import com.Selenium_Java.repository.ManufacturerRepository;
import com.Selenium_Java.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication

public class Main {

	public static void main(String[] args) {
//extraer cualquier objeto de la app gracias a spring.
// Guardar contexto spring (contenedor objetos de la app) en 1 variable
		var context = SpringApplication.run(Main.class, args);
//metodo getBean obtener cualquier objeto de la aplicación
		ProductRepository productRepository = context.getBean(ProductRepository.class);
//comprobar si hay algun producto, si no hay ninguno o es =0, insertar un par
		long numeroProductos = productRepository.count();
		if (numeroProductos > 0) {
			return;
		}
// guardar producto product repository con el builder
		var producto1= Product.builder()
				.name("Producto 1")
				.price(10.0)
				.quantity(100)
				.active(true)
				.build();
		productRepository.save(producto1);
		
//otra forma
//		Product producto3 = productRepository.save(new Product(null, "Producto 2", 20.0, 200, true, null));

//crear un producto con el constructor + getters + setters
		Product producto2 = new Product();
		producto2.setName("Laptop");
		producto2.setPrice(3000.0);
		producto2.setQuantity(10);
		producto2.setActive(true);
		productRepository.save(producto2);

// main-> opcional: insertar datos demo: para que la bbdd no este vacia
		var manufacturerRepo = context.getBean(ManufacturerRepository.class);
		//repo address->
		var addressRepo = context.getBean(AddressRepository.class);
		//se está solicitando al contenedor de Spring que devuelva el bean que corresponde a la clase AddressRepository.

		if (manufacturerRepo.count() == 0);//si es nulo
		//insertar direccione-> Crear objetos Address en base de datos
		var address1 = Address.builder().street("Calle Alfonso").city("Zaragoza").state("Aragon").zipCode("5003").build();
		var address2 = Address.builder().street("Calle Córdoba").city("Jaen").state("Andalucia").zipCode("23007").build();
		// insertar fabricantes-> Crear objetos Manufacturer en base de datos
		var manufacturer1 = Manufacturer.builder().name("Adidas").description("description A").year(2024)
				.imageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Adidas_2022_logo.svg/1920px-Adidas_2022_logo.svg.png")
				.address(address1).build();
		var manufacturer2 = Manufacturer.builder().name("CertiDevs").description("description A").year(2024)
				.imageUrl("https://app.certidevs.com/content/images/CertiDevs-logo.svg")
				.address(address2).build();
		manufacturerRepo.saveAll(List.of(manufacturer1, manufacturer2));

		manufacturerRepo.saveAll(List.of
				(manufacturer1, manufacturer2));






	}
}
