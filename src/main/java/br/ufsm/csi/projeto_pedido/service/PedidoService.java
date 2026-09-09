package br.ufsm.csi.projeto_pedido.service;

import br.ufsm.csi.projeto_pedido.model.cliente.Cliente;
import br.ufsm.csi.projeto_pedido.model.cliente.Endereco;
import br.ufsm.csi.projeto_pedido.model.pedido.ItemPedido;
import br.ufsm.csi.projeto_pedido.model.pedido.Pedido;
import br.ufsm.csi.projeto_pedido.model.pedido.StatusPedido;
import br.ufsm.csi.projeto_pedido.model.produto.Produto;
import br.ufsm.csi.projeto_pedido.repository.ClienteRepository;
import br.ufsm.csi.projeto_pedido.repository.EnderecoRepository;
import br.ufsm.csi.projeto_pedido.repository.PedidoRepository;
import br.ufsm.csi.projeto_pedido.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public Pedido criarPedido(Pedido pedido) {

        Long clienteId = pedido.getCliente().getId();

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() ->
                        new RuntimeException("Cliente não encontrado"));

        Long enderecoId = pedido.getEnderecoEntrega().getId();

        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() ->
                        new RuntimeException("Endereço não encontrado"));

        if (!endereco.getCliente().getId().equals(cliente.getId())) {
            throw new RuntimeException(
                    "O endereço não pertence ao cliente"
            );
        }

        pedido.setCliente(cliente);
        pedido.setEnderecoEntrega(endereco);
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setCriadoEm(LocalDateTime.now());

        for (ItemPedido item : pedido.getItens()) {

            Long produtoId = item.getProduto().getId();

            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Produto não encontrado: " + produtoId
                            ));

            if (item.getQuantidade() == null ||
                    item.getQuantidade() <= 0) {

                throw new RuntimeException(
                        "A quantidade deve ser maior que zero"
                );
            }

            item.setProduto(produto);

            item.setPrecoUnitario(produto.getPrecoBase());

            item.setPedido(pedido);
        }

        return pedidoRepository.save(pedido);
    }

    public Pedido buscarPorId(Long id) {

        return pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pedido não encontrado"
                        ));
    }

    public List<Pedido> listar() {

        return pedidoRepository.findAll();
    }
}