package com.sulwork.cafe.service.validation.impl;

import com.sulwork.cafe.exception.BusinessException;
import com.sulwork.cafe.service.validation.BusinessValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataFuturaValidator implements BusinessValidator<LocalDate> {
  @Override
  public void validate(LocalDate data) {
    if (data == null || !data.isAfter(LocalDate.now())) {
      throw new BusinessException("A data do café deve ser maior que a data atual", HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
  }
}
