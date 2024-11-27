package br.com.zebodega.vendas.repository;

import br.com.zebodega.vendas.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    // Método para buscar um usuário pelo nome
    Optional<UsuarioModel> findByUserName(String userName);
}