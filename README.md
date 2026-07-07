# 💈 Agendamento Barbearia API

API REST para gerenciamento de agendamentos em barbearias, desenvolvida em Spring Boot com arquitetura em camadas.

## Tecnologias

- Java 21
- Spring Boot
- Spring Data JPA
- Bean Validation
- Maven

## Arquitetura

- **DTOs** separados para request e response, evitando exposição direta das entidades
- **Exceções customizadas** (`ConflitoHorarioException`, `ResourceNotFoundException`) com `GlobalExceptionHandler` centralizado
- **Validação de conflitos de horário** via queries no repository
- Camadas bem definidas: Controller → Service → Repository

## Recursos da API

### Barbeiros
- CRUD completo (criar, listar, buscar por ID, atualizar, deletar)

### Agendamentos
- CRUD completo
- Verificação automática de conflito de horário ao criar um novo agendamento

## Como rodar o projeto

Pré-requisitos: Java 21 e Maven (ou use o wrapper incluso).

```bash
# Clonar o repositório
git clone https://github.com/vitorcipriano77/agendamento-barbearia-api.git
cd agendamento-barbearia-api

# Rodar com o wrapper (Windows)
.\mvnw.cmd spring-boot:run

# Rodar com o wrapper (Linux/Mac)
./mvnw spring-boot:run
```

A aplicação sobe por padrão em `http://localhost:8080`.

## Autor

Vitor Cipriano
