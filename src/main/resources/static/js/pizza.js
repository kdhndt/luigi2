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
    verberg("nieuwePrijsFout");
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

byId("bewaar").onclick = async function () {
    const nieuwePrijs = byId("nieuwePrijs");
    if (!nieuwePrijs.checkValidity()) {
        toon("nieuwePrijsFout");
        nieuwePrijs.focus();
        return
    }
    verberg("nieuwePrijsFout");
    // JavaScript object
    const wijzigPrijs = {
        prijs: nieuwePrijs.value
    };
    await updatePrijs(wijzigPrijs);
}

async function updatePrijs(wijzigPrijs) {
    // onderstaande kan, method interpolatie i.p.v. een aparte local variabele aan te maken
    const response = await fetch(`pizzas/${byId("zoekId").value}/prijs`,
        {
            method: "PATCH",
            headers: {"Content-Type": "application/json"},
            // JavaScript object naar JSON string, spreek het prijs veld aan!
            // anders krijg je {"prijs":"4"}
            body: JSON.stringify(wijzigPrijs.prijs)
        });
    // console.log(JSON.stringify(wijzigPrijs));
    if (response.ok) {
        setText("prijs", wijzigPrijs.prijs)
    } else {
        toon("storing");
    }
}

byId("prijzen").onclick = function () {
    const idEnNaam = {id: byId("zoekId").value, naam: byId("naam").innerText}
    sessionStorage.setItem("idEnNaam", JSON.stringify(idEnNaam));
}