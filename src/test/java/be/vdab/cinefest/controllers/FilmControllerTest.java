package be.vdab.cinefest.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
@SpringBootTest
@Sql("/films.sql")
@AutoConfigureMockMvc
class FilmControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final static String FILMS = "films";
    private final MockMvc mockMvc;

    public FilmControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    private long idVanTest1Film() {
        return jdbcTemplate.queryForObject(
                "select id from films where titel = 'test1'", Long.class);
    }

    @Test void findById() throws Exception {
        var id = idVanTest1Film();
        mockMvc.perform(get("/films/{id}", id))
            .andExpectAll(
                    status().isOk(),
                    jsonPath("id").value(id),
                    jsonPath("titel").value("test1")
            );
    }

    @Test void findByIdGeeftNotFoundBijEenOnbestaandeFilm() throws Exception {
        mockMvc.perform(get("/films/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound()
                );
    }
    @Test void findAll() throws Exception {
        mockMvc.perform(get("/films"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()").value(countRowsInTable(FILMS)
                        )
                );
    }

    @Test
    void findByJaar() throws Exception {
        mockMvc.perform(get("/films").param("jaar", "2001"))
        .andExpectAll(
                status().isOk(),
                jsonPath("length()").value(countRowsInTableWhere(FILMS, "jaar = 2001")
                )
        );
    }
    @Test
    void deleteVerwijdertFilm() throws Exception {
        var id = idVanTest1Film(); mockMvc.perform(delete("/films/{id}", id))
                .andExpect(status().isOk());
        assertThat(countRowsInTableWhere(FILMS, "id = " + id)).isZero();
    }

}