package com.Selenium_Java.repository;

import com.Selenium_Java.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b inner join b.categories categories where categories.id = ?1")
    List <Book> findByCategories_Id(Long id);

    @Query("select b from Book b where upper(b.title) = upper(?1)")
    List<Book> findByTitleIgnoreCase(String title);
    @Query(""" 
select b from Book b join fetch b.categories where b.title = ?1 """)
    //selecciona el libro y las categorias que tiene asociadas al libro
    //join fetch sirve para traer las categorias asociadas al libro
    Optional<Book> findBookEagerByTitle(String title);
}