
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
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    container_name: mysql-db
    environment:
      MYSQL_DATABASE: mydb
      MYSQL_ROOT_PASSWORD: root
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  backend:
    build: ./backend
    container_name: spring-api
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql-db:3306/mydb
      SPRING_DATASOURCE_USERNAME: usuario cadastrado do seu banco de dados instalado
      SPRING_DATASOURCE_PASSWORD: senha cadstrada no banco de dados instalado 
    depends_on:
      - mysql

  frontend:
    image: nginx:alpine
    container_name: frontend-ui
    volumes:
      - ./frontend:/usr/share/nginx/html
    ports:
      - "8081:80"

volumes:
  mysql_data:

