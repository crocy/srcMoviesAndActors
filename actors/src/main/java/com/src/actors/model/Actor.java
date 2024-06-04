package com.src.actors.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "actors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "born_date")
  private LocalDate bornDate;
  @Column(name = "movies_ids")
  private List<Integer> moviesIds;

}
