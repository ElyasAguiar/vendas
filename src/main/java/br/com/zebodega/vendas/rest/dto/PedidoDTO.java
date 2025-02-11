package br.com.zebodega.vendas.rest.dto;

import br.com.zebodega.vendas.model.PedidoModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
public class PedidoDTO {
    private Float valorTotal;

    private LocalDateTime dataHora;

    private String numeroPedido;

    private Boolean ativo;

    private String observacao;

    private Long idFormaPagamento;

    private Long idCliente;

    public PedidoModel toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, PedidoModel.class);
    }
}
