package com.sulwork.cafe.service.validation.impl;

import com.sulwork.cafe.exception.BusinessException;
import com.sulwork.cafe.repository.nativequery.ColaboradorNativeRepository;
import com.sulwork.cafe.service.validation.BusinessValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CpfValidator implements BusinessValidator<String> {
  private final ColaboradorNativeRepository repo;

  @Override
  public void validate(String cpf) {
    if (cpf == null || !cpf.matches("\\d{11}")) {
      throw new BusinessException("CPF deve conter 11 dígitos numéricos", HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
    if (repo.existsByCpf(cpf)) {
      throw new BusinessException("CPF já cadastrado", HttpStatus.CONFLICT.value());
    }
  }
}
