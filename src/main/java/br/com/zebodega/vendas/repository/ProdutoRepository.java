package br.com.zebodega.vendas.repository;

import br.com.zebodega.vendas.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

    // Método para buscar um produto pelo nome
    Optional<ProdutoModel> findByNome(String nome);
}