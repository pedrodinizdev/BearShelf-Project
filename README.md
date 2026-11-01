# BearShelf 📚

Sistema web de biblioteca digital e leitura de livros, desenvolvido como projeto interdisciplinar das disciplinas de **Desenvolvimento de Aplicações Web II (DAW2)** e **Banco de Dados II (BDII)**.

O objetivo é permitir que usuários organizem seus livros digitais e realizem a leitura diretamente pela aplicação.

## Tecnologias

- Java e Spring Boot
- Maven
- PostgreSQL (Supabase)

## Configuração

1. Clone o repositório e acesse a branch de desenvolvimento:

   ```bash
   git clone https://github.com/pedrodinizdev/BearShelf-Project.git
   cd BearShelf-Project
   git switch dev
   ```

2. Em `src/main/resources/`, copie `application.example.properties` para `application.properties`.

3. Preencha os dados de conexão do PostgreSQL no arquivo local ou configure as variáveis de ambiente `DB_URL`, `DB_USER` e `DB_PASSWORD`.

4. Execute a aplicação:

   ```bash
   ./mvnw spring-boot:run
   ```

**Importante:** não envie o `application.properties` com credenciais para o GitHub.

## Branches

- **main:** versão principal e estável.
- **dev:** desenvolvimento e testes.

## Status

Projeto em desenvolvimento.
