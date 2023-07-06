package be.vdab.cinefest.repositories;

import be.vdab.cinefest.domain.Film;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository {
    private final JdbcTemplate template;

    public FilmRepository(JdbcTemplate template) {
        this.template = template;
    }

    private final RowMapper<Film> mapper = (rs, rowNum) ->
            new Film(rs.getLong("id"), rs.getString("titel"),
                    rs.getInt("jaar"), rs.getLong("vrijePlaatsen"),
                    rs.getBigDecimal("aankoopprijs"));

    public long findTotaalVrijePlaatsen(){
        var sql = """
                SELECT SUM(vrijePlaatsen) AS totaalVrijePlaatsen
                FROM films
                """;

        return template.queryForObject(sql, Long.class);
    }

    public Optional<Film> findByid(long id){
        try{
            var sql = """
                    SELECT id, titel, jaar, vrijePlaatsen, aankoopprijs
                    FROM films
                    WHERE id = ?
                    """;
            return Optional.of(template.queryForObject(sql, mapper, id));
        }
        catch (IncorrectResultSizeDataAccessException e){
            return Optional.empty();
        }
    }

    public List<Film> findAll() {
        var sql= """
                SELECT id, titel, jaar, vrijePlaatsen, aankoopprijs
                FROM films
                ORDER BY titel
                """;

        return template.query(sql,mapper);
    }

    public List<Film> findByJaar(int jaar) {
        var sql = """
                SELECT id, titel, jaar, vrijePlaatsen, aankoopprijs
                FROM films
                WHERE jaar = ?
                ORDER BY titel
                """;

        return template.query(sql,mapper, jaar);

    }

    public void delete(long id){
        var sql = """
                DELETE FROM films
                WHERE id = ?
                """;

        template.update(sql, id);
    }

    public long create(Film film) {
        var sql= """
                INSERT INTO films(titel, jaar, vrijePlaatsen, aankoopprijs)
                VALUES (?,?,?,?)
                """;

        var keyHolder = new GeneratedKeyHolder();
        template.update(con -> {
            var stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1,film.getTitel());
            stmt.setInt(2,film.getJaar());
            stmt.setLong(3, film.getVrijePlaatsen());
            stmt.setBigDecimal(4, film.getAankoopPrijs());
            return stmt;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
