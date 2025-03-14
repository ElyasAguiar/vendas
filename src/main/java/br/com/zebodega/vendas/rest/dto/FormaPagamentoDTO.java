package br.com.zebodega.vendas.rest.dto;

import br.com.zebodega.vendas.model.FormaPagamentoModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class FormaPagamentoDTO {
    private Long id;

    private String nome;

    private String descricao;

    public FormaPagamentoModel toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, FormaPagamentoModel.class);
    }
}
