
---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- MySQL
- Docker & Docker Compose
- HTML/CSS/JavaScript

---

## ⚙️ Funcionalidade

A API permite operações CRUD simples (exemplo: cadastro de usuários).

### Endpoints Exemplo

| Método | Endpoint     | Descrição              |
|--------|--------------|------------------------|
| GET    | `/users`     | Lista todos os usuários |
| POST   | `/users`     | Cria um novo usuário    |

---

## 🖥️ Front-end Inicial

O front-end simples em HTML + JavaScript consome os endpoints da API e mostra os dados em uma tabela.

- `GET /users`: mostra os usuários cadastrados
- `POST /users`: formulário para novo cadastro

---

## 🐳 Contêinerização com Docker

Utilizamos o Docker e o Docker Compose para facilitar a configuração e execução.

### docker-compose.yml

```yaml
version: "3.8"

services:
  mysqldb:
    image: mysql:8.0
    restart: unless-stopped
    env_file: ./.env
    container_name: mysqldb
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQLDB_ROOT_PASSWORD}
      MYSQL_DATABASE: ${MYSQLDB_DATABASE}
      MYSQL_USER: ${MYSQLDB_USER}
      MYSQL_PASSWORD: ${MYSQLDB_PASSWORD}
    ports:
      - "${MYSQLDB_LOCAL_PORT}:${MYSQLDB_DOCKER_PORT}"
    volumes:
      - mysqldb_data:/var/lib/mysql
  app:
    depends_on:
      - mysqldb
    build:
      context: ./
      dockerfile: Dockerfile  
    working_dir: /usr/src/projectsimplesapp
    container_name: projectsimplesapp
    restart: on-failure
    env_file: ./.env
    ports:
      - "${SPRING_LOCAL_PORT}:${SPRING_DOCKER_PORT}"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysqldb:3306/ProjectSimples
      SPRING_DATASOURCE_USERNAME: ${MYSQLDB_USER}
      SPRING_DATASOURCE_PASSWORD: ${MYSQLDB_PASSWORD}
    volumes:
      - .m2:/root/.m2
    stdin_open: true
    tty: true

volumes:
  mysqldb_data:

