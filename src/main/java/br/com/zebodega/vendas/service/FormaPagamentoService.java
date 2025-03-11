package br.com.zebodega.vendas.service;

import br.com.zebodega.vendas.exception.*;
import br.com.zebodega.vendas.model.FormaPagamentoModel;
import br.com.zebodega.vendas.repository.FormaPagamentoRepository;
import br.com.zebodega.vendas.rest.dto.FormaPagamentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;


    @Transactional(readOnly = true)
    public List<FormaPagamentoDTO> obterTodos() {
        List<FormaPagamentoModel> listaFormaPagamentos = formaPagamentoRepository.findAll();

        return listaFormaPagamentos.stream().map(FormaPagamentoModel::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FormaPagamentoDTO obterPorId(Long id) {
        FormaPagamentoModel formaPagamento = formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Forma de pagamento com ID " + id + " não encontrado."));
        return formaPagamento.toDTO();
    }

    public FormaPagamentoDTO salvar(FormaPagamentoModel novaFormaPagamento) {
        try {
            if (formaPagamentoRepository.findByNome(novaFormaPagamento.getNome()).isPresent()) {
                throw new ConstraintException("Forma de pagamento já cadastrada! Id: " + novaFormaPagamento.getId() + ", Nome: " + novaFormaPagamento.getNome());
            }

            return formaPagamentoRepository.save(novaFormaPagamento).toDTO();
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível criar uma nova forma de pagamento! " + novaFormaPagamento.getId());
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar a forma de pagamento " + novaFormaPagamento.getId() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível criar uma nova forma de pagamento " + novaFormaPagamento.getId() + ",pois violou uma regra de negócio");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível criar uma nova forma de pagamento" + novaFormaPagamento.getId() + ", pois houve um erro de conexão com o banco de dados");
        }
    }

    @Transactional
    public FormaPagamentoDTO atualizar(FormaPagamentoModel formaPagamentoExistente) {
        try {
            // Caso ocorra a tentaiva de salvar uma forma de pagamento com o id já existente, mostre a exceção abaixo.
            if (!formaPagamentoRepository.existsById(formaPagamentoExistente.getId())) {
                throw new ConstraintException("A forma de pagamento " + formaPagamentoExistente.getId() + " não foi encontrada!");
            }

            return formaPagamentoRepository.save(formaPagamentoExistente).toDTO();
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar a forma de pagamento! " + formaPagamentoExistente.getId());
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar a Forma de pagamento " + formaPagamentoExistente.getId() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar a forma de pagamento " + formaPagamentoExistente.getId() + ",pois violou uma regra de negócio");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar a forma de pagamento" + formaPagamentoExistente.getId() + ", pois houve um erro de conexão com o banco de dados");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! não foi possível localizar a forma de pagamento " + formaPagamentoExistente.getId() + " no banco de dados");
        }
    }

    @Transactional
    public void deletar(FormaPagamentoModel formaPagamentoExistente) {
        try {
            // Caso ocorra a tentaiva de salvar uma forma de pagamento com o id já existente, mostre a exceção abaixo.
            if (!formaPagamentoRepository.existsById(formaPagamentoExistente.getId())) {
                throw new ConstraintException("A forma de pagamento " + formaPagamentoExistente.getId() + " não foi encontrada!");
            }

            formaPagamentoRepository.delete(formaPagamentoExistente);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar a forma de pagamento! " + formaPagamentoExistente.getId());
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar a forma de pagamento " + formaPagamentoExistente.getId() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar a forma de pagamento " + formaPagamentoExistente.getId() + ",pois violou uma regra de negócio");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível deletar a forma de pagamento" + formaPagamentoExistente.getId() + ", pois houve um erro de conexão com o banco de dados");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! não foi possível localizar a forma de pagamento " + formaPagamentoExistente.getId() + " no banco de dados");
        }
    }
}