package br.ufsm.csi.projeto_pedido.controller;

import br.ufsm.csi.projeto_pedido.model.pedido.Pedido;
import br.ufsm.csi.projeto_pedido.model.pedido.StatusPedido;
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
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoService.buscarCompletoPorId(id)
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

    @GetMapping("/jdv")
    public ResponseEntity<String> listarJdv() {
        return ResponseEntity.ok(
                pedidoJdvService.listar()
        );
    }

    @PostMapping("/jdv/lote")
    public ResponseEntity<Void> inserirLote(
            @RequestBody List<String> jsons) {

        pedidoJdvService.inserirEmLote(jsons);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/jdv/lote")
    public ResponseEntity<Void> excluirLote(
            @RequestBody List<Long> ids) {

        pedidoJdvService.excluirEmLote(ids);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        StatusPedido status = StatusPedido.valueOf(body.get("status"));

        return ResponseEntity.ok(
                pedidoService.atualizarStatus(id, status)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        pedidoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/lote")
    public ResponseEntity<Void> inserirEmLote(
            @RequestParam int quantidade) {

        pedidoService.inserirEmLote(quantidade);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/lote")
    public ResponseEntity<Void> excluirEmLote(
            @RequestParam int quantidade) {

        pedidoService.excluirEmLote(quantidade);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/completo/{id}")
    public ResponseEntity<Pedido> buscarCompleto(@PathVariable Long id) {
        return ResponseEntity.ok(
                pedidoService.buscarCompletoPorId(id)
        );
    }
}
