package br.ufsm.csi.projeto_pedido.serviceJRV;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoJdvService {

    private final JdbcTemplate jdbcTemplate;

    public String buscarPorId(Long id) {

        long inicio = System.nanoTime();

        String sql = """
            SELECT JSON_SERIALIZE(DATA)
            FROM PEDIDO_JDV
            WHERE JSON_VALUE(DATA, '$._id' RETURNING NUMBER) = ?
            """;

        List<String> resultado = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getString(1),
                id
        );

        long fim = System.nanoTime();

        System.out.println(
                "Tempo de busca: " +
                        ((fim - inicio) / 1_000_000.0) +
                        " ms"
        );

        if (resultado.isEmpty()) {
            return null;
        }

        return resultado.get(0);
    }

    public void inserir(String json) {

        long inicio = System.nanoTime();

        String sql = """
            INSERT INTO PEDIDO_JDV (DATA)
            VALUES (JSON(?))
            """;

        jdbcTemplate.update(sql, json);

        long fim = System.nanoTime();

        System.out.println(
                "Tempo de inserção: " +
                        ((fim - inicio) / 1_000_000.0) +
                        " ms"
        );
    }

    public void atualizarStatus(Long id, String status) {

        long inicio = System.nanoTime();

        String sql = """
            UPDATE PEDIDO_JDV
            SET DATA = JSON_TRANSFORM(
                DATA,
                SET '$.status' = ?
            )
            WHERE JSON_VALUE(DATA, '$._id') = ?
            """;

        jdbcTemplate.update(sql, status, id);

        long fim = System.nanoTime();

        System.out.println(
                "Tempo de atualização: " +
                        ((fim - inicio) / 1_000_000.0) +
                        " ms"
        );
    }

    public void excluir(Long id) {

        long inicio = System.nanoTime();

        String sql = """
            DELETE FROM PEDIDO_JDV
            WHERE JSON_VALUE(DATA, '$._id') = ?
            """;

        jdbcTemplate.update(sql, id);

        long fim = System.nanoTime();

        System.out.println(
                "Tempo de exclusão: " +
                        ((fim - inicio) / 1_000_000.0) +
                        " ms"
        );
    }

    public String listar() {

        long inicio = System.nanoTime();

        String sql = """
                SELECT DATA
                FROM PEDIDO_JDV
                """;

        String resultado = jdbcTemplate.queryForList(
                sql,
                String.class
        ).toString();

        long fim = System.nanoTime();

        System.out.println(
                "Tempo de listagem: " +
                        ((fim - inicio) / 1_000_000.0) +
                        " ms"
        );

        return resultado;
    }

    public void inserirEmLote(List<String> jsons) {

        long inicio = System.nanoTime();

        String sql = """
        INSERT INTO PEDIDO_JDV (DATA)
        VALUES (JSON(?))
        """;

        for (String json : jsons) {
            jdbcTemplate.update(sql, json);
        }

        long fim = System.nanoTime();

        System.out.println(
                "Tempo de inserção em lote (" + jsons.size() + " registros): " +
                        ((fim - inicio) / 1_000_000.0) +
                        " ms"
        );
    }

    public void excluirEmLote(List<Long> ids) {

        long inicio = System.nanoTime();

        String sql = """
        DELETE FROM PEDIDO_JDV
        WHERE JSON_VALUE(DATA, '$._id') = ?
        """;

        for (Long id : ids) {
            jdbcTemplate.update(sql, id);
        }

        long fim = System.nanoTime();

        System.out.println(
                "Tempo de exclusão em lote (" + ids.size() + " registros): " +
                        ((fim - inicio) / 1_000_000.0) +
                        " ms"
        );
    }
}