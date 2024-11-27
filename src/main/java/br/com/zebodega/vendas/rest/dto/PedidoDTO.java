package br.com.zebodega.vendas.rest.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PedidoDTO {
    @Column(name = "valorTotal", nullable = false)
    private Float valorTotal;

    @Column(name = "dataHora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "numeroPedido", length = 10, nullable = false, unique = true)
    private String numeroPedido;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "observacao", length = 255)
    private String observacao;

    @Column(name = "idFormaPagamento", nullable = false)
    private Long idFormaPagamento;

    @Column(name = "idCliente", nullable = false)
    private Long idCliente;
}
