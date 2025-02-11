package br.com.zebodega.vendas.service;

import br.com.zebodega.vendas.exception.*;
import br.com.zebodega.vendas.model.ItensPedidoModel;
import br.com.zebodega.vendas.repository.ItensPedidoRepository;
import br.com.zebodega.vendas.rest.dto.ItensPedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItensPedidoService {
    @Autowired
    private ItensPedidoRepository itensPedidoRepository;

    @Transactional(readOnly = true)
    public ItensPedidoDTO obterItensPedidoPorId(Long idItensPedido) {
        return itensPedidoRepository.findById(idItensPedido)
                .orElseThrow(() -> new ObjectNotFoundException("ItensPedido não encontrado!"))
                .toDTO();
    }

    @Transactional(readOnly = true)
    public List<ItensPedidoDTO> obterTodos() {
        List<ItensPedidoModel> listaItensPedido = itensPedidoRepository.findAll();

        return listaItensPedido.stream().map(ItensPedidoModel::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItensPedidoDTO salvar(ItensPedidoModel novoItensPedido) {
        try {
            // Caso ocorra a tentaiva de salvar um item do pedido com o id pedido já existente, mostre a exceção abaixo.
            if (itensPedidoRepository.existsByIdItensPedido(novoItensPedido.getIdPedido())) {
                throw new ConstraintException("Já existe um itens pedido cadastrado com esse id pedido" + novoItensPedido.getIdPedido() + " !");
            }

            return itensPedidoRepository.save(novoItensPedido).toDTO();
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível criar um novo itens pedido! " + novoItensPedido.getIdPedido());
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar o itens pedido " + novoItensPedido.getIdPedido() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível criar um novo itens pedido " + novoItensPedido.getIdPedido() + ",pois violou uma regra de negócio");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível criar um novo itens pedido" + novoItensPedido.getIdPedido() + ", pois houve um erro de conexão com o banco de dados");
        }
    }

    @Transactional
    public ItensPedidoDTO atualizar(ItensPedidoModel itensPedidoExistente) {
        try {
            // Caso ocorra a tentaiva de salvar um ietns do pedido com o id pedido já existente, mostre a exceção abaixo.
            if (!itensPedidoRepository.existsByIdItensPedido(itensPedidoExistente.getIdPedido())) {
                throw new ConstraintException("O Itens pedido " + itensPedidoExistente.getIdPedido() + " não foi encontrado!");
            }

            return itensPedidoRepository.save(itensPedidoExistente).toDTO();
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar os itens do pedido ! " + itensPedidoExistente.getIdPedido());
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar o itens pedido " + itensPedidoExistente.getIdPedido() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar os itens do pedido " + itensPedidoExistente.getIdPedido() + ",pois violou uma regra de negócio");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar os itens do pedido" + itensPedidoExistente.getIdPedido() + ", pois houve um erro de conexão com o banco de dados");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! não foi possível localizar os itens do pedido " + itensPedidoExistente.getIdPedido() + " no banco de dados");
        }
    }

    @Transactional
    public void deletar(ItensPedidoModel itensPedidoExistente) {
        try {
            // Caso ocorra a tentaiva de salvar um ietns do pedido com o id pedido já existente, mostre a exceção abaixo.
            if (!itensPedidoRepository.existsByIdItensPedido(itensPedidoExistente.getIdPedido())) {
                throw new ConstraintException("O Itens pedido " + itensPedidoExistente.getIdPedido() + " não foi encontrado!");
            }

            itensPedidoRepository.delete(itensPedidoExistente);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar os itens do pedido ! " + itensPedidoExistente.getIdPedido());
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar o itens pedido " + itensPedidoExistente.getIdPedido() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar os itens do pedido " + itensPedidoExistente.getIdPedido() + ",pois violou uma regra de negócio");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível deletar os itens do pedido" + itensPedidoExistente.getIdPedido() + ", pois houve um erro de conexão com o banco de dados");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! não foi possível localizar os itens do pedido " + itensPedidoExistente.getIdPedido() + " no banco de dados");
        }
    }
}
