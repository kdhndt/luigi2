"use strict";

import {byId, toon} from "./util.js";

const response = await fetch("pizzas");
if (response.ok) {
    // vergeet await niet als je werkt met await voor je response of in het algemeen met async functions!
    // dit heeft te maken met promise?
    const pizzas = await response.json();
    const pizzasBody = byId("pizzasBody");
    for (const pizza of pizzas) {
        const tr = pizzasBody.insertRow();
        tr.insertCell().innerText = pizza.id;
        tr.insertCell().innerText = pizza.naam;
        tr.insertCell().innerText = pizza.prijs;
    }
} else {
    toon("storing");
}