package br.ufsm.csi.projeto_pedido.repository;

import br.ufsm.csi.projeto_pedido.model.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}