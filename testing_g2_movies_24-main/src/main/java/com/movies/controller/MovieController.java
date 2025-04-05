package com.movies.controller;

import com.movies.model.Categoria;
import com.movies.model.Customer;
import com.movies.model.Movie;
import com.movies.repository.CategoriaRepository;
import com.movies.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;
import java.util.List;
import java.util.NoSuchElementException;


@RequiredArgsConstructor
@Controller
//@Valid
//@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepository;
    private final CategoriaRepository categoriaRepository;



    @GetMapping("movies")
    public String findAll(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "movie-list";
    }

    @GetMapping("movies/{id}")
    public String findById(Model model, @PathVariable Long id) {
        movieRepository.findById(id)
                .ifPresentOrElse(
                        movie -> {
                            model.addAttribute("movie", movie);
                            List<Categoria> categorias = categoriaRepository.findAll();
                            model.addAttribute("categorias", categorias);
                        },
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "movie not found");
                        }
                );
        return "movie-detail";
    }

    @GetMapping("movies/new")
    public String createForm(Model model) {
        Movie movie = new Movie();
        List <Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute( "movie", movie);
       model.addAttribute("categorias", categorias);
        return "movie-form";
    }

    @GetMapping("movies/update/{id}")
    public String editForm(Model model, @PathVariable Long id) {
        List <Categoria> categorias = categoriaRepository.findAll();
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        model.addAttribute("movie", movie);
        model.addAttribute("categorias", categorias);
        return "movie-form";
    }

    @PostMapping("movies")
    public String saveMovie(@ModelAttribute Movie movie) {
       movieRepository.save(movie);
        return "redirect:/movies";
    }

    @GetMapping("movies/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
        movieRepository.deleteById(id);
        return "redirect:/movies";
    }

    @GetMapping("movies404/{id}")
    public String findById_NotExist(Model model, @PathVariable Long id) {
        return movieRepository.findById(id)
                .map(movie -> {
                    model.addAttribute("movie", movie);
                    return "movie-detail";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pelicula no encontrada"));
    }


}