package br.com.zebodega.vendas.rest.controller;

import br.com.zebodega.vendas.model.ItensPedidoModel;
import br.com.zebodega.vendas.rest.dto.ItensPedidoDTO;
import br.com.zebodega.vendas.service.ItensPedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos itens do pedido.
 */
@RestController
@RequestMapping("/itensPedido")
public class ItensPedidoController {

    @Autowired
    private ItensPedidoService itensPedidoService;

    @GetMapping
    public ResponseEntity<List<ItensPedidoDTO>> obterTodos() {
        List<ItensPedidoDTO> itensPedido = itensPedidoService.obterTodos();
        return ResponseEntity.ok(itensPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItensPedidoDTO> obterPorId(@PathVariable Long id) {
        ItensPedidoDTO itensPedido = itensPedidoService.obterItensPedidoPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(itensPedido);
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<ItensPedidoDTO>> listarItensPorPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.status(HttpStatus.OK).body(itensPedidoService.listarItensPorPedido(pedidoId));
    }

    @PostMapping
    public ResponseEntity<ItensPedidoDTO> salvar(@Valid @RequestBody ItensPedidoModel novoItensPedido) {
        ItensPedidoDTO novoItensPedidoDTO = itensPedidoService.salvar(novoItensPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoItensPedidoDTO);
    }

    @PutMapping
    public ResponseEntity<ItensPedidoDTO> atualizar(@Valid @RequestBody ItensPedidoModel itensPedidoExistente) {
        ItensPedidoDTO itensPedidoAtualizadoDTO = itensPedidoService.atualizar(itensPedidoExistente);
        return ResponseEntity.status(HttpStatus.OK).body(itensPedidoAtualizadoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        itensPedidoService.deletar(itensPedidoService.obterItensPedidoPorId(id).toModel());
        return ResponseEntity.noContent().build();
    }
}