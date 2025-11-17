package com.sulwork.cafe.service;

import com.sulwork.cafe.dto.ColaboradorDTO;
import com.sulwork.cafe.dto.request.CreateColaboradorRequest;
import com.sulwork.cafe.entity.Colaborador;
import com.sulwork.cafe.exception.BusinessException;
import com.sulwork.cafe.repository.nativequery.ColaboradorNativeRepository;
import com.sulwork.cafe.service.validation.impl.CpfValidator;
import com.sulwork.cafe.service.validation.impl.NomeUnicoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColaboradorService {
  private final ColaboradorNativeRepository repo;
  private final NomeUnicoValidator nomeValidator;
  private final CpfValidator cpfValidator;

  public ColaboradorDTO criar(CreateColaboradorRequest req) {
    var nome = req.nome().trim();
    var cpf = req.cpf().trim();

    nomeValidator.validate(nome);
    cpfValidator.validate(cpf);

    Colaborador c = repo.insert(nome, cpf);
    return new ColaboradorDTO(c.getId(), c.getNome(), c.getCpf());
  }

  public List<ColaboradorDTO> listar(String nome, String cpf) {
    return repo.search(nome, cpf).stream()
        .map(c -> new ColaboradorDTO(c.getId(), c.getNome(), c.getCpf()))
        .toList();
  }

  public void excluir(Long id) {
    var rows = repo.deleteById(id);
    if (rows == 0) throw new BusinessException("Colaborador não encontrado", HttpStatus.NOT_FOUND.value());
  }
}
