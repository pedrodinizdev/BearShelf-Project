// o dto serve pra garantir as entradas somente necessárias, sem precisar do corpo inteiro da entidade enviado na requisição

package com.BearShelf.projeto.dto;

// record java facilita criação de classes usadas pra carregamento de dados, tem automaticamente construtores e getters
public record CategoriaCadastroRequest(String nome) {

}
