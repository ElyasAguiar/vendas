package br.com.zebodega.vendas.rest.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientDTO {

    @Column(name = "nome", length = 255, nullable = false)
    private String nome;


    @Column(name = "cpf", length = 14, nullable = false, unique = true)
    private String cpf;


    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "telefone", length = 14, nullable = false)
    private String telefone;

    @Column(name = "dataNascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "sexo", length = 1, nullable = false)
    private String sexo;

    @Column(name = "apelido", length = 255, nullable = true)
    private String apelido;
}
