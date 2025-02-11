package br.com.zebodega.vendas.repository;

import br.com.zebodega.vendas.model.PedidoModel;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

    Optional<PedidoModel> findByNumeroPedido(String numeroPedido);

    boolean existsByNumeroPedido(String idPedido);

    List<PedidoModel> findByDataHoraBetweenAndAtivo(
            @NotNull(message = "A data e hora são obrigatórias!") LocalDateTime dataHora,
            @NotNull(message = "A data e hora são obrigatórias!") LocalDateTime dataHora2,
            @NotNull(message = "O valor não pode ser nulo!") Boolean ativo);

}
