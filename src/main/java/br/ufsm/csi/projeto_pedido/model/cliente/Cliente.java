package br.ufsm.csi.projeto_pedido.model.cliente;

import br.ufsm.csi.projeto_pedido.model.pedido.Pedido;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CLIENTE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @OneToMany(
            mappedBy = "cliente",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Endereco> enderecos = new ArrayList<>();

    @OneToMany(
            mappedBy = "cliente"
    )
    private List<Pedido> pedidos = new ArrayList<>();
}