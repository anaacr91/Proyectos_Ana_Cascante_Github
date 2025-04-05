package com.movies.service;

import com.movies.model.Customer;
import com.movies.model.Movie;
import com.movies.model.Rental;
import com.movies.repository.CustomerRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.RentalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final MovieRepository movieRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public Rental rentMovie(Long customerId, Long movieId, Integer durationDays) {
        if (durationDays <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0 días.");
        }

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Película no encontrada."));

        if (!movie.isAvailable()) {
            throw new IllegalArgumentException("La película no está disponible.");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado."));

        Double rentalPrice = movie.getRentalPricePerDay() * durationDays;

        Rental rental = new Rental();
        rental.setMovie(movie);
        rental.setCustomer(customer);
        rental.setRentalDate(LocalDateTime.now());
        rental.setReturnDueDate(LocalDateTime.now().plusDays(durationDays));
        rental.setRentalPrice(rentalPrice);

        movie.setAvailable(false);
        movieRepository.save(movie);

        return rentalRepository.save(rental);
    }

    @Transactional
    public void returnMovie(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Alquiler no encontrado."));

        Movie movie = rental.getMovie();

        if (movie.isAvailable()) {
            throw new IllegalArgumentException("La película ya está marcada como disponible.");
        }

        rental.setReturnedDate(LocalDateTime.now());
        movie.setAvailable(true);

        movieRepository.save(movie);
        rentalRepository.save(rental);
    }

    public List<Rental> getRentalsByCustomer(Long customerId) {
        return rentalRepository.findByCustomerId(customerId);
    }

    public List<Rental> getRentalsByMovie(Long movieId) {
        return rentalRepository.findByMovieId(movieId);
    }

    public List<Rental> getRentalsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return rentalRepository.findByRentalDateBetween(startDate, endDate);
    }
}

