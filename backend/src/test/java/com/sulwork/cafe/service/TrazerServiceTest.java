package com.sulwork.cafe.service;

import com.sulwork.cafe.dto.request.CreateOpcaoRequest;
import com.sulwork.cafe.dto.request.UpdateTrouxeRequest;
import com.sulwork.cafe.entity.Colaborador;
import com.sulwork.cafe.entity.Trazer;
import com.sulwork.cafe.exception.BusinessException;
import com.sulwork.cafe.repository.nativequery.ColaboradorNativeRepository;
import com.sulwork.cafe.repository.nativequery.TrazerNativeRepository;
import com.sulwork.cafe.service.validation.impl.DataFuturaValidator;
import com.sulwork.cafe.service.validation.impl.ItemUnicoPorDataValidator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrazerServiceTest {

  @Test
  void deveValidarItemUnicoPorData() {
    var repo = mock(TrazerNativeRepository.class);
    var colab = mock(ColaboradorNativeRepository.class);

    when(colab.findById(1L)).thenReturn(Optional.of(Colaborador.builder().id(1L).nome("Ana").cpf("12345678901").build()));
    when(repo.existsItemOnDate(any(), any())).thenReturn(false);
    when(repo.insert(eq(1L), any(), eq("queijo"))).thenReturn(Trazer.builder().id(10L).colaboradorId(1L).build());
    
    Object[] row = new Object[]{
      10L, 1L, "Ana", "12345678901", LocalDate.now().plusDays(2), "queijo", Boolean.FALSE
    };
    when(repo.listByDateRaw(any())).thenReturn(List.<Object[]>of(row));

    var service = new TrazerService(repo, colab, new DataFuturaValidator(), new ItemUnicoPorDataValidator(repo));
    var dto = service.criar(new CreateOpcaoRequest(1L, LocalDate.now().plusDays(2), "Queijo"));
    assertEquals("queijo", dto.item());
  }

  @Test
  void naoPermiteMarcarForaDoDia() {
    var repo = mock(TrazerNativeRepository.class);
    var colab = mock(ColaboradorNativeRepository.class);
    var service = new TrazerService(repo, colab, new DataFuturaValidator(), new ItemUnicoPorDataValidator(repo));

    when(repo.findById(5L)).thenReturn(Optional.of(Trazer.builder().id(5L)
        .dataDoCafe(LocalDate.now().plusDays(1)).build()));
    when(repo.updateTrouxe(anyLong(), anyBoolean())).thenReturn(1);
    var ex = assertThrows(BusinessException.class, () -> service.marcarTrouxe(5L, new UpdateTrouxeRequest(true)));
    assertTrue(ex.getMessage().contains("apenas no dia do café"));
  }
}
