package br.ufsm.csi.projeto_pedido.controller;

import br.ufsm.csi.projeto_pedido.model.pedido.Pedido;
import br.ufsm.csi.projeto_pedido.service.PedidoService;
import br.ufsm.csi.projeto_pedido.serviceJRV.PedidoJdvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoJdvService pedidoJdvService;

    @PostMapping
    public ResponseEntity<Pedido> criar(
            @RequestBody Pedido pedido) {

        Pedido pedidoSalvo = pedidoService.criarPedido(pedido);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pedidoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {

        return ResponseEntity.ok(
                pedidoService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoService.buscarPorId(id)
        );
    }

    @GetMapping("/jdv/{id}")
    public ResponseEntity<String> buscarPorIdJdv(@PathVariable Long id) {

        String resultado = pedidoJdvService.buscarPorId(id);

        if (resultado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/jdv")
    public ResponseEntity<Void> inserirJdv(@RequestBody String json) {
        pedidoJdvService.inserir(json);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/jdv/{id}")
    public ResponseEntity<Void> atualizarStatusJdv(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        pedidoJdvService.atualizarStatus(id, body.get("status"));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/jdv/{id}")
    public ResponseEntity<Void> excluirJdv(@PathVariable Long id) {
        pedidoJdvService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}