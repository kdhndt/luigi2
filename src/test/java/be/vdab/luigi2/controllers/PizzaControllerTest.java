package be.vdab.luigi2.controllers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Sql("/insertPizzas.sql")
@AutoConfigureMockMvc
class PizzaControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final static String PIZZAS = "pizzas";
    private final MockMvc mockMvc;
    private static final Path TEST_RESOURCES = Path.of("src/test/resources");

    public PizzaControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    private long idVanTest1Pizza() {
        return jdbcTemplate.queryForObject("select id from pizzas where naam = 'test1'", Long.class);
    }

    @Test
    void findAantal() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pizzas/aantal"))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("$").value(countRowsInTable(PIZZAS))
                );
//        System.out.println(countRowsInTable(PIZZAS));
    }

    @Test
    void findById() throws Exception {
        var id = idVanTest1Pizza();
        // must import the static version of these to be able to use get() and status() without all the boilerplate, mind that it's written in italic
        mockMvc.perform(get("/pizzas/{id}", id))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("id").value(id),
                        jsonPath("naam").value("test1")
                );
    }

    @Test
    void findByIdGeeftNotFoundBijEenOnbestaandePizza() throws Exception {
        mockMvc.perform(get("/pizzas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
//        System.out.println(Long.MAX_VALUE);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/pizzas"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()").value(countRowsInTable(PIZZAS))
                );
    }

    @Test
    public void findByNaamBevat() throws Exception {
        mockMvc.perform(get("/pizzas")
                        // /pizzas?naamBevat=test
                        .param("naamBevat", "test")
                )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()").value(countRowsInTableWhere(PIZZAS, "naam like '%test%'"))
                );
    }

    @Test
    public void findByPrijsTussen() throws Exception {
        mockMvc.perform(get("/pizzas")
                        .param("vanPrijs", "10")
                        .param("totPrijs", "20")
                )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()").value(countRowsInTableWhere(PIZZAS, "prijs between 10 and 20"))
                );
    }

    @Test
    public void delete() throws Exception {
        var id = idVanTest1Pizza();
        mockMvc.perform(MockMvcRequestBuilders.delete("/pizzas/{id}", id))
                .andExpect(status().isOk());
        assertThat(countRowsInTableWhere(PIZZAS, "id=" + id)).isZero();

    }

    @Test
    public void create() throws Exception {
        // lees de volledige tekst van het bestand
        var jsonData = Files.readString(TEST_RESOURCES.resolve("correctePizza.json"));

        // we hebben het aangemaakt id nodig, dit zal in de responseBody zitten
        var responseBody = mockMvc.perform(post("/pizzas")
                // request header type instellen, net zoals bij generated-requests.http
                .contentType(MediaType.APPLICATION_JSON)
                // dit is niet de ideale manier (geen kleuren syntax en controle)
/*                .content(
                        """
                        {
                          "naam": "test3",
                          "prijs": 30
                        }
                        """
                )*/
                .content(jsonData)
                )
                .andExpect(status().isOk())
                // response body er ook daadwerkelijk uit halen
                .andReturn().getResponse().getContentAsString();
        assertThat(countRowsInTableWhere(PIZZAS, "naam = 'test3' and id=" + responseBody)).isOne();
    }

    @ParameterizedTest
    @ValueSource(strings = {"pizzaMetLegeNaam.json", "pizzaMetNegatievePrijs.json", "pizzaZonderNaam.json", "pizzaZonderPrijs.json"})
    void createMetVerkeerdeDataMislukt(String bestandsNaam) throws Exception {
        var jsonData= Files.readString(TEST_RESOURCES.resolve(bestandsNaam));
        mockMvc.perform(post("/pizzas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData))
                .andExpect(status().isBadRequest());
    }

}