package br.ufsm.csi.projeto_pedido.model.produto;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(
            name = "PRECO_BASE",
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal precoBase;
}