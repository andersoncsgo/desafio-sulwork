package com.sulwork.cafe.service;

import com.sulwork.cafe.dto.TrazerDTO;
import com.sulwork.cafe.dto.request.CreateOpcaoRequest;
import com.sulwork.cafe.dto.request.UpdateTrouxeRequest;
import com.sulwork.cafe.exception.BusinessException;
import com.sulwork.cafe.repository.nativequery.ColaboradorNativeRepository;
import com.sulwork.cafe.repository.nativequery.TrazerNativeRepository;
import com.sulwork.cafe.service.validation.impl.DataFuturaValidator;
import com.sulwork.cafe.service.validation.impl.ItemUnicoPorDataValidator;
import com.sulwork.cafe.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrazerService {
  private final TrazerNativeRepository repo;
  private final ColaboradorNativeRepository colabRepo;
  private final DataFuturaValidator dataFuturaValidator;
  private final ItemUnicoPorDataValidator itemUnicoValidator;

  public TrazerDTO criar(CreateOpcaoRequest req) {
    var colaboradorId = req.colaboradorId();
    var data = req.dataDoCafe();
    var item = normalizeItem(req.item());

    
    dataFuturaValidator.validate(data);
    colabRepo.findById(colaboradorId).orElseThrow(() ->
        new BusinessException("Colaborador inexistente", HttpStatus.UNPROCESSABLE_ENTITY.value()));
    itemUnicoValidator.validate(new ItemUnicoPorDataValidator.Input(data, item));

    var entidade = repo.insert(colaboradorId, data, item);

    var lista = repo.listByDateRaw(data).stream()
        .map(Mapper::toTrazerDTO)
        .filter(d -> d.id().equals(entidade.getId()))
        .findFirst();
    
    if (lista.isEmpty()) {
      var colab = colabRepo.findById(entidade.getColaboradorId()).orElseThrow();
      return new TrazerDTO(
          entidade.getId(),
          entidade.getColaboradorId(),
          colab.getNome(),
          colab.getCpf(),
          entidade.getDataDoCafe(),
          entidade.getNomeItemNormalizado(),
          entidade.getTrouxe()
      );
    }
    return lista.get();
  }

  public List<TrazerDTO> listarPorData(LocalDate data) {
    return repo.listByDateRaw(data).stream()
        .map(Mapper::toTrazerDTO)
        .toList();
  }

  public void marcarTrouxe(Long id, UpdateTrouxeRequest req) {
    var trazer = repo.findById(id).orElseThrow(() ->
        new BusinessException("Opção não encontrada", HttpStatus.NOT_FOUND.value()));
    if (!trazer.getDataDoCafe().isEqual(LocalDate.now())) {
      throw new BusinessException("Marcação permitida apenas no dia do café", HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
    var rows = repo.updateTrouxe(id, req.trouxe());
    if (rows == 0) throw new BusinessException("Falha ao atualizar", HttpStatus.INTERNAL_SERVER_ERROR.value());
  }

  public void excluir(Long id) {
    var rows = repo.deleteById(id);
    if (rows == 0) throw new BusinessException("Opção não encontrada", HttpStatus.NOT_FOUND.value());
  }

  public int autoMarcarPendentesComoNaoTrouxe() {
    return repo.autoMarkPastAsFalse(LocalDate.now());
  }

  private String normalizeItem(String item) {
    return item == null ? null : item.trim().toLowerCase();
  }
}
