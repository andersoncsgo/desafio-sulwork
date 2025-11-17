package com.sulwork.cafe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "trazer", uniqueConstraints = {
    @UniqueConstraint(name = "uq_trazer_data_item", columnNames = {"data_do_cafe", "nome_item_normalizado"})
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Trazer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="colaborador_id", nullable=false)
  private Long colaboradorId;

  @Column(name="data_do_cafe", nullable=false)
  private LocalDate dataDoCafe;

  @Column(name="nome_item_normalizado", nullable=false, length=200)
  private String nomeItemNormalizado;

  @Column
  private Boolean trouxe;

  @Column(name="created_at", nullable=false)
  private LocalDateTime createdAt;

  @Column(name="updated_at", nullable=false)
  private LocalDateTime updatedAt;
}
