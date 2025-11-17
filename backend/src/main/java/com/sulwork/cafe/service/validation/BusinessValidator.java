package com.sulwork.cafe.service.validation;

public interface BusinessValidator<T> {
  void validate(T input);
}
