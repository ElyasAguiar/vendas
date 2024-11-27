package br.com.zebodega.vendas.rest.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ItensPedidoDTO {
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "idPedido", nullable = false)
    private Long idPedido;

    @Column(name = "idProduto", nullable = false)
    private Long idProduto;
}
