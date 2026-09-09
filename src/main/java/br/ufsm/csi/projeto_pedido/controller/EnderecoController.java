package br.ufsm.csi.projeto_pedido.controller;

import br.ufsm.csi.projeto_pedido.model.cliente.Endereco;
import br.ufsm.csi.projeto_pedido.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<Endereco> criar(
            @RequestBody Endereco endereco) {

        Endereco enderecoSalvo =
                enderecoService.salvar(endereco);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(enderecoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Endereco>> listar() {

        return ResponseEntity.ok(
                enderecoService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                enderecoService.buscarPorId(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        enderecoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}