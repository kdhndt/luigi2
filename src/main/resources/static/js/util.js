// Dit voorkomt foutieve JavaScript code, geeft bv. meldingen als er undeclared variables zijn, make it easier to write "secure" JavaScript
"use strict";

// Typ export voor een functie die je ook in andere JavaScript sources zal oproepen.
export function byId(id) {
    return document.getElementById(id);
}

export function setText(id, text) {
    byId(id).innerText = text;
}

export function toon(id) {
    byId(id).hidden = false;
}

export function verberg(id) {
    byId(id).hidden = true;
}

export function verwijderChildElementenVan(element) {
    while (element.lastChild !== null) {
        element.lastChild.remove();
    }
}