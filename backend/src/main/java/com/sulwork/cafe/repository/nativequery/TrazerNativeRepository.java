package com.sulwork.cafe.repository.nativequery;

import com.sulwork.cafe.entity.Trazer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrazerNativeRepository {
  Trazer insert(Long colaboradorId, LocalDate dataDoCafe, String itemNormalizado);
  Optional<Trazer> findById(Long id);
  boolean existsItemOnDate(LocalDate data, String itemNormalizado);
  List<Object[]> listByDateRaw(LocalDate data);
  int updateTrouxe(Long id, Boolean trouxe);
  int deleteById(Long id);
  int autoMarkPastAsFalse(LocalDate today);
}
