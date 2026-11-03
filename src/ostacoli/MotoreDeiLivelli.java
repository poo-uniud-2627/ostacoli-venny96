// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
package ostacoli;

import geometria.Rettangolo;
import java.util.ArrayList;
import java.util.List;

/**
 * Il motore dei livelli: dato il numero del livello, decide quanto va veloce la palla, quanto sbaglia
 * il giocatore automatico e quali ostacoli ci sono, e dove. È l'unico posto in cui si scrive
 * new OstacoloFisso, new OstacoloFragile...: tutte le altre classi conoscono solo Ostacolo.
 *
 * Gli ostacoli vanno sempre in coppia: uno e il suo gemello dall'altra parte del centro del campo
 * (come girando il campo di mezzo giro), così il campo è uguale per i due giocatori.
 */
public class MotoreDeiLivelli {
    public static final int NUMERO_LIVELLI = 5;

    /** La palla: 5 pixel per fotogramma al livello 1, mezzo pixel in più a ogni livello. */
    static final double VELOCITA_AL_LIVELLO_1 = 5;
    static final double VELOCITA_IN_PIU_A_OGNI_LIVELLO = 0.5;

    /**
     * Il giocatore automatico: sbaglia fino a 90 pixel al livello 1, 12 in meno a ogni livello.
     * La racchetta è alta 80 e la palla ha raggio 8: un errore oltre 48 pixel vuol dire mancarla.
     */
    static final double ERRORE_AL_LIVELLO_1 = 90;
    static final double ERRORE_IN_MENO_A_OGNI_LIVELLO = 12;

    /**
     * Il livello numero, per un campo largo larghezzaCampo e alto altezzaCampo pixel
     * (gli ostacoli sono pensati per il campo del gioco, 800 x 500).
     * Promette: un livello nuovo, con ostacoli nuovi, mai colpiti.
     * Lancia IllegalArgumentException se numero non è fra 1 e NUMERO_LIVELLI.
     */
    public static Livello livello(int numero, int larghezzaCampo, int altezzaCampo) {
        if (numero < 1 || numero > NUMERO_LIVELLI) {
            throw new IllegalArgumentException("il livello va da 1 a " + NUMERO_LIVELLI + ", invece è " + numero);
        }
        double velocita = VELOCITA_AL_LIVELLO_1 + VELOCITA_IN_PIU_A_OGNI_LIVELLO * (numero - 1);
        double errore = Math.max(0, ERRORE_AL_LIVELLO_1 - ERRORE_IN_MENO_A_OGNI_LIVELLO * (numero - 1));
        return new Livello(numero, velocita, errore, new Ostacoli(ostacoli(numero, larghezzaCampo / 2.0, altezzaCampo / 2.0)));
    }

    /**
     * Gli ostacoli del livello numero, attorno al centro (cx, cy) del campo. Si aggiungono di livello
     * in livello: il livello 1 non ne ha; dal 2 ci sono quelli del 2, dal 3 anche quelli del 3, e così via.
     */
    private static List<Ostacolo> ostacoli(int numero, double cx, double cy) {
        List<Ostacolo> ostacoli = new ArrayList<>();
        if (numero >= 2) {
            // due muretti fissi, lontani dal centro
            ostacoli.add(new OstacoloFisso(attorno(cx, cy, -140, -130, 20, 80)));
            ostacoli.add(new OstacoloFisso(attorno(cx, cy, 140, 130, 20, 80)));
        }
        if (numero >= 3) {
            // quattro fragili da due colpi, uno per ogni quarto del campo
            ostacoli.add(new OstacoloFragile(attorno(cx, cy, -90, -70, 20, 40), 2));
            ostacoli.add(new OstacoloFragile(attorno(cx, cy, 90, 70, 20, 40), 2));
            ostacoli.add(new OstacoloFragile(attorno(cx, cy, -90, 70, 20, 40), 2));
            ostacoli.add(new OstacoloFragile(attorno(cx, cy, 90, -70, 20, 40), 2));
        }
        if (numero >= 4) {
            // altri due fissi vicino alle racchette e due fragili da tre colpi, in alto e in basso
            ostacoli.add(new OstacoloFisso(attorno(cx, cy, -220, 110, 20, 60)));
            ostacoli.add(new OstacoloFisso(attorno(cx, cy, 220, -110, 20, 60)));
            ostacoli.add(new OstacoloFragile(attorno(cx, cy, 0, -190, 60, 20), 3));
            ostacoli.add(new OstacoloFragile(attorno(cx, cy, 0, 190, 60, 20), 3));
            // due mobili vicino al centro: vanno su e giù fra y = 90 e y = 410, a 2 pixel per passo
            // (il secondo parte più in basso del suo gemello, così non vanno su e giù appaiati)
            ostacoli.add(new OstacoloMobile(attorno(cx, cy, -45, 0, 16, 60), 2, cy - 160, cy + 160));
            ostacoli.add(new OstacoloMobile(attorno(cx, cy, 45, 100, 16, 60), 2, cy - 160, cy + 160));
        }
        if (numero >= 5) {
            // due fragili da un colpo davanti alle racchette e due fissi verso i bordi
            ostacoli.add(new OstacoloFragile(attorno(cx, cy, -200, -20, 20, 30), 1));
            ostacoli.add(new OstacoloFragile(attorno(cx, cy, 200, 20, 20, 30), 1));
            ostacoli.add(new OstacoloFisso(attorno(cx, cy, -110, 175, 20, 50)));
            ostacoli.add(new OstacoloFisso(attorno(cx, cy, 110, -175, 20, 50)));
            // due coppie di esplosivi, accanto ai fragili da un colpo: una catena per parte
            ostacoli.add(new OstacoloEsplosivo(attorno(cx, cy, -160, -5, 20, 20)));
            ostacoli.add(new OstacoloEsplosivo(attorno(cx, cy, 160, 5, 20, 20)));
            ostacoli.add(new OstacoloEsplosivo(attorno(cx, cy, -160, 45, 20, 20)));
            ostacoli.add(new OstacoloEsplosivo(attorno(cx, cy, 160, -45, 20, 20)));
        }
        return ostacoli;
    }

    /** Il rettangolo largo larghezza e alto altezza, con il centro spostato di (dx, dy) dal centro (cx, cy) del campo. */
    private static Rettangolo attorno(double cx, double cy, double dx, double dy, double larghezza, double altezza) {
        return new Rettangolo(cx + dx - larghezza / 2, cy + dy - altezza / 2, larghezza, altezza);
    }
}
