package com.src.movies.service;

import com.src.movies.model.Movie;
import com.src.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

  private final MovieRepository movieRepository;

  @Autowired
  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public Page<Movie> getAllMovies(Pageable pageable) {
    return movieRepository.findAll(pageable);
  }

  public Movie getMovieById(Long id) {
    return movieRepository.findById(id).orElse(null);
  }

  public Movie createMovie(Movie movie) {
    return movieRepository.save(movie);
  }

  public Movie updateMovie(Long id, Movie movie) {
    Movie existingMovie = movieRepository.findById(id).orElse(null);
    if (existingMovie != null) {
      existingMovie.setTitle(movie.getTitle());
      existingMovie.setYearReleased(movie.getYearReleased());
      existingMovie.setDescription(movie.getDescription());
      existingMovie.setActorsIds(movie.getActorsIds());
      return movieRepository.save(existingMovie);
    }
    return null;
  }

  public void deleteMovie(Long id) {
    movieRepository.deleteById(id);
  }

  public List<Movie> getMoviesByExample(Example<Movie> example) {
    return movieRepository.findAll(example);
  }
}
