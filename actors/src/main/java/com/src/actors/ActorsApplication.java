package com.src.actors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ActorsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ActorsApplication.class, args);
  }

}
