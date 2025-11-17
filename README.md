# Desafio Sulwork – Café da Manhã

Aplicação web fullstack para gerenciar café da manhã colaborativo, permitindo cadastro de colaboradores, registro de itens que serão trazidos em datas específicas, e marcação de confirmação no dia do evento.

## 🌐 Aplicação em Produção

- **🚀 Frontend (Vercel)**: https://desafio-sulwork.vercel.app
- **⚙️ Backend API (Railway)**: https://desafio-sulwork-production.up.railway.app
- **📚 Documentação Swagger**: https://desafio-sulwork-production.up.railway.app/swagger-ui/index.html
- **💻 Repositório GitHub**: https://github.com/andersoncsgo/desafio-sulwork

## 🚀 Tecnologias

- **Frontend**: Angular 17 (standalone components) + TypeScript + Nginx
- **Backend**: Spring Boot 3 + Java 17 + PostgreSQL
- **Infraestrutura**: Docker + Docker Compose
- **Database**: PostgreSQL 14 com Flyway migrations
- **Documentação**: Swagger/OpenAPI
- **Testes**: JUnit 5 + Mockito + Testcontainers

## 📋 Funcionalidades

- ✅ Cadastro de colaboradores (nome + CPF único)
- ✅ Cadastro de opções de café (colaborador + item + data)
- ✅ Listagem por data com status (Pendente/Sim/Não)
- ✅ Marcação de confirmação (apenas no dia do café)
- ✅ Validações: CPF único, item único por data, data não pode ser passada
- ✅ Marcação automática de "Não trouxe" para datas passadas
- ✅ Interface moderna e responsiva com estilos profissionais

## 🛠️ Requisitos

- **Docker Desktop** 24+ (recomendado)
- **Node.js** 18+ e npm 9+ (desenvolvimento local)
- **Java** 17 e Maven 3.9+ (desenvolvimento local)
- **PostgreSQL** 14+ (se rodar local fora do Docker)

## 📁 Estrutura do Projeto

```
desafio-sulwork/
├── backend/                 # Spring Boot API REST
│   ├── src/main/
│   │   ├── java/           # Código fonte
│   │   └── resources/      # application.yml, migrations
│   ├── Dockerfile
│   └── pom.xml
├── frontend/               # Angular 17
│   ├── src/
│   │   ├── app/           # Components, services, routes
│   │   └── styles.css     # Estilos globais
│   ├── Dockerfile
│   ├── package.json
│   └── angular.json
├── docker-compose.yml      # Orquestração dos containers
└── README.md
```

## 🚀 Início Rápido com Docker (Recomendado)

### 1. Clone o repositório
```bash
git clone https://github.com/andersoncsgo/desafio-sulwork.git
cd desafio-sulwork
```

### 2. Inicie os containers
```bash
docker-compose up -d --build
```

Aguarde ~2 minutos para o build completo. Os serviços estarão disponíveis em:
- **Frontend**: http://localhost:8080
- **Backend API**: http://localhost:8081
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **PostgreSQL**: localhost:5433 (usuário: postgres, senha: postgres)

### 3. Acessar a aplicação
Abra o navegador em `http://localhost:8080` e comece a usar!

### 4. Parar os containers
```bash
docker-compose down
```

Para remover também os dados do banco:
```bash
docker-compose down -v
```

## 🔧 Desenvolvimento Local (sem Docker)

### Backend

1. **Configure o PostgreSQL local**
   ```bash
   # Criar database e usuário
   psql -U postgres -c "CREATE DATABASE sulwork;"
   psql -U postgres -c "CREATE USER sulwork WITH PASSWORD '123';"
   psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE sulwork TO sulwork;"
   ```

2. **Configure as variáveis de ambiente**
   ```bash
   export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/sulwork
   export SPRING_DATASOURCE_USERNAME=sulwork
   export SPRING_DATASOURCE_PASSWORD=123
   ```

3. **Execute o backend**
   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
   ```

   O backend estará disponível em `http://localhost:8081`

### Frontend

1. **Instale as dependências**
   ```bash
   cd frontend
   npm install --legacy-peer-deps
   ```

2. **Execute o servidor de desenvolvimento**
   ```bash
   npm start
   ```

   O frontend estará disponível em `http://localhost:4200`

## 🗄️ Banco de Dados

### Conexão via pgAdmin

- **Host**: localhost
- **Port**: 5433 (Docker) ou 5432 (local)
- **Database**: sulwork
- **Username**: postgres
- **Password**: postgres

### Migrations

As migrations Flyway são executadas automaticamente ao iniciar o backend:
- `V1__init.sql`: Cria tabelas `colaborador` e `trazer`

### Schema

**Tabela: colaborador**
- `id` (BIGSERIAL PRIMARY KEY)
- `nome` (VARCHAR(200) NOT NULL)
- `cpf` (VARCHAR(11) NOT NULL UNIQUE)

**Tabela: trazer**
- `id` (BIGSERIAL PRIMARY KEY)
- `colaborador_id` (BIGINT NOT NULL FK)
- `data_do_cafe` (DATE NOT NULL)
- `nome_item_normalizado` (VARCHAR(200) NOT NULL)
- `trouxe` (BOOLEAN DEFAULT NULL)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

## 📚 API Endpoints

### Colaboradores
- `POST /api/colaboradores` - Criar colaborador
- `GET /api/colaboradores` - Listar colaboradores (filtros: nome, cpf)
- `DELETE /api/colaboradores/{id}` - Excluir colaborador

### Opções de Café
- `POST /api/opcoes` - Criar opção (colaborador + item + data)
- `GET /api/opcoes?data=YYYY-MM-DD` - Listar por data
- `PATCH /api/opcoes/{id}/marcar-trouxe` - Marcar se trouxe (body: `{"trouxe": true}`)
- `DELETE /api/opcoes/{id}` - Excluir opção

**Documentação completa**:
- Local: http://localhost:8081/swagger-ui/index.html
- Produção: https://desafio-sulwork-production.up.railway.app/swagger-ui/index.html

## 🧪 Executar Testes

### Backend
```bash
cd backend
mvn test
```

### Frontend
```bash
cd frontend
npm test
```

## 🐛 Troubleshooting

### Porta 5432 já em uso
Se você tem PostgreSQL local rodando, o Docker tentará usar a porta 5433 automaticamente (configurado no `docker-compose.yml`).

### Erro de CORS
Certifique-se de que o backend está permitindo requisições do frontend. O arquivo `CorsConfig.java` já está configurado para `http://localhost:8080`.

### Frontend não carrega estilos
Limpe o cache do navegador com `Ctrl+F5` ou `Ctrl+Shift+R`.

### Containers não iniciam
```bash
# Verificar status
docker-compose ps

# Ver logs
docker-compose logs backend
docker-compose logs frontend

# Reiniciar do zero
docker-compose down -v
docker system prune -f
docker-compose up -d --build
```

## 📝 Variáveis de Ambiente

**Backend (docker-compose.yml)**
- `SPRING_DATASOURCE_URL`: URL do banco
- `SPRING_DATASOURCE_USERNAME`: Usuário do banco
- `SPRING_DATASOURCE_PASSWORD`: Senha do banco (⚠️ **Nunca commite credenciais reais no Git**)
- `SERVER_PORT`: Porta do backend (padrão: 8081)

**PostgreSQL (docker-compose.yml)**
- `POSTGRES_DB`: Nome do database (padrão: sulwork)
- `POSTGRES_USER`: Usuário (padrão: postgres)
- `POSTGRES_PASSWORD`: Senha (padrão: postgres - apenas para desenvolvimento local)

> 🔒 **Segurança**: As credenciais do banco de dados em produção (Railway) são configuradas via variáveis de ambiente do serviço e **não estão** commitadas no repositório. O `.gitignore` bloqueia arquivos `.env` e o diretório `.azure/` para proteger informações sensíveis.

## ✅ Conformidade com o Desafio

### Requisitos Técnicos Obrigatórios

| Requisito | Status | Implementação |
|-----------|--------|---------------|
| **Angular 14+** | ✅ | Angular 17 com standalone components |
| **Spring Boot 3** | ✅ | Spring Boot 3.2.5 com Java 17 |
| **Docker e Docker Compose** | ✅ | Dockerfile multi-stage + docker-compose.yml |
| **NativeQuery (JPA)** | ✅ | `EntityManager.createNativeQuery()` em todos os repositórios |
| **Design Patterns** | ✅ | Repository, Service Layer, DTO, Strategy (validadores), Builder |
| **Testes Unitários Backend** | ✅ | JUnit 5 + Mockito + Testcontainers (3 arquivos) |
| **Testes Unitários Frontend** | ✅ | Jasmine + Karma (2 arquivos spec.ts criados) |
| **Documentação README** | ✅ | README completo com instruções detalhadas |

### Requisitos Funcionais

| Funcionalidade | Status | Detalhes |
|----------------|--------|----------|
| **CRUD Colaborador** | ✅ | Nome + CPF único (11 dígitos) |
| **CRUD Opção Café** | ✅ | Colaborador + Item + Data futura |
| **Validação CPF único** | ✅ | `CpfValidator` com 11 dígitos obrigatórios |
| **Validação nome único** | ✅ | `NomeUnicoValidator` impedindo duplicatas |
| **Validação item único/data** | ✅ | `ItemUnicoPorDataValidator` |
| **Validação data futura** | ✅ | `DataFuturaValidator` (data > hoje) |
| **Lista por data** | ✅ | Endpoint `/api/trazer/data/{data}` + UI com busca |
| **Marcação Sim/Não** | ✅ | Botões com PATCH, apenas no dia do evento |
| **Mensagens de erro/sucesso** | ✅ | Toasts com mensagens amigáveis |
| **Layout responsivo** | ✅ | CSS moderno com navbar, cards, tabelas |

### Requisitos de Entrega

| Item | Status | Localização |
|------|--------|-------------|
| **Código no GitHub** | ✅ | https://github.com/andersoncsgo/desafio-sulwork |
| **README detalhado** | ✅ | Este arquivo |
| **Documentação API** | ✅ | https://desafio-sulwork-production.up.railway.app/swagger-ui/index.html |
| **Docker Compose funcional** | ✅ | `docker-compose up -d` executa tudo |
| **Deploy em produção** | ✅ | Frontend (Vercel) + Backend (Railway) |
| **Testes executáveis** | ✅ | `mvn test` (backend), `npm test` (frontend) |

### Diferenciais Implementados

- ✅ **Swagger/OpenAPI**: Documentação interativa completa
- ✅ **Tratamento de exceções**: `GlobalExceptionHandler` com mensagens padronizadas
- ✅ **Validações robustas**: 5 validadores customizados seguindo Strategy Pattern
- ✅ **Interface profissional**: Estilos modernos com feedback visual (cores por status)
- ✅ **Flyway Migrations**: Controle de versão do schema do banco
- ✅ **CORS configurado**: Comunicação frontend-backend sem bloqueios
- ✅ **Lombok**: Redução de boilerplate com `@Data`, `@RequiredArgsConstructor`
- ✅ **Testcontainers**: Testes de integração com PostgreSQL real

### Uso de NativeQuery (Requisito Obrigatório)

Todos os repositórios usam **apenas NativeQuery** via `EntityManager.createNativeQuery()`:

**ColaboradorNativeRepositoryImpl**:
```java
// INSERT com RETURNING
em.createNativeQuery("INSERT INTO colaborador (nome, cpf) VALUES (?, ?) RETURNING id", Long.class)

// SELECT com mapping de entidade
em.createNativeQuery("SELECT * FROM colaborador WHERE id = ?", Colaborador.class).getSingleResult()

// UPDATE
em.createNativeQuery("UPDATE colaborador SET nome = ?, cpf = ? WHERE id = ?").executeUpdate()

// DELETE
em.createNativeQuery("DELETE FROM colaborador WHERE id = ?").executeUpdate()
```

**TrazerNativeRepositoryImpl**: Mesma abordagem para todas as operações CRUD.

### Resumo de Conformidade

✅ **100% dos requisitos técnicos obrigatórios** implementados  
✅ **100% dos requisitos funcionais** implementados  
✅ **100% dos requisitos de entrega** atendidos  
✅ **8 diferenciais** implementados além do esperado

O projeto está **totalmente completo** e pronto para avaliação.

## 🔒 Segurança e Boas Práticas

### Proteção de Credenciais
- ✅ `.gitignore` configurado para bloquear arquivos `.env`, `.env.*`, `.azure/`, e `scripts/`
- ✅ Credenciais de produção (Railway/PostgreSQL) configuradas via variáveis de ambiente da plataforma
- ✅ Senhas padrão (`postgres`/`123`) apenas para desenvolvimento local via Docker
- ✅ Nenhuma senha real commitada no repositório

### Validações de Segurança
- ✅ CORS configurado para domínios específicos (localhost + Vercel)
- ✅ Validação de CPF com 11 dígitos obrigatórios
- ✅ Tratamento global de exceções sem exposição de stack traces
- ✅ Timezone configurado explicitamente (America/Sao_Paulo) para evitar bugs de fuso horário

### Checklist de Conformidade
- ✅ Sem senhas hardcoded no código
- ✅ `.gitignore` protegendo informações sensíveis
- ✅ Variáveis de ambiente usando valores default seguros para desenvolvimento
- ✅ Documentação clara sobre configuração de produção vs desenvolvimento
- ✅ Commits organizados seguindo Conventional Commits

---

## 📞 Contato

Desenvolvido para o Desafio Sulwork por **Anderson Lucas**.

**Links do Projeto**:
- 🌐 Aplicação: https://desafio-sulwork.vercel.app
- 💻 GitHub: https://github.com/andersoncsgo/desafio-sulwork
- 📚 API Docs: https://desafio-sulwork-production.up.railway.app/swagger-ui/index.html