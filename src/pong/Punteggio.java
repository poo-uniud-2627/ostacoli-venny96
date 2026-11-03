// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
package pong;

import java.util.ArrayList;
import java.util.List;

/**
 * I conti di una partita: i punti dei due lati, chi ha vinto, la storia dei punti.
 * La Partita gli dice chi ha fatto punto; lo Schermo chiede i punti alla Partita.
 */
public class Punteggio {

    private final int puntiPerVincere;
    private final int scartoMinimo;
    private final List<Lato> storia = new ArrayList<>();
    private int puntiSinistra;
    private int puntiDestra;
    private Lato vincitore;

    /**
     * Una partita nuova, 0 a 0, con la sua regola per vincere.
     * Promette: una partita 0 a 0, non finita, senza vincitore e con la storia vuota.
     * Lancia IllegalArgumentException se puntiPerVincere o scartoMinimo è minore di 1.
     */
    public Punteggio(int puntiPerVincere, int scartoMinimo) {
        if (puntiPerVincere < 1 || scartoMinimo < 1) {
            throw new IllegalArgumentException("puntiPerVincere e scartoMinimo devono essere almeno 1, invece sono "
                    + puntiPerVincere + " e " + scartoMinimo);
        }
        this.puntiPerVincere = puntiPerVincere;
        this.scartoMinimo = scartoMinimo;
    }

    public void segnaPunto(Lato chi) {
        controlla(chi);
        if (isFinita()) return;
        storia.add(chi);
        if (chi == Lato.SINISTRA) puntiSinistra++; else puntiDestra++;
        if (haVinto(chi)) vincitore = chi;
    }

    private static void controlla(Lato chi) {
        if (chi == null) throw new IllegalArgumentException("chi non può essere null");
    }

    private boolean haVinto(Lato chi) {
        int miei = getPunti(chi);
        int altri = getPunti(altro(chi));
        return miei >= puntiPerVincere && miei - altri >= scartoMinimo;
    }

    private static Lato altro(Lato chi) {
        return chi == Lato.SINISTRA ? Lato.DESTRA : Lato.SINISTRA;
    }

    public int getPunti(Lato chi) {
        controlla(chi);
        return chi == Lato.SINISTRA ? puntiSinistra : puntiDestra;
    }

    public boolean isFinita() {
        return vincitore != null;
    }

    public Lato getVincitore() {
        return vincitore;
    }

    public void annullaUltimoPunto() {
        if (storia.isEmpty()) return;
        Lato ultimo = storia.remove(storia.size() - 1);
        if (ultimo == Lato.SINISTRA) puntiSinistra--; else puntiDestra--;
        vincitore = null;
    }

    public List<Lato> getStoria() {
        return List.copyOf(storia);
    }

    public void azzera() {
        storia.clear();
        puntiSinistra = 0;
        puntiDestra = 0;
        vincitore = null;
    }

    /** L'invariante: punti fra 0 e la storia, coerenti con la storia; vincitore se e solo se un lato ha vinto. */
    boolean repOK() {
        int s = 0;
        int d = 0;
        for (Lato l : storia) {
            if (l == null) return false;
            if (l == Lato.SINISTRA) s++; else d++;
        }
        boolean vinceS = s >= puntiPerVincere && s - d >= scartoMinimo;
        boolean vinceD = d >= puntiPerVincere && d - s >= scartoMinimo;
        Lato chiDovrebbeAverVinto = vinceS ? Lato.SINISTRA : vinceD ? Lato.DESTRA : null;
        return puntiPerVincere >= 1 && scartoMinimo >= 1
            && puntiSinistra == s && puntiDestra == d
            && vincitore == chiDovrebbeAverVinto;
    }
}
