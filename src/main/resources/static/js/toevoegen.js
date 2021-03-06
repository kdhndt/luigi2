"use strict";

import {byId, setText, toon, verberg} from "./util.js";

byId("toevoegen").onclick = async function () {
    verbergFouten();
    const naam = byId("naam");
    if (!naam.checkValidity()) {
        toon("naamFout");
        naam.focus();
        return;
    }
    const prijs = byId("prijs");
    if (!prijs.checkValidity()) {
        toon("prijsFout");
        prijs.focus();
        return;
    }
    // JavaScript object maken
    const pizza = {naam: naam.value, prijs: prijs.value}
    await create(pizza)
}

function verbergFouten() {
    verberg("naamFout");
    verberg("prijsFout");
    verberg("storing");
    verberg("conflict");
}

async function create(pizza) {
    const response = await fetch("pizzas",
        // ook hier is er meer uitgebreide info nodig, je moet namelijk een JSON object kunnen aanbieden
        // een request body moet een header en een body hebben
        {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            // JavaScript object naar JSON string
            body: JSON.stringify(pizza)
        });
    if (response.ok) {
        // andere locatie tonen in browser
        window.location = "allepizzas.html";
    } else {
        if (response.status === 409) {
            const responseBody = await response.json();
            setText("conflict", responseBody.message);
            toon("conflict");
        }
        toon("storing");
    }
}