package br.com.zebodega.vendas.model;

import br.com.zebodega.vendas.rest.dto.ClienteDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Cliente")
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;

    @NotBlank(message = "O valor é obrigatório!")
    @NotNull(message = "O valor não pode ser nulo!")
    @Size(message = "O valor máximo não pode ultrapassar 255 caracteres!", max = 255)
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank(message = "O valor é obrigatório!")
    @NotNull(message = "O valor não pode ser nulo!")
    @Size(message = "O valor máximo não pode ultrapassar 255 caracteres!", min = 14, max = 14)
    @Column(name = "cpf", length = 14, nullable = false, unique = true)
    private String cpf;

    @NotBlank(message = "O valor é obrigatório!")
    @NotNull(message = "O valor não pode ser nulo!")
    @Size(message = "O valor máximo não pode ultrapassar 255 caracteres!", max = 255)
    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @NotBlank(message = "O valor é obrigatório!")
    @NotNull(message = "O valor não pode ser nulo!")
    @Size(message = "O valor máximo não pode ultrapassar 14 caracteres!", max = 14)
    @Column(name = "telefone", length = 14, nullable = false)
    private String telefone;

    @NotNull(message = "O valor não pode ser nulo!")
    @Column(name = "dataNascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotBlank(message = "O valor é obrigatório!")
    @NotNull(message = "O valor não pode ser nulo!")
    @Size(message = "O valor máximo não pode ultrapassar 1 caractere!", min = 1, max = 1)
    @Column(name = "sexo", length = 1, nullable = false)
    private String sexo;

    @NotBlank(message = "O valor é obrigatório!")
    @NotNull(message = "O valor não pode ser nulo!")
    @Size(message = "O valor máximo não pode ultrapassar 255 caracteres!", max = 255)
    @Column(name = "apelido", length = 255, nullable = true)
    private String apelido;

    public ClienteDTO toDTO(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ClienteDTO.class);
    }
}
