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
import org.springframework.data.domain.Pageable;
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

        long inicio = System.nanoTime();

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

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        pedidoRepository.flush();

        long fim = System.nanoTime();

        System.out.println(
                "Tempo de criação: " +
                        ((fim - inicio) / 1_000_000.0) +
                        " ms"
        );

        return pedidoSalvo;
    }

    @Transactional(readOnly = true)
    public Pedido buscarCompletoPorId(Long id) {

        long inicio = System.nanoTime();

        Pedido pedido = pedidoRepository.buscarCompletoPorId(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pedido não encontrado"
                        ));

        pedido.getCliente().getId();
        pedido.getEnderecoEntrega().getId();

        for (ItemPedido item : pedido.getItens()) {
            item.getProduto().getId();
        }

        long fim = System.nanoTime();

        System.out.println(
                "Tempo de busca completa JPA: " +
                        ((fim - inicio) / 1_000_000.0) +
                        " ms"
        );

        return pedido;
    }

    @Transactional
    public Pedido atualizarStatus(Long id, StatusPedido novoStatus) {

        long inicio = System.nanoTime();

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pedido não encontrado"
                        ));

        pedido.setStatus(novoStatus);

        Pedido pedidoAtualizado = pedidoRepository.save(pedido);

        pedidoRepository.flush();

        long fim = System.nanoTime();

        System.out.println(
                "Tempo de atualização: " +
                        ((fim - inicio) / 1_000_000.0) +
                        " ms"
        );

        return pedidoAtualizado;
    }

    @Transactional
    public void excluir(Long id) {

        long inicio = System.nanoTime();

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pedido não encontrado"
                        ));

        pedidoRepository.delete(pedido);

        pedidoRepository.flush();

        long fim = System.nanoTime();

        System.out.println(
                "Tempo de exclusão: " +
                        ((fim - inicio) / 1_000_000.0) +
                        " ms"
        );
    }

    @Transactional
    public void inserirEmLote(int quantidade) {

        long inicio = System.nanoTime();

        Cliente cliente = clienteRepository.findById(1L)
                .orElseThrow(() ->
                        new RuntimeException("Cliente não encontrado"));

        Endereco endereco = enderecoRepository.findById(1L)
                .orElseThrow(() ->
                        new RuntimeException("Endereço não encontrado"));

        Produto produto = produtoRepository.findById(1L)
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado"));

        for (int i = 0; i < quantidade; i++) {

            Pedido pedido = new Pedido();

            pedido.setCliente(cliente);
            pedido.setEnderecoEntrega(endereco);
            pedido.setStatus(StatusPedido.PENDENTE);
            pedido.setCriadoEm(LocalDateTime.now());

            ItemPedido item = new ItemPedido();

            item.setProduto(produto);
            item.setQuantidade(1);
            item.setPrecoUnitario(produto.getPrecoBase());
            item.setPedido(pedido);

            pedido.getItens().add(item);

            pedidoRepository.save(pedido);
        }

        pedidoRepository.flush();

        long fim = System.nanoTime();

        System.out.println(
                "Tempo de inserção em lote (" + quantidade + " registros): " +
                        ((fim - inicio) / 1_000_000.0) +
                        " ms"
        );
    }

    @Transactional
    public void excluirEmLote(int quantidade) {

        long inicio = System.nanoTime();

        List<Pedido> pedidos = pedidoRepository
                .findAllByOrderByIdDesc(
                        Pageable.ofSize(quantidade)
                );

        if (pedidos.size() < quantidade) {
            throw new RuntimeException(
                    "Não existem pedidos suficientes para excluir"
            );
        }

        pedidoRepository.deleteAll(pedidos);

        pedidoRepository.flush();

        long fim = System.nanoTime();

        System.out.println(
                "Tempo de exclusão em lote (" + quantidade + " registros): " +
                        ((fim - inicio) / 1_000_000.0) +
                        " ms"
        );
    }

    public List<Pedido> listar() {

        long inicio = System.nanoTime();

        List<Pedido> pedidos = pedidoRepository.listarCompleto();

        long fim = System.nanoTime();

        System.out.println(
                "Tempo de listagem: " +
                        ((fim - inicio) / 1_000_000.0) +
                        " ms"
        );

        return pedidos;
    }
}