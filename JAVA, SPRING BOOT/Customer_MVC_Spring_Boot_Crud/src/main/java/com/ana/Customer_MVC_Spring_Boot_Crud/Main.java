package com.ana.Customer_MVC_Spring_Boot_Crud;

import com.ana.Customer_MVC_Spring_Boot_Crud.model.Customer;
import com.ana.Customer_MVC_Spring_Boot_Crud.repository.CustomerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		var context= SpringApplication.run(Main.class, args);
		CustomerRepository productRepository = context.getBean(CustomerRepository.class);
		Customer customer = Customer.builder()
				.id(1)
				.nombre("Ana")
				.apellido("Cascante")
				.email("anacascanterodriguez@gmail.com")
				.edad(33)
				.telefono(638670653)
				.build();

		System.out.println(customer);
		productRepository.save(customer);
	}

}
