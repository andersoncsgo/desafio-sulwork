package com.sulwork.cafe.controller;

import com.sulwork.cafe.dto.TrazerDTO;
import com.sulwork.cafe.dto.request.CreateOpcaoRequest;
import com.sulwork.cafe.dto.request.UpdateTrouxeRequest;
import com.sulwork.cafe.service.TrazerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/opcoes")
@RequiredArgsConstructor
public class TrazerController {
  private final TrazerService service;

  @Operation(summary = "Cria opção (colaborador + item + data)")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TrazerDTO criar(@Valid @RequestBody CreateOpcaoRequest req) {
    return service.criar(req);
  }

  @Operation(summary = "Lista opções por data")
  @GetMapping
  public List<TrazerDTO> listar(@RequestParam("data") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate data) {
    return service.listarPorData(data);
  }

  @Operation(summary = "Marca trouxe/não trouxe por ID (somente no dia do café)")
  @PatchMapping("/{id}/marcar-trouxe")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void marcarTrouxe(@PathVariable Long id, @Valid @RequestBody UpdateTrouxeRequest req) {
    service.marcarTrouxe(id, req);
  }

  @Operation(summary = "Exclui opção por ID")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluir(@PathVariable Long id) { service.excluir(id); }
}
