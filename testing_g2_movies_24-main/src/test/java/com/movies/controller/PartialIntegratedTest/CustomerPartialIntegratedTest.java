package com.movies.controller.PartialIntegratedTest;

import com.movies.model.Customer;
import com.movies.repository.CategoriaRepository;
import com.movies.repository.CustomerRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.ValoracionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerPartialIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

     @MockBean
    private CustomerRepository customerRepository;

     @MockBean
    private MovieRepository movieRepository;

     @MockBean
     private CategoriaRepository categoriaRepository;

    @MockBean
    private ValoracionRepository valoracionRepository;

    @Test
    @DisplayName("test de integración parcial de encontrar todas los clientes, de customerController")
    void findAll() throws Exception {
        when (customerRepository.findAll()).thenReturn(List.of(
                Customer.builder().id(1L).build(),
        Customer.builder().id(2L).build()));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer-list"))
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attribute("customers", hasSize(2)));
    }

    @Test
    @DisplayName("test de integración parcial de encontrar un cliente por id, de customerController")
    void findById() throws Exception {
        Customer customer = Customer.builder().id(1L).build();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("customer-detail"))
                .andExpect(model().attributeExists("customer"));
    }

    @Test
    @DisplayName("test de integración parcial de encontrar un cliente por id, de customerController, cliente no encontrado")
    void findById_CustomerNotFound() throws Exception{
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/customers/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals("Customer not found", ((ResponseStatusException) result.getResolvedException()).getReason()));

        verify(customerRepository).findById(1L);
    }

    @Test
    @DisplayName("test de integración parcial de encontrar un cliente por id, de customerController, id no encontrado")
    void findById_IdNotFound() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/customers404/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals("Customer not found", ((ResponseStatusException) result.getResolvedException()).getReason()));

        verify(customerRepository).findById(1L);
    }

    @Test
    @DisplayName("Test de integración parcial para Obtener formulario para crear nuevo cliente, de customerController")
    void getFormCreateCustomer() throws Exception {
        mockMvc.perform(post("/customers")
                .param("nombre", "Cliente")
                .param("apellido", "1")
                .param("email", "123@gmail.com")
                .param("phone", "123"))
        .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));

        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("Test de integración parcial para Obtener formulario para actualizar un cliente, de customerController")
    void getFormUpdateCustomer() throws Exception {
        Customer customer = Customer.builder().id(1L).build();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customers/update/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("customer-form"))
                .andExpect(model().attributeExists("customer"))
                .andExpect(model().attribute("customer", customer));
    }

     @Test
        @DisplayName("Test de integración parcial para Obtener formulario para actualizar un cliente, de customerController, cliente no encontrado")
     void getFormUpdateCustomer_NotFound() throws Exception{
         when(customerRepository.findById(1L)).thenReturn(Optional.empty());

         mockMvc.perform(get("/customers/update/{id}", 1L))
                 .andExpect(status().isNotFound());
     }
    @Test
    @DisplayName("Test de integración parcial para guardar nuevo cliente, de customerController")
    void saveCustomerNew () throws Exception {
        mockMvc.perform(post("/customers")
                        .param("nombre", "Cliente")
                        .param("apellido", "1")
                        .param("email", "123@gmail.com")
                        .param("password", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));

        verify(customerRepository).save(any(Customer.class));
    }
    @Test
    @DisplayName("Test de integración parcial para guardar cliente actualizado, de customerController")
    void saveCustomerUpdate() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setNombre("Juan");
        customer.setApellido("Cuesta");
        customer.setEmail("presidente@example.com");
        customer.setPassword("password123");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/customers")
                        .param("id", "1")
                        .param("nombre", "Cliente Actualizado")
                        .param("apellido", "1")
                        .param("email", "123@gmail.com")
                        .param("password", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));

        verify(customerRepository, times(1)).save(any(Customer.class));
    }
    @Test
    @DisplayName("Test de integración parcial para eliminar un cliente, de customerController")
    void deleteCustomer() throws Exception{
        when(customerRepository.existsById(1L)).thenReturn(true);
        mockMvc.perform(get("/customers/delete/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));

        verify(customerRepository).deleteById(1L);
    }

}
