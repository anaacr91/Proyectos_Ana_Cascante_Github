package com.movies;

import com.movies.model.Categoria;
import com.movies.model.Customer;
import com.movies.model.Movie;
import com.movies.model.Valoracion;
import com.movies.repository.CategoriaRepository;
import com.movies.repository.CustomerRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.ValoracionRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		var context = SpringApplication.run(Main.class, args);

		// Repositorios
		CustomerRepository customerRepository = context.getBean(CustomerRepository.class);
		CategoriaRepository categoriaRepository = context.getBean(CategoriaRepository.class);
		MovieRepository movieRepository = context.getBean(MovieRepository.class);
		ValoracionRepository valoracionRepository = context.getBean(ValoracionRepository.class);

		// Verificar y cargar datos iniciales de Customer
		if (customerRepository.count() == 0) {
			Customer customer1 = Customer.builder()
					.nombre("Cliente")
					.apellido("1")
					.email("123@gmail.com")
					.password("123")
					.build();
			Customer customer2 = Customer.builder()
					.nombre("Cliente")
					.apellido("2")
					.email("456@gmail.com")
					.password("456")
					.build();

			customerRepository.save(customer1);
			customerRepository.save(customer2);
		}

		// Verificar y cargar datos iniciales de Categoria
		if (categoriaRepository.count() == 0) {
			Categoria categoria1 = new Categoria(null, "Acción", "Películas de acción", null);
			Categoria categoria2 = new Categoria(null, "Comedia", "Películas de comedia", null);

			categoriaRepository.save(categoria1);
			categoriaRepository.save(categoria2);
		}

		// Verificar y cargar datos iniciales de Movie
		if (movieRepository.count() == 0) {
			Categoria categoriaAccion = categoriaRepository.findAll().stream()
					.filter(c -> c.getNombre().equals("Acción"))
					.findFirst()
					.orElse(null);

			Movie movie1 = Movie.builder()
					.name("Pelicula Acción")
					.duration(120)
					.year(2021)
					.available(true)
					.rentalPricePerDay(5.00)
					.categoria(categoriaAccion)
					.build();

			movieRepository.save(movie1);
		}

		// Verificar y cargar datos iniciales de Valoracion
		if (valoracionRepository.count() == 0) {
			Movie peliculaAccion = movieRepository.findAll().stream()
					.filter(m -> m.getName().equals("Pelicula Acción"))
					.findFirst()
					.orElse(null);

			Customer cliente1 = customerRepository.findAll().stream()
					.filter(c -> c.getEmail().equals("123@gmail.com"))
					.findFirst()
					.orElse(null);

			Valoracion valoracion1 = Valoracion.builder()
					.movie(peliculaAccion)
					.customer(cliente1)
					.puntuacion(5)
					.comentario("Excelente película de acción")
					.build();

			valoracionRepository.save(valoracion1);
		}
	}
}