package br.com.zebodega.vendas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ItensPedido")
public class ItensPedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItensPedido;

    @NotNull(message = "A quantidade é obrigatória!")
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @NotNull(message = "O pedido é obrigatório!")
    @Column(name = "idPedido", nullable = false)
    private Long idPedido;

    @NotNull(message = "O produto é obrigatório!")
    @Column(name = "idProduto", nullable = false)
    private Long idProduto;

}
