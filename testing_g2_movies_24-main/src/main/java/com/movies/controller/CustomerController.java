package com.movies.controller;

import com.movies.model.Categoria;
import com.movies.model.Customer;
import com.movies.model.Movie;
import com.movies.model.Valoracion;
import com.movies.repository.CategoriaRepository;
import com.movies.repository.CustomerRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.ValoracionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
//@RequestMapping("/customers")
@AllArgsConstructor

public class CustomerController {


    private CustomerRepository customerRepository;
    private MovieRepository movieRepository;
    private ValoracionRepository valoracionRepository;
    private CategoriaRepository categoriaRepository;


    @GetMapping("customers")
    public String findAll(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "customer-list";
    }

    @GetMapping("customers/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        model.addAttribute("customer", customer);
        model.addAttribute("movies", movieRepository.findAll());
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("valoraciones", valoracionRepository.findAll());
        return "customer-detail";
    }

    @GetMapping("customers404/{id}")
    public String findById_NotExist(@PathVariable Long id, Model model) {
        return customerRepository.findById(id)
                .map(customer -> {
                    model.addAttribute("customer", customer);
                    return "customer-detail";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    @GetMapping("customers/new")
    public String getFormCreateCustomer(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        model.addAttribute("movies", movieRepository.findAll());
        return "customer-form";
    }

    @GetMapping("customers/update/{id}")
    public String getFormUpdateCustomer(Model model, @PathVariable Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
                            model.addAttribute("customer", customer);
                            model.addAttribute("movies", movieRepository.findAll());
        return "customer-form";
    }


    @PostMapping("customers")
    public String saveCustomer(@ModelAttribute Customer customer) {
        customerRepository.save(customer);
         return "redirect:/customers";
        }


    @GetMapping("customers/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        customerRepository.deleteById(id);
        return "redirect:/customers";
    }

}
