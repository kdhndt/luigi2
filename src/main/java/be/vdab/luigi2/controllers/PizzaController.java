package be.vdab.luigi2.controllers;

import be.vdab.luigi2.services.PizzaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PizzaController {
    private final PizzaService pizzaService;

    PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping("pizzas/aantal")
    long findAantal() {
        return pizzaService.findAantal();
    }
}
