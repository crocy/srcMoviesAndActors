package com.src.movies.service;

import com.src.movies.model.Movie;
import com.src.movies.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

  private Movie testMovie = new Movie(1L, "Test movie", 2222, "Test description", List.of(1, 2, 3));

  @Mock
  private MovieRepository movieRepository;

  @InjectMocks
  private MovieService movieService;

  @Test
  void testGetAllMovies() {
    Pageable pageable = mock(Pageable.class);
    Page<Movie> movies = mock(Page.class);

    when(movieRepository.findAll(pageable)).thenReturn(movies);

    Page<Movie> result = movieService.getAllMovies(pageable);

    assertEquals(movies, result);
  }

  @Test
  void testGetMovieById() {
    when(movieRepository.findById(testMovie.getId())).thenReturn(Optional.of(testMovie));

    Movie result = movieService.getMovieById(testMovie.getId());

    assertEquals(testMovie, result);
  }

  @Test
  void testGetMoviesByExample() {
    Example<Movie> example = mock(Example.class);
    List<Movie> movies = List.of(testMovie);

    when(movieRepository.findAll(example)).thenReturn(movies);

    List<Movie> result = movieService.getMoviesByExample(example);

    assertNotNull(result);
    assertEquals(movies.size(), result.size());
  }

  @Test
  void testCreateMovie() {
    when(movieRepository.save(testMovie)).thenReturn(testMovie);

    Movie result = movieService.createMovie(testMovie);

    assertEquals(testMovie, result);
  }

  @Test
  void testUpdateMovie() {
    Movie updatedMovie = new Movie();

    when(movieRepository.findById(testMovie.getId())).thenReturn(Optional.of(testMovie));
    when(movieRepository.save(testMovie)).thenReturn(updatedMovie);

    Movie result = movieService.updateMovie(testMovie.getId(), updatedMovie);
    assertEquals(updatedMovie, result);
  }

  @Test
  void testDeleteMovie() {
    movieService.deleteMovie(testMovie.getId());

    verify(movieRepository, times(1)).deleteById(testMovie.getId());
  }
}
