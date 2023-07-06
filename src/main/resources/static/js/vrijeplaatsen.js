"use strict"

import {setText, toon} from "./util.js";

const resp = await fetch("films/totaalvrijeplaatsen");
if(resp.ok){
    const body = await resp.text();
    setText("totaalVrijePlaatsen", body)
} else {
    toon("storing")
}