"use strict";

import {toon, byId, verberg, verwijderChildElementenVan} from "./util.js";

byId("zoek").onclick = async function () {
    verbergPizzasEnFouten();
    const van = byId("van");
    const tot = byId("tot");
    if (!van.checkValidity()) {
        toon("fouteInvoerVan")
        van.focus()
        return;
    }
    if (!tot.checkValidity()) {
        toon("fouteInvoerTot")
        tot.focus()
        return;
    }
    await findByPrijsTussen(van.value, tot.value);
}

function verbergPizzasEnFouten() {
    verberg("pizzasTable");
    verberg("fouteInvoerVan");
    verberg("fouteInvoerTot");
    verberg("storing");
}

async function findByPrijsTussen(vanPrijs, totPrijs) {
    const response = await fetch(`pizzas?vanPrijs=${vanPrijs}&totPrijs=${totPrijs}`);
    if (response.ok) {
        const pizzas = await response.json();
        const pizzasBody = byId("pizzasBody");
        verwijderChildElementenVan(pizzasBody);
        toon("pizzasTable")
        for (const pizza of pizzas) {
            const tr = pizzasBody.insertRow();
            tr.insertCell().innerText = pizza.id;
            tr.insertCell().innerText = pizza.naam;
            tr.insertCell().innerText = pizza.prijs;
        }
    } else {
        toon("storing");
    }
}