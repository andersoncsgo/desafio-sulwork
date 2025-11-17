package com.sulwork.cafe.service.validation.impl;

import com.sulwork.cafe.exception.BusinessException;
import com.sulwork.cafe.repository.nativequery.TrazerNativeRepository;
import com.sulwork.cafe.service.validation.BusinessValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ItemUnicoPorDataValidator implements BusinessValidator<ItemUnicoPorDataValidator.Input> {

  public record Input(LocalDate data, String itemNormalizado) {}

  private final TrazerNativeRepository repo;

  @Override
  public void validate(Input input) {
    if (repo.existsItemOnDate(input.data(), input.itemNormalizado())) {
      throw new BusinessException("Item já selecionado nesta data", HttpStatus.CONFLICT.value());
    }
  }
}
