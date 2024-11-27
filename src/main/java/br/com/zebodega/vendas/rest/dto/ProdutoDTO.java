package br.com.zebodega.vendas.rest.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProdutoDTO {
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @Column(name = "preco", nullable = false)
    private Float preco;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo; // Representa se o produto está ativo (bit)
}
