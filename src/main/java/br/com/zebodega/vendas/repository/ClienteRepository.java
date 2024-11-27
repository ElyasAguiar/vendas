package br.com.zebodega.vendas.repository;

import br.com.zebodega.vendas.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
    // Buscar Cliente pelo CPF
    Optional<ClienteModel> findByCpf(String cpf);

    // Buscar Cliente pelo Email
    Optional<ClienteModel> findByEmail(String email);

    // Buscar Cliente pelo Nome (exemplo de busca mais complexa)
    List<ClienteModel> findByNomeContaining(String nome);
}
