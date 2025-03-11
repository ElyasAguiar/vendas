package br.com.zebodega.vendas.rest.dto;

import br.com.zebodega.vendas.model.PedidoModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
public class PedidoResponseDTO {
    private Long idPedido;
    private Float valorTotal;
    private String numeroPedido;
    private Boolean ativo;
    private String observacao;
    private LocalDateTime dataHora;

    private String nomeCliente;
    private String emailCliente;
    private String telefoneCliente;

    private String descricaoFormaPagamento;


    public PedidoModel toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, PedidoModel.class);
    }
}