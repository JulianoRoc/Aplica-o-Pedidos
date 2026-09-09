package br.ufsm.csi.projeto_pedido.repository;

import br.ufsm.csi.projeto_pedido.model.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}