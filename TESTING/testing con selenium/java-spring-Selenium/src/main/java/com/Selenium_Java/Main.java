package com.Selenium_Java;

import com.Selenium_Java.model.*;
import com.Selenium_Java.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Set;

@SpringBootApplication

public class Main {

	public static void main(String[] args) {
		//crear e insertar objetos en la BBDD
		var context = SpringApplication.run(Main.class, args);
		// Se crea el contexto de la aplicación spring y se guarda en la variable context.
		//para extraer cualquier objeto de la app gracias a spring.

		var productRepository = context.getBean(ProductRepository.class);//	Se obtiene el repositorio de Product.
		var manufacturerRepo = context.getBean(ManufacturerRepository.class);// Se obtiene el repositorio de Manufacturer.
		var categoryRepository = context.getBean(CategoryRepository.class);// Se obtiene el repositorio de Category.
		var bookRepository = context.getBean(BookRepository.class);// Se obtiene el repositorio de Book.
		//metodo getBean obtener cualquier objeto de la aplicación
		//se está solicitando al contenedor de Spring que devuelva el bean que corresponde a la clase correspondiente

		if (productRepository.count() == 0) {// Si no hay productos en la base de datos, los creamos.
			var prod1 = Product.builder().name("Zumo multifrutas").price(1.33).quantity(1).active(true).build();
			var prod2 = Product.builder().name("Granola").price(4.33).quantity(4).active(false).build();
			productRepository.save(prod1);
			productRepository.save(prod2);
		}
		if (manufacturerRepo.count() == 0) {// Si no hay fabricantes en la base de datos, los creamos.
			var address1 = Address.builder().street("Calle Alfonso").city("Zaragoza").state("Aragón").zipCode("50003").build();
			var address2 = Address.builder().street("Calle Córdoba").city("Jaén").state("Andalucía").zipCode("23007").build();

			var manufacturer1 = Manufacturer.builder()
					.name("Adidas")
					.description("description A")
					.year(2024)
					.imageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Adidas_2022_logo.svg/1920px-Adidas_2022_logo.svg.png")
					.address(address1)
					.build();

			var manufacturer2 = Manufacturer.builder()
					.name("CertiDevs")
					.description("description A")
					.year(2024)
					.imageUrl("https://app.certidevs.com/content/images/CertiDevs-logo.svg")
					.address(address2)
					.build();
			manufacturerRepo.saveAll(
					List.of(manufacturer1, manufacturer2)
			);
		}
		if (categoryRepository.count() == 0) {// Si no hay categorías en la base de datos, las creamos.
			Category cat1 = categoryRepository.save(Category.builder().name("Ficción").description("description larga").build());
			Category cat2 = categoryRepository.save(Category.builder().name("Comedia").description("description larga").build());
			Category cat3 = categoryRepository.save(Category.builder().name("Romántica").description("description larga").build());

			Book book1 = new Book(); // Crearlo con constructor para que tenga el Set de categories inicializado en vez de null
			book1.setTitle("libro1");
			book1.setPrice(30.5);
			book1.getCategories().add(cat1);
			book1.getCategories().add(cat2);
			book1.getCategories().add(cat3);
			//book1.getCategories().addAll(Set.of(cat1, cat2, cat3));
			bookRepository.save(book1); // Owner de la asociación con Category, se tiene que guardar el book para que se guarde la asociación.
		}

	}
}