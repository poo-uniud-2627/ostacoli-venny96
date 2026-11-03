// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
package ostacoli;

import geometria.Rettangolo;
import java.awt.Color;

/**
 * Un ostacolo nel campo, fra le due racchette: la palla ci rimbalza contro.
 * Ogni tipo di ostacolo è una classe che mantiene questo accordo: la partita, lo schermo e il
 * contenitore Ostacoli usano solo questi metodi, e non sanno di che tipo è l'ostacolo.
 */
public interface Ostacolo {

    /**
     * Il rettangolo occupato dall'ostacolo adesso.
     * Promette: non è null. Per un ostacolo fermo non cambia mai; per uno che si muove cambia nel tempo.
     */
    Rettangolo getArea();

    /**
     * L'ostacolo viene colpito (dalla palla, o da un altro ostacolo che esplode).
     * Il parametro campo serve agli ostacoli che, quando si rompono, fanno qualcosa agli altri
     * ostacoli: agli ostacoli che non lo fanno non serve, ma lo ricevono lo stesso.
     * Pretende: campo è il contenitore di cui l'ostacolo fa parte.
     * Promette: se l'ostacolo era già rotto lancia IllegalStateException e non cambia niente;
     *           altrimenti il colpo conta, in un modo che dipende dal tipo di ostacolo
     *           (un ostacolo può anche non rompersi mai).
     */
    void colpisci(Ostacoli campo);

    /** Vero se l'ostacolo è rotto: non si vede più e la palla ci passa attraverso. Promette: una volta rotto, resta rotto. */
    boolean isRotto();

    /** Il colore con cui disegnarlo adesso. Promette: non è null. */
    Color getColore();

    /**
     * Un passo del gioco: viene chiamato 60 volte al secondo, mentre si gioca.
     * Promette: un ostacolo fermo non fa niente; uno che si muove si sposta.
     */
    void avanza();
}
