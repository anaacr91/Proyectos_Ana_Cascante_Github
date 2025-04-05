package com.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.movies.model.Movie;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

        @Query("SELECT m FROM Movie m JOIN m.customers c WHERE c.id = :customerId")
        List<Movie> findByCustomerId(@Param("customerId") Long customerId);

        @Query("SELECT m FROM Movie m WHERE m.available = true")
        List<Movie> findAllAvailableMovies();

        @Query("SELECT m FROM Movie m WHERE m.categoria.id = :categoriaId")
        List<Movie> findByCategoriaId(@Param("categoriaId") Long categoriaId);

        @Query("SELECT m FROM Movie m WHERE m.year BETWEEN :startYear AND :endYear")
        List<Movie> findMoviesByYearRange(@Param("startYear") int startYear, @Param("endYear") int endYear);

        @Query("SELECT m FROM Movie m WHERE m.name LIKE %:keyword%")
        List<Movie> searchMoviesByName(@Param("keyword") String keyword);

        @Query("SELECT m FROM Movie m WHERE m.rentalPricePerDay < :price")
        List<Movie> findMoviesByRentalPriceLessThan(@Param("price") Double price);

}