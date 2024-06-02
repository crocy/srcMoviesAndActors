package com.src.actors.controller;

import com.src.actors.model.Actor;
import com.src.actors.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/actors")
public class ActorController {

  private final ActorService actorService;

  @Autowired
  public ActorController(ActorService actorService) {
    this.actorService = actorService;
  }

  @GetMapping
  public ResponseEntity<Page<Actor>> getAllActors(Pageable pageable) {
    Page<Actor> actors = actorService.getAllActors(pageable);
    return ResponseEntity.ok(actors);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Actor> getActorById(@PathVariable Long id) {
    Actor actor = actorService.getActorById(id);
    return ResponseEntity.ok(actor);
  }

  @PostMapping
  public ResponseEntity<Actor> createActor(@RequestBody Actor actor) {
    Actor createdActor = actorService.createActor(actor);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdActor);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Actor actor) {
    Actor updatedActor = actorService.updateActor(id, actor);
    return ResponseEntity.ok(updatedActor);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
    actorService.deleteActor(id);
    return ResponseEntity.noContent().build();
  }
}
