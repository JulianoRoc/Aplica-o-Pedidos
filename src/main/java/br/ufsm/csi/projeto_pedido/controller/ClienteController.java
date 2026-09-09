package br.ufsm.csi.projeto_pedido.controller;

import br.ufsm.csi.projeto_pedido.model.cliente.Cliente;
import br.ufsm.csi.projeto_pedido.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> criar(
            @RequestBody Cliente cliente) {

        Cliente clienteSalvo = clienteService.salvar(cliente);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clienteSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {

        return ResponseEntity.ok(
                clienteService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                clienteService.buscarPorId(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        clienteService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}