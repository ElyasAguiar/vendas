package br.com.zebodega.vendas.service;

import br.com.zebodega.vendas.exception.*;
import br.com.zebodega.vendas.model.ClienteModel;
import br.com.zebodega.vendas.model.FormaPagamentoModel;
import br.com.zebodega.vendas.model.PedidoModel;
import br.com.zebodega.vendas.repository.ClienteRepository;
import br.com.zebodega.vendas.repository.FormaPagamentoRepository;
import br.com.zebodega.vendas.repository.PedidoRepository;
import br.com.zebodega.vendas.rest.dto.PedidoDTO;
import br.com.zebodega.vendas.rest.dto.PedidoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Transactional(readOnly = true)
    public PedidoResponseDTO obterPorId(Long id) {
        PedidoModel pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pedido com ID " + id + " não encontrado."));
        return pedido.toResponseDTO();
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> obterTodos() {
        List<PedidoModel> listaPedidos = pedidoRepository.findAllWithClienteAndFormaPagamento();
        return listaPedidos.stream().map(PedidoModel::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public PedidoResponseDTO salvar(PedidoDTO novoPedido) {
        if (pedidoRepository.existsByNumeroPedido(novoPedido.getNumeroPedido())) {
            throw new ConstraintException("Já existe um pedido cadastrado com esse número: " + novoPedido.getNumeroPedido() + " !");
        }
        ClienteModel cliente = this.clienteRepository.findById(novoPedido.getIdCliente())
                .orElseThrow(() -> new ObjectNotFoundException("Cliente com ID " + novoPedido.getIdCliente() + " não encontrado."));
        FormaPagamentoModel formaPagamento = this.formaPagamentoRepository.findById(novoPedido.getIdFormaPagamento())
                .orElseThrow(() -> new ObjectNotFoundException("Forma de pagamento com ID " + novoPedido.getIdFormaPagamento() + " não encontrada."));

        PedidoModel pedido = new PedidoModel();

        pedido.setValorTotal(BigDecimal.valueOf(novoPedido.getValorTotal()));
        pedido.setNumeroPedido(novoPedido.getNumeroPedido());
        pedido.setAtivo(novoPedido.getAtivo());
        pedido.setObservacao(novoPedido.getObservacao());
        pedido.setDataHora(novoPedido.getDataHora() != null ? novoPedido.getDataHora() : LocalDateTime.now());
        pedido.setCliente(cliente);
        pedido.setFormaPagamento(formaPagamento);
        this.pedidoRepository.save(pedido).toResponseDTO();
        return this.aplicarDescontoPedido(pedido);
    }

    @Transactional
    public PedidoResponseDTO atualizar(PedidoDTO pedidoExistente) {
        if (!pedidoRepository.existsByNumeroPedido(pedidoExistente.getNumeroPedido())) {
            throw new ConstraintException("O Pedido " + pedidoExistente.getNumeroPedido() + " não foi encontrado!");
        }
        ClienteModel cliente = this.clienteRepository.findById(pedidoExistente.getIdCliente())
                .orElseThrow(() -> new ObjectNotFoundException("Cliente com ID " + pedidoExistente.getIdCliente() + " não encontrado."));
        FormaPagamentoModel formaPagamento = this.formaPagamentoRepository.findById(pedidoExistente.getIdFormaPagamento())
                .orElseThrow(() -> new ObjectNotFoundException("Forma de pagamento com ID " + pedidoExistente.getIdFormaPagamento() + " não encontrada."));
        PedidoModel pedidoUpdate = new PedidoModel();
        pedidoUpdate.setIdPedido(pedidoExistente.getIdPedido());
        pedidoUpdate.setValorTotal(BigDecimal.valueOf(pedidoExistente.getValorTotal()));
        pedidoUpdate.setNumeroPedido(pedidoExistente.getNumeroPedido());
        pedidoUpdate.setAtivo(pedidoExistente.getAtivo());
        pedidoUpdate.setObservacao(pedidoExistente.getObservacao());
        pedidoUpdate.setDataHora(pedidoExistente.getDataHora() != null ? pedidoExistente.getDataHora() : LocalDateTime.now());
        pedidoUpdate.setCliente(cliente);
        pedidoUpdate.setFormaPagamento(formaPagamento);
        return pedidoRepository.save(pedidoUpdate).toResponseDTO();
    }

    @Transactional
    public void deletar(PedidoModel pedidoExistente) {
        if (!pedidoRepository.existsByNumeroPedido(pedidoExistente.getNumeroPedido())) {
            throw new ConstraintException("O Pedido " + pedidoExistente.getNumeroPedido() + " não foi encontrado!");
        }
        pedidoRepository.delete(pedidoExistente);
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularFaturamentoPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
        if (dataInicial.isAfter(dataFinal)) {
            throw new IllegalArgumentException("A data inicial não pode ser posterior à data final.");
        }

        LocalDateTime dataInicialDateTime = dataInicial.atStartOfDay();
        LocalDateTime dataFinalDateTime = dataFinal.atStartOfDay().plusDays(1).minusSeconds(1);

        List<PedidoModel> pedidos = pedidoRepository.findByDataHoraBetweenAndAtivo(dataInicialDateTime, dataFinalDateTime, true);

        return pedidos.stream()
                .map(PedidoModel::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public PedidoResponseDTO aplicarDescontoPedido(PedidoModel pedido) {
        if (!pedido.getAtivo()) {
            throw new BusinessRuleException("Apenas pedidos ativos podem receber descontos.");
        }

        BigDecimal valorTotal = pedido.getValorTotal();
        BigDecimal desconto = BigDecimal.ZERO;

        if (valorTotal.compareTo(new BigDecimal("500")) > 0 && valorTotal.compareTo(new BigDecimal("1000")) <= 0) {
            desconto = valorTotal.multiply(new BigDecimal("0.05"));
        } else if (valorTotal.compareTo(new BigDecimal("1000")) > 0) {
            desconto = valorTotal.multiply(new BigDecimal("0.10"));
        }

        pedido.setValorTotal(valorTotal.subtract(desconto));
        return pedidoRepository.save(pedido).toResponseDTO();
    }
}