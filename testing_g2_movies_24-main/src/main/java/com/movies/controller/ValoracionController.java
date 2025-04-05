package com.movies.controller;


import com.movies.model.Valoracion;
import com.movies.repository.CustomerRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.ValoracionRepository;
import com.movies.repository.CustomerRepository;
import com.movies.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@AllArgsConstructor
public class ValoracionController {

    private MovieRepository movieRepository;
    private CustomerRepository customerRepository;
    private ValoracionRepository valoracionRepository;


    @GetMapping("valoraciones")
    public String findAll(Model model) {
        model.addAttribute("valoraciones", valoracionRepository.findAll());
        return "valoracion-list";
    }

    @GetMapping("valoraciones/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        Valoracion valoracion = valoracionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valoración no encontrada"));
        model.addAttribute("valoracion", valoracion);
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("movies", movieRepository.findAll());
        return "valoracion-detail";
    }

    @GetMapping("valoraciones/new")
    public String createValoracion(Model model) {
        model.addAttribute("valoracion", new Valoracion());
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("movies", movieRepository.findAll());
        return "valoracion-form";
    }

    @GetMapping("valoraciones/edit/{id}")
    public String updateValoracion(@PathVariable Long id, Model model) {
        Valoracion valoracion = valoracionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valoración no encontrada"));
        model.addAttribute("valoracion", valoracion);
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("movies", movieRepository.findAll());
        return "valoracion-form";
    }

    @PostMapping("valoraciones")
    public String save(@ModelAttribute Valoracion valoracion) {
        valoracionRepository.save(valoracion);
        return "redirect:/valoraciones";
    }

    @GetMapping("valoraciones/delete/{id}")
    public String deleteValoracion(@PathVariable("id") Long id) {
        valoracionRepository.deleteById(id);
        return "redirect:/valoraciones";
    }
}
