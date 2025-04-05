package com.movies.controller.UnitTest;

import com.movies.controller.CustomerController;
import com.movies.model.Customer;
import com.movies.repository.CategoriaRepository;
import com.movies.repository.CustomerRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.ValoracionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class CustomerControllerUnitTest {
    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ValoracionRepository valoracionRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    Model model;

    @Test
    @DisplayName("Test unitario findall controlador customer")
    void findAll(){
        when (customerRepository.findAll()).thenReturn(List.of(
                Customer.builder().id(1L).build()));
        String view = customerController.findAll(model);
        verify(customerRepository).findAll();
        assertEquals("customer-list", view);
    }

    @Test
    @DisplayName("Test unitario find por id controlador customer")
    void findById(){
        Customer customer = Customer.builder().id(1L).build();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        String view = customerController.findById(1L, model);
        assertEquals("customer-detail", view);
        verify(customerRepository).findById(1L);
        verify(model).addAttribute("customer", customer);
    }
    @Test
    @DisplayName("Test unitario find por id, cliente no encontrado, controlador customer")
    void findById_CustomerNotFound(){
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            customerController.findById(1L, model);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Customer not found", exception.getReason());
        verify(customerRepository).findById(1L);
        verify(model, never()).addAttribute(eq("customer"), any());
    }
    @Test
    @DisplayName("Test unitario find por id, id no encontrado, controlador customer")
    void findById_IdNotFound(){
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            customerController.findById_NotExist(1L, model);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Customer not found", exception.getReason());
        verify(customerRepository).findById(1L);
        verify(model, never()).addAttribute(eq("customer"), any());
    }

    @Test
    @DisplayName("Test unitario ir al formulario, crear cliente nuevo, controlador customer")
    void getFormCreateCustomer(){
        Customer customer = new Customer();
        String view = customerController.getFormCreateCustomer(model);
        assertEquals("customer-form", view);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    @DisplayName("Test unitario ir al formulario, editar cliente existente, controlador customer")
    void getFormUpdateCustomer(){
        Customer customer = Customer.builder().id(1L).build();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        String view = customerController.getFormUpdateCustomer(model, 1L);
        assertEquals("customer-form", view);
        verify(model).addAttribute("customer", customer);
        verify(customerRepository).findById(1L);
    }
    @Test
    @DisplayName("Test unitario guardar cliente, cliente no encontrado, controlador customer")
    void getFormUpdateCustomer_NotFound(){
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            customerController.getFormUpdateCustomer(model, 1L);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Customer not found", exception.getReason());
        verify(customerRepository).findById(1L);
        verify(model, never()).addAttribute(eq("customer"), any());
    }

    @Test
    @DisplayName("Test unitario guardar cliente nuevo, controlador customer")
    void saveCustomerNew(){
        Customer customer = new Customer();
        String view = customerController.saveCustomer(customer);
        assertEquals("redirect:/customers", view);
        verify(customerRepository).save(customer);
    }

    @Test
    @DisplayName("Test unitario guardar cliente editado controlador customer")
    void saveCustomerUpdate(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setNombre("Juan");
        customer.setApellido("Cuesta");
        customer.setEmail("presidente@example.com");
        customer.setPassword("password123");
        String result = customerController.saveCustomer(customer);
        verify(customerRepository, times(1)).save(customer); // Verifica que se llama una vez al método save
        assertEquals("redirect:/customers", result); // Verifica la redirección esperada
    }

    @Test
    @DisplayName("Test unitario borrar cliente controlador customer")
    void deleteCustomer(){
        when(customerRepository.existsById(1L)).thenReturn(true);
        String view = customerController.deleteCustomer(1L);
        assertEquals("redirect:/customers", view);
        verify(customerRepository).deleteById(1L);
    }


}
