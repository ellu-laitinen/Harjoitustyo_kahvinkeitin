package org.example.harjoitustyo_kahvinkeitin;


import java.io.*;

/**
 * Luokka toteuttaa Kahvinkeitin-olion, jolla on kenttinä seuraavia tietoja:
 * kuinka paljon keittimessä on papuja jäljellä
 * mikä on kahvinkeiton tila, eli voiko keittää annetuilla tiedoilla
 * kahvilaadun id
 * valitut vesi- ja kahvimäärät
 *
 */

public class Kahvinkeitin implements Serializable {

    /**
     * keittimessä olevien papujen määrä: desimaaliluku
     */
    private double keittimessaKahvipapuja;

    /**
     * kahvinkeiton tila: merkkijono
     */
    private String tila;

    /**
     * kahvlaadun id: kokonaisluku
     */
    private int id;

    /**
     * käyttäjän valitsema kahvimäärä: desimaaliluku
     */
    double valittuKahvimaara;

    /**
     * käyttäjän valitsema vesimäärä: desimaaliluku
     */
    double valittuVesimaara;

    /**
     * onko kahvista tulossa sopivaa vai ei: Boolean
     */
    Boolean sopiva;

    /**
     * Paluttaa koneessa olebien kahvipapujen määrän
     * @return double keittimessaKahvipapuja
     */

    public double getKahvi() {
        return keittimessaKahvipapuja;
    }

    /**
     * laskee valmiin kahvijuoman vahvuuden annettujen vesi- ja kahvimäärien perusteella
     * @return double valittukahvimaara/valittuvesimaara
     */
    public double laskeKahvinVahvuus() {
        return valittuKahvimaara / valittuVesimaara;
    }

    /**
     * asettaa uuden papumäärän koneeseen
     * @param kahvipapuja double
     */

    public void lisaaKahvipapuja(double kahvipapuja) {
        this.keittimessaKahvipapuja = this.keittimessaKahvipapuja+ kahvipapuja;
    }

    /**
     * asettaa kahvinkeiton tilan tarkistamlla kahvin vahvuuden sekä tarkistamlla papujen määrän
     */
    public void setTila() {
        if (laskeKahvinVahvuus() < 0.7) {
            this.tila = "Kahvista on tulossa liian laihaa! \nLisää kahvin tai vähennä veden määrää";
            this.sopiva = false;
        } else if (laskeKahvinVahvuus() > 1.5) {
            this.tila = "Kahvista on tulossa liian vahvaa! \nLisää veden tai vähennä kahvin määrää";
            this.sopiva = false;
        } else if (valittuKahvimaara > this.keittimessaKahvipapuja) {
            this.tila="Kahvipavut eivät riitä.";
        } else {
            this.tila="Valmis!";
            this.sopiva = true;
            this.keittimessaKahvipapuja = this.keittimessaKahvipapuja - this.valittuKahvimaara;
        }
    }



    /**
     * palautaa kahvinkeiton tilan
     * @return String tila
     */
    public String getTila() {
        return this.tila;
    }

    /**
     * paratetriton alustaja
     */
    public Kahvinkeitin() {
    }

    /**
     * Kahvinkeitinalustaja id- ja papumäärätiedoilla
     * @param id
     * @param keittimessaKahvipapuja
     */
    public Kahvinkeitin(int id, double keittimessaKahvipapuja) {
        this.id = id;
        this.keittimessaKahvipapuja = keittimessaKahvipapuja;

    }

    public static void main(String[] args) {

    }
}