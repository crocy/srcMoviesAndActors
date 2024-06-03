package com.src.movies.controller;

import com.src.movies.model.Movie;
import com.src.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

  private final MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping
  @Cacheable("movies")
  public ResponseEntity<Page<Movie>> getAllMovies(Pageable pageable) {
    Page<Movie> movies = movieService.getAllMovies(pageable);
    return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(movies);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
    Movie movie = movieService.getMovieById(id);
    return ResponseEntity.ok(movie);
  }

  @PostMapping
  public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
    Movie createdMovie = movieService.createMovie(movie);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
    Movie updatedMovie = movieService.updateMovie(id, movie);
    return ResponseEntity.ok(updatedMovie);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
    movieService.deleteMovie(id);
    return ResponseEntity.noContent().build();
  }
}
