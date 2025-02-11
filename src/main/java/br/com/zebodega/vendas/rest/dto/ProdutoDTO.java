package br.com.zebodega.vendas.rest.dto;

import br.com.zebodega.vendas.model.ProdutoModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class ProdutoDTO {
    private String nome;

    private String descricao;

    private Float preco;

    private Boolean ativo; // Representa se o produto está ativo (bit)

    public ProdutoModel toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ProdutoModel.class);
    }
}
