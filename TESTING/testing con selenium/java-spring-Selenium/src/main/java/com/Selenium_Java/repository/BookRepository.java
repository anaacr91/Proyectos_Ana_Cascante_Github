package com.Selenium_Java.repository;

import com.Selenium_Java.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b inner join b.categories categories where categories.id = ?1")
    List <Book> findByCategories_Id(Long id);

    @Query("select b from Book b where upper(b.title) = upper(?1)")
    List<Book> findByTitleIgnoreCase(String title);


}