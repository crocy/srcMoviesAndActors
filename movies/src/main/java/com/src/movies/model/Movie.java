package com.src.movies.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "TEXT")
  private String title;
  @Column(name = "year_released")
  private int yearReleased;
  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "actors_ids")
  private List<Integer> actorsIds;

}
