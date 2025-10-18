# Brinquedos - Checkpoint 5 (Parte 2 - Spring Security)

**Grupo:** LTAKN
- Enzo Prado Soddano — RM557937
- Lucas Resende Lima — RM556564
- Vinicius Prates Altafini — RM556183

---

## 📌 Descrição
Este projeto é a **Parte 2** do Checkpoint 5 de Java Advanced.

- **Spring Security** com autenticação e autorização
- **Sistema de login personalizado** com tela de cadastro (Sign Up)
- **Dois tipos de usuários** (USER e ADMIN) com diferentes acessos
- **PostgreSQL** como banco de dados
- **Interface web** usando Spring MVC + Thymeleaf
- **API REST** com HATEOAS
- **CRUD completo** de brinquedos esportivos
- **Validações** com Spring Validation
- **Deploy** via Render

---

## 🚀 Deploy / Entrega

- 🌐 **Projeto Live:**  
  https://java-cp5-2025-parte2.onrender.com

- 📦 **Dashboard do Deploy Render**  
  https://dashboard.render.com/web/srv-d3q0gu3ipnbc73a833sg/deploys/dep-d3q18p6r433s73ap31kg

---

## 🛠️ Tecnologias

- **Java 21**
- **Spring Boot 3.5.4**
- **Spring Security 6**
- **Spring MVC + Thymeleaf**
- **Spring Data JPA**
- **Spring HATEOAS**
- **PostgreSQL**
- **Lombok**
- **Maven**

---

## ⚙️ Como rodar localmente

1. Clone o repositório:
   ```bash
   git clone https://github.com/DerBrasilianer/java-cp5-2025-parte2.git
   cd java-cp5-2025-parte2
   ```

2. Configure o banco de dados PostgreSQL no `application.yml`

3. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

4. A aplicação ficará disponível em:  
   [http://localhost:8081](http://localhost:8081)

---

## 🔐 Funcionalidades de Segurança

### Sistema de Autenticação
- **Tela de login personalizada** (`/login`)
- **Tela de cadastro** (`/signup`) para novos usuários
- **Dois tipos de roles**: `USER` e `ADMIN`
- **Password encoder** com BCrypt
- **Autorização baseada em roles** para diferentes funcionalidades

### Acessos por Tipo de Usuário
- **Usuários não autenticados**: Acesso apenas à página inicial e telas de login/cadastro
- **Usuários USER**: Podem ver lista de brinquedos e cadastrar novos
- **Usuários ADMIN**: Podem ver, cadastrar, editar e excluir brinquedos

---

## 🗃️ Estrutura do Banco de Dados

### Tabela: `TDS_TB_Usuarios`
- `id` (PK, auto increment)
- `username` (unique, not null)
- `password` (criptografada)
- `role` (USER/ADMIN)
- `enabled` (boolean)

### Tabela: `TDS_TB_Brinquedos`
- `id` (PK, auto increment)
- `nome` (not null)
- `preco` (not null, positive)

---

## ⚙️ Configuração

O projeto utiliza **application.yml** para configuração, conforme exigido no requisito:

```yaml
spring:
datasource:
url: ${DATABASE_URL:jdbc:postgresql://localhost:5432/java-cp5-2025-db}
username: ${DATABASE_USERNAME:postgres}
password: ${DATABASE_PASSWORD:120304}
jpa:
hibernate:
ddl-auto: ${DDL_AUTO:update}
```

---

## 📡 Endpoints Principais

### Web MVC (Thymeleaf)
- `GET /` - Página inicial
- `GET /login` - Tela de login
- `GET /signup` - Tela de cadastro
- `GET /brinquedos/web` - Lista de brinquedos (web)
- `GET /brinquedos/web/new` - Formulário de criação
- `GET /brinquedos/web/{id}` - Detalhes do brinquedo
- `GET /brinquedos/web/{id}/edit` - Formulário de edição

### API REST (JSON)
- `GET /brinquedos` - Lista todos (HATEOAS)
- `GET /brinquedos/{id}` - Busca por ID
- `POST /brinquedos` - Cria novo brinquedo
- `PUT /brinquedos/{id}` - Atualização completa
- `PATCH /brinquedos/{id}` - Atualização parcial
- `DELETE /brinquedos/{id}` - Exclui brinquedo

---

## 🔧 Configuração Spring Security

### SecurityConfig
- CSRF desabilitado para APIs
- Rotas públicas: `/`, `/login`, `/signup`, `/css/**`, `/js/**`, `/images/**`
- Rotas autenticadas: `/brinquedos/**`
- Login personalizado em `/login`
- Logout configurado
- Suporte a HTTP Basic Auth para APIs

### CustomUserDetailsService
- Implementação customizada do UserDetailsService
- Integração com UserRepository
- Suporte a roles e status de usuário (enabled/disabled)

---

## 🚀 Deploy no Render

### Configuração:
- **Runtime**: Docker
- **Plano**: Free
- **Banco**: PostgreSQL (interno do Render)
- **Health Check**: `/` endpoint

### Variáveis de Ambiente:
- `DATABASE_URL`: URL JDBC do PostgreSQL
- `DATABASE_USERNAME`: Usuário do banco
- `DATABASE_PASSWORD`: Senha do banco
- `SPRING_PROFILES_ACTIVE`: prod
- `DDL_AUTO`: update

---

## 🎯 IDE Utilizado

**IntelliJ IDEA** - Ambiente de desenvolvimento principal

---

## 📸 Screenshots

### Página Inicial
![Home Page](src/main/resources/static/images/ltakn-logo.png)

### Cadastro de Usuário
![Signup Page](src/main/resources/static/prints/signup.png)

### Login
![Login Page](src/main/resources/static/prints/login.png)

### Lista de Brinquedos
![Brinquedos List](src/main/resources/static/prints/list.png)

### Adicionar Brinquedo
![Add Brinquedo](src/main/resources/static/prints/create.png)

### Editar Brinquedo
![Edit Brinquedo](src/main/resources/static/prints/edit.png)

### Detalhes do Brinquedo
![Details Brinquedo](src/main/resources/static/prints/details.png)

---

## 👥 Desenvolvido por

**Grupo LTAKN** - Java Advanced · FIAP · 2025