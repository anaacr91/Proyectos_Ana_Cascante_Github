package com.Selenium_Java.controllerTest.IntegratedTest;

import com.Selenium_Java.model.Book;
import com.Selenium_Java.model.Category;
import com.Selenium_Java.repository.BookRepository;
import com.Selenium_Java.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest//importar repositorios
@AutoConfigureMockMvc
@Transactional
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findAll() throws Exception {
        bookRepository.saveAll(List.of(
                Book.builder().title("libro1").price(20.0).build(),
                Book.builder().title("libro2").price(23.0).build()
        ));

        mockMvc.perform(get("/libros"))//hace una peticion get a la url /libros
                .andExpect(status().isOk())
                .andExpect(view().name("book-list"))//comprueba la vista
                .andExpect(model().attributeExists("books"))//books es el modelo del controlador
                .andExpect(model().attribute("books", hasSize(2)));//comprueba que haya 2 libros
    }
    @Test
    @DisplayName("Enviar formulario con nuevo libro para crearlo en bbdd")
    void crearNuevoLibro() throws Exception{

        var cat1= categoryRepository.save(Category.builder().name("cat1").build());//creo 3 categorias
        var cat2= categoryRepository.save(Category.builder().name("cat2").build());
        var cat3= categoryRepository.save(Category.builder().name("cat3").build());

        String id1 = String.valueOf(cat1.getId());
        String id2 = String.valueOf(cat2.getId());
        String id3 = String.valueOf(cat3.getId());
//paso los id a string porque .param de MVC requiere que los parámetros sean cadenas de texto/String
    mockMvc.perform(post("/libros")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title","LibroTest")
            .param("price","20.0")//atributos en el modelo
            .param("categories",id1, id2, id3)//categories ->modelo del controlador
    ).andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/libros"));

    var bookSaved = bookRepository.findByTitleIgnoreCase("LibroTest").get(0);//busca el 1r libro en la bbdd
    assertEquals("LibroTest", bookSaved.getTitle());//comprueba que el titulo sea el mismo
    assertEquals(20.0, bookSaved.getPrice());//comprueba que el precio sea el mismo
    assertEquals(3, bookSaved.getCategories().size());//comprueba que haya 3 categorias

    }
    @Test
    @DisplayName("Editar un libro que ya existe")
    void editarLibroExistente() throws Exception {

        var cat1 = categoryRepository.save(Category.builder().name("cat1").build());
        var cat2 = categoryRepository.save(Category.builder().name("cat2").build());
        var cat3 = categoryRepository.save(Category.builder().name("cat3").build());

        Book book1 = new Book(); // Crearlo con constructor para que tenga el Set de categories inicializado en vez de null
        book1.setTitle("libro1");
        book1.setPrice(30.5);
        book1.getCategories().add(cat1);
        book1.getCategories().add(cat2);
        book1.getCategories().add(cat3);
        bookRepository.save(book1);

        String id1 = String.valueOf(cat1.getId());
        String id2 = String.valueOf(cat2.getId());
        String id3 = String.valueOf(cat3.getId());

        mockMvc.perform(post("/libros")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", String.valueOf(book1.getId()))
                        .param("title", "LibroTest")
                        .param("price", "23.44")
                        .param("categories", id1, id3)
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/libros"));

        var bookSaved = bookRepository.findByTitleIgnoreCase("LibroTest").get(0);
        assertEquals("LibroTest", bookSaved.getTitle());
        assertEquals(23.44, bookSaved.getPrice());
        assertEquals(2, bookSaved.getCategories().size());

        assertTrue(bookSaved.getCategories().contains(cat1));
        assertFalse(bookSaved.getCategories().contains(cat2));
        assertTrue(bookSaved.getCategories().contains(cat3));

    }
}

