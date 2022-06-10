package be.vdab.luigi2.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("/insertPizzas.sql")
@AutoConfigureMockMvc
class PizzaControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final static String PIZZAS = "pizzas";
    private final MockMvc mockMvc;

    public PizzaControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void findAantal() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pizzas/aantal"))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("$").value(countRowsInTable(PIZZAS))
                );
//        System.out.println(countRowsInTable(PIZZAS));
        ;
    }
}