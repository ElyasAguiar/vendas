package br.com.zebodega.vendas.repository;

import br.com.zebodega.vendas.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
    Optional<ProdutoModel> findByNome(String nome);

    boolean existsByNome(String nome);
}