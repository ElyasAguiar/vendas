package br.com.zebodega.vendas.model;

import br.com.zebodega.vendas.rest.dto.PedidoDTO;
import br.com.zebodega.vendas.rest.dto.PedidoResponseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Pedido")
public class PedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @NotBlank(message = "O número do pedido é obrigatório!")
    @Size(max = 10, message = "O número do pedido não pode ultrapassar 10 caracteres!")
    @Column(name = "numeroPedido", length = 10, nullable = false, unique = true)
    private String numeroPedido;

    @NotNull(message = "O valor total é obrigatório!")
    @Column(name = "valorTotal", nullable = false)
    private BigDecimal valorTotal;

    @NotNull(message = "A data e hora são obrigatórias!")
    @Column(name = "dataHora", nullable = false)
    private LocalDateTime dataHora;

    @NotNull(message = "O valor não pode ser nulo!")
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Size(max = 255, message = "A observação não pode ultrapassar 255 caracteres!")
    @Column(name = "observacao", length = 255)
    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFormaPagamento")
    private FormaPagamentoModel formaPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCliente")
    private ClienteModel cliente;

    public PedidoDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, PedidoDTO.class);
    }

    public PedidoResponseDTO toResponseDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, PedidoResponseDTO.class);
    }
}
