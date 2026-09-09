package br.ufsm.csi.projeto_pedido.repository;

import br.ufsm.csi.projeto_pedido.model.cliente.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}