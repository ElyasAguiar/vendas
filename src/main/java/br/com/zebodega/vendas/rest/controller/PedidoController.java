package br.com.zebodega.vendas.rest.controller;

import br.com.zebodega.vendas.model.PedidoModel;
import br.com.zebodega.vendas.rest.dto.PedidoDTO;
import br.com.zebodega.vendas.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos pedidos.
 */
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<PedidoDTO> obterTodos() {
        return pedidoService.obterTodos();
    }

    @GetMapping("/{id}")
    public PedidoDTO obterPorId(@PathVariable Long id) {
        return pedidoService.obterPorId(id);
    }

    @PostMapping
    public PedidoDTO salvar(@RequestBody PedidoModel novoPedido) {
        return pedidoService.salvar(novoPedido);
    }

    @PutMapping("/{id}")
    public PedidoDTO atualizar(@PathVariable Long id, @RequestBody PedidoModel pedidoAtualizado) {
        pedidoAtualizado.setIdPedido(id);
        return pedidoService.atualizar(pedidoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        PedidoModel pedido = new PedidoModel();
        pedido.setIdPedido(id);
        pedidoService.deletar(pedido);
    }

    @GetMapping("/faturamento")
    public BigDecimal calcularFaturamentoPeriodo(@RequestParam LocalDate dataInicial, @RequestParam LocalDate dataFinal) {
        return pedidoService.calcularFaturamentoPeriodo(dataInicial, dataFinal);
    }

    @PostMapping("/{id}/desconto")
    public BigDecimal aplicarDesconto(@PathVariable Long id) {
        PedidoModel pedido = pedidoService.obterPorId(id).toModel();
        return pedidoService.aplicarDescontoPedido(pedido);
    }
}
