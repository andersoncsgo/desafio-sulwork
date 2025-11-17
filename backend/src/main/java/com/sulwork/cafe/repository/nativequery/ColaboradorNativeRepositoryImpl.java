package com.sulwork.cafe.repository.nativequery;

import com.sulwork.cafe.entity.Colaborador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ColaboradorNativeRepositoryImpl implements ColaboradorNativeRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Colaborador insert(String nome, String cpf) {
    em.flush();
    var id = ((Number) em.createNativeQuery(
        "INSERT INTO colaborador (nome, cpf) VALUES (:n, :c) RETURNING id")
        .setParameter("n", nome)
        .setParameter("c", cpf)
        .getSingleResult()).longValue();
    em.flush();
    return em.find(Colaborador.class, id);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Colaborador> findById(Long id) {
    try {
      var c = (Colaborador) em.createNativeQuery(
          "SELECT id, nome, cpf FROM colaborador WHERE id = :id", Colaborador.class)
          .setParameter("id", id)
          .getSingleResult();
      return Optional.of(c);
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Colaborador> findByNome(String nome) {
    try {
      var c = (Colaborador) em.createNativeQuery(
          "SELECT id, nome, cpf FROM colaborador WHERE nome = :n", Colaborador.class)
          .setParameter("n", nome)
          .getSingleResult();
      return Optional.of(c);
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByNome(String nome) {
    var count = ((Number) em.createNativeQuery(
        "SELECT COUNT(1) FROM colaborador WHERE nome = :n")
        .setParameter("n", nome)
        .getSingleResult()).intValue();
    return count > 0;
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByCpf(String cpf) {
    var count = ((Number) em.createNativeQuery(
        "SELECT COUNT(1) FROM colaborador WHERE cpf = :c")
        .setParameter("c", cpf)
        .getSingleResult()).intValue();
    return count > 0;
  }

  @Override
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<Colaborador> search(String nome, String cpf) {
    var sb = new StringBuilder("SELECT id, nome, cpf FROM colaborador WHERE 1=1");
    if (nome != null && !nome.isBlank()) sb.append(" AND LOWER(nome) LIKE LOWER(CONCAT('%', :n, '%'))");
    if (cpf != null && !cpf.isBlank()) sb.append(" AND cpf = :c");
    var q = em.createNativeQuery(sb.toString(), Colaborador.class);
    if (nome != null && !nome.isBlank()) q.setParameter("n", nome);
    if (cpf != null && !cpf.isBlank()) q.setParameter("c", cpf);
    return q.getResultList();
  }

  @Override
  public int deleteById(Long id) {
    return em.createNativeQuery("DELETE FROM colaborador WHERE id = :id")
        .setParameter("id", id)
        .executeUpdate();
  }
}
