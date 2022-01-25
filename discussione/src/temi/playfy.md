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

(suggerimento)=
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

### Album e brani

Implementare i brani (e gli album che li contengono) non è completamente banale,
come anticipato nel [suggerimento implementativo](suggerimento) della traccia.

Dato un brano è necessario poter determinare l'album a cui appartiene. Non solo
per poter distinguere brani di album diversi che abbiano accidentalmente il
medesimo titolo, o per poter aggiungere il titolo dell'album a quello del brano
(emettendo il contenuto delle playlist, come risulta dagli esempi); ma perché ha
poco senso in generale parlare di un brano se non nel contesto dell'album a cui
appartiene.

Per rappresentare questo legame sono possibili due scelte:

- implementare album e brani in classi indipendenti, memorizzando nel brano un
  riferimento all'album a cui appartiene,
- implementare il brano come una classe interna (non statica) dell'album.

Entrambe le scelte richiedono che il legame stabilito tra brano ed album sia
documentato nell'invariante di rappresentazione, costruito e preservato per
tutta la durata di vita delle due entità.

Le due sezioni seguenti discutono molto approfonditamente le caratteristiche
delle due scelte di cui sopra, chi è meno interessato ai dettagli può proseguire
la lettura direttamente con la sezione sull'[implementazione](innerimpl).

#### Classi indipendenti

La prima soluzione può apparire più semplice, nel senso che non richiede
dimestichezza con le classi interne, ma potrebbe nascondere due problemi nel
caso in cui le classi siano, come sarebbe opportuno fossero, immutabili.

Il primo problema è che il costruttore di brano necessita di un album (per poter
definire il valore del riferimento ad esso) e il brano potrebbe avere solo un
costruttore che richieda un elenco di brani; questo renderebbe le due classi non
istanziabili: non ci sarebbe verso di creare un brano prima di un album, o un
album prima di un brano!

A tal problema potrebbe essere posto rimedio facendo in modo che l'album abbia
un costruttore che (come suggerito) invece di un elenco di brani riceva un
elenco di titoli e di durate, provvedendo a costruire i brani al suo interno,
dove gli sarà possibile usare il riferimento `this` come valore da passare al
costruttore di album.

Un esempio di bozza del codice potrebbe essere il seguente

```{code-block} java
public class Brano {
  private final Album album;
  ...
  public Brano(final Album album, final String titolo, final Durata durata) {
    this.album = album;
    ...
  }
  ...
  public Album album() {
    return album;
  }
  ...
}

public class Album {
  private final Brano[] brani;
  ...
  public Album(List<String> titoli, List<Durate> durate, ...) {
    ...
    brani = new Brano[titoli.size()];
    for (int i = 0; i < titoli.size(); i++)
      brani[i] = new Brano(this, titoli[i], durate[i]);
    ...
  }
}
```

Il secondo problema è che il brano, per poter essere istanziato, deve avere
(almeno) un costruttore pubblico il che fa si che non sia possibile, una volta
costruito un album, evitare che siano liberamente creati altri brani che si
riferiscono ad esso (oltre a quelli che contiene). Non è ovvio cioè come
impedire che le classi vengano impiegate come segue

```{code-block} java
Album album = new Album(List.of("Primo", "Secondo"), List.of(new Durata(10), new Durata(29)));
Brano terzo = new Brano(album, "Terzo", new Durata(30));
```

determinando la creazione di un terzo brano che non rappresenterebbe nulla di
sensato.

Mentre è banale per l'invariante di rappresentazione dell'album controllare che
in `brani` ci siano solo quelli il cui attributo `album` sia esso stesso

```{code-block} java
private boolean repOk() { // in Album
  for (final Brano brano : brani)
    if (brano.album() != this) return false;
  ...
}
```

non è però possibile adottare un atteggiamento simile nel brano; se `contiene` è
un metodo dell'album che consente di determinare se un dato brano gli appartiene
(ossia figura tra i valori dell'array `brani`), si potrebbe essere tentati di
scrivere il seguente

```{code-block} java
private boolean repOk() { // in Brano
  if (!album.contains(this)) return false;
  ...
}
```

questo di certo impedirebbe la creazione impropria del "Terzo" brano
nell'esempio precedente, ma finiremmo di nuovo in una condizione di non
istanziabilità: talvolta è necessario creare un brano prima di aggiungerlo ad un
album! Nel costruttore stesso dell'album, l'istruzione

```{code-block} java
brani[i] = new Brano(this, titoli[i], durate[i]);
```

causa l'invocazione del `repOk` di brano (per via dell'istruzione `new`) che
restituisce `false`, dato che al momento della costruzione l'assegnamento
all'elemento dell'array è ancora avvenuto!

#### Il brano interno all'album

Le classi interne (*inner class*) sono lo strumento linguistico offerto da Java
per modellare esattamente la circostanza in cui ci troviamo, ossia di un oggetto
(il brano) che ha senso solo se legato all'istanza di un altro (l'album).

Un esempio di bozza del codice con il brano interno all'album è

```{code-block} java
public class Album {

  public class Brano {
    private Brano(final String titolo, final Durata durata) {
      ...
    }
    ...
    public Album album() {
      return Album.this;
    }
  }
  ...
  private final Brano[] brani;
  ...
  public Album(List<String> titoli, List<Durate> durate) {
    ...
    brani = new Brano[titoli.size()];
    for (int i = 0; i < titoli.size(); i++)
      brani[i] = new Brano(this, titoli[i], durate[i]);
    ...
  }
}
```

La necessità di realizzare il legame tra le istanze di brani e album è risolta
in modo "automatico" dal linguaggio, di conseguenza in un brano è possibile
ottenere il riferimento all'istanza di album che lo racchiude con l'espressione
`Album.this`.

Resta sempre il problema che non è possibile costruire un album se il suo
costruttore richiede che ne siano indicati i brani, che a loro volta non possono
essere costruiti prima di avere una istanza dell'album; per questa ragione, il
costruttore dell'album riceve, come nella scelta precedente, una lista di titoli
e durate.

Con la classe interna è però possibile risolvere il problema della creazione di
ulteriori brani oltre a quelli contenuti nell'album. È sufficiente rendere il
costruttore del brano `private` per far si che esso possa venire invocato
soltanto all'interno dell'album, che provvederà a farlo solo nel modo adatto a
garantire che, una volta creato un brano, esso gli venga aggiunto.

(innerimpl)=
#### L'implementazione del brano

Assumendo quindi di seguire il suggerimento implementativo del tema d'esame,
procediamo con la descrizione della soluzione basata sulla classe interna.

La rappresentazione di un brano è data semplicemente da una stringa (che ne
memorizzi il titolo) e da una durata che essendo immutabili possono essere
lasciate pubbliche, l'invariante si limita a richiedere che non siano `null`, il
titolo non sia vuoto e la durata non sia zero (codice evidenziato)

```{code-cell}
:tags: [remove-input]
sol.show('Album', 'rep', 'ri')
```

Avendo reso pubblici gli attributi non è necessario scrivere metodi
osservazionali che li riguardino, può però avere senso aggiungerne alcuni come:
un metodo che consenta di risalire da un brano all'album che lo contiene

```{code-cell}
:tags: [remove-input]
sol.show('Album', 'outer')
```

uno per sapere se il brano appartiene ad un dato album

```{code-cell}
:tags: [remove-input]
sol.show('Album', 'appartiene')
```

Infine può essere comodo avere un metodo che consenta di ottenere una
rappresentazione come stinga che contenga, facoltativamente, anche l'indicazione
del titolo dell'album (da usare nel `toString` di questa classe e quindi di
quella delle playlist)

```{code-cell}
:tags: [remove-input]
sol.show('Album', 'stringa')
```

Non c'è alcun bisogno di definire i metodi `equals` e `hashCode` per i brani: è
del tutto plausibile ritenere diverse anche due istanze con la stesso titolo e
durata; si pensi ad esempio a un album corrispondente alla registrazione di un
podcast in cui i brani siano una serie di interviste di titolo e durata
differente alternate ad uno "stacchetto" musicale che abbia sempre la stessa
durata e "Intermezzo" per titolo.

#### L'implementazione dell'album

La rappresentazione dell'album, oltre al titolo, deve contenere un elenco
ordinato di brani; dato che il loro numero è fissato ha senso usare un array
(non c'è bisogno di scomodare le liste). L'array non deve contenere duplicati,
ossia due riferimenti identici (la coincidenza dei titoli, come osservato in
precedenza, non è viceversa proibita).

Può essere conveniente precomputare la durata complessiva (che resterà immutata,
dato che l'elenco dei brani è fissato) e immagazzinarla in un attributo; in tal
caso è però necessario specificare nell'invariante di rappresentazione la
coincidenza tra il valore di tale attributo e la somma delle durate degli
elementi dell'array.

Titolo e durata possono essere pubblici (sono infatti immutabili), ma certamente
non può esserlo l'array (per quanto dichiarato `final`): renderlo pubblico
consentirebbe di alterarne il contenuto!

Rappresentazione e costruttore sono quindi dati dal seguente codice

```{code-cell}
:tags: [remove-input]
sol.show('Album', 'repa')
```

si osservi che, per le ragioni illustrate in precedenza, il costruttore non
riceve un elenco di brani, bensì due liste "parallele" di stringhe (i titoli) e
durate; è compito del costruttore controllare che le liste abbiano la stessa
dimensione, non siano vuote e che, una volta che i valori corrispondenti siano
usati per costruire un brano, non venga sollevata una eccezione (che, nel caso,
verrà rilanciata come eccezione del costruttore dell'album).

Se i parametri sono accettati, il costruttore prosegue valorizzando gli
attributi in modo che l'invariante descritto sia verificato; dato che gli
attributi sono immutabili o, nel caso dell'array, non viene mai assegnato altro
valore ad alcuno dei suoi elementi fuori dal costruttore, è ovvio constatare che
esso è sempre preservato.

Due dei metodi osservazionali richiesti dalla traccia sono relativi alla
posizione dei brani

```{code-cell}
:tags: [remove-input]
sol.show('Album', 'pos')
```

Il metodo che consente di determinare la posizione di un brano nell'album
adopera il metodo
[`indexOf`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/List.html#indexOf(java.lang.Object))
della lista ottenuta avvolgendo l'array col metodo
[`Arrays.asList`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#asList(T...)),
ma potrebbe essere parimenti implementato con un ciclo for; l'uso di `indexOf`
si basa sull'identità (non avendo ridefinito i metodi `equals` e `hashCode` in
brano). Si osservi il dettaglio dato dal fatto che le posizioni sono corrette
aggiungendo, o togliendo, 1 (a seconda dei casi) dovuto al fatto che le
posizioni nell'album corrispondono a interi positivi (mentre in generale negli
array a numero non negativi).

Il metodo che consente di rintracciare un brano dato il titolo

```{code-cell}
:tags: [remove-input]
sol.show('Album', 'titolo')
```

è più delicato da specificare: dal momento che sono possibili più brani col
medesimo titolo, occorre specificare cosa accade nel caso di ripetizioni; qui si
è scelto di restituire il primo (nell'ordine in cui compaiono nell'album), ma si
sarebbe potuto scegliere anche di sottospecificare.

Osservate che in nessun caso i metodi che cercano (dato un brano, o un titolo)
sollevano eccezione in caso di fallimento nella ricerca, ma piuttosto
restituiscono un valore convenzionale (0, o `null`); questo è dovuto al fatto
che in genere non costituisce condizione eccezionale cercare un elemento che non
c'è (come si nota da molti metodi analoghi nelle API di Java). In questo modo,
peraltro, le ricerche possono essere convenientemente usate anche per
determinare l'esistenza (di un dato brano, o di un brano di dato titolo).

Diverso il discorso per il metodo che restituisce un brano data la sua
posizione: dal momento che è possibile sapere a priori quali sono i valori
corretti con cui invocarlo, grazie al metodo osservazionale

```{code-cell}
:tags: [remove-input]
sol.show('Album', 'num')
```

tale metodo solleva una `IndexOutOfBoundsException` (conformemente a quel che
farebbe un array o `List`) nel caso la posizione sia un indice che eccede i
limiti legittimi.

Come richiesto, la classe consente di iterare sui suoi brani

```{code-cell}
:tags: [remove-input]
sol.show('Album', 'iter')
```

l'uso di
[`Arrays.asList`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#asList(T...))
consente di adoperare il metodo
[`iterator`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/List.html#iterator())
delle liste invece di scrivere l'iteartore "a mano" (cosa comunque banale);
osservate che l'array non è strutturalmente modificabile, ragione per cui
l'iteratore non espone la rappresentazione (al rischio di modifiche
dall'esterno) perché in caso di invocazione di `remove` solleverà una
`UnsupportedOperationException`.

### Le playlist

Le playlist hanno parecchie somiglianze con gli album, per iniziare sono elenchi
di brani con un titolo e una durata complessiva. Non bisogna però farsi trarre
in inganno:

- gli album sono immutabili, le playlist no;
- gli album contengono sempre almeno un brano, le playlist possono essere vuote
  (o diventarlo);
- tutti i brani di un album sono relativi a quell'album, le playlist viceversa
  in genere contengono brani di album diversi;
- effettuare ricerche per titolo ha senz'altro senso in un album (anche in
  presenza di titoli ripetuti, caso comunque raro), in una playlist invece (dove
  è molto probabile che ci siano titoli ripetuti) la ricerca per titolo ha meno
  senso e dovrebbe quanto meno essere affiancata da quella per titolo ed album;
- nell'emettere nel flusso d'uscita un album non ha senso riportarne il titolo
  per ogni brano, mentre in una playlist è necessario farlo.

Non appare quindi molto semplice raccogliere competenze così dissimili in un
supertipo (ad esempio una classe astratta) che possa essere fruttuosamente
utilizzato per definire album e playlist per estensione; il codice di cui
consentirebbe di evitare la ripetizione molto verosimilmente si limiterebbe a
quello di alcuni metodi osservazionali (che sono in ogni caso di banale implementazione).

A prescindere dalla difficoltà e dall'efficacia (in termini di risparmio di
codice) ottenibile attraverso un supertipo, sarebbe ancor più discutibile la sua
utilità. Nella traccia del progetto non c'è alcuna indicazione del fatto che
potrebbe essere necessario sfruttare il polimorfismo per gestire in modo
omogeneo playlist e brani. Una classe astratta, o interfaccia, non sarebbe
praticamente mai usata come tipo per nessuna delle variabili del progetto!

La rappresentazione di una playlist

```{code-cell}
:tags: [remove-input]
sol.show('Playlist', 'rep')
```

prevede un `nome` (che potrebbe essere cambiato) e un elenco di `brani`, il cui
numero può aumentare, o diminuire, che pertanto è più pratico mantenere in una
lista. Come nel caso dell'album può aver senso memorizzare la `durata`
complessiva in un attributo, li cui valore però non può essere determinato una
volta per tutte, ma andrà mantenuto equivalente alla somma delle durate dei
brani dell'elenco qualora ad esso ne vengano rimossi, o aggiunti. Per questa
ragione, ad eccezione della lista che può essere allocata una volta per tutte,
`nome` e `durata` non possono essere dichiarati `final` (dato che i loro tipi
sono immutabili).

L'invariante di rappresentazione (oltre alle banali richieste circa i `null`,
non ammessi per gli attributi e gli elementi della lista e il nome che non deve
essere vuoto) deve semplicemente garantire che la durata corrisponda alla somma
delle durate. Dato che la classe è mutabile, per ogni metodo mutazionale sarà
necessario riflettere sulla preservazione di tale invariante.

Per quanto riguarda il nome è utile avere la coppia di metodi

```{code-cell}
:tags: [remove-input]
sol.show('Playlist', 'nome')
```

che consentono di conoscerlo, o modificarlo (prestando attenzione a preservare
l'invariante di rappresentazione); il costruttore, peraltro, consiste di fatto
in una invocazione del secondo metodo sopra riportato.

Ci sono poi i metodi relativi ai brani in relazione al loro numero e posizione;
essi sono molti simili a quelli per gli album (e costituiscono l'unica
ripetizione effettiva di codice tra le due classi)

```{code-cell}
:tags: [remove-input]
sol.show('Playlist', 'pos')
```

Dato che la playlist è mutabile occorrono almeno due metodi in grado di
accodare, o rimuovere, un brano dato alla playlist

```{code-cell}
:tags: [remove-input]
sol.show('Playlist', 'addrm')
```

unica cosa degna di nota nelle implementazioni dei due metodi è l'osservazione
che gli aggiornamenti della durata complessiva sono collocati in posizioni del
codice che possono essere raggiunte se e solo se l'aggiunta, o la rimozione,
avvengono effettivamente; questo consente di preservare l'invariante di
rappresentazione. Ovviamente è possibile immaginare una messe di metodi
analoghi, che funzionino anche tenendo conto della posizione, dell'album, o di
combinazioni varie; osserviamo però che i due metodi scelti sono sufficienti a
sviluppare le funzionalità richieste dal resto del progetto e questo basta.

Un metodo di produzione consente di ottenere la fusione tra playlist

```{code-cell}
:tags: [remove-input]
sol.show('Playlist', 'fondi', 'dup')
```

anche in questo caso, l'unica parte degna di nota è quel che accade accodando i
brani della seconda lista, che non devono essere aggiunti se già presenti nella
lista corrente (codice evidenziato); vale la pena osservare l'uso del metodo
`posizione` per determinare se un brano è contenuto nella playlist.




