package com.src.actors.service;

import com.src.actors.model.Actor;
import com.src.actors.repository.ActorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

  private Actor testActor = new Actor(1L, "Jon", "Doh", LocalDate.of(1985, 5, 5), List.of(1, 2, 3));

  @Mock
  private ActorRepository actorRepository;

  @InjectMocks
  private ActorService actorService;

  @Test
  void testGetAllActors() {
    Page<Actor> actors = mock(Page.class);
    Pageable pageable = mock(Pageable.class);

    when(actorRepository.findAll(pageable)).thenReturn(actors);

    Page<Actor> result = actorService.getAllActors(pageable);

    assertEquals(actors, result);
  }

  @Test
  void testGetActorById() {
    when(actorRepository.findById(testActor.getId())).thenReturn(Optional.of(testActor));

    Actor result = actorService.getActorById(testActor.getId());

    assertEquals(testActor, result);
  }

  @Test
  void testCreateActor() {
    Actor actor = new Actor();

    when(actorRepository.save(actor)).thenReturn(new Actor());

    Actor result = actorService.createActor(actor);

    assertEquals(actor, result);
  }

  @Test
  void testUpdateActor() {
    Actor updatedActor = new Actor();

    when(actorRepository.findById(testActor.getId())).thenReturn(Optional.of(testActor));
    when(actorRepository.save(testActor)).thenReturn(updatedActor);

    Actor result = actorService.updateActor(testActor.getId(), updatedActor);

    assertEquals(updatedActor, result);
  }

  @Test
  void testDeleteActor() {
    actorService.deleteActor(testActor.getId());

    verify(actorRepository, times(1)).deleteById(testActor.getId());
  }
}
