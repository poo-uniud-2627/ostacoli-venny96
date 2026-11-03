// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
package ostacoli;

/**
 * Tutto quello che cambia da un livello all'altro: quanto va veloce la palla quando viene lanciata
 * (pixel per fotogramma, in orizzontale), di quanti pixel al massimo sbaglia il giocatore automatico,
 * e gli ostacoli nel campo. È un record: lo crea il MotoreDeiLivelli.
 */
public record Livello(int numero, double velocitaPalla, double erroreMassimo, Ostacoli ostacoli) {
}
