package com.src.actors.controller;

import com.src.actors.model.Actor;
import com.src.actors.service.ActorService;
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
@RequestMapping(Routes.ACTORS)
public class ActorController {

  private static final String CACHE_ACTOR = "actor";
  private static final String CACHE_ACTORS = "actors";

  @Autowired
  private final ActorService actorService;

  @Autowired
  public ActorController(ActorService actorService) {
    this.actorService = actorService;
  }

  @Cacheable(value = CACHE_ACTORS, sync = true)
  @GetMapping
  public ResponseEntity<Page<Actor>> getAllActors(Pageable pageable) {
    Page<Actor> actors = actorService.getAllActors(pageable);
    log.info("GET: returning all actors: {}", actors);
    return ResponseEntity.ok(actors);
  }

  @Cacheable(value = CACHE_ACTOR, key = "#id", sync = true)
  @GetMapping(Routes.PATH_ID)
  public ResponseEntity<Actor> getActorById(@PathVariable Long id) {
    Actor actor = actorService.getActorById(id);
    log.info("GET: returning actor: {}", actor);
    return ResponseEntity.ok(actor);
  }

  @Caching(evict = @CacheEvict(value = CACHE_ACTORS, allEntries = true))
  @PostMapping
  public ResponseEntity<Actor> createActor(@RequestBody Actor actor) {
    log.info("POST: got actor: %s".formatted(actor));
    Actor createdActor = actorService.createActor(actor);
    log.info("POST: created actor: {}", createdActor);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdActor);
  }

  @Caching(evict = @CacheEvict(value = CACHE_ACTORS, allEntries = true), put = @CachePut(value = CACHE_ACTOR, key = "#id"))
  @PutMapping(Routes.PATH_ID)
  public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Actor actor) {
    log.info("PUT: got actor: {}", actor);
    Actor updatedActor = actorService.updateActor(id, actor);
    return ResponseEntity.ok(updatedActor);
  }

  @Caching(evict = @CacheEvict(value = CACHE_ACTORS, allEntries = true), put = @CachePut(value = CACHE_ACTOR, key = "#id"))
  @DeleteMapping(Routes.PATH_ID)
  public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
    log.info("DELETE: deleting actor with id: {}", id);
    actorService.deleteActor(id);
    return ResponseEntity.noContent().build();
  }

}
