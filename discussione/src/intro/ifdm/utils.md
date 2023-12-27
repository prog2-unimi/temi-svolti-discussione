---
jupytext:
  formats: md:myst
  text_representation:
    extension: .md
    format_name: myst
kernelspec:
  display_name: Java
  language: java
  name: java
---

# Classi e interfacce di utilità

Nelle API di Java, per diversi tipi `T` è comune incontrare una classe di nome
`Ts` (di seguito vedremo ad esempio le classi `Objects`, `Arrays` e
`Collections`). Tali classi (che non possono essere istanziate, avendo solo il
costruttore di default e con visiilità privata), sono dei "contenitori" di
metodi statici di utilità che riguardano il tipo `T`.

Questa sezione presenta un paio di tali classi e alcuni dei loro metodi che
possono risultare molto utili per la prova pratica; inoltre richiama brevemente
le interfacce che definiscono come *comparare* gli oggetti secondo le API di
Java.

## La classe `Objects`

La classe
[`java.util.Objects`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html)
contiene alcuni metodi statici di utilità generale che possono essere adoperati
per tutti gli oggetti, indipendentemente dal loro tipo.

### Sovrascrivere `hashCode`

Nel caso in cui si intendano sovrascrivere i metodi `equals` e `hashCode` di un
oggetto, il metodo [`hash`](
https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html#hash(java.lang.Object...)) (che è  [*variadico*](https://www.wikiwand.com/en/Variadic_function)) può risultare molto comodo.

Se `equals` viene sovrascritto come congiunzione dell'uguaglianza di (un
sottoinsieme degli) attributi dell'oggetto (diciamo `attr_1`, `attr_2`,
`attr_N`), allora `hashCode` può essere sovrascritto come
```{code-block} java
@Override
public int hashCode() {
  return Objects.hash(attr_1, attr_2, attr_N);
}
```
piuttosto che implementando direttamente la ricetta proposta nell'Item 11 del
Capitolo 3 del libro di testo "Effective Java".

### Gestire i `null`

Il metodo
[`requireNonNull`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html#requireNonNull(T,java.lang.String))
consente di verificare se una espressione è `null` e, nel caso, sollevare una
`NullPointerException` col messaggio indicato; ad esempio
```{code-block} java
Objects.requireNonNull(espressione, "Messaggio");
```
può essere usato invece di:
```{code-block} java
if (espressione == null)
  throw new NullPointerException("Messaggio");
```
Dal momento che qualora l'espressione non sia `null` il metodo ne restituisce il
valore, esso può essere convenientemente usato in un assegnamento o invocazione
di metodo; ad esempio
```{code-block} java
variabile = Objects.requireNonNull(espressione, "Messaggio");
```
può essere usato invece di:
```{code-block} java
if (espressione == null)
  throw new NullPointerException("Messaggio");
variabile = espressione;
```
e similmente
```{code-block} java
Objects.requireNonNull(espressione, "Messaggio").metodo();
```
può essere usato invece di:
```{code-block} java
if (espressione == null)
  throw new NullPointerException("Messaggio");
espressione.metodo();
```

Possono risultare comodi anche i metodi statici
[`equals`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html#equals(java.lang.Object,java.lang.Object)),
[`toString`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html#toString(java.lang.Object))
e
[`hashCode`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html#hashCode(java.lang.Object))
che possono essere usati anche su riferimenti `null`; ad esempio
```{code-block} java
String stringa = Objects.toString(oggetto);
boolean uguali = Objects.equals(questo, quello);
int hash = Objects.hashCode(oggetto);
```
possono essere rispettivamente usati invece di
```{code-block} java
String stringa = oggetto == null ? "null" : oggetto.toString();
boolean uguali = questo == null ? quello == null : questo.equals(quello);
int hash = oggetto == null ? 0 : oggetto.hashCode();
```

### Controllare indici e intervalli

In molte circostanze può capitare di dover controllare se un indice (o un
intervallo di indici interi, che può essere specificato dandone gli estremi,
oppure l'estremo sinistro e la dimensione) è contenuto in un segmento iniziale
dei numeri naturali (specificato tramite la sua dimensione).

Il metodo
[`checkIndex`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html#checkIndex(int,int))
e le sue varianti possono essere comodamente utilizzati a tale scopo: nel caso
la condizione sia soddisfatta, essi restituiscono il valore dell'indice (o il
limite inferiore dell'intervallo), viceversa sollevano una
`IndexOutOfBoundException`.

## Le interfacce `Comparable` e `Comparator`

Se si è interessati a definire una [*relazione
d'ordine*](https://www.wikiwand.com/it/Relazione_d%27ordine) sugli oggetti di
una certo tipo sono possibili due strategie:

* se gli oggetti sono dotati di un ordinamento *naturale*, generalmente si
  rendono *comparabili* facendo in modo che il loro tipo lo realizzi
  implementando l'interfaccia
  [`Comparable`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Comparable.html),
* se viceversa si vogliono tenere in considerazione più ordinamenti, si ricorre
  di volta in volta ad un *comparatore* diverso, ottenuto implementando
  opportunamente l'interfaccia
  [`Comparator`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Comparator.html).

Le due interfacce descritte prescrivono rispettivamente l'implementazione di un
metodo `compareTo` (che compara l'oggetto corrente con un altro oggetto del
medesimo tipo), o di un metodo `compare` (che compara due oggetti dello stesso
tipo tra loro).

Una discussione esaustiva di queste interfacce esula dagli scopi di questo
documento, chi volesse approfondire è invitato a consultare la documentazione
delle API e a leggere l'Item 14 del Capitolo 3 del libro di testo "Effective
Java".

Può essere però utile richiamare alcuni metodi (di default e statici) di
`Comparator` che consentono di ottenere dei comparatori d'uso comune:

* il metodo di default
  [`reversed`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Comparator.html#reversed())
  che consente di ottenere il comparatore corrispondente all'ordine inverso;
* i metodi statici
  [`naturalOrder`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Comparator.html#naturalOrder())
  e
  [`reverseOrder`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Comparator.html#reverseOrder())
  che restituiscono rispettivamente i comparatori dell'ordine naturale e del suo
  inverso;
* i metodi statici
  [`nullsFirst`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Comparator.html#nullsFirst(java.util.Comparator))
  e
  [`nullsLast`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Comparator.html#nullsLast(java.util.Comparator))
  che restituiscono i comparatori ottenuti dal comparatore specificato che, in
  aggiunta, considerano i riferimenti `null` rispettivamente minori o maggiori
  di ogni altro valore.

Osservate che i metodi relativi all'ordine naturale non hanno argomento, devono
pertanto inferire il tipo del comparatore da restituire o dal contesto, come ad
esempio in
```{code-cell}
Comparator<Integer> DA_GRANDE_A_PICCOLO = Comparator.reverseOrder();
DA_GRANDE_A_PICCOLO.compare(1, 2)
```
dove il tipo è dedotto da quello deella variabile a cui assegnare il risultato,
oppure da uno *hint*, come in
```{code-cell}
Comparator.<Integer>reverseOrder().compare(2, 1)
```

### Un esempio di uso

Si consideri una classe che rappresenti un orario della mattina (a prescindere
dall'opportunità di sviluppare un tipo del genere, accennato qui a solo a titolo
esemplificativo). Una implementazione minimale di tale tipo è data da
```{code-cell}
class OrarioMattina implements Comparable<OrarioMattina> {
  private static final String[] NUMERO_A_PAROLE = {"mezzanotte", "una", "due", "tre", "quattro", "cinque", "sei", "sette", "otto", "nove", "dieci", "undici", "dodici"};
  public final int ore, minuti;
  public OrarioMattina(final int minutiDaMezzanotte) {
    if (minutiDaMezzanotte < 0 || minutiDaMezzanotte >= 12 * 60 ) throw new IllegalArgumentException();
    ore = minutiDaMezzanotte / 60;
    minuti = minutiDaMezzanotte % 60;
  }
  public String ore() {
    return NUMERO_A_PAROLE[ore];
  }
  @Override
  public String toString() {
    if (minuti == 0) return ore();
    return ore() + " e " + minuti;
  }
  @Override
  public int compareTo(OrarioMattina altro) {
    int result = Integer.compare(ore, altro.ore);
    if (result == 0) result = Integer.compare(minuti, altro.minuti);
    return result;
  }
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof OrarioMattina)) return false;
    final OrarioMattina altro = (OrarioMattina)obj;
    return ore == altro.ore && minuti == altro.minuti;
  }
  @Override
  public int hashCode() {
    return Objects.hash(ore, minuti);
  }
}
```
Il `toString` della classe indca le ore in parole, seguite dai minuti (se non
pari a 0). Gli oggetti della classe sono *comparabili* secondo l'ordine naturale
dello scorrere del tempo; si osservi che (come da specifiche dell'interfaccia)
l'implementazione di `compareTo` porta con se la necessità di implementare
`equals` e `hashCode` in modo coerente.

Dati due orari di questo tipo
```{code-cell}
OrarioMattina
  colazione = new OrarioMattina(8 * 60 + 30),
  merenda = new OrarioMattina(10 * 60);
```
è possibile confrontarli secondo l'ordine naturale come segue
```{code-cell}
colazione.compareTo(merenda) < 0
```
con l'atteso risultato che la colazione si fa prima della merenda.

Se ora volessimo confrontarli in base all'ordine lessicografico delle loro
rappresentazioni testuali potremmo definire (qui facendo uso di una [*classe
anonima*](https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html))
il *comparatore*
```{code-cell}
final Comparator<OrarioMattina> LESSICOGRAFICO_ORE = new Comparator<>() {
    @Override
    public int compare(OrarioMattina primo, OrarioMattina secondo) {
      return primo.ore().compareTo(secondo.ore());
    }
  };
```
secondo quest'ultimo, l'ordine tra i due orari scelti in precedenza si ribalta
```{code-cell}
LESSICOGRAFICO_ORE.compare(colazione, merenda) > 0
```
in quanto "sette" viene lessicograficamente dopo "dieci" (dato che la "s" è dopo
la "d" nell'ordine alfabetico).

## La classe `Arrays`

La classe
[`java.util.Arrays`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html)
contiene alcuni metodi statici di utilità generale che riguardano gli array.

Per i metodi illustrati di seguito sono state scelte le segnature con argomenti
di tipo *generico* o `Object`, osservate però che di ciascuno di essi esiste
(per ragioni di semplicità ed efficienza) una versione sovraccaricata per
ciascun *tipo primitivo* di argomento (come è ovvio sarà il compilatore a
scegliere la segnatura adatta di volta in volta, sulla scorta del tipo apparente
degli argomenti).

### Il metodo `toString`

Può capitare molte volte di dover emettere il contenuto di un array sotto forma
di stringa, purtroppo l'implementazione del metodo `toString` di `Object`
ereditata dagli array non è particolarmente leggibile; è però possibile usare il
metodo
[`toString`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#toString(java.lang.Object%5B%5D))
di questa classe per ottenere una rappresentazione molto semplice; ad esempio
```{code-cell}
int[] arr = new int[] {1, 2, 3, 4};
Arrays.toString(arr) + " è più leggibile di " + arr
```

### Riempire o copiare

Usando il metodo [`fill`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#fill(java.lang.Object%5B%5D,int,int,java.lang.Object)) è possibile riempire (un segmento) di un array con un valore di *default*; ad esempio
```{code-cell}
String[] slot = new String[6];
Arrays.fill(slot, 0, 3, "primi tre");
Arrays.fill(slot, 4, 6, "ultimi due");
Arrays.toString(slot)
```

Esistono diversi metodi per ottenere una copia di un array (non tutti realizzati
tramite la classe `Arrays`); è necessario ricordare che se gli elementi
dell'array non sono di tipo primitivo, tutti effettuano una [*copia per
indirizzo*](https://www.wikiwand.com/it/Copia_di_un_oggetto), ossia copiano solo
i riferimenti degli elementi dall'array d'origine a quello copiato, senza però
copiare gli elementi stessi. Questo significa, tra l'altro, che se gli elementi
sono di tipo *mutabile* attraverso la copia dell'array è possibile modificare
gli elementi dell'array di origine (ovviamente a prescindere dal fatto che i
riferimenti in cui sono memorizzati gli array siano dichiarati come `final`!).

Il primo modo di ottenere una copia è data dal metodo
[`copyOf`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#copyOf(T%5B%5D,int));
osservate che tale metodo può produrre una copia con un numero di elementi
maggiore di quello dell'originale (popolando con `null` le posizioni
aggiuntive). Questo può essere molto utile nel caso in cui, memorizzando valori
in un array, si stia per eccederne la dimensione: sarà sufficiente copiarlo in
uno di dimensione doppia e quindi procedere. Di ciascun metodo esistono anche
delle versioni sovraccaricate senza i limiti del segmento (che vengono assunti
coincidere con l'inizio e la fine dell'array). Con il metodo
[`copyOfRange`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#copyOfRange(T%5B%5D,int,int))
è invece possibile ottenere una copia di (un segmento) di un array; ad esempio
```{code-cell}
String[] subslot = Arrays.copyOfRange(slot, 2, 5);
Arrays.toString(subslot)
```

Una delle versioni sovraccaricate di
[`copyOf`](https://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#copyOf(U%5B%5D,%20int,%20java.lang.Class))
può essere usata per effettuare una sorta di "casting" tra array i cui elementi
siano l'uno il sottotipo dell'altro. Come è ben noto, anche se `S` è sottotipo
di `T` ed è certo che gli elementi di un array `t` di tipo `T[]` siano tutti di
tipo concreto `S`, non è possibile effettuare il cast di tale array come
`(S[])t`; per fare un esempio
```{code-cell}
Number[] numeri = new Number[] {1, 2, 3};
try {
  Integer[] interi = (Integer[])numeri;
} catch (ClassCastException e) {
  System.err.println(e);
}
```
solleva una eccezione: evidentemente, il cast non può avvenire solo sul
riferimento, perché la cosa avesse senso, dovrebbe venir applicato anche
elemento per elemento (ad esempio con un ciclo); ma si può anche usare `copyOf`
nella versione che accetta una istanza di
[`Class`](https://docs.oracle.com/javase/7/docs/api/java/lang/Class.html) per
determinare il tipo degli elementi
```{code-cell}
Integer[] interi = Arrays.copyOf(numeri, numeri.length, Integer[].class);
Arrays.toString(interi)
```

Per concludere, vale la pena ricordare anche due modi di ottenere una copia
indipendenti dalla classe `Arrays`.

Il più elementare è usare il metodo `clone` dell'array stesso. Il secondo è
usare il metodo statico
[`arraycopy`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/System.html#arraycopy(java.lang.Object,int,java.lang.Object,int,int))
della classe `System`. Questo metodo invece di restituire la copia in nuovo
array, copia i riferimenti da un array sorgente ad uno destinazione (che deve
essere già allocato e della dimensione opportuna); ad esempio
```{code-cell}
int[] positivi = new int[] {1, 2, 3, 4, 5, 6, 7};
int[] negativi = new int[] {-1, -2, -3, -4, -5, -6, -7};
System.arraycopy(positivi, 2, negativi, 1, 3);
```
ha l'effetto di copiare 3 elementi dalla posizione 2 di `positivi` alla posizione 1 di `negativi`
```{code-cell}
Arrays.toString(negativi)
```

#### Adattare la dimensione di un array

Vogliamo raccogliere in un array di `long` di nome `pows` gli elementi
dell'insieme $\{n < 10^{12} | n = 2^{2k}, k \geq 0 \}$; supponendo di non
conoscere a priori la cardinalità dell'insieme, possiamo riempire iterativamente
l'array, inizialmente di dimensione 1, raddoppiandone la dimensione ogni volta
che il numero `i` di potenze individuate ne uguaglia la lunghezza
```{code-cell}
long[] pows = new long[1];
long n = 0;
int i = 0, k = 0;
while ((n = (long)Math.pow(2, 2 * k++)) < 1_000_000_000_000L) {
  if (i == pows.length) pows = Arrays.copyOf(pows, pows.length * 2);
    pows[i++] = n;
}
pows = Arrays.copyOf(pows, i);
Arrays.toString(pows)
```
Osservate come, oltre al modo comodo di scrivere la costante $10^{12}$ come
`1_000_000_000_000L` interponendo per leggibilità il separatore `_`, al termine
del riempimento, si possono eliminare le posizioni rimaste vuote dell'array
copiandolo in uno di dimensione esattamentepari al numero totale di potenze
individuate.

### Confrontare

Il metodo
[`equals`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#equals(java.lang.Object%5B%5D,java.lang.Object%5B%5D))
può essere usato per decidere se due array contengono lo stesso numero di
elementi e tutti gli elementi in posizione corrispondente risultano uguali
(secondo il metodo `equals` del tipo degli element dell'array, o la comparazione
con `==` nel caso di tipi primitivi).

In modo analogo, per i tipi primitivi e quelli che sono *comparabili* il metodo
[`compare`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#compare(T%5B%5D,T%5B%5D))
permette di determinare l'[*ordine
lessicografico*](https://www.wikiwand.com/it/Ordine_lessicografico) tra i due
array, basandosi sull'ordine naturale degli elementi. Nel caso in cui gli
elementi non siano *comparabili* (o si voglia utilizzare un ordine diverso da
quello naturale), esiste una versione sovraccaricata del metodo
[`compare`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#compare(T%5B%5D,T%5B%5D,java.util.Comparator))
che accetta un
[`Comparator`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Comparator.html)
come argomento.

(oec-array)=
### Ordinare e cercare

Dato un vettore, è possibile ordinarlo [*in loco*](https://www.wikiwand.com/it/Algoritmo_in_loco) secondo l'*ordine naturale* dei suoi elementi tramite il metodo [`sort`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#sort(java.lang.Object[])), oppure specificando esplicitamente un *comparatore*  con la versoine sovraccaricata [`sort`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#sort(T[],java.util.Comparator)).

Riutilizzando la classe `OrarioMattina` della sezione precedente, ad esempio
```{code-cell}
OrarioMattina[] orari = new OrarioMattina[] {colazione, merenda, new OrarioMattina(5 * 60 + 10)};
Arrays.sort(orari);
Arrays.toString(orari)
```
permette di ordinare gli orari secondo l'ordine naturale, mentre
```{code-cell}
Arrays.sort(orari, LESSICOGRAFICO_ORE);
Arrays.toString(orari)
```
li ordina secondo l'ordine lessicografico dell'ora (in parole). La versione in
cui è possibile specificare il comparatore può essere utile per invertire
l'orine; ad esempio
```{code-cell}
Arrays.sort(orari, LESSICOGRAFICO_ORE.reversed());
Arrays.toString(orari)
```
oppure, basandosi sull'ordine naturale,
```{code-cell}
Arrays.sort(orari, Comparator.reverseOrder());
Arrays.toString(orari)
```

Dato un vettore ordinato, è possibile cercare la posizione di un elemento nel
vettore (o scoprire se non è contenuto nel vettore), usando la [*ricerca
dicotomica*](https://www.wikiwand.com/it/Ricerca_dicotomica), tramite il metodo
[`binarySearch`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#binarySearch(java.lang.Object[],java.lang.Object))
che si basa sull'ordine naturale, o la versione sovraccaricata di
[`binarySearch`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#binarySearch(T[],T,java.util.Comparator))
che consente di specificare un comparatore (che, evidentemente, deve essere il
medesimo che era stat usato per ordinare l'array prima della ricerca).

#### Un esempio di ricerca e inserimento

Come esempio della ricerca, consideriamo l'array `cifreOrdinate` che contenga le
parole corrispondenti alle cifre decimali ordinato lessicograficamente, ottenuto
come
```{code-cell}
String[] cifreInParole = new String[] {"zero", "un", "due", "quattro", "cinque", "sei", "sette", "otto", "nove" };
String[] cifreInParoleOrdinate = cifreInParole.clone();
Arrays.sort(cifreInParoleOrdinate);
Arrays.toString(cifreInParoleOrdinate)
```
Usiamo la ricerca per ottenere l'inversa della funzione che mappa `i` in
`cifreInParoleOrdinate[i]`, ossia la funzione `cifraAPosizione` che mappa la
parola `cifra` corrispondente a una cifra in parole nell'indice `i` tale che
`cifreInParoleOrdinate[i].equals(cifra)` sia vero
```{code-cell}
static final int cifraAPosizione(final String cifra) {
  int valore = Arrays.binarySearch(cifreInParoleOrdinate, cifra);
  if (valore < 0) throw new IllegalArgumentException();
  return valore;
}
```
che si comporta come atteso
```{code-cell}
cifraAPosizione("zero")
```
dato che "z" è certamente l'ultima lettera dell'alfabeto.

Come è facile accorgersi, ci siamo scordati del tre, cercandolo infatti
otteniamo un valore negativo dell'indice!
```{code-cell}
int idx = Arrays.binarySearch(cifreInParoleOrdinate, "tre");
idx
```
Secondo il contratto del metodo di ricerca, un risultato negativo non solo
indica che l'elemento non è stato trovato, ma suggerisce la posizione `pos` dove
dovrebbe venir inserito nell'array secondo la formula `idx = -pos - 1` (che
ovviamente rende `idx` sempre negativo); usando questa informazione è possibile
sistemare la mancanza
```{code-cell}
int pos = -idx - 1;
pos
```
A questo punto è sufficiente allocare un nuovo array `corretto` con una
posizione in più, copiare dall'array `cifreInParoleOrdinate` le posizioni fino a
`pos` esclusa, aggiungere in tale posizione di `corretto` la stringa `"tre"` e
quindi copiare le rimanenti `cifreInParoleOrdinate.length - pos` posizioni da
`cifreInParoleOrdinate` in `corretto` a partire da `pos + 1`
```{code-cell}
String[] corretto = new String[cifreInParoleOrdinate.length + 1];
System.arraycopy(cifreInParoleOrdinate, 0, corretto, 0, pos);
corretto[pos] = "tre";
System.arraycopy(cifreInParoleOrdinate, pos, corretto, pos + 1, cifreInParoleOrdinate.length - pos);
Arrays.toString(corretto)
```

:::{note}
A maggior conferma del fatto che nessuna delle conoscenze di questo documento è
strettamente necessaria al superamento della prova pratica, quanto mostrato sin
qui consente di costruire e mantenere un array (eventualmente ordinato) di
dimensione adattabile facendo uso soltanto di concetti elementari (appresi
dall'insegnamento di "Programmazione" del primo anno) che, peraltro, possono
essere molto facilmente implementati anche usando esclusivamente array e cicli
`for`.

Con un array di dimensione adattabile è molto semplice implementare buona parte
dei comportamenti delle collezioni che saranno illustrate nella seguente sezione
(può essere un ottimo esercizio provare a farlo!). Ai fini del superamento della
prova pratica, le *liste* possono essere banalmente sostituite da un array di
dimensione adattabile, così come lo possono gli *insiemi* (e sufficiente
prestare attenzione ai duplicati); persino le *mappe* possono essere
implementate senza scomodare nessuna struttura dati tra quelle più sofisticate
(incontrate nell'insegnamento di "Algoritmi e strutture dati" del secondo anno),
ma usando semplicemente due array "paralleli"; tale implementazione può essere
persino resa ragionevolmente efficiente se le chiavi sono comparabili!
:::

## Concatenare stringhe

Le stringhe in Java sono immutabili, questo comporta che se è necessario
concatenarne un numero variabile occorre prestare attenzione ai risultati
intermedi. Il codice
```{code-cell}
String[] parti = {"queste", "stringhe", "vengono", "concatenate", "in", "modo", "inefficiente"};
String risultato = "";
for (int i = 0; i < parti.length - 1; i++) risultato += parti[i] + " ";
risultato += parti[parti.length - 1];
risultato
```
produrrà, ad ogni iterazione, un prefisso del risultato finale che resterà nello
heap finché il *garbage collector* non lo eliminerà (in quanto non riferito da
alcuna variabile). Attenzione che questo non vale se il numero di stringhe è
fisso e noto a priori; il codice 
```{code-cell}
String risultato = "queste" + " " + "stringhe" + " " + "vengono" + " " + "concatenate" + " " + "in" + " " + "modo" + " " + "corretto!";
risultato
```
non presenta alcun problema di efficienza perché non produce alcun risultato
intermedio (di cui doversi liberare).

Le API mettono a disposizione tre modi (di complessità e versatilità crescente)
per ottenere la concatenazione di un numero variabile di stringhe.

### Usando un metodo di `String`

Il primo è il metodo
[`String.join`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#join(java.lang.CharSequence,java.lang.CharSequence...))
,che funziona con un array, o con un numero variabile di argomenti, e la
versione
[`String.join`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#join(java.lang.CharSequence,java.lang.Iterable)),
che funziona con un iterabile. L'esempio precedente si può riscrivere come
```{code-cell}
String risultato = String.join(" ", parti);
risultato
```

### Usando la classe `StringJoiner`

Se si vuole specificare non solo il *separatore* ma anche il *prefisso* e
*suffisso* del risultato è possibile usare la classe
[`StringJoiner`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/StringJoiner.html) come nel seguente esempio
```{code-cell}
StringJoiner sj = new StringJoiner(", ", "<", ">");
for (String parte : parti) sj.add(parte);
sj.toString()
```

### Usando la classe `StringBuilder`

Per finire, volendo avere il controllo completo del processo, ad esempio usando
separatori diversi, è possibile usare la classe
[`StringBuilder`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/StringBuilder.html) come nel seguente esempio
```{code-cell}
String[] separatori = {":", ";", "."};
StringBuilder sb = new StringBuilder();
for (int i = 0; i < parti.length - 1; i++) {
  sb.append(parti[i]).append(separatori[i % separatori.length]).append(" ");
}
sb.append(parti[parti.length - 1]);
sb.toString()
```