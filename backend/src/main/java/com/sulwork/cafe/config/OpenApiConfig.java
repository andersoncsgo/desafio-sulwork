package com.sulwork.cafe.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI apiInfo() {
    return new OpenAPI()
        .info(new Info()
            .title("Café da Manhã - Desafio Sulwork")
            .description("API para gerenciamento de colaboradores e opções de café da manhã")
            .version("1.0.0"))
        .externalDocs(new ExternalDocumentation()
            .description("Repositório GitHub")
            .url("https://github.com/andersoncsgo/desafio-sulwork"));
  }
}
