package com.movies.controller.IntegrationTest;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasItem;
import com.movies.model.Customer;
import com.movies.repository.CategoriaRepository;
import com.movies.repository.CustomerRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.ValoracionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
//@Transactional
public class CustomerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ValoracionRepository valoracionRepository;

    @BeforeEach
    void setUp() {
        valoracionRepository.deleteAll();
        movieRepository.deleteAll();
        categoriaRepository.deleteAll();
        customerRepository.deleteAll();
    }
    @Test
    @DisplayName("Test de integración findAll de customerController")
    void findAll() throws Exception {
        Customer customer1 = customerRepository.save(Customer.builder()
                .nombre("Ana")
                .apellido("C")
                .email("ana.c@example.com")
                .password("password")
                .build());

        Customer customer2 = customerRepository.save(Customer.builder()
                .nombre("P")
                .apellido("C")
                .email("p.c@example.com")
                .password("password")
                .build());

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attribute("customers", hasSize(2)))
                .andExpect(model().attribute("customers", hasItem(
                        allOf(
                                hasProperty("id", is(customer1.getId())),
                                hasProperty("nombre", is(customer1.getNombre())),
                                hasProperty("apellido", is(customer1.getApellido())),
                                hasProperty("email", is(customer1.getEmail()))
                        )
                )))
                .andExpect(model().attribute("customers", hasItem(
                        allOf(
                                hasProperty("id", is(customer2.getId())),
                                hasProperty("nombre", is(customer2.getNombre())),
                                hasProperty("apellido", is(customer2.getApellido())),
                                hasProperty("email", is(customer2.getEmail()))
                        )
                )));
    }
    @Test
    @DisplayName("Test de integración findById de customerController")
    void findById() throws Exception {
        Customer customer = customerRepository.save(Customer.builder()
                .id(1L)
                .nombre("Ana")
                .apellido("C")
                .email("ana.c@example.com")
                .password("password")
                .build());

        mockMvc.perform(get("/customers/{id}", customer.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("customer-detail"))
                .andExpect(model().attributeExists("customer"));
    }
    @Test
    @DisplayName("Test de integración findById, id no existente, de customerController")
    void findById_NotExist() throws Exception {
        mockMvc.perform(get("/customers404/{id}", 999L))
                .andExpect(status().isNotFound());
    }
        @Test
        @DisplayName("Test de integración ir al formulario, crear cliente nuevo, de customerController")
    void getFormToCreateCustomer() throws Exception {
        mockMvc.perform(get("/customers/new"))
               .andExpect(status().isOk())
               .andExpect(view().name("customer-form"))
               .andExpect(model().attributeExists("customer"));
    }
    @Test
    @DisplayName("Test de integración ir al formulario, editar cliente existente, de customerController")
    void getFormToUpdateCustomer() throws Exception {
        Customer customer = customerRepository.save(Customer.builder().id(1L).build());

        mockMvc.perform(get("/customers/update/{id}", customer.getId()))
               .andExpect(status().isOk())
               .andExpect(view().name("customer-form"))
               .andExpect(model().attributeExists("customer"));
    }
    @Test
    @DisplayName("Test de integración guardar cliente nuevo, de customerController")
    void saveCustomer() throws Exception {
        Customer customer = Customer.builder()
                .id(1L)
                .nombre("Ana")
                .apellido("C")
                .email("ana.c@example.com")
                .password("password")
                .build();
          mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nombre", "Ana")
                        .param("apellido", "C")
                        .param("email", "ana.c@example.com")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));


        assertEquals("Ana", customer.getNombre());
        assertEquals("C", customer.getApellido());
        assertEquals("ana.c@example.com", customer.getEmail());
        assertEquals("password", customer.getPassword());
    }

    @Test
    @DisplayName("Test de integración borrar cliente, de customerController")
    void deleteCustomer() throws Exception {
        Customer customer = customerRepository.save(Customer.builder()
                .id(1L)
                .nombre("P")
                .apellido("C")
                .email("p.c@example.com")
                .password("password")
                .build());

        mockMvc.perform(get("/customers/delete/{id}", customer.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));
    }

}
