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

Biblioteca e Leitura já estão mapeadas no Java. Como as duas possuem chaves estrangeiras para Livro, o PostgreSQL continua impedindo que um Livro global seja excluído enquanto houver dados de usuários relacionados a ele. Nesse caso, a API retorna `409`.

## Decisões atuais

- Os endpoints usam os nomes das entidades no singular, como `/autor`, `/categoria`, `/livro`, `/usuario`, `/biblioteca` e `/leitura`.
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
- Excluir um Livro global não apaga Biblioteca ou Leitura de usuários. Esses registros impedem a exclusão do Livro relacionado.
- Remover um livro da Biblioteca pessoal apaga também o progresso de Leitura daquele usuário e daquele livro. O Livro global e os dados dos outros usuários permanecem.
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

### Biblioteca

Biblioteca está implementada e foi testada pelo Swagger:

| Método | Rota | Ação |
| --- | --- | --- |
| `POST` | `/biblioteca` | Adiciona um livro à biblioteca do usuário |
| `GET` | `/biblioteca/usuario/{usuarioId}` | Lista a biblioteca do usuário |
| `DELETE` | `/biblioteca/usuario/{usuarioId}/livro/{livroId}` | Remove um livro da biblioteca |

O cadastro recebe `usuarioId` e `livroId`, valida a existência dos dois registros e preenche a data de adição. O mesmo livro não pode ser adicionado duas vezes à biblioteca do mesmo usuário; nesse caso, a API retorna `409`.

Exemplo de cadastro:

```json
{
  "usuarioId": 1,
  "livroId": 16
}
```

A resposta contém os dois IDs, o título do livro e a data em que ele foi adicionado. A API usa `BibliotecaResponse` para devolver apenas esses dados, sem expor diretamente as entidades JPA de Usuario e Livro.

#### Por que Biblioteca é uma entidade

`Biblioteca` liga Usuario e Livro, mas não é tratada apenas como uma tabela intermediária. Ela possui um dado próprio, `data_adicao`, e operações próprias na API. Por isso existe uma entidade `Biblioteca` e não apenas um `@ManyToMany` dentro de Usuario ou Livro.

No banco, a chave primária é composta por `id_usuario` e `id_livro`, pois um livro só pode aparecer uma vez na biblioteca de cada usuário.

A classe `BibliotecaId` representa esse par no Java. Ela não cria uma tabela nova. `@Embeddable` informa que seus campos podem fazer parte de outra entidade, e `@EmbeddedId` coloca esse objeto como a chave da entidade `Biblioteca`. A implementação de `Serializable` atende ao formato exigido pelo JPA para uma classe de chave composta.

Na entidade, `@MapsId("usuarioId")` e `@MapsId("livroId")` ligam cada parte da chave aos relacionamentos com Usuario e Livro. Assim, os objetos `usuario` e `livro` usam as mesmas colunas `id_usuario` e `id_livro` que formam a chave primária; não são colunas duplicadas.

Uma rota que identifique uma associação poderá receber os dois valores, por exemplo:

```text
/biblioteca/usuario/3/livro/14
```

O controller recebe `3` e `14` com `@PathVariable`, e o service monta a chave:

```java
BibliotecaId id = new BibliotecaId(usuarioId, livroId);
```

Essa nova instância pode representar a mesma chave de outra instância que o JPA já carregou ou está controlando. Por isso `BibliotecaId` implementa `equals()` e `hashCode()`: duas chaves com o mesmo usuário e o mesmo livro precisam ser tratadas como a mesma identidade, mesmo que tenham sido criadas em momentos diferentes.

O `equals()` não faz parte de uma validação obrigatória executada antes de toda alteração. O próprio Java ou o JPA pode chamá-lo ao comparar chaves, controlar entidades já carregadas ou trabalhar com coleções. A consulta ao banco continua usando as duas colunas, equivalente a procurar por `id_usuario = 3` e `id_livro = 14`.

O `hashCode()` gera um valor a partir dos mesmos dois IDs. Ele é usado por estruturas como `HashSet` e `HashMap` para localizar objetos. Como `equals()` considera usuário e livro, `hashCode()` também precisa considerar os dois.

#### Fluxo das operações

Ao adicionar um livro, o service busca Usuario e Livro pelos repositories, verifica se o par já existe, cria `Biblioteca` com a data atual e salva. Ao listar, `BibliotecaRepository` pesquisa os registros pelo ID do usuário e o service transforma cada entidade em `BibliotecaResponse`.

Ao remover um livro da biblioteca, o service:

1. monta `BibliotecaId` com os IDs recebidos pela rota;
2. confirma que a associação existe;
3. monta `LeituraId` com o mesmo par;
4. remove o progresso de leitura, se ele existir;
5. remove o registro de Biblioteca.

As duas exclusões ficam no mesmo `@Transactional`. Se uma delas falhar, a operação inteira é desfeita. Essa regra foi escrita no service; não foi usado `cascade` para apagar o Livro, o Usuario ou dados de outras pessoas.

### Leitura

Leitura registra em qual página o usuário parou e quando ocorreu o último acesso.

| Método | Rota | Ação |
| --- | --- | --- |
| `POST` | `/leitura` | Inicia a leitura de um livro da biblioteca |
| `GET` | `/leitura/usuario/{usuarioId}` | Lista as leituras do usuário |
| `GET` | `/leitura/usuario/{usuarioId}/livro/{livroId}` | Busca um progresso específico |
| `PATCH` | `/leitura/usuario/{usuarioId}/livro/{livroId}` | Atualiza a página atual |
| `DELETE` | `/leitura/usuario/{usuarioId}/livro/{livroId}` | Remove o progresso |

Exemplo para iniciar uma leitura:

```json
{
  "usuarioId": 1,
  "livroId": 16,
  "paginaAtual": 0
}
```

Assim como Biblioteca, Leitura usa uma chave primária composta por `id_usuario` e `id_livro`. A classe `LeituraId` representa esse par e segue o mesmo mapeamento com `@Embeddable`, `@EmbeddedId`, `@MapsId`, `Serializable`, `equals()` e `hashCode()`.

Não existe um ID numérico separado porque o próprio par já identifica o registro: um usuário possui no máximo um progresso para cada livro. O service cria `LeituraId` usando os IDs que chegaram pelo corpo da requisição ou pela rota e o repository usa os dois valores na consulta.

O service usa três repositories, todos ligados ao mesmo banco:

- `LeituraRepository`: consulta e salva o progresso;
- `BibliotecaRepository`: confirma que o livro pertence à biblioteca do usuário;
- `UsuarioRepository`: confirma a existência do usuário nas listagens.

Ao iniciar uma leitura, o fluxo é:

1. receber `usuarioId`, `livroId` e uma página inicial opcional;
2. montar `BibliotecaId` e confirmar que o livro está na biblioteca;
3. montar `LeituraId` e impedir uma leitura duplicada;
4. usar a página enviada ou zero quando ela não for informada;
5. impedir página negativa ou maior que o total de páginas do livro;
6. salvar a leitura com `ultimoAcesso` preenchido pelo service.

`biblioteca.getLivro()` devolve a entidade Livro ligada àquela associação. O service usa esse objeto para consultar `totalPaginas` durante a validação, sem precisar fazer outra busca pelo `LivroRepository`.

Métodos atuais do service:

| Método | Função |
| --- | --- |
| `iniciarLeitura` | Cria o progresso de um livro que já está na Biblioteca |
| `listarLeituras` | Lista os progressos de um usuário |
| `buscarLeitura` | Busca um progresso pelo par usuário e livro |
| `atualizarProgresso` | Altera a página e atualiza `ultimoAcesso` |
| `removerLeitura` | Exclui somente o registro de progresso |

Para atualizar a página, o corpo do `PATCH` recebe somente:

```json
{
  "paginaAtual": 120
}
```

O avanço não acontece sozinho dentro do backend. `atualizarProgresso` é executado quando algum cliente chama essa rota. Durante os testes, o Swagger faz esse papel; futuramente, uma interface poderá enviar a página quando o usuário avançar ou fechar a leitura.

A página não pode ser negativa nem ultrapassar `totalPaginas`. Quando `paginaAtual` não é enviada no cadastro, a leitura começa em zero. Uma tentativa de iniciar novamente o mesmo par usuário/livro retorna `409`, e uma leitura inexistente retorna `404`.

`LeituraResponse` devolve os IDs do usuário e do livro, o título, a página atual, o total de páginas e o último acesso. O horário é atualizado pelo service sempre que o progresso é alterado.

Os métodos de escrita usam `@Transactional`, para que a operação seja desfeita se ocorrer um erro. As consultas usam `@Transactional(readOnly = true)`. O cadastro e o fluxo de remoção conjunta com Biblioteca foram testados pelo Swagger.

## Estado das próximas partes

- `Categoria`, `Autor` e `Livro`: CRUD implementado;
- `Usuario`: CRUD implementado; atualização e exclusão aguardam teste manual;
- `Biblioteca`: cadastro, consulta e exclusão implementados e validados;
- `Leitura`: cadastro, consultas, atualização de progresso e exclusão implementados;
- base inicial do backend de DAW2 concluída.

As próximas partes previstas são a revisão das políticas de RLS e da integração com Supabase Auth, o Object Storage e o uso de Redis. Essas etapas ainda não mudam as regras descritas acima.

## Pendências

- Criar DTOs de resposta para Categoria e Autor.
- Testar `PUT`, `PATCH` e `DELETE` de Usuario pelo Swagger.

## Cuidados ao trabalhar no projeto

- Não versionar `src/main/resources/application.properties`, pois ele contém as credenciais locais.
- Usar `application.example.properties` como modelo de configuração.
- Conferir `git status` antes de alterar arquivos para não sobrescrever trabalho de outra pessoa.
- Mudanças no schema ou no seed devem ser combinadas com a equipe antes de serem executadas no banco compartilhado.
- Sempre que uma decisão estrutural mudar, atualizar este guia no mesmo commit.
