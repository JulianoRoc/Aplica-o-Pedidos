package br.ufsm.csi.projeto_pedido.controller;

import br.ufsm.csi.projeto_pedido.model.produto.Produto;
import br.ufsm.csi.projeto_pedido.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> criar(
            @RequestBody Produto produto) {

        Produto produtoSalvo = produtoService.salvar(produto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(produtoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listar() {

        return ResponseEntity.ok(
                produtoService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                produtoService.buscarPorId(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        produtoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}