// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
package geometria;

/**
 * Un rettangolo con i lati paralleli ai bordi dello schermo: (x, y) è l'angolo in alto a sinistra.
 * È un record: non cambia mai. Spostare un rettangolo vuol dire crearne uno nuovo.
 */
public record Rettangolo(double x, double y, double larghezza, double altezza) {

    /** Vero se questo rettangolo e altro hanno almeno un punto in comune (anche solo un bordo). */
    public boolean tocca(Rettangolo altro) {
        return x <= altro.x + altro.larghezza && altro.x <= x + larghezza
                && y <= altro.y + altro.altezza && altro.y <= y + altezza;
    }

    /** Un rettangolo nuovo, più grande di margine pixel su ogni lato, con lo stesso centro. */
    public Rettangolo allargato(double margine) {
        return new Rettangolo(x - margine, y - margine, larghezza + 2 * margine, altezza + 2 * margine);
    }

    /** Il punto in mezzo al rettangolo. */
    public Posizione centro() {
        return new Posizione(x + larghezza / 2, y + altezza / 2);
    }
}
