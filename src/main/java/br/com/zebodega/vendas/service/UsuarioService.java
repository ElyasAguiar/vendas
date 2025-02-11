package br.com.zebodega.vendas.service;

import br.com.zebodega.vendas.exception.*;
import br.com.zebodega.vendas.model.UsuarioModel;
import br.com.zebodega.vendas.repository.UsuarioRepository;
import br.com.zebodega.vendas.rest.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public UsuarioDTO obterPorId(Long id) {
        UsuarioModel usuario = usuarioRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuario com ID " + id + " não encontrado."));
        return usuario.toDTO();
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> obterTodos() {

        List<UsuarioModel> listaUsuarios = usuarioRepository.findAll();

        return listaUsuarios.stream().map(usuarioModel -> usuarioModel.toDTO()).collect(Collectors.toList());
    }

    @Transactional
    public UsuarioDTO salvar(UsuarioModel novoUsuario) {
        try {
            if (usuarioRepository.existsByIdCliente(novoUsuario.getIdCliente())) {
                throw new ConstraintException("Já existe um usuário cadastrado com esse id" + novoUsuario.getIdCliente() + " !");
            }

            return usuarioRepository.save(novoUsuario).toDTO();
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível criar um novo usuário! " + novoUsuario.getIdCliente());
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar o usuario " + novoUsuario.getIdCliente() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível criar um novo usuário " + novoUsuario.getIdCliente() + ",pois violou uma regra de negócio");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível criar um novo usuário" + novoUsuario.getIdCliente() + ", pois houve um erro de conexão com o banco de dados");
        }
    }

    @Transactional
    public UsuarioDTO atualizar(UsuarioModel usuarioExistente) {
        try {
            if (!usuarioRepository.existsByIdCliente(usuarioExistente.getIdCliente())) {
                throw new ConstraintException("O usuário " + usuarioExistente.getIdCliente() + " não foi encontrado!");
            }

            return usuarioRepository.save(usuarioExistente).toDTO();
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar o usuário! " + usuarioExistente.getIdCliente());
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar o usuario " + usuarioExistente.getIdCliente() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar o usuário " + usuarioExistente.getIdCliente() + ",pois violou uma regra de negócio");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o usuário" + usuarioExistente.getIdCliente() + ", pois houve um erro de conexão com o banco de dados");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! não foi possível localizar o usuário " + usuarioExistente.getIdCliente() + " no banco de dados");
        }
    }

    @Transactional
    public void deletar(UsuarioModel usuarioExistente) {
        try {
            if (!usuarioRepository.existsByIdCliente(usuarioExistente.getIdCliente())) {
                throw new ConstraintException("O usuário " + usuarioExistente.getIdCliente() + " não foi encontrado!");
            }

            usuarioRepository.delete(usuarioExistente);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar o usuário! " + usuarioExistente.getIdCliente());
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar o usuario " + usuarioExistente.getIdCliente() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar o usuário " + usuarioExistente.getIdCliente() + ",pois violou uma regra de negócio");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível deletar o usuário" + usuarioExistente.getIdCliente() + ", pois houve um erro de conexão com o banco de dados");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! não foi possível localizar o usuário " + usuarioExistente.getIdCliente() + " no banco de dados");
        }
    }
}
