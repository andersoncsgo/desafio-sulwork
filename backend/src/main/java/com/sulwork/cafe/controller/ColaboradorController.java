package com.sulwork.cafe.controller;

import com.sulwork.cafe.dto.ColaboradorDTO;
import com.sulwork.cafe.dto.request.CreateColaboradorRequest;
import com.sulwork.cafe.service.ColaboradorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/colaboradores")
@RequiredArgsConstructor
public class ColaboradorController {
  private final ColaboradorService service;

  @Operation(summary = "Cria um colaborador")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ColaboradorDTO criar(@Valid @RequestBody CreateColaboradorRequest req) {
    return service.criar(req);
  }

  @Operation(summary = "Lista colaboradores com filtros opcionais")
  @GetMapping
  public List<ColaboradorDTO> listar(@RequestParam(required = false) String nome,
                                     @RequestParam(required = false) String cpf) {
    return service.listar(nome, cpf);
  }

  @Operation(summary = "Exclui colaborador por ID")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluir(@PathVariable Long id) { service.excluir(id); }
}
