# Guia de desenvolvimento

Este arquivo resume o estado atual do BearShelf. A ideia é facilitar a retomada do projeto por quem ficou um tempo sem acompanhar as alterações.

## Visão geral

O BearShelf é uma biblioteca digital. O backend está sendo feito com Java 21, Spring Boot, Spring Data JPA e PostgreSQL.

O banco está dividido nas seguintes partes:

- `Usuario`: dados das pessoas cadastradas;
- `Livro`: informações das obras;
- `Autor` e `Categoria`: classificação dos livros;
- `Livro_Autor` e `Livro_Categoria`: relacionamentos muitos para muitos;
- `Biblioteca`: livros salvos por cada usuário;
- `Leitura`: progresso de leitura de cada usuário.

O modelo completo está em [`database/schema.sql`](../database/schema.sql). Os diagramas conceitual e lógico estão nesta mesma pasta.

## Organização do backend

O código Java fica em `src/main/java/com/BearShelf/projeto` e segue esta separação:

```text
controller/  rotas HTTP e leitura dos dados da requisição
dto/         formatos usados na entrada da API
entity/      mapeamento das tabelas com JPA
repository/  acesso aos dados com Spring Data
service/     validações e regras de negócio
```

O fluxo esperado é:

```text
requisição -> controller -> service -> repository -> banco
```

As validações ficam no service. O controller deve se limitar às rotas, aos códigos HTTP e à chamada do service.

## O que já está implementado

### Categoria

Rotas disponíveis:

| Método | Rota | Ação |
| --- | --- | --- |
| `GET` | `/categoria` | Lista as categorias |
| `GET` | `/categoria/{id}` | Busca uma categoria |
| `POST` | `/categoria` | Cadastra uma categoria |
| `PUT` | `/categoria/{id}` | Atualiza uma categoria |
| `DELETE` | `/categoria/{id}` | Exclui uma categoria |

O cadastro e a atualização recebem:

```json
{
  "nome": "Fantasia"
}
```

### Autor

Rotas disponíveis:

| Método | Rota | Ação |
| --- | --- | --- |
| `GET` | `/autor` | Lista os autores |
| `GET` | `/autor/{id}` | Busca um autor |
| `POST` | `/autor` | Cadastra um autor |
| `PUT` | `/autor/{id}` | Atualiza um autor |
| `DELETE` | `/autor/{id}` | Exclui um autor |

O cadastro e a atualização recebem:

```json
{
  "nome": "Machado de Assis",
  "nacionalidade": "Brasileira"
}
```

Regras atuais:

- o nome é obrigatório e pode ter até 100 caracteres;
- a nacionalidade é opcional e pode ter até 60 caracteres;
- espaços no começo e no fim são removidos;
- uma busca por ID inexistente retorna `404`;
- dados inválidos retornam `400`;
- uma exclusão impedida por relacionamento no banco retorna `409`.

As rotas de Autor foram testadas manualmente pelo Swagger.

## Decisões atuais

- Os endpoints usam os nomes das entidades no singular: `/autor`, `/categoria` e `/livro`.
- Cadastro e atualização recebem DTOs, em vez de receber diretamente as entidades JPA.
- As entidades usam os nomes de tabelas e colunas que já existem em `schema.sql`.
- Livro poderá ter mais de um autor e mais de uma categoria.
- No futuro CRUD de Livro, a requisição deverá informar IDs de autores e categorias já cadastrados. O service será responsável por buscar e validar esses registros.
- Não estamos usando exclusão em cascata nas tabelas associativas. Um autor ou uma categoria vinculada a um livro não deve ser excluído silenciosamente.
- Os scripts da pasta `database` são a referência do banco, mas não são executados automaticamente pela aplicação como migrations.

## Estado das próximas partes

- `Livro`: controller provisório e entidade vazia;
- `Usuario`: controller provisório;
- `Biblioteca` e `Leitura`: ainda não possuem implementação Java.

A ordem sugerida para continuar é definir os formatos de resposta da API e depois implementar Livro. Livro depende de Autor e Categoria, então esses dois módulos devem estar estáveis primeiro.

## Pendências

- Antes de mapear os relacionamentos de Livro, precisamos definir os DTOs de resposta. Retornar entidades relacionadas diretamente pode causar recursão no JSON.

## Cuidados ao trabalhar no projeto

- Não versionar `src/main/resources/application.properties`, pois ele contém as credenciais locais.
- Usar `application.example.properties` como modelo de configuração.
- Conferir `git status` antes de alterar arquivos para não sobrescrever trabalho de outra pessoa.
- Mudanças no schema ou no seed devem ser combinadas com a equipe antes de serem executadas no banco compartilhado.
- Sempre que uma decisão estrutural mudar, atualizar este guia no mesmo commit.
