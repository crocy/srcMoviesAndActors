package com.src.movies.controller;

import com.src.movies.model.Movie;
import com.src.movies.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(Routes.MOVIES)
public class MovieController {

  private static final String CACHE_MOVIE = "movie";
  private static final String CACHE_MOVIES = "movies";

  private final MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @Cacheable(value = CACHE_MOVIES, sync = true)
  @GetMapping
  public ResponseEntity<Page<Movie>> getAllMovies(Pageable pageable) {
    Page<Movie> movies = movieService.getAllMovies(pageable);
    log.info("GET: returning all movies: {}", movies);
    return ResponseEntity.ok(movies);
  }

  @Cacheable(value = CACHE_MOVIE, key = "#id", sync = true)
  @GetMapping(Routes.PATH_ID)
  public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
    Movie movie = movieService.getMovieById(id);
    log.info("GET: returning movie: {}", movie);
    return ResponseEntity.ok(movie);
  }

  @Caching(evict = @CacheEvict(value = CACHE_MOVIES, allEntries = true))
  @PostMapping
  public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
    log.info("POST: got movie: %s".formatted(movie));
    Movie createdMovie = movieService.createMovie(movie);
    log.info("POST: created movie: {}", createdMovie);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
  }

  @Caching(evict = @CacheEvict(value = CACHE_MOVIES, allEntries = true), put = @CachePut(value = CACHE_MOVIE, key = "#id"))
  @PutMapping(Routes.PATH_ID)
  public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
    log.info("PUT: got movie: {}", movie);
    Movie updatedMovie = movieService.updateMovie(id, movie);
    return ResponseEntity.ok(updatedMovie);
  }

  @Caching(evict = @CacheEvict(value = CACHE_MOVIES, allEntries = true), put = @CachePut(value = CACHE_MOVIE, key = "#id"))
  @DeleteMapping(Routes.PATH_ID)
  public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
    log.info("DELETE: deleting movie with id: {}", id);
    movieService.deleteMovie(id);
    return ResponseEntity.noContent().build();
  }
}
