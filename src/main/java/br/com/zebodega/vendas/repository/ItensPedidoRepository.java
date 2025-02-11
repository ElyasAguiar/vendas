package br.com.zebodega.vendas.repository;

import br.com.zebodega.vendas.model.ItensPedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItensPedidoRepository extends JpaRepository<ItensPedidoModel, Long> {

    List<ItensPedidoModel> findByIdPedido(Long idPedido);

    boolean existsByIdItensPedido(Long idItensPedido);
}
