"use strict";
import {byId, toon, verberg, setText} from "./util.js";
byId("zoek").onclick = async function () {
    verbergFilmsEnFouten();
    const zoekIdInput = byId("zoekId");
    if (zoekIdInput.checkValidity()) {
        findById(zoekIdInput.value);
    } else {
        toon("zoekIdFout");
        zoekIdInput.focus();
    }
};
function verbergFilmsEnFouten() {
    verberg("film");
    verberg("zoekIdFout");
    verberg("nietGevonden");
    verberg("storing");
    verberg("verwijder");
}
async function findById(id) {
    const response = await fetch(`films/${id}`);
    if (response.ok) {
        const films = await response.json();
        toon("film");
        toon("verwijder");
        setText("naam", films.titel);
        setText("jaar", films.jaar);
        setText("vrijePlaatsen", films.vrijePlaatsen);

    } else {
        if (response.status === 404) {
            toon("nietGevonden");
        } else {
            toon("storing");
        }
    }

    byId("verwijder").onclick = async function(){
        const zoekIdInput = byId("zoekId");
        const resp = await fetch(`films/${zoekIdInput.value}`, {method:"DELETE"});
        if(resp.ok){
            verbergFilmsEnFouten();
            zoekIdInput.value="";
            zoekIdInput.focus();
        }else{
            toon("storing");
        }
    }
}