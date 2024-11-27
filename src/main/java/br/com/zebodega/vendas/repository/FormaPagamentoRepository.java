package br.com.zebodega.vendas.repository;

import br.com.zebodega.vendas.model.FormaPagamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamentoModel, Long> {

    // Método para buscar uma forma de pagamento pelo nome
    Optional<FormaPagamentoModel> findByNome(String nome);
}
