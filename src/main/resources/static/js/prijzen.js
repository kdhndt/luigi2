"use strict";

// session; setItem en getItem -- we hebben nu toegang tot een variabele die we ergens anders hebben gemaakt
import {setText} from "./util";

const idEnNaam = JSON.parse(sessionStorage.getItem("idEnNaam"));

setText("pizzaId", idEnNaam.id);
setText("pizzaNaam", idEnNaam.naam);

const response = await fetch(`pizzas/${idEnNaam.id}/prijzen`);

if (response.ok) {

}