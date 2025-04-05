package com.movies.repository;

import com.movies.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
  List<Rental> findByRentalDateBetween(LocalDateTime startDate, LocalDateTime endDate);

  List<Rental> findByCustomerId(Long customerId);

  List<Rental> findByMovieId(Long movieId);
}