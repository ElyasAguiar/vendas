package br.com.zebodega.vendas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Usuario")
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotBlank(message = "O valor é obrigatório!")
    @NotNull(message = "O valor não pode ser nulo!")
    @Size(max = 255, message = "O valor máximo não pode ultrapassar 255 caracteres!")
    @Column(name = "userName", length = 255, nullable = false, unique = true)
    private String userName;

    @NotBlank(message = "O valor é obrigatório!")
    @NotNull(message = "O valor não pode ser nulo!")
    @Size(max = 255, message = "O valor máximo não pode ultrapassar 255 caracteres!")
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @NotNull(message = "O valor não pode ser nulo!")
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @NotNull(message = "O valor não pode ser nulo!")
    @Column(name = "idCliente", nullable = false)
    private Long idCliente;

}
