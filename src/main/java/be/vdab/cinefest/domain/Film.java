package be.vdab.cinefest.domain;

import java.math.BigDecimal;

public class Film {

    private final long id;
    private final String titel;
    private final int jaar;
    private final long vrijePlaatsen;
    private final BigDecimal aankoopPrijs;

    public Film(long id, String titel, int jaar, long vrijePlaatsen, BigDecimal aankoopPrijs) {
        this.id = id;
        this.titel = titel;
        this.jaar = jaar;
        this.vrijePlaatsen = vrijePlaatsen;
        this.aankoopPrijs = aankoopPrijs;
    }

    public Film(String titel, int jaar, long vrijePlaatsen, BigDecimal aankoopPrijs) {
        this.id= 0;
        this.titel = titel;
        this.jaar = jaar;
        this.vrijePlaatsen = vrijePlaatsen;
        this.aankoopPrijs = aankoopPrijs;
    }

    public long getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public int getJaar() {
        return jaar;
    }

    public long getVrijePlaatsen() {
        return vrijePlaatsen;
    }

    public BigDecimal getAankoopPrijs() {
        return aankoopPrijs;
    }
}
