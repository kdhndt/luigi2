"use strict";
import {setText, toon} from "./util";

// This could be considered an alternative to Thymeleaf and MVC, we're still showing frontend and using backend from the same application
// But we're using a RestController, meaning others can use the backend too (native mobile phones/tablets, other applications, a different frontend)

const response = await fetch("pizzas/aantal");
// status ok?
if (response.ok) {
    // response body
    const body = await response.text();
    // vul HTML met response body
    setText("aantal", body);
} else {
    // toon de div met de storing id
    toon("storing");
}