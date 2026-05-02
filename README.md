# 📦 Inventory Control API
 
> Projeto de aprendizado e portfólio — REST API construída com Java 17 e Spring Boot 3.5
 
Este projeto foi desenvolvido com o objetivo de colocar em prática um conjunto de tecnologias do ecossistema Java/Spring em um contexto realista: uma API de controle de estoque com autenticação JWT, movimentações de entrada e saída, e documentação interativa via Swagger.
 
**Não se trata de um sistema pronto para produção**, mas de um projeto estruturado para aprendizado.
 
---
 
## 🛠️ Stack
 
| Tecnologia | Descrição |
|---|---|
| Java 17 | Linguagem principal |
| Spring Boot 3.5 | Framework principal |
| Spring Security + JWT | Autenticação e autorização |
| JPA + Hibernate | ORM |
| PostgreSQL | Banco de dados relacional |
| Flyway | Migrations de banco de dados |
| Maven | Gerenciador de dependências |
| Docker + Docker Compose | Containerização |
| SpringDoc OpenAPI (Swagger) | Documentação interativa |
| JUnit 5 + Mockito | Testes unitários e de integração |
 
---
 
## 📋 Endpoints
 
**Base URL:** `/api`
**Autenticação:** Bearer Token JWT (exceto `/auth`)
 
### 🔐 Auth
 
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| `POST` | `/auth/register` | Cadastra novo usuário e retorna JWT | ❌ Público |
| `POST` | `/auth/login` | Autentica e retorna JWT | ❌ Público |
 
### 📦 Products
 
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/products` | Lista todos os produtos |
| `GET` | `/api/products/{id}` | Busca produto por ID |
| `GET` | `/api/products/low-stock` | Lista produtos abaixo do estoque mínimo |
| `POST` | `/api/products` | Cadastra novo produto |
| `PUT` | `/api/products/{id}` | Atualiza produto |
| `DELETE` | `/api/products/{id}` | Remove produto |
 
### 🏷️ Categories
 
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/categories` | Lista todas as categorias |
| `GET` | `/api/categories/{id}` | Busca categoria por ID |
| `POST` | `/api/categories` | Cadastra nova categoria |
| `PUT` | `/api/categories/{id}` | Atualiza categoria |
| `DELETE` | `/api/categories/{id}` | Remove categoria |
 
### 🏭 Suppliers
 
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/suppliers` | Lista todos os fornecedores |
| `GET` | `/api/suppliers/{id}` | Busca fornecedor por ID |
| `POST` | `/api/suppliers` | Cadastra novo fornecedor |
| `PUT` | `/api/suppliers/{id}` | Atualiza fornecedor |
| `DELETE` | `/api/suppliers/{id}` | Remove fornecedor |
 
### 📊 Stock Movements
 
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/stock-movements` | Lista todas as movimentações |
| `GET` | `/api/stock-movements/product/{productId}` | Histórico de movimentações de um produto |
| `POST` | `/api/stock-movements` | Registra entrada (`IN`) ou saída (`OUT`) |
 
> Movimentações são registros históricos imutáveis — não possuem PUT nem DELETE.
 
---
 
## 🗂️ Arquitetura de Pacotes
 
```
src/
├── main/
│   ├── java/com/api/inventory_control/
│   │   ├── config/          # SecurityConfig, SwaggerConfig
│   │   ├── controller/      # Camada HTTP
│   │   ├── service/         # Regras de negócio
│   │   ├── repository/      # Comunicação com o banco (JPA)
│   │   ├── model/           # Entidades e enums
│   │   ├── dto/             # Objetos de entrada e saída
│   │   ├── security/        # Filtros e lógica JWT
│   │   └── exception/       # Tratamento centralizado de erros
│   └── resources/
│       ├── db/migration/    # Scripts Flyway (V1 a V6)
│       ├── application-example.properties
│       └── application-docker.properties
└── test/
    └── java/com/api/inventory_control/
        ├── service/         # Testes de service com Mockito
        └── controller/      # Testes de controller com MockMvc
```
 
---
 
## 🗄️ Modelo de Dados
 
```
┌─────────────┐         ┌─────────────┐
│   Category  │         │   Supplier  │
└──────┬──────┘         └──────┬──────┘
       │ 1:N                   │ 1:N
       └──────────┬────────────┘
                  ▼
           ┌─────────────┐
           │   Product   │
           └──────┬──────┘
                  │ 1:N
                  ▼
      ┌───────────────────┐     ┌──────────┐
      │   StockMovement   │◄────│   User   │
      │  type: IN | OUT   │ 1:N └──────────┘
      └───────────────────┘
```
 
---
 
## 🚀 Como rodar
 
### Pré-requisitos
 
- [Docker](https://www.docker.com/) e Docker Compose instalados
### 1. Clone o repositório
 
```bash
git clone https://github.com/devDougie/api-controle-de-estoque.git
cd api-controle-de-estoque
```
 
### 2. Configure as variáveis de ambiente
 
Copie o template e preencha com seus valores:
 
```bash
cp .env.example .env
```
 
Edite o `.env`:
 
```env
DB_HOST=postgres
DB_PORT=5432
DB_NAME=inventory_db
DB_USER=inventory_user
DB_PASSWORD=sua_senha_aqui
 
JWT_SECRET=sua-chave-secreta-longa-e-aleatoria
JWT_EXPIRATION=86400000
```
 
> 💡 Para gerar um JWT secret seguro: `openssl rand -hex 64`
 
### 3. Suba os containers
 
```bash
docker-compose up --build
```
 
A aplicação estará disponível em `http://localhost:8080`.
 
> ⚠️ **Windows:** se você tiver o PostgreSQL instalado localmente, ele pode estar ocupando a porta 5432. Pare o serviço `postgresql-x64-XX` no Gerenciador de Serviços antes de subir o Docker.
 
---
 
## 📖 Documentação interativa (Swagger)
 
Com a aplicação rodando, acesse:
 
```
http://localhost:8080/swagger-ui.html
```
 
Para testar endpoints protegidos:
1. Use `POST /auth/register` ou `POST /auth/login` para obter um token JWT
2. Clique em **Authorize** e informe `Bearer <seu_token>`
---
 
## 🧪 Testes
 
O projeto conta com **13 testes** distribuídos em duas camadas:
 
**Testes de Service** (com Mockito, sem banco):
- `CategoryServiceTest` — nome duplicado, categoria não encontrada
- `ProductServiceTest` — SKU duplicado, categoria/fornecedor inexistente, produto não encontrado
- `StockMovementServiceTest` — saída sem estoque, produto inexistente, entrada e saída válidas
**Testes de Controller** (com MockMvc):
- `ProductControllerTest` — GET all, GET by id, GET not found
- `StockMovementControllerTest` — GET all, GET by product, POST create
Para rodar os testes localmente (requer apenas Maven, sem Docker):
 
```bash
mvn test
```
 
---
 
## ⚙️ Comandos úteis
 
```bash
# Sobe apenas o banco (para desenvolvimento local via IDE)
docker-compose up postgres
 
# Sobe banco + aplicação em container
docker-compose up
 
# Rebuild da imagem após mudanças no código
docker-compose up --build
 
# Derruba os containers (mantém os dados do banco)
docker-compose down
 
# Derruba os containers e apaga os dados do banco
docker-compose down -v
 
# Acompanha os logs da aplicação em tempo real
docker-compose logs -f app
```
 
---
 
## 🔧 Configuração local (sem Docker para a aplicação)
 
Se preferir rodar a aplicação pela IDE e usar apenas o banco em container:
 
1. Suba o banco: `docker-compose up postgres`
2. Copie `application-example.properties` para `application.properties` e preencha com as credenciais locais
3. Execute a aplicação pela IDE normalmente
O `application.properties` está no `.gitignore` — suas credenciais não serão versionadas.
 
---
 
## 🎯 Objetivos de aprendizado
 
Este projeto foi desenvolvido em fases para praticar progressivamente:
 
- ✅ Estrutura de projeto Spring Boot com Maven
- ✅ Modelagem relacional e migrations com Flyway
- ✅ CRUDs completos com Spring Data JPA
- ✅ Autenticação stateless com Spring Security e JWT
- ✅ Regras de negócio em camada de Service
- ✅ Tratamento centralizado de erros com `@ControllerAdvice`
- ✅ Testes unitários e de controller com JUnit 5 e Mockito
- ✅ Containerização com Docker multi-stage build e Docker Compose
---
 
## 📄 Licença
 
Projeto de uso livre para fins de estudo e portfólio.
