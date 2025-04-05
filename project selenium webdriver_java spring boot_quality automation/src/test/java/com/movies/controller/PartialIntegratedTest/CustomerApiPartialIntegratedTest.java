package com.movies.controller.PartialIntegratedTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies.controller.CustomerApiController;
import com.movies.model.Customer;
import com.movies.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerApiPartialIntegratedTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerApiController customerApiController;


    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setNombre("Juan");
        customer.setApellido("Pérez");
        customer.setEmail("juan@example.com");
    }

    @Test
    @DisplayName("GET API CUSTOMERS")
    void testWelcome() throws Exception {
        mockMvc.perform(get("/api/welcome"))
                .andExpect(status().isOk())
                .andExpect(content().string("Bienvenido a un controlador de Spring"));
    }

    @Test
    @DisplayName("GET API CUSTOMERS BY NAME")
    void testGetUserName() throws Exception {
        mockMvc.perform(get("/api/user?name=Daniela"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome user Daniela"));
    }

    @Test
    @DisplayName("GET API CUSTOMERS BY NAME NOT FOUND")
    void testFindAllCustomers() throws Exception {
        when(customerRepository.findAll()).thenReturn(List.of(customer));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    @DisplayName("GET API CUSTOMERS BY ID")
    void testFindCustomerById() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    @DisplayName("GET API CUSTOMERS BY ID NOT FOUND")
    void testFindCustomerByIdNotFound() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST API CUSTOMERS")
    void testCreateCustomer() throws Exception {
        Customer newCustomer = new Customer();
        newCustomer.setNombre("Ana");
        newCustomer.setApellido("Lopez");
        newCustomer.setEmail("ana@example.com");

        when(customerRepository.save(any(Customer.class))).thenReturn(newCustomer);

        mockMvc.perform(post("/api/customers/new")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(newCustomer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Ana"));
    }

    @Test
    @DisplayName("POST API CUSTOMERS BAD REQUEST")
    void testCreateCustomerBadRequest() throws Exception {
        customer.setId(1L);  // Simulando que ya tiene un ID, lo cual es un error.
        when(customerRepository.save(any(Customer.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        mockMvc.perform(post("/api/customers/new")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT API CUSTOMERS")
    void testUpdateCustomer() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        customer.setNombre("Carlos");

        mockMvc.perform(put("/api/customers/update")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos"));
    }

    @Test
    @DisplayName("delete API CUSTOMERS ")
    void testDeleteCustomer() throws Exception {
        doNothing().when(customerRepository).deleteById(1L);

        mockMvc.perform(delete("/api/customers/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("delete API CUSTOMERS NOT FOUND")
    void testDeleteCustomerNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.CONFLICT)).when(customerRepository).deleteById(1L);

        mockMvc.perform(delete("/api/customers/delete/1"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testPartialUpdate_Success() throws Exception {
        Long customerId = 1L;
        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerId);
        existingCustomer.setNombre("Juan");
        existingCustomer.setApellido("Pérez");
        existingCustomer.setEmail("juan@example.com");

        Customer updateCustomer = new Customer();
        updateCustomer.setNombre("Carlos");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));

        mockMvc.perform(patch("/api/customers/{id}", customerId)
                        .contentType("application/merge-patch+json")
                        .content("{\"nombre\":\"Carlos\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos"))
                .andExpect(jsonPath("$.apellido").value("Pérez"))
                .andExpect(jsonPath("$.email").value("juan@example.com"));

        verify(customerRepository).save(existingCustomer);
    }

    @Test
    public void testPartialUpdate_CustomerNotFound() throws Exception {
        Long customerId = 1L;
        Customer updateCustomer = new Customer();
        updateCustomer.setNombre("Carlos");

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        mockMvc.perform(patch("/api/customers/{id}", customerId)
                        .contentType("application/merge-patch+json")
                        .content("{\"nombre\":\"Carlos\"}"))
                .andExpect(status().isNotFound());

        verify(customerRepository, never()).save(any());
    }

    @Test
    public void testPartialUpdate_BadRequest() throws Exception {
        mockMvc.perform(patch("/api/customers/{id}", (Long)null)
                        .contentType("application/merge-patch+json")
                        .content("{\"nombre\":\"Carlos\"}"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testDeleteAll_Success() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);

        doNothing().when(customerRepository).deleteAllByIdInBatch(ids);

        mockMvc.perform(delete("/api/customers/deleteAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ids)))
                .andExpect(status().isNoContent());

        verify(customerRepository).deleteAllByIdInBatch(ids);
    }

    @Test
    public void testDeleteAll_Conflict() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);

        doThrow(new RuntimeException()).when(customerRepository).deleteAllByIdInBatch(ids);

        mockMvc.perform(delete("/api/customers/deleteAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ids)))
                .andExpect(status().isConflict());

        verify(customerRepository).deleteAllByIdInBatch(ids);
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
