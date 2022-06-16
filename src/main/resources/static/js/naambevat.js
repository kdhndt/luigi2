"use strict";

import {toon, byId, verberg, verwijderChildElementenVan} from "./util.js";

byId("zoek").onclick = async function () {
    verbergPizzasEnFouten();
    const woord = byId("woord");
    if (!woord.checkValidity()) {
        toon("woordFout")
        woord.focus()
        return;
    }
    // works without await too
    await findByNaamBevat(woord.value);
}

function verbergPizzasEnFouten() {
    verberg("pizzasTable");
    verberg("woordFout");
    verberg("storing");
}

async function findByNaamBevat(woord) {
    const response = await fetch(`pizzas?naamBevat=${woord}`);
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