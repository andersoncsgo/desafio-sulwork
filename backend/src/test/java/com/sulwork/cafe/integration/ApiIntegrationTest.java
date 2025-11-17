package com.sulwork.cafe.integration;

import com.sulwork.cafe.CafeApplication;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(classes = CafeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiIntegrationTest {

  @Container
  @SuppressWarnings("resource")
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine")
      .withDatabaseName("sulwork")
      .withUsername("postgres")
      .withPassword("postgres");

  @Autowired
  TestRestTemplate rest;

  @DynamicPropertySource
  static void register(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @AfterAll
  static void tearDown() {
    if (postgres != null && postgres.isRunning()) {
      postgres.close();
    }
  }

  @Test
  void fluxoCompleto() {
    var colabReq = Map.of("nome","Joao","cpf","11122233344");
    var colabResp = rest.postForEntity("/api/colaboradores", colabReq, Map.class);
    assertEquals(HttpStatus.CREATED, colabResp.getStatusCode());
    var colabId = ((Number) colabResp.getBody().get("id")).longValue();

    var data = LocalDate.now().plusDays(3).toString();
    var opcaoReq = Map.of("colaboradorId", colabId, "dataDoCafe", data, "item", "Bolo");
    var opcaoResp = rest.postForEntity("/api/opcoes", opcaoReq, Map.class);
    if (opcaoResp.getStatusCode() != HttpStatus.CREATED) {
      System.err.println("Erro ao criar opção: " + opcaoResp.getStatusCode());
      System.err.println("Body: " + opcaoResp.getBody());
    }
    assertEquals(HttpStatus.CREATED, opcaoResp.getStatusCode());
  }
}
