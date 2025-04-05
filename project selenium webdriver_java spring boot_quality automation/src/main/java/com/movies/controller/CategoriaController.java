package com.movies.controller;

import com.movies.model.Categoria;
import com.movies.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Controller
//@RequestMapping("/categorias")
@AllArgsConstructor

public class CategoriaController {


    //    private CustomerRepository customerRepository;
//    private MovieRepository movieRepository;
//    private ValoracionRepository valoracionRepository;
    private CategoriaRepository categoriaRepository;

    @GetMapping("categorias")
    public String findAll(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "categoria-list";
    }

    @GetMapping("categorias/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        categoriaRepository.findById(id)
                .ifPresentOrElse(
                        categoria -> model.addAttribute("categoria", categoria),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "categoria not found");
                        }
                );
        return "categoria-detail";
    }

    @GetMapping("categorias404/{id}")
    public String findById_NotExist(@PathVariable Long id, Model model) {
        Categoria categoria = (Categoria) categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "categoria not found"));
        model.addAttribute("categoria", categoria);
        return "categoria-detail";
    }


    @GetMapping("categorias/new")
    public String getFormCreateCategoria(Model model) {
        Categoria categoria = new Categoria();
        model.addAttribute("categoria", categoria);
        return "categoria-form";
    }

    @GetMapping("categorias/edit/{id}")
    public String getFormUpdateCategoria(Model model, @PathVariable Long id) {
        categoriaRepository.findById(id)
                .ifPresentOrElse(categoria -> {
                            model.addAttribute("categoria", categoria);
                        },
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");
                        });
        return "categoria-form";
    }

    @PostMapping("categorias")
    public String saveCategoria(@ModelAttribute Categoria categoria, Model model) {
        // Verificar si ya existe una categoría con el mismo nombre
        Optional<Categoria> existingCategoria = categoriaRepository.findByNombre(categoria.getNombre());
        if (existingCategoria.isPresent() && !existingCategoria.get().getId().equals(categoria.getId())) {
            model.addAttribute("error", "Ya existe una categoría con este nombre.");
            return "categoria-form";
        }

        if (categoria.getId() == null) {
            categoriaRepository.save(categoria);
        } else {
            categoriaRepository.findById(categoria.getId())
                    .ifPresent(categoriaDB -> {
                        BeanUtils.copyProperties(categoria, categoriaDB, "id");
                        categoriaRepository.save(categoriaDB);
                    });
        }
        return "redirect:/categorias";
    }

    @GetMapping("categorias/delete/{id}")
    public String deleteCategoria(@PathVariable("id") Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "categoria no encontrada");
        }
        categoriaRepository.deleteById(id);
        return "redirect:/categorias";
    }

}
