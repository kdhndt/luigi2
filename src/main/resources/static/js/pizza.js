"use strict";

import {byId, toon, verberg, setText} from "./util.js";

byId("zoek").onclick = async function () {
    verbergPizzaEnFouten();
    const zoekIdInput = byId("zoekId");
    if (!zoekIdInput.checkValidity()) {
        toon("zoekIdFout");
        zoekIdInput.focus();
        return
    }
    /*await */
    findById(zoekIdInput.value);
}

function verbergPizzaEnFouten() {
    verberg("pizza");
    verberg("zoekIdFout");
    verberg("nietGevonden");
    verberg("storing");
}

async function findById(id) {
    const response = await fetch(`pizzas/${id}`);
    if (response.ok) {
        const pizza = await response.json();
        toon("pizza");
        // je hebt hier geen auto complete in JavaScript, verschiet hier dus niet van
        setText("naam", pizza.naam)
        setText("prijs", pizza.prijs)
    } else {
        if (response.status === 404) {
            toon("nietGevonden");
        } else {
            toon("storing");
        }
    }
}