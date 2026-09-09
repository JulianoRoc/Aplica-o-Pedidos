package br.ufsm.csi.projeto_pedido.model.cliente;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ENDERECO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String rua;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(nullable = false, length = 20)
    private String cep;

    @Column(nullable = false, length = 50)
    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "CLIENTE_ID",
            nullable = false
    )
    private Cliente cliente;
}