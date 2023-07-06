"use strict";
import {byId, toon, verberg, verwijderChildElementenVan} from "./util.js";

byId("zoek").onclick = async function () {
    verbergjaarEnFouten();
    const jaarInput = byId("jaar");
    if (jaarInput.checkValidity()) {
        findByJaar(jaarInput.value);
    } else {
        toon("jaarFout");
        jaarInput.focus();
    }
};

function verbergjaarEnFouten() {
    verberg("jaarTable");
    verberg("jaarFout");
    verberg("storing");
}

async function findByJaar(jaar) {
    const response = await fetch(`films?jaar=${jaar}`);
    if (response.ok) {
        const jaar = await response.json();
        toon("jaarTable");
        const jaarBody = byId("jaarBody");
        verwijderChildElementenVan(jaarBody);
        for (const eenJaar of jaar) {
            const tr = jaarBody.insertRow();
            tr.insertCell().innerText = eenJaar.id;
            tr.insertCell().innerText = eenJaar.titel;
            tr.insertCell().innerText = eenJaar.jaar;
            tr.insertCell().innerText = eenJaar.vrijePlaatsen;
        }
    } else {
        toon("storing");
    }
}