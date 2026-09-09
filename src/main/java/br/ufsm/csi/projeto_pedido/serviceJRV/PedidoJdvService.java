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
        String sql = """
            SELECT JSON_SERIALIZE(DATA)
            FROM PEDIDO_JDV
            WHERE JSON_VALUE(DATA, '$._id') = ?
            """;

        List<String> resultado = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getString(1),
                id
        );

        if (resultado.isEmpty()) {
            return null;
        }

        return resultado.get(0);
    }

    public void inserir(String json) {
        String sql = """
            INSERT INTO PEDIDO_JDV (DATA)
            VALUES (JSON(?))
            """;

        jdbcTemplate.update(sql, json);
    }

    public void atualizarStatus(Long id, String status) {
        String sql = """
            UPDATE PEDIDO_JDV
            SET DATA = JSON_TRANSFORM(
                DATA,
                SET '$.status' = ?
            )
            WHERE JSON_VALUE(DATA, '$._id') = ?
            """;

        jdbcTemplate.update(sql, status, id);
    }

    public void excluir(Long id) {
        String sql = """
            DELETE FROM PEDIDO_JDV
            WHERE JSON_VALUE(DATA, '$._id') = ?
            """;

        jdbcTemplate.update(sql, id);
    }

    public String listar() {

        String sql = """
                SELECT JSON_SERIALIZE(DATA)
                FROM PEDIDO_JDV
                """;

        return jdbcTemplate.queryForList(
                sql,
                String.class
        ).toString();
    }
}