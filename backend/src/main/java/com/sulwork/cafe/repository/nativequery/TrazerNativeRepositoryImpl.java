package com.sulwork.cafe.repository.nativequery;

import com.sulwork.cafe.entity.Trazer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class TrazerNativeRepositoryImpl implements TrazerNativeRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Trazer insert(Long colaboradorId, LocalDate dataDoCafe, String itemNormalizado) {
    em.flush();
    var id = ((Number) em.createNativeQuery(
        "INSERT INTO trazer (colaborador_id, data_do_cafe, nome_item_normalizado, trouxe) " +
        "VALUES (:cid, :d, :i, NULL) RETURNING id")
        .setParameter("cid", colaboradorId)
        .setParameter("d", dataDoCafe)
        .setParameter("i", itemNormalizado)
        .getSingleResult()).longValue();
    em.flush(); 
    return em.find(Trazer.class, id);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Trazer> findById(Long id) {
    try {
      
      var t = em.find(Trazer.class, id);
      return t != null ? Optional.of(t) : Optional.empty();
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsItemOnDate(LocalDate data, String itemNormalizado) {
    var count = ((Number) em.createNativeQuery(
        "SELECT COUNT(1) FROM trazer WHERE data_do_cafe = :d AND nome_item_normalizado = :i")
        .setParameter("d", data)
        .setParameter("i", itemNormalizado)
        .getSingleResult()).intValue();
    return count > 0;
  }

  @Override
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<Object[]> listByDateRaw(LocalDate data) {
    
    return em.createNativeQuery(
        "SELECT t.id, t.colaborador_id, c.nome, c.cpf, t.data_do_cafe, t.nome_item_normalizado, t.trouxe " +
        "FROM trazer t JOIN colaborador c ON c.id = t.colaborador_id " +
        "WHERE t.data_do_cafe = :d ORDER BY c.nome ASC")
        .setParameter("d", data)
        .getResultList();
  }

  @Override
  public int updateTrouxe(Long id, Boolean trouxe) {
    return em.createNativeQuery("UPDATE trazer SET trouxe = :tr WHERE id = :id")
        .setParameter("tr", trouxe)
        .setParameter("id", id)
        .executeUpdate();
  }

  @Override
  public int deleteById(Long id) {
    return em.createNativeQuery("DELETE FROM trazer WHERE id = :id")
        .setParameter("id", id)
        .executeUpdate();
  }

  @Override
  public int autoMarkPastAsFalse(LocalDate today) {
    return em.createNativeQuery(
        "UPDATE trazer SET trouxe = FALSE " +
        "WHERE data_do_cafe < :today AND trouxe IS NULL")
        .setParameter("today", today)
        .executeUpdate();
  }
}
