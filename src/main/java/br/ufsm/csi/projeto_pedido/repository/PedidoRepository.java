package br.ufsm.csi.projeto_pedido.repository;

import br.ufsm.csi.projeto_pedido.model.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}