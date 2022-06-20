"use strict";
import {byId, toon, verberg, setText} from "./util.js";

// session; setItem en getItem -- we hebben nu toegang tot een variabele die we ergens anders hebben gemaakt
const idEnNaam = JSON.parse(sessionStorage.getItem("idEnNaam"));
setText("pizzaId", idEnNaam.id);
setText("pizzaNaam", idEnNaam.naam);
const response = await fetch(`pizzas/${idEnNaam.id}/prijzen`);
if (response.ok) {
    const prijzen = await response.json();
    const prijzenBody = byId("prijzenBody");
    for (const prijs of prijzen) {
        const tr = prijzenBody.insertRow();
        tr.insertCell().innerText = prijs.prijs;
        tr.insertCell().innerText =
            new Date(prijs.vanaf).toLocaleString("nl-BE");
    }
} else {
    toon("storing");
}
