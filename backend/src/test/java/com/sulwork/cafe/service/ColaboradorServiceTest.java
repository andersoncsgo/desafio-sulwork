package com.sulwork.cafe.service;

import com.sulwork.cafe.dto.request.CreateColaboradorRequest;
import com.sulwork.cafe.entity.Colaborador;
import com.sulwork.cafe.exception.BusinessException;
import com.sulwork.cafe.repository.nativequery.ColaboradorNativeRepository;
import com.sulwork.cafe.service.validation.impl.CpfValidator;
import com.sulwork.cafe.service.validation.impl.NomeUnicoValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ColaboradorServiceTest {

  @Test
  void deveValidarNomeECpfUnicos() {
    var repo = mock(ColaboradorNativeRepository.class);
    var nomeVal = new NomeUnicoValidator(repo);
    var cpfVal = new CpfValidator(repo);
    var service = new ColaboradorService(repo, nomeVal, cpfVal);

    when(repo.existsByNome("Ana")).thenReturn(false);
    when(repo.existsByCpf("12345678901")).thenReturn(false);
    when(repo.insert("Ana","12345678901"))
        .thenReturn(Colaborador.builder().id(1L).nome("Ana").cpf("12345678901").build());

    var dto = service.criar(new CreateColaboradorRequest("Ana","12345678901"));
    assertEquals("Ana", dto.nome());
    assertEquals("12345678901", dto.cpf());
  }

  @Test
  void deveFalharCpfInvalido() {
    var repo = mock(ColaboradorNativeRepository.class);
    var service = new ColaboradorService(repo, new NomeUnicoValidator(repo), new CpfValidator(repo));
    var ex = assertThrows(BusinessException.class, () -> service.criar(new CreateColaboradorRequest("Ana","abc")));
    assertTrue(ex.getMessage().contains("CPF deve conter 11 dígitos"));
  }
}
