package br.com.zebodega.vendas.rest.controller;

import br.com.zebodega.vendas.model.FormaPagamentoModel;
import br.com.zebodega.vendas.rest.dto.FormaPagamentoDTO;
import br.com.zebodega.vendas.service.FormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas às formas de pagamento.
 */
@RestController
@RequestMapping("/formaPagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> obterTodos() {
        List<FormaPagamentoDTO> formaPagamentoDTOList = formaPagamentoService.obterTodos();
        return ResponseEntity.ok(formaPagamentoDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> obterPorId(@PathVariable Long id) {
        FormaPagamentoDTO formaPagamentoDTO = formaPagamentoService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(formaPagamentoDTO);
    }

    @PostMapping
    public ResponseEntity<FormaPagamentoDTO> salvar(@Valid @RequestBody FormaPagamentoModel novaFormaPagamento) {
        FormaPagamentoDTO novaFormaPagamentoDTO = formaPagamentoService.salvar(novaFormaPagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaFormaPagamentoDTO);
    }

    @PutMapping
    public ResponseEntity<FormaPagamentoDTO> atualizar(@Valid @RequestBody FormaPagamentoModel formaPagamentoExistente) {
        FormaPagamentoDTO formaPagamentoExistenteDTO = formaPagamentoService.atualizar(formaPagamentoExistente);
        return ResponseEntity.status(HttpStatus.OK).body(formaPagamentoExistenteDTO);
    }

    @DeleteMapping
    public void deletar(@Valid @RequestBody FormaPagamentoModel formaPagamentoModel) {
        formaPagamentoService.deletar(formaPagamentoModel);
    }

}