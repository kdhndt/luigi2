package be.vdab.luigi2.repositories;

import be.vdab.luigi2.domain.Pizza;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PizzaRepository {
    private final JdbcTemplate template;

    private final RowMapper<Pizza> pizzaMapper = (result, rowNum) -> new Pizza(result.getLong("id"), result.getString("naam"), result.getBigDecimal("prijs"),
            result.getBigDecimal("winst"));

    public PizzaRepository(JdbcTemplate template) {
        this.template = template;
    }

    public long findAantal() {
        var sql = """
                select count(*)
                from pizzas
                """;
        return template.queryForObject(sql, Long.class);
    }

    public Optional<Pizza> findById(long id) {
        try {
            var sql = """
                    select id, naam, prijs, winst
                    from pizzas
                    where id = ?
                    """;
            return Optional.of(template.queryForObject(sql, pizzaMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    public List<Pizza> findAll() {
        var sql = """
                select id, naam, prijs, winst
                from pizzas
                order by naam;
                """;
        // geen queryForObject want het is een verzameling
        return template.query(sql, pizzaMapper);
    }
}
