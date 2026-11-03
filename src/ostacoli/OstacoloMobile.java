// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
// Consegna 4: gli ostacoli. Scadenza: domenica 15 novembre 2026.
package ostacoli;

import geometria.Rettangolo;
import java.awt.Color;

/**
 * L'ostacolo mobile: va su e giù fra un limite alto e un limite basso, sempre alla stessa velocità,
 * e ai limiti torna indietro. Non si rompe mai: colpito, non cambia niente.
 *
 * IL PEZZO MANCANTE: i corpi dei metodi segnati «scrivete qui» e i campi che vi servono.
 * I contratti dei metodi sono quelli dell'interfaccia Ostacolo (aprite Ostacolo.java), più quello
 * che dice questo commento e quelli sopra il costruttore e sopra avanza.
 */
public class OstacoloMobile implements Ostacolo {
    public static final Color COLORE = new Color(80, 200, 120);

    private Rettangolo area;   // non è final: l'ostacolo si muove
    // scrivete qui gli altri campi che vi servono

    /**
     * Pretende: area non è null; velocita è maggiore di 0; l'area sta fra i due limiti
     * (limiteAlto <= area.y() e area.y() + area.altezza() <= limiteBasso).
     * Promette: un ostacolo che sta in area e che, al primo avanza, comincia a scendere.
     */
    public OstacoloMobile(Rettangolo area, double velocita, double limiteAlto, double limiteBasso) {
        this.area = area;
        // scrivete qui
    }

    @Override
    public Rettangolo getArea() { return area; }

    /** Non si rompe mai: il colpo non cambia niente (né dove sta, né come si muove). */
    @Override
    public void colpisci(Ostacoli campo) {
        // scrivete qui
    }

    @Override
    public boolean isRotto() {
        return false; // scrivete qui
    }

    /** Promette: COLORE. */
    @Override
    public Color getColore() {
        return null; // scrivete qui
    }

    /**
     * Promette: l'ostacolo si sposta in verticale di velocita pixel nel verso in cui sta andando
     * (all'inizio verso il basso). Se così arriverebbe su un limite o lo supererebbe, si ferma
     * esattamente sul limite e cambia verso: al passo dopo va dall'altra parte.
     * Il bordo alto non va mai sopra limiteAlto, il bordo basso mai sotto limiteBasso
     * (area.y() + area.altezza() <= limiteBasso); x, larghezza e altezza non cambiano.
     */
    @Override
    public void avanza() {
        // scrivete qui
    }
}
