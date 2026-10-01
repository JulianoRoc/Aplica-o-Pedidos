package br.ufsm.csi.projeto_pedido.repository;

import br.ufsm.csi.projeto_pedido.model.pedido.Pedido;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @EntityGraph(attributePaths = {
            "cliente",
            "enderecoEntrega",
            "itens",
            "itens.produto"
    })
    @Query("""
        SELECT p
        FROM Pedido p
        WHERE p.id = :id
        """)
    Optional<Pedido> buscarCompletoPorId(@Param("id") Long id);

    List<Pedido> findAllByOrderByIdDesc(Pageable pageable);

    @EntityGraph(attributePaths = {
            "cliente",
            "enderecoEntrega",
            "itens",
            "itens.produto"
    })
    @Query("""
    SELECT DISTINCT p
    FROM Pedido p
    """)
    List<Pedido> listarCompleto();
}