package com.sulwork.cafe.service.validation.impl;

import com.sulwork.cafe.exception.BusinessException;
import com.sulwork.cafe.service.validation.BusinessValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class DataFuturaValidator implements BusinessValidator<LocalDate> {
  
  private static final ZoneId BRAZIL_ZONE = ZoneId.of("America/Sao_Paulo");
  
  @Override
  public void validate(LocalDate data) {
    LocalDate hoje = LocalDate.now(BRAZIL_ZONE);
    if (data == null || !data.isAfter(hoje)) {
      throw new BusinessException("A data do café deve ser maior que a data atual", HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
  }
}
