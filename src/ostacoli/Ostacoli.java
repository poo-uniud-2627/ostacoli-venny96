// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
// Consegna 4: gli ostacoli. Scadenza: domenica 15 novembre 2026. Il pezzo mancante è colpisciAttorno.
package ostacoli;

import geometria.Rettangolo;
import java.util.List;

/** Gli ostacoli del campo: di tipi diversi, trattati tutti allo stesso modo. */
public class Ostacoli {
    private final List<Ostacolo> ostacoli;

    /** Pretende: ostacoli non è null e non contiene null. Il contenitore tiene una sua copia della lista. */
    public Ostacoli(List<Ostacolo> ostacoli) {
        this.ostacoli = List.copyOf(ostacoli);
    }

    /** Tutti gli ostacoli, anche quelli rotti, in una lista che non si può cambiare. */
    public List<Ostacolo> getOstacoli() {
        return ostacoli;
    }

    /** Il primo ostacolo non rotto che tocca l'area; null se non ce n'è nessuno. */
    public Ostacolo ostacoloCheTocca(Rettangolo area) {
        for (Ostacolo o : ostacoli) {
            if (!o.isRotto() && o.getArea().tocca(area)) return o;
        }
        return null;
    }

    /**
     * Un'esplosione: colpisce una volta ogni ostacolo che tocca la zona e che non è rotto.
     * Pretende: zona non è null.
     * Promette: gli ostacoli si guardano uno alla volta, nell'ordine della lista; ciascuno viene
     *           colpito (una volta sola) se, quando arriva il suo turno, tocca la zona e non è rotto.
     *           Così un ostacolo che si è rotto durante l'esplosione, per esempio per un'altra
     *           esplosione a catena, non viene colpito di nuovo.
     */
    public void colpisciAttorno(Rettangolo zona) {
        // scrivete qui
    }

    /** Un passo del gioco per tutti gli ostacoli: quelli che si muovono si spostano. */
    public void avanzaTutti() {
        for (Ostacolo o : ostacoli) {
            o.avanza();
        }
    }
}
