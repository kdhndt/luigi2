package be.vdab.luigi2.controllers;

import be.vdab.luigi2.domain.Pizza;
import be.vdab.luigi2.dto.NieuwePizza;
import be.vdab.luigi2.exceptions.PizzaNietGevondenException;
import be.vdab.luigi2.services.PizzaService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.stream.Stream;

@RequestMapping("pizzas")
@RestController
class PizzaController {
    private final PizzaService pizzaService;

    PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping("aantal")
    long findAantal() {
        return pizzaService.findAantal();
    }

    // Plaats de DTO hier aangezien we deze nergens anders nodig hebben
    private record IdNaamPrijs(long id, String naam, BigDecimal prijs) {
        IdNaamPrijs(Pizza pizza) {
            this(pizza.getId(), pizza.getNaam(), pizza.getPrijs());
        }
    }

    @GetMapping("{id}")
    IdNaamPrijs findById(@PathVariable long id) {
        return pizzaService.findById(id)
                .map(pizza -> new IdNaamPrijs(pizza))
                .orElseThrow(() -> new PizzaNietGevondenException(id));
    }

    // Meerdere path variabelen
    @GetMapping("verkoop/{jaar}/{maand}/{dag}")
    BigDecimal verkoop(@PathVariable String dag, @PathVariable String maand, @PathVariable String jaar) {
        // Demo
        return BigDecimal.ONE;
    }

    @GetMapping
    Stream<IdNaamPrijs> findAll() {
        return pizzaService.findAll()
                .stream()
                .map(pizza -> new IdNaamPrijs(pizza));
    }

    // voeg ook value = "pizzas" toe als die nog niet in de @RequestMapping zit
    @GetMapping(params = "naamBevat")
    Stream<IdNaamPrijs> findByNaamBevat(String naamBevat) {
        return pizzaService.findByNaamBevat(naamBevat)
                .stream()
                .map(pizza -> new IdNaamPrijs(pizza));
    }

    @GetMapping(params = {"vanPrijs", "totPrijs"})
    Stream<IdNaamPrijs> findByPrijsTussen(BigDecimal vanPrijs, BigDecimal totPrijs) {
        return pizzaService.findByPrijsTussen(vanPrijs, totPrijs)
                .stream()
                .map(pizza -> new IdNaamPrijs(pizza));
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable long id) {
        pizzaService.delete(id);
    }

    @PostMapping
    // _request_ body (dit is de JSON die ik invoer bij generated requests, Postman, andere applicatie, o.i.d)
    long create(@RequestBody @Valid NieuwePizza nieuwePizza) {
        // binnenkomende JSON wordt vertaald naar een NieuwePizza object
        var id = pizzaService.create(nieuwePizza);
        // dit wordt dan getoond in de _response_ body
        return id;
    }
}