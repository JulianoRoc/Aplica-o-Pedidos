package br.ufsm.csi.projeto_pedido.model.pedido;

import br.ufsm.csi.projeto_pedido.model.produto.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ITEM_PEDIDO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "PEDIDO_ID",
            nullable = false
    )
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "PRODUTO_ID",
            nullable = false
    )
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(
            name = "PRECO_UNITARIO",
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal precoUnitario;

    public BigDecimal calcularSubtotal() {

        return precoUnitario.multiply(
                BigDecimal.valueOf(quantidade)
        );
    }
}