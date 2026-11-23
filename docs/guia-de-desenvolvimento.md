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
dto/         formatos usados na entrada e na resposta da API
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

### Livro

O cadastro e as consultas de Livro estão implementados:

| Método | Rota | Ação |
| --- | --- | --- |
| `GET` | `/livro` | Lista os livros com autores e categorias |
| `GET` | `/livro/{id}` | Busca um livro pelo ID |
| `POST` | `/livro` | Cadastra um livro com autores e categorias existentes |

Exemplo de cadastro:

```json
{
  "titulo": "O Bom Presságio",
  "anoPublicacao": 1990,
  "descricao": "Uma comédia sobre o fim do mundo.",
  "totalPaginas": 413,
  "autorIds": [13, 14],
  "categoriaIds": [1, 2]
}
```

Regras atuais:

- o título é obrigatório e pode ter até 200 caracteres;
- o total de páginas é obrigatório e deve ser maior que zero;
- o ano de publicação e a descrição são opcionais;
- o livro deve ter pelo menos um autor e uma categoria;
- todos os IDs de autores e categorias precisam existir;
- o ID do livro é gerado pelo banco.

O relacionamento é unidirecional no Java: Livro possui conjuntos de autores e categorias. As tabelas `Livro_Autor` e `Livro_Categoria` guardam essas associações no banco.

Após o cadastro, a API devolve um `LivroResponse` com o ID gerado e resumos dos autores e categorias. O cadastro foi testado manualmente pelo Swagger.

As consultas também devolvem `LivroResponse`. A busca de um ID inexistente retorna `404`. Os métodos de consulta usam uma transação somente de leitura para carregar os relacionamentos de Autor e Categoria antes de montar a resposta.

## Decisões atuais

- Os endpoints usam os nomes das entidades no singular: `/autor`, `/categoria` e `/livro`.
- Cadastro e atualização recebem DTOs, em vez de receber diretamente as entidades JPA.
- As respostas de Livro usam DTOs para controlar os dados devolvidos no JSON.
- As entidades usam os nomes de tabelas e colunas que já existem em `schema.sql`.
- Livro poderá ter mais de um autor e mais de uma categoria.
- O cadastro de Livro informa IDs de autores e categorias já cadastrados. O service busca e valida esses registros.
- Autor e Categoria não possuem coleções de livros. Pesquisas de livros por autor ou categoria partirão do `LivroRepository`.
- O ano de publicação pode ficar vazio para permitir o cadastro de obras ainda não lançadas.
- Livro exige pelo menos uma categoria como regra da aplicação.
- Não estamos usando exclusão em cascata nas tabelas associativas. Um autor ou uma categoria vinculada a um livro não deve ser excluído silenciosamente.
- Os scripts da pasta `database` são a referência do banco, mas não são executados automaticamente pela aplicação como migrations.

## Estado das próximas partes

- `Livro`: cadastro e consultas implementados; atualizações e exclusão ainda não foram feitas;
- `Usuario`: controller provisório;
- `Biblioteca` e `Leitura`: ainda não possuem implementação Java.

A próxima etapa de Livro é implementar a exclusão antes das atualizações.

## Pendências

- Implementar `PUT`, `PATCH` e `DELETE` de Livro.

## Cuidados ao trabalhar no projeto

- Não versionar `src/main/resources/application.properties`, pois ele contém as credenciais locais.
- Usar `application.example.properties` como modelo de configuração.
- Conferir `git status` antes de alterar arquivos para não sobrescrever trabalho de outra pessoa.
- Mudanças no schema ou no seed devem ser combinadas com a equipe antes de serem executadas no banco compartilhado.
- Sempre que uma decisão estrutural mudar, atualizar este guia no mesmo commit.
