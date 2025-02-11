package br.com.zebodega.vendas.repository;

import br.com.zebodega.vendas.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    Optional<UsuarioModel> findByUserName(String userName);

    boolean existsByIdCliente(Long idCliente);
}
