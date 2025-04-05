package com.movies.repository;

import com.movies.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    @Query("SELECT c FROM Customer c JOIN c.rentals r WHERE r.movie.id = :movieId")
    List<Customer> findByRentedMovie(@Param("movieId") Long movieId);

    List<Customer> findByNombre(String nombre);

    @Query("SELECT c FROM Customer c JOIN c.valoraciones v WHERE v.movie.id = :movieId")
    List<Customer> findByValoracionDePelicula(@Param("movieId") Long movieId);

    @Query("SELECT c FROM Customer c WHERE SIZE(c.rentals) > :rentalCount")
    List<Customer> findByRentalCountGreaterThan(@Param("rentalCount") int rentalCount);

    @Query("SELECT c FROM Customer c JOIN c.rentals r WHERE r.rentalDate BETWEEN :startDate AND :endDate")
    List<Customer> findByRentalsInDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT c FROM Customer c JOIN c.rentals r JOIN c.valoraciones v WHERE r.movie = v.movie ")
    List<Customer> findByRentedAndRatedMovies();

}
