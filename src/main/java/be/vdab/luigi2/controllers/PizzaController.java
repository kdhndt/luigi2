package be.vdab.luigi2.controllers;

import be.vdab.luigi2.domain.Pizza;
import be.vdab.luigi2.exceptions.PizzaNietGevondenException;
import be.vdab.luigi2.services.PizzaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}