package com.src.actors.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.src.actors.model.Actor;
import com.src.actors.service.ActorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActorController.class)
@WithMockUser
class ActorControllerTest {

  private Actor testActor = new Actor(1L, "Jon", "Doh", LocalDate.of(1985, 5, 5), List.of(1, 2, 3));

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ActorService actorService;

  @Test
  void testListAllActors() throws Exception {
    Pageable pageable = mock(Pageable.class);
    Page<Actor> actors = mock(Page.class);

    when(actorService.getAllActors(pageable)).thenReturn((actors));

    mockMvc.perform(get(Routes.ACTORS)).andDo(print()).andExpect(status().isOk());
  }

  @Test
  void testGetActorById() throws Exception {
    when(actorService.getActorById(testActor.getId())).thenReturn(new Actor());

    mockMvc.perform(get(Routes.ACTORS + "/" + testActor.getId())).andDo(print()).andExpect(status().isOk());
  }

  @Test
  void testCreateActor() throws Exception {
    Actor actor = new Actor();

    when(actorService.createActor(actor)).thenReturn(new Actor());

    mockMvc.perform(post(Routes.ACTORS).with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(actor))).andDo(print()).andExpect(status().isCreated());
  }

  @Test
  void testUpdateActor() throws Exception {
    Actor actor = new Actor();

    when(actorService.updateActor(testActor.getId(), actor)).thenReturn(new Actor());

    mockMvc.perform(put(Routes.ACTORS + "/" + testActor.getId()).with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(actor))).andDo(print()).andExpect(status().isOk());
  }

  @Test
  void testDeleteActor() throws Exception {
    mockMvc.perform(delete(Routes.ACTORS + "/" + testActor.getId()).with(csrf())).andDo(print())
        .andExpect(status().isNoContent());
  }
}
