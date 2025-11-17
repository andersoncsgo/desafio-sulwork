package com.sulwork.cafe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "colaborador", uniqueConstraints = {
    @UniqueConstraint(name = "uk_colaborador_nome", columnNames = {"nome"}),
    @UniqueConstraint(name = "uk_colaborador_cpf", columnNames = {"cpf"})
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Colaborador {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, length=200)
  private String nome;

  @Column(nullable=false, length=11)
  private String cpf;
}
