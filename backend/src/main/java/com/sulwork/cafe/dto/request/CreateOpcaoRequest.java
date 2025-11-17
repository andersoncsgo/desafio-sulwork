package com.sulwork.cafe.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateOpcaoRequest(
    @NotNull(message="colaboradorId é obrigatório") Long colaboradorId,
    @NotNull(message="dataDoCafe é obrigatória") @Future(message="Data do café deve ser futura") LocalDate dataDoCafe,
    @NotBlank(message="item é obrigatório") String item
) {}
