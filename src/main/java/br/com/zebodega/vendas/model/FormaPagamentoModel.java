package br.com.zebodega.vendas.model;

import br.com.zebodega.vendas.rest.dto.FormaPagamentoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FormaPagamento")
public class FormaPagamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFormaPagamento;

    @NotBlank(message = "O nome é obrigatório!")
    @NotNull(message = "O nome não pode ser nulo!")
    @Size(max = 255, message = "O nome não pode ultrapassar 255 caracteres!")
    @Column(name = "nome", length = 255, nullable = false, unique = true)
    private String nome;

    @NotBlank(message = "A descrição é obrigatória!")
    @NotNull(message = "A descrição não pode ser nula!")
    @Size(max = 255, message = "A descrição não pode ultrapassar 255 caracteres!")
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    public FormaPagamentoDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, FormaPagamentoDTO.class);
    }
}
