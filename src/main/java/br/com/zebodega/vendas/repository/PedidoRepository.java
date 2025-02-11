package br.com.zebodega.vendas.repository;

import br.com.zebodega.vendas.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

    Optional<PedidoModel> findByNumeroPedido(String numeroPedido);

    boolean existsByNumeroPedido(String idPedido);

    List<PedidoModel> findByDataHoraBetweenAndAtivo(LocalDate dataInicial, LocalDate dataFinal, boolean status);

}
