package com.Selenium_Java.controller;

import com.Selenium_Java.model.Book;
import com.Selenium_Java.repository.BookRepository;
import com.Selenium_Java.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
@AllArgsConstructor
public class BookController {

    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;

    @GetMapping("libros")
    public String findAll(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "book-list";
    }

    @GetMapping("libros/{id}")
    public String findById(@PathVariable Long id, Model model) {
        bookRepository.findById(id).ifPresent(book -> model.addAttribute("book", book));
        return "book-detail";
    }

    @GetMapping("libros/crear")
    public String getFormToCreate(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        return "book-form";
    }
    @GetMapping("libros/editar/{id}")
    public String getFormToEdit(@PathVariable Long id, Model model) {
        bookRepository.findById(id).ifPresent(book -> model.addAttribute("book", book));
        model.addAttribute("categories", categoryRepository.findAll());
        return "book-form";
    }
    @PostMapping("libros")
    public String save(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/libros";
    }

}
