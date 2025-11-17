package com.sulwork.cafe.dto;

import java.time.LocalDate;

public record TrazerDTO(Long id, Long colaboradorId, String colaboradorNome, String cpf,
                        LocalDate dataDoCafe, String item, Boolean trouxe) { }
