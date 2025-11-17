package com.sulwork.cafe.repository.nativequery;

import com.sulwork.cafe.entity.Colaborador;

import java.util.List;
import java.util.Optional;

public interface ColaboradorNativeRepository {
  Colaborador insert(String nome, String cpf);
  Optional<Colaborador> findById(Long id);
  Optional<Colaborador> findByNome(String nome);
  boolean existsByNome(String nome);
  boolean existsByCpf(String cpf);
  List<Colaborador> search(String nome, String cpf);
  int deleteById(Long id);
}
