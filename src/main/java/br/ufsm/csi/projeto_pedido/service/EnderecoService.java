package br.ufsm.csi.projeto_pedido.service;

import br.ufsm.csi.projeto_pedido.model.cliente.Endereco;
import br.ufsm.csi.projeto_pedido.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> listar() {
        return enderecoRepository.findAll();
    }

    public Endereco buscarPorId(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Endereço não encontrado"));
    }

    public void excluir(Long id) {
        Endereco endereco = buscarPorId(id);
        enderecoRepository.delete(endereco);
    }
}