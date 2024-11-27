package br.com.zebodega.vendas.rest.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UsuarioDTO {
    @Column(name = "userName", length = 255, nullable = false, unique = true)
    private String userName;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "idCliente", nullable = false)
    private Long idCliente;
}
