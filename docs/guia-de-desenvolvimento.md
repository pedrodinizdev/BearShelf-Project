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

O CRUD de Livro está implementado:

| Método | Rota | Ação |
| --- | --- | --- |
| `GET` | `/livro` | Lista os livros com autores e categorias |
| `GET` | `/livro/{id}` | Busca um livro pelo ID |
| `POST` | `/livro` | Cadastra um livro com autores e categorias existentes |
| `PUT` | `/livro/{id}` | Atualiza todos os dados de um livro |
| `PATCH` | `/livro/{id}` | Atualiza somente os dados enviados |
| `DELETE` | `/livro/{id}` | Exclui um livro e suas associações |

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

O `PUT` recebe o mesmo formato do cadastro. Como é uma atualização completa, todos os campos devem ser enviados. Os autores e as categorias informados substituem as associações anteriores do livro. O ID do livro não muda. Essa rota também foi testada manualmente pelo Swagger.

No `PATCH`, os campos que não forem enviados mantêm seus valores atuais. Se `autorIds` ou `categoriaIds` forem informados, o conjunto recebido substitui as associações anteriores. A rota foi validada manualmente.

As consultas também devolvem `LivroResponse`. A busca de um ID inexistente retorna `404`. Os métodos de consulta usam uma transação somente de leitura para carregar os relacionamentos de Autor e Categoria antes de montar a resposta.

Na exclusão, o JPA remove as linhas correspondentes de `Livro_Autor` e `Livro_Categoria` antes de remover o Livro. Os registros de Autor e Categoria permanecem no banco. Um livro inexistente retorna `404`, e uma exclusão concluída retorna `204`.

As tabelas `Biblioteca` e `Leitura` ainda não estão mapeadas no Java, mas suas chaves estrangeiras já protegem o banco. Se uma delas estiver relacionada ao livro, o PostgreSQL impede a exclusão e a API está preparada para retornar `409`. Esse cenário ainda não foi testado manualmente.

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
- Excluir um Livro remove somente o Livro e suas linhas em `Livro_Autor` e `Livro_Categoria`.
- Registros de Biblioteca e Leitura não são apagados automaticamente; eles impedem a exclusão do Livro relacionado.
- Os scripts da pasta `database` são a referência do banco, mas não são executados automaticamente pela aplicação como migrations.

### Usuario

A entidade `Usuario` representa a tabela atual do banco. `id_usuario` continua como a chave interna gerada pelo PostgreSQL, enquanto `auth_id` guarda o UUID do Supabase Auth.

Rotas disponíveis:

| Método | Rota | Ação |
| --- | --- | --- |
| `GET` | `/usuario` | Lista os usuários |
| `GET` | `/usuario/{id}` | Busca um usuário pelo ID |
| `POST` | `/usuario` | Cadastra um usuário |
| `PUT` | `/usuario/{id}` | Atualiza nome e e-mail |
| `PATCH` | `/usuario/{id}` | Atualiza somente os dados enviados |
| `DELETE` | `/usuario/{id}` | Exclui um usuário sem dados relacionados |

O cadastro recebe nome e e-mail. A data é preenchida pelo service e o `auth_id` fica vazio por enquanto, permitindo testar pelo Swagger sem criar antes uma conta no Supabase Auth. A resposta não expõe o `auth_id`.

O nome e o e-mail são obrigatórios. O e-mail é salvo em letras minúsculas e não pode estar repetido.

O `PUT` exige nome e e-mail, enquanto o `PATCH` mantém os campos que não forem enviados. A data de cadastro e o `auth_id` não são alterados nessas operações.

Na exclusão, um usuário inexistente retorna `404`. Se houver registros relacionados em Biblioteca ou Leitura, o banco impede a exclusão e a API retorna `409`. As novas rotas ainda precisam ser testadas manualmente pelo Swagger.

## Estado das próximas partes

- `Livro`: CRUD implementado e validado;
- `Usuario`: CRUD implementado; atualização e exclusão aguardam teste manual;
- `Biblioteca` e `Leitura`: ainda não possuem implementação Java.

A próxima etapa é mapear Biblioteca e depois Leitura.

## Pendências

- Criar DTOs de resposta para Categoria e Autor.
- Testar `PUT`, `PATCH` e `DELETE` de Usuario pelo Swagger.

## Cuidados ao trabalhar no projeto

- Não versionar `src/main/resources/application.properties`, pois ele contém as credenciais locais.
- Usar `application.example.properties` como modelo de configuração.
- Conferir `git status` antes de alterar arquivos para não sobrescrever trabalho de outra pessoa.
- Mudanças no schema ou no seed devem ser combinadas com a equipe antes de serem executadas no banco compartilhado.
- Sempre que uma decisão estrutural mudar, atualizar este guia no mesmo commit.
