# Consegna 4 — Gli ostacoli di Pong

**Scadenza: domenica 15 novembre 2026, alle 23:59.** Conta com'è il repository a quell'ora: se i test automatici sono verdi, prendete un gettone (e, se è la vostra prima consegna verde, 10 crediti, da spendere alle aste). Dopo la scadenza il repository diventa pubblico e il codice va all'asta del giro 2, che si chiude martedì 17 novembre in serata: al laboratorio 2 (mercoledì 18) chi lo compra dovrà aggiungere al vostro Pong una delle varianti scelte dal voto della classe.

## Cosa c'è qui

È il Pong della lezione 9: `src/pong` il gioco, `src/ostacoli` gli ostacoli, il loro contenitore e il motore dei livelli, `src/geometria` la `Posizione` e il `Rettangolo`, `src/giocatori` il giocatore automatico. Il gioco si lancia da `src/pong/Main.java` («Run» sopra `main`): un tasto da 1 a 5 sceglie il livello, SPAZIO fa partire la palla, le frecce muovono la vostra racchetta (a sinistra).

Due ostacoli ci sono già e funzionano: `OstacoloFisso` e `OstacoloFragile`. Il `MotoreDeiLivelli` mette nei livelli 4 e 5 anche i due ostacoli nuovi, che sono da scrivere: finché non li scrivete stanno fermi, non si rompono e non hanno un colore loro.

La cartella `test` contiene i test ufficiali (`test/ostacoli/`, 35 test: 30 per i pezzi da scrivere, 5 per gli ostacoli già scritti e per il contenitore); `lib` contiene JUnit; `.vscode` le impostazioni per VS Code; `.github` il file che fa partire i test su GitHub.

## Cosa dovete fare

Tre pezzi, nel pacchetto `ostacoli`:

1. **`OstacoloMobile`**: va su e giù in verticale, sempre alla stessa velocità (data al costruttore, in pixel per passo), fra un limite alto e un limite basso dati al costruttore; all'inizio scende; quando arriva su un limite si ferma lì e al passo dopo torna indietro. Si muove solo quando viene chiamato `avanza()` (la partita lo fa 60 volte al secondo, con `avanzaTutti`). Non si rompe mai: colpito, non cambia niente. Il colore è `COLORE`.
2. **`OstacoloEsplosivo`**: si rompe al primo colpo e, quando si rompe, esplode: colpisce una volta ogni ostacolo non rotto che tocca la sua area allargata di `PORTATA` pixel su ogni lato (`area.allargato(PORTATA)`). È fermo. Il colore è `COLORE`, anche da rotto.
3. **Il metodo `colpisciAttorno(Rettangolo zona)` della classe `Ostacoli`**: l'esplosione. Colpisce una volta ogni ostacolo che tocca la zona e che non è rotto quando arriva il suo turno; gli ostacoli si guardano nell'ordine della lista. L'esplosivo lo chiama sul contenitore che riceve: `campo.colpisciAttorno(…)`.

I contratti sono nei commenti: quello dell'interfaccia `Ostacolo` (aprite `Ostacolo.java`: vale per tutti gli ostacoli, anche per i vostri), più quello sopra ogni classe e metodo. Guardate come sono scritti `OstacoloFisso` e `OstacoloFragile`: i vostri due mantengono lo stesso accordo.

Quello che vi serve, e che non avete ancora usato:

- **Se un ostacolo tocca la zona**: `o.getArea().tocca(zona)` (il metodo `tocca` del `Rettangolo`: vero se i due rettangoli hanno almeno un punto in comune, anche solo un bordo). È quello che usa già `ostacoloCheTocca`, nello stesso `Ostacoli`.
- **Il contenitore stesso, dentro un metodo di `Ostacoli`**: `this`. Alla lezione 1 `this.x` era il campo `x` di questo oggetto; `this` da solo è l'oggetto. Per colpire un ostacolo dal contenitore: `o.colpisci(this)`.
- **Spostare il mobile**: il `Rettangolo` è un record e non cambia mai; per spostare l'ostacolo si crea un rettangolo nuovo, con la stessa `x`, larghezza e altezza e la `y` nuova, e lo si mette nel campo `area` (per questo nel mobile `area` non è `final`). Come per la `Posizione` della palla.

Quattro cose a cui pensare:

- **Un esplosivo tocca anche la propria zona**, e due esplosivi vicini si toccano a vicenda. Il primo esplode e colpisce il secondo, che si rompe ed esplode a sua volta… e colpisce il primo? Il contratto di `colpisci` dice che un ostacolo già rotto lancia `IllegalStateException`, e `colpisciAttorno` non colpisce i rotti. Pensate a *quando* l'esplosivo diventa rotto rispetto a quando esplode. Se un test finisce con `StackOverflowError`: ogni chiamata in corso occupa un po' di memoria (la pila delle chiamate: la traccia della lezione 7 è il suo elenco); se `colpisci` e `colpisciAttorno` continuano a chiamarsi a vicenda senza finire mai, la memoria finisce. È questo il caso.
- **Un ostacolo rotto durante l'esplosione.** Mentre `colpisciAttorno` scorre la lista, un'esplosione a catena può rompere un ostacolo che viene più avanti nella lista. Quando arriva il suo turno è già rotto: non va colpito di nuovo (e non basta mettere un `try … catch` che ignora l'eccezione: il contratto dice che non va proprio colpito).
- **Un fragile vicino a due esplosioni** prende un colpo per ciascuna.
- **Il mobile ai limiti.** Se lo spazio che manca al limite non è un multiplo della velocità, l'ultimo passo è più corto: il mobile si ferma esattamente sul limite, non oltre e non prima. Se invece ci arriva giusto giusto, al passo dopo va già nell'altro verso (non resta fermo un passo). Provate a mano, su un foglio, con i numeri dei test `arrivaAlLimite…`.

Potete aggiungere campi e metodi `private` di aiuto, e altre classi nel pacchetto `ostacoli`. Potete anche riscrivere `OstacoloFisso` e `OstacoloFragile` (per esempio dopo la lezione 10, per non ripetere il codice comune agli ostacoli): devono continuare a mantenere il loro contratto, e ci sono test anche per loro. Non cambiate le intestazioni dei metodi `public` (nome, parametri, tipo restituito) né le costanti, non cambiate l'interfaccia `Ostacolo` e non toccate i test: i test ufficiali vengono comunque rimessi a ogni prova su GitHub. Non toccate nemmeno la cartella `.github`: se il file che fa partire i test viene cambiato, i test della consegna non contano.

## Come si prova

**Sul vostro portatile, in VS Code** (lezione 5): aprite la cartella del repository, poi la barra laterale **Testing** (l'icona della provetta): i test di `test/ostacoli` compaiono da soli; il triangolo li lancia, verde o rosso per ciascuno. Il debugger (laboratorio 1) funziona anche qui: «Debug Test» con il tasto destro sul triangolo del test.

**Su GitHub**: a ogni `git push` GitHub lancia i test. Il risultato compare nella pagina del repository, accanto all'ultimo commit (un segno verde o una croce rossa), e nella scheda **Actions**.

**Nei test** trovate quattro cose nuove: `assertThrows(IllegalStateException.class, () -> …)`, verde se il codice dopo la freccia lancia un'eccezione di quel tipo (è il modo di controllare un contratto che promette un'eccezione, come quello di `colpisci` su un ostacolo rotto); `assertDoesNotThrow(() -> …)`, verde se il codice non lancia eccezioni; `assertNotNull(x)`, verde se `x` non è `null`; `cella(riga, colonna)` (in `Celle.java`), l'area di un ostacolo in una griglia con 30 pixel fra l'uno e l'altro, proprio la `PORTATA` dell'esplosione. `OstacoloSpia.java` è un ostacolo finto, scritto per i test: conta quante volte viene colpito, anche da rotto.

**E giocando**: al livello 4 ci sono due mobili verdi vicino al centro, al livello 5 anche quattro esplosivi arancioni, a coppie, accanto ai fragili da un colpo.

## Come si consegna

Con git: `git add`, `git commit -m "…"`, `git push`. Se git non funziona, dalla pagina web del repository: aprite il file, la matita in alto a destra («Edit this file»), incollate il codice, poi «Commit changes».

## L'invito

Il repository vi arriva con un invito di GitHub, per email (o dalla campanella delle notifiche su GitHub). Va accettato entro 7 giorni. Senza invito, scrivete al docente su Teams.
