// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
package ostacoli;

import geometria.Rettangolo;
import java.awt.Color;

/** L'ostacolo fisso: un muretto grigio che non si rompe mai. Colpito, non cambia niente. */
public class OstacoloFisso implements Ostacolo {
    public static final Color GRIGIO = new Color(150, 150, 150);

    private final Rettangolo area;

    /** Pretende: area non è null. */
    public OstacoloFisso(Rettangolo area) {
        this.area = area;
    }

    @Override
    public Rettangolo getArea() { return area; }

    /** Non è mai rotto, quindi non lancia mai l'eccezione; e il colpo non cambia niente. */
    @Override
    public void colpisci(Ostacoli campo) {
        // niente: l'ostacolo fisso non si rompe
    }

    @Override
    public boolean isRotto() { return false; }

    @Override
    public Color getColore() { return GRIGIO; }

    @Override
    public void avanza() {
        // niente: è fermo
    }
}
