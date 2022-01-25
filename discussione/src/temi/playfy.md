---
jupytext:
  formats: md:myst
  text_representation:
    extension: .md
    format_name: myst
kernelspec:
  display_name: Python 3
  language: python
  name: python3
---

```{code-cell}
:tags: [remove-input]
from tslib import Solution
sol = Solution('playfy')
```
# Playfy

:::{warning}
Lo svolgimento di questo tema è ancora in **versione preliminare**, non dovrebbe
contenere errori grossolani e probabilmente non sarà radicalmente modificato, ma
è possibile che in futuro vengano apportate correzioni e piccole revisioni.
:::

## La traccia

Scopo della prova è progettare e implementare una gerarchia di oggetti utili a
rappresentare delle *playlist* contenenti una selezione di *brani* di diversi
*album* musicali.

### Album, brani e durate

Un **album** è un elenco (ordinato, non vuoto e senza ripetizioni) di *brani*
con un *titolo* (non vuoto) e una *durata complessiva*. Un **brano** è
caratterizzato da un *titolo* (non vuoto) e da una *durata* (positiva); album e
brano sono evidentemente immutabili.

Dato un album, è necessario essere in grado di *individuare*:

- un brano, dato il suo titolo;
- un brano, data la sua posizione nell'album;
- la posizione nell'album, dato un brano.

Per rendere più complete le funzionalità dell'album è possibile renderlo in
grado di *enumerare* (con un iteratore) i brani che lo costituiscono.

Le **durate** sono misurate in secondi (e non possono evidentemente essere
negative). Una possibile rappresentazione di una durata è data da una stringa
suddivisa in ore, minuti e secondi, ad esempio, la durata di 7295 secondi
corrisponde alla stringa `2:01:35` e viceversa; per tale ragione potrebbe essere
sensato avere modo di costruire una durata da una stringa del genere, o
viceversa di ottenere la stringa così formata a partire da una durata. Per
finire, può tornare utile essere in grado di effettuare *somme* e *sottrazioni*
(purché il risultato sia positivo) tra durate.

Un esempio di (rappresentazione testuale di) un album è:

    Titolo album: In the Court of the Crimson King
    1 - "21st Century Schizoid Man" (07:24)
    2 - "I Talk to the Wind" (06:04)
    3 - "Epitaph" (08:49)
    4 - "Moonchild" (12:13)
    5 - "The Court of the Crimson King" (09:26)
    Durata totale: 43:56

#### Suggerimento implementativo

Riflettete sul fatto che un brano dipende strettamente dall'album in cui è
contenuto, nel senso ad esempio che brani pur con lo stesso titolo, ma
appartenenti ad album diversi, sono da considerare distinti; per questa ragione
può aver senso implementare il brano come classe interna (*inner class*)
dell'album. In tal caso, occorre però prestare attenzione al costruttore
dell'album: esso non può ricevere un elenco di brani (che possono essere
istanziati solo all'interno dell'album stesso), ma potrebbe piuttosto ricevere,
ad esempio, un elenco di titoli e uno di corrispondenti durate.

### Le Playlist

Una **playlist** è un elenco (ordinato) di *brani* (possibilmente di album
diversi) con un *nome* e una *durata complessiva* (che può essere 0 se la
playlist è vuota). Una playlist è evidentemente mutabile ed è necessario che sia
possibile *aggiungere* e *rimuovere* brani, così come *individuarne* la presenza
e la posizione nella playlist.

Un esempio di (rappresentazione testuale di) una playlist è:

    Nome playlist: Mescolotto
    1 - "Another Brick in the Wall, Part 1" (03:11), (da "The wall")
    2 - "21st Century Schizoid Man" (07:24), (da "In the Court of the Crimson King")
    3 - "Another Brick in the Wall, Part 2" (03:59), (da "The wall")
    4 - "Hey You" (04:40), (da "The wall")
    5 - "Is There Anybody Out There?" (02:44), (da "The wall")
    6 - "The Court of the Crimson King" (09:26), (da "In the Court of the Crimson King")
    7 - "Mother" (05:32), (da "The wall")
    Durata totale: 45:23

Si noti che di ciascun brano è riportato, oltre a titolo e durata, anche l'album
di provenienza.

Per rendere più complete le funzionalità della playlist è possibile renderla in
grado di *enumerare*:

- tutti i suoi brani (con l'indicazione dell'album da cui provengono),
- i suoi brani che appartengono a un dato album,
- gli album dei suoi brani (senza ripetizione).

Dato l'esempio precedente, la seconda enumerazione, riguardo all'album `In the
Court of the Crimson King` deve restituire i brani

    "21st Century Schizoid Man" (07:24)
    "The Court of the Crimson King" (09:26)

mentre la terza enumerazione deve restituire gli album

    The wall
    In the Court of the Crimson King

Inoltre, una playlist deve essere in grado di *fondersi* con un'altra playlist
dando origine a una nuova playlist (con un titolo da specificare) che contenga
tutti i suoi brani (nell'ordine in cui compaiono in essa), seguiti dai brani che
compaiono nell'altra playlist (nell'ordine in cui compaiono nell'altra playlist,
a meno che non siano già comparsi in precedenza nella fusione).

La playlist dell'esempio precedente è data dalla fusione della playlist

    Nome playlist: La mia gioventù
    1 - "Another Brick in the Wall, Part 1" (03:11), (da "The wall")
    2 - "21st Century Schizoid Man" (07:24), (da "In the Court of the Crimson King")
    3 - "Another Brick in the Wall, Part 2" (03:59), (da "The wall")
    4 - "Hey You" (04:40), (da "The wall")
    5 - "Is There Anybody Out There?" (02:44), (da "The wall")
    6 - "The Court of the Crimson King" (09:26), (da "In the Court of the Crimson King")
    Durata totale: 31:24

con la playlist

    Nome playlist: I Pink Floyd
    1 - "Mother" (05:32), (da "The wall")
    2 - "Hey You" (04:40), (da "The wall")
    Durata totale: 10:12

si osservi, infatti, che `Mescolotto` contiene il brano dal titolo `Hey You` una
sola volta (nella posizione in cui compare nelle prima playlist).

## La classe di test

Potete implementare dei test in una o più classi; gli esempi di esecuzione
provvisti assumono che alla fine la soluzione (ossia l'*unica classe che
contiene un metodo `main`*) sia in grado di leggere una sequenza (possibilmente
alternata) di album e playlist ed emettere alcune informazioni di conseguenza
come descritto di seguito.

La classe deve essere in grado di leggere degli album rappresentati come segue:

    ALBUM In the Court of the Crimson King
    7:24 - 21st Century Schizoid Man
    6:04 - I Talk to the Wind
    8:49 - Epitaph
    12:13 - Moonchild
    9:26 - The Court of the Crimson King
    .

si noti che la prima riga inizia con `ALBUM` (seguito dal titolo) e l'ultima
riga è costituita dal solo carattere `.`; di ciascun brano è specificata la
durata ed il titolo (separati dalla prima occorrenza del carattere `-` sulla
linea).

Assumendo che gli album vengano letti e memorizzati in sequenza, la classe deve
essere poi in grado di leggere delle playlist rappresentate come segue:

    PLAYLIST La mia gioventù
    1 3
    2 1
    1 5
    1 14
    1 15
    2 5
    .

si noti che la prima riga inizia con `PLAYLIST` (seguito dal nome) e l'ultima
riga è costituita dal solo carattere `.`; di ciascun brano è specificato l'album
(il primo numero) e la posizione del brano (il secondo numero); album e brani
sono numerati a partire da 1.

La classe di test deve:

- emettere nel flusso d'uscita standard ogni album (non appena ne termina la
  lettura),
- emettere nel flusso d'uscita standard  ogni playlist (non appena ne termina la
  lettura),
- fondere tutte le playlist in una di nome `Fusa` e alla fine dell'input
  emetterla nel flusso d'uscita standard;

al termine dell'esecuzione deve inoltre emettere nel flusso d'uscita standard
tutti gli album della playlist ottenuta per fusione e, per ciascun album, tutti
i brani della playlist che provengono da esso.

Un esempio di output da emettere al termine dell'esecuzione è dato da:

    Nome playlist: Fusa
    1 - "Another Brick in the Wall, Part 1" (03:11), (da "The wall")
    2 - "21st Century Schizoid Man" (07:24), (da "In the Court of the Crimson King")
    3 - "Another Brick in the Wall, Part 2" (03:59), (da "The wall")
    4 - "Hey You" (04:40), (da "The wall")
    5 - "Is There Anybody Out There?" (02:44), (da "The wall")
    6 - "The Court of the Crimson King" (09:26), (da "In the Court of the Crimson King")
    7 - "Mother" (05:32), (da "The wall")
    8 - "Batman" (01:58), (da "Naked City")
    9 - "The Sicilian Clan" (03:27), (da "Naked City")
    10 - "The James Bond Theme" (03:02), (da "Naked City")
    Durata totale: 45:23

    The wall
      "Another Brick in the Wall, Part 1" (03:11)
      "Another Brick in the Wall, Part 2" (03:59)
      "Hey You" (04:40)
      "Is There Anybody Out There?" (02:44)
      "Mother" (05:32)

    In the Court of the Crimson King
      "21st Century Schizoid Man" (07:24)
      "The Court of the Crimson King" (09:26)

    Naked City
      "Batman" (01:58)
      "The Sicilian Clan" (03:27)
      "The James Bond Theme" (03:02)

## La soluzione

### Le durate

Ogni oggetto del tema avrà a che fare con una durata (sia esso un brano, un
album o una playlist) e su di essere sarà necessario fare un po' di conti (ad
esempio sommarle per ottenere le durate totali, o farne la differenza se un
brano verrà rimosso da una playlist). Per tale ragione, la traccia suggerisce di
creare un tipo apposito; dato che le sue istanze (usate all'interno delle altre
classi) saranno presumibilmente passate come parametri e restituite da diversi
metodi, è bene che il tipo sia *immutabile* (per evitare la necessità di
proteggere ogni volta le istanze con delle copie).

Volendo adoperare una delle feature più recenti di Java, il tipo può essere
definito tramite un
[`record`](https://docs.oracle.com/en/java/javase/17/language/records.html); chi
non li avesse studiati, può definire il tipo in modo sostanzialmente equivalente
tramite una classe concreta con un solo attributo dichiarato come `public final
int durata`.

L'invariante di tale rappresentazione è che l'intero sia non negativo, lo zero
on va escluso: servirà per rappresentare la durata delle playlist vuote; dato
che il tipo è immutabile, sarà sufficiente accertarsi che questo sia vero in
costruzione. Osservate che sebbene sarebbe possibile usare anche una stringa di
formato `HH:MM:SS` come rappresentazione, essa sarebbe scomodissima per
effettuare le operazioni di somma e differenza.

Oltre al costruttore (che avrà per parametro il numero di secondi della durata),
è bene avere un metodo statico di *fabbricazione* che costruisca una data a
partire da una stringa nel formato `HH:MM:SS`, `MM:SS` o `SS`. Tale metodo è
banale da implementare: basta dividere la stringa in parti col metodo
[`split`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#split(java.lang.String))
e sommare gli interi corrispondenti alle varie parti (dopo averli moltiplicati
per l'opportuna potenza di 60)

```{code-cell}
:tags: [remove-input]
sol.show('Durata', 'fab', 'conv')
```

D'altro canto è molto ripetitivo controllare che ogni componente dell'orario
sia:

- non vuota,
- traducibile in un intero,
- abbia un valore corretto: sia compresa tra 0 (incluso) e 60 (escluso) per
  minuti e secondi e sia positiva nel caso delle ore;

può essere pertanto comodo un metodo statico di utilità che si accerti di queste
condizioni e segnali, con una opportuna eccezione, il caso in cui siano violate

```{code-cell}
:tags: [remove-input]
sol.show('Durata', 'util')
```

Il chiamante (ossia il metodo di fabbricazione), può far tesoro del messaggio
dell'eccezione sollevata dal metodo di utilità avvolgendone tutte le chiamate in
un unico `try-catch` e risollevando l'eccezione, riusandone il messaggio

```{code-block}
try {
  // chiamate a hoMSS
} catch (IllegalArgumentException e) {
  throw new IllegalArgumentException("Formato della durata invalito. " + e.getMessage());
}
```

I metodi di *produzione* che effettuano le operazioni sono banali, la somma

```{code-cell}
:tags: [remove-input]
sol.show('Durata', 'sum')
```

deve solo controllare il caso di parametro nullo, mentre la differenza

```{code-cell}
:tags: [remove-input]
sol.show('Durata', 'diff')
```

può delegare al costruttore (pur documentandolo) il controllo del fatto che
dalla differenza non risulti una durata negativa.



