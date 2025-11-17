package com.sulwork.cafe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateColaboradorRequest(
    @NotBlank(message="Nome é obrigatório")
    String nome,
    @NotBlank(message="CPF é obrigatório")
    @Pattern(regexp="\\d{11}", message="CPF deve conter 11 dígitos")
    @Size(min=11, max=11, message="CPF deve conter 11 dígitos")
    String cpf
) {}
