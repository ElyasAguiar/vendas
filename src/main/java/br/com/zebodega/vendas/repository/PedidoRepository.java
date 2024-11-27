package br.com.zebodega.vendas.repository;

import br.com.zebodega.vendas.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

    // Método para buscar pedido por número
    Optional<PedidoModel> findByNumeroPedido(String numeroPedido);
}
