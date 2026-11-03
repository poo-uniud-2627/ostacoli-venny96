// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
package pong;

import geometria.Posizione;
import geometria.Rettangolo;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import ostacoli.MotoreDeiLivelli;
import ostacoli.Ostacolo;

/**
 * Mostra la partita: sessanta volte al secondo la fa avanzare di un passo e la ridisegna.
 * Prima di cominciare legge i tasti da 1 a 5 (il livello) e SPAZIO (via!).
 * Non prende nessuna decisione di gioco (quelle sono della Partita e del motore dei livelli).
 */
class Schermo extends JPanel {
    private final Partita partita;
    private final Tastiera tastiera;

    private Schermo(Partita partita, Tastiera tastiera) {
        this.partita = partita;
        this.tastiera = tastiera;
        Campo campo = partita.getCampo();
        setPreferredSize(new Dimension(campo.getLarghezza(), campo.getAltezza()));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(tastiera);
        new Timer(16, e -> passo()).start();
    }

    static void mostra(Partita partita, Tastiera tastiera) {
        JFrame finestra = new JFrame("Pong - POO 2026-27");
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Schermo schermo = new Schermo(partita, tastiera);
        finestra.add(schermo);
        finestra.pack();
        finestra.setResizable(false);
        finestra.setLocationRelativeTo(null);
        finestra.setVisible(true);
        schermo.requestFocusInWindow();
    }

    private void passo() {
        if (!partita.isInCorso()) {
            for (int numero = 1; numero <= MotoreDeiLivelli.NUMERO_LIVELLI; numero++) {
                // il tasto del numero, sopra le lettere o nel tastierino numerico
                if (tastiera.premuto(KeyEvent.VK_0 + numero) || tastiera.premuto(KeyEvent.VK_NUMPAD0 + numero)) {
                    partita.preparaLivello(numero);
                }
            }
            if (tastiera.premuto(KeyEvent.VK_SPACE)) partita.avvia();
        }
        partita.aggiorna();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        disegna((Graphics2D) g, partita);
    }

    /**
     * Disegna la partita su g2. È a parte, e static, perché si possa usare anche senza finestra:
     * la simulazione del docente la usa per disegnare il campo su un'immagine.
     */
    static void disegna(Graphics2D g2, Partita partita) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Campo campo = partita.getCampo();
        int larghezza = campo.getLarghezza();
        int altezza = campo.getAltezza();
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, larghezza, altezza);

        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[]{10, 10}, 0));
        g2.drawLine(larghezza / 2, 0, larghezza / 2, altezza);

        // Lo schermo non sa di che tipo è ogni ostacolo: chiede a ciascuno se è rotto e di che colore è.
        for (Ostacolo o : partita.getOstacoli().getOstacoli()) {
            if (!o.isRotto()) {
                g2.setColor(o.getColore());
                riempi(g2, o.getArea());
            }
        }

        g2.setColor(Color.WHITE);
        disegna(g2, partita.getRacchettaSinistra());
        disegna(g2, partita.getRacchettaDestra());
        Palla palla = partita.getPalla();
        int r = palla.getRaggio();
        Posizione p = palla.getPosizione();
        g2.fillOval((int) Math.round(p.x() - r), (int) Math.round(p.y() - r), 2 * r, 2 * r);

        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
        g2.drawString(String.valueOf(partita.getPunti(Lato.SINISTRA)), larghezza / 2 - 80, 60);
        g2.drawString(String.valueOf(partita.getPunti(Lato.DESTRA)), larghezza / 2 + 50, 60);

        g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        scriviAlCentro(g2, "Livello " + partita.getLivello().numero(), larghezza, altezza - 20);

        if (!partita.isInCorso()) {
            g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
            if (partita.getVincitore() == Lato.SINISTRA) {
                scriviAlCentro(g2, "Hai vinto!", larghezza, altezza / 2 - 40);
            } else if (partita.getVincitore() == Lato.DESTRA) {
                scriviAlCentro(g2, "Ha vinto il computer.", larghezza, altezza / 2 - 40);
            }
            scriviAlCentro(g2, "Premi un tasto da 1 a 5 per scegliere il livello, poi SPAZIO", larghezza, altezza / 2 + 60);
        }
    }

    private static void disegna(Graphics2D g2, Racchetta racchetta) {
        g2.fillRect(racchetta.getX(), (int) Math.round(racchetta.getY()),
                racchetta.getLarghezza(), racchetta.getAltezza());
    }

    private static void riempi(Graphics2D g2, Rettangolo r) {
        g2.fillRect((int) Math.round(r.x()), (int) Math.round(r.y()),
                (int) Math.round(r.larghezza()), (int) Math.round(r.altezza()));
    }

    /** Scrive il testo centrato in orizzontale in un campo largo larghezza, all'altezza y. */
    private static void scriviAlCentro(Graphics2D g2, String testo, int larghezza, int y) {
        g2.drawString(testo, (larghezza - g2.getFontMetrics().stringWidth(testo)) / 2, y);
    }
}
