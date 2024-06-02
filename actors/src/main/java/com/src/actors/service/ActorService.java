package com.src.actors.service;

import com.src.actors.model.Actor;
import com.src.actors.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public Actor getActorById(Long id) {
        return actorRepository.findById(id).orElse(null);
    }

    public Actor createActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public Actor updateActor(Long id, Actor actor) {
        Actor existingActor = actorRepository.findById(id).orElse(null);
        if (existingActor != null) {
            existingActor.setFirstName(actor.getFirstName());
            existingActor.setLastName(actor.getLastName());
            existingActor.setBornDate(actor.getBornDate());
            existingActor.setMoviesIds(actor.getMoviesIds());
            return actorRepository.save(existingActor);
        }
        return null;
    }

    public void deleteActor(Long id) {
        actorRepository.deleteById(id);
    }
}