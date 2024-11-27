package br.com.zebodega.vendas.rest.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class FormaPagamentoDTO {

    @Column(name = "nome", length = 255, nullable = false, unique = true)
    private String nome;

    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;
}
