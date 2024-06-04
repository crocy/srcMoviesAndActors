package com.src.movies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.src.movies.model.Movie;
import com.src.movies.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
@WithMockUser
class MovieControllerTest {

  private Movie testMovie = new Movie(1L, "Test movie", 2222, "Test description", List.of(1, 2, 3));

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private MovieService movieService;

  @Test
  void testListAllMovies() throws Exception {
    Pageable pageable = mock(Pageable.class);
    Page<Movie> movies = mock(Page.class);

    when(movieService.getAllMovies(pageable)).thenReturn(movies);

    mockMvc.perform(get(Routes.MOVIES)).andDo(print()).andExpect(status().isOk());
  }

  @Test
  void testGetMovieById() throws Exception {
    when(movieService.getMovieById(testMovie.getId())).thenReturn(new Movie());

    mockMvc.perform(get(Routes.MOVIES + "/" + testMovie.getId())).andDo(print()).andExpect(status().isOk());
  }

  @Test
  void testGetMoviesByExample() throws Exception {
    when(movieService.getMoviesByExample(Example.of(testMovie))).thenReturn(List.of(testMovie));

    mockMvc.perform(get(Routes.MOVIES + Routes.MOVIES_FIND).param("id", "" + testMovie.getId())).andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void testCreateMovie() throws Exception {
    Movie movie = new Movie();

    when(movieService.createMovie(movie)).thenReturn(new Movie());

    mockMvc.perform(post(Routes.MOVIES).with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(movie))).andDo(print()).andExpect(status().isCreated());
  }

  @Test
  void testUpdateMovie() throws Exception {
    Movie movie = new Movie();

    when(movieService.updateMovie(testMovie.getId(), movie)).thenReturn(movie);

    mockMvc.perform(put(Routes.MOVIES + "/" + testMovie.getId()).with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(testMovie))).andDo(print()).andExpect(status().isOk());
  }

  @Test
  void testDeleteMovie() throws Exception {
    mockMvc.perform(delete(Routes.MOVIES + "/" + testMovie.getId()).with(csrf())).andDo(print())
        .andExpect(status().isNoContent());
  }
}
