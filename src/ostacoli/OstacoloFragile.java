// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
package ostacoli;

import geometria.Rettangolo;
import java.awt.Color;

/**
 * L'ostacolo fragile: si rompe dopo un certo numero di colpi, deciso quando lo si crea.
 * A ogni colpo il suo colore si schiarisce, così si vede che sta per rompersi.
 */
public class OstacoloFragile implements Ostacolo {
    /** Il colore di un ostacolo fragile mai colpito. */
    public static final Color COLORE_INTEGRO = new Color(60, 120, 230);

    private final Rettangolo area;
    private final int colpiPerRompersi;
    private int colpiRimasti;

    /** Pretende: area non è null e colpi è almeno 1. Promette: un ostacolo che si rompe dopo colpi colpi. */
    public OstacoloFragile(Rettangolo area, int colpi) {
        this.area = area;
        this.colpiPerRompersi = colpi;
        this.colpiRimasti = colpi;
    }

    @Override
    public Rettangolo getArea() { return area; }

    @Override
    public void colpisci(Ostacoli campo) {
        if (isRotto()) throw new IllegalStateException("l'ostacolo fragile in " + area + " è già rotto");
        colpiRimasti--;
    }

    @Override
    public boolean isRotto() { return colpiRimasti == 0; }

    /** Promette: non è null; a ogni colpo è più chiaro (va verso il bianco). */
    @Override
    public Color getColore() {
        double schiarito = (double) (colpiPerRompersi - colpiRimasti) / colpiPerRompersi;
        return verso(COLORE_INTEGRO, Color.WHITE, schiarito * 0.75);
    }

    @Override
    public void avanza() {
        // niente: è fermo
    }

    /**
     * Quanti colpi mancano perché si rompa: 0 se è rotto. Questo metodo c'è solo nell'ostacolo
     * fragile: non fa parte dell'accordo Ostacolo.
     */
    public int getColpiRimasti() { return colpiRimasti; }

    /** Il colore che sta a quota t (da 0 a 1) fra il colore da e il colore a. */
    private static Color verso(Color da, Color a, double t) {
        return new Color(
                (int) Math.round(da.getRed() + (a.getRed() - da.getRed()) * t),
                (int) Math.round(da.getGreen() + (a.getGreen() - da.getGreen()) * t),
                (int) Math.round(da.getBlue() + (a.getBlue() - da.getBlue()) * t));
    }
}
