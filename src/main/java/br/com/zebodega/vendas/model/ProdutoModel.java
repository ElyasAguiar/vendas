package br.com.zebodega.vendas.model;

import br.com.zebodega.vendas.rest.dto.ProdutoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Produto")
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Long id;

    @NotBlank(message = "O nome é obrigatório!")
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @NotBlank(message = "A descrição é obrigatória!")
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @NotNull(message = "O preço é obrigatório!")
    @Column(name = "preco", nullable = false)
    private Float preco;

    @NotNull(message = "O status de ativo é obrigatório!")
    @Column(name = "ativo", nullable = false)
    private Boolean ativo; // Representa se o produto está ativo (bit)

    public ProdutoDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ProdutoDTO.class);
    }
}
