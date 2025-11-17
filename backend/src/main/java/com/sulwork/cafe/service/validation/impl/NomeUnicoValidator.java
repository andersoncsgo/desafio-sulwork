package com.sulwork.cafe.service.validation.impl;

import com.sulwork.cafe.exception.BusinessException;
import com.sulwork.cafe.repository.nativequery.ColaboradorNativeRepository;
import com.sulwork.cafe.service.validation.BusinessValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NomeUnicoValidator implements BusinessValidator<String> {
  private final ColaboradorNativeRepository repo;
  @Override
  public void validate(String nome) {
    if (repo.existsByNome(nome)) {
      throw new BusinessException("Nome já cadastrado", HttpStatus.CONFLICT.value());
    }
  }
}
