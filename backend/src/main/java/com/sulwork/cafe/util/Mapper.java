package com.sulwork.cafe.util;

import com.sulwork.cafe.dto.ColaboradorDTO;
import com.sulwork.cafe.dto.TrazerDTO;

import java.sql.Date;
import java.time.LocalDate;

public class Mapper {
  public static ColaboradorDTO toDTO(Long id, String nome, String cpf) {
    return new ColaboradorDTO(id, nome, cpf);
  }


  public static TrazerDTO toTrazerDTO(Object[] row) {
    var id = ((Number) row[0]).longValue();
    var colaboradorId = ((Number) row[1]).longValue();
    var colaboradorNome = (String) row[2];
    var cpf = (String) row[3];
    LocalDate data;
    if (row[4] instanceof Date) {
      data = ((Date) row[4]).toLocalDate();
    } else if (row[4] instanceof LocalDate) {
      data = (LocalDate) row[4];
    } else {
      throw new IllegalArgumentException("Tipo de data não suportado: " + row[4].getClass());
    }
    var item = (String) row[5];
    var trouxe = (Boolean) row[6];
    return new TrazerDTO(id, colaboradorId, colaboradorNome, cpf, data, item, trouxe);
  }
}
