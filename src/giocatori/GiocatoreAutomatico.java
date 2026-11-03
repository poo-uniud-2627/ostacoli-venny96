// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
package giocatori;

import java.util.Random;
import pong.Giocatore;
import pong.Mossa;
import pong.Palla;
import pong.Racchetta;

/**
 * La soluzione più semplice della lezione 1: la racchetta insegue la palla.
 * Con i livelli sbaglia: ogni volta che la palla arriva verso di lui, valuta male dove arriverà,
 * di un po' a caso, fino a erroreMassimo pixel; se l'errore è grande, a volte la manca del tutto.
 * Di quanto sbaglia lo decide il livello (il MotoreDeiLivelli).
 */
public class GiocatoreAutomatico implements Giocatore {
    private final double erroreMassimo;
    private final Random caso = new Random();
    private double errore;           // l'errore scelto per la palla che sta arrivando
    private boolean arrivava;        // se al fotogramma prima la palla veniva verso di lui

    /** Il giocatore della lezione 1: non sbaglia mai. */
    public GiocatoreAutomatico() {
        this(0);
    }

    /** Un giocatore che sbaglia fino a erroreMassimo pixel. Pretende: erroreMassimo non è negativo. */
    public GiocatoreAutomatico(double erroreMassimo) {
        this.erroreMassimo = erroreMassimo;
    }

    @Override
    public Mossa decidiMossa(Palla palla, Racchetta racchetta) {
        double dove = palla.getPosizione().y() + erroreDiValutazione(palla, racchetta);
        if (dove < racchetta.getCentroY() - 3) return Mossa.SU;
        if (dove > racchetta.getCentroY() + 3) return Mossa.GIU;
        return Mossa.FERMO;
    }

    /** Quando la palla comincia a venire verso di lui, sceglie a caso di quanto sbagliare per quella palla. */
    private double erroreDiValutazione(Palla palla, Racchetta racchetta) {
        boolean arriva = (racchetta.getX() - palla.getPosizione().x()) * palla.getVelocitaX() > 0;
        if (arriva && !arrivava) {
            errore = (caso.nextDouble() * 2 - 1) * erroreMassimo;
        }
        arrivava = arriva;
        return errore;
    }
}
