package br.ufsm.csi.projeto_pedido.repository;

import br.ufsm.csi.projeto_pedido.model.pedido.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}