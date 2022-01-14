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

# I ferri del mestiere

Questo documento contiene alcuni suggerimenti sull'uso delle [Java Development
Kit API](https://docs.oracle.com/en/java/javase/17/docs/api/), sebbene
**nessuna** delle informazioni contenute in questo documento è *strettamente
necessaria* per il superamento della prova pratica, tuttavia alcuni dei
suggerimenti seguenti possono *rendere molto più rapido* lo sviluppo delle
soluzioni e *aiutare ad evitare errori di programmazione*.

L'esposizione è suddivisa in due parti:

* una presentazione di alcune [classi e interfacce di uso
  pratico](#classi-e-interfacce-di-uso-pratico), in particolare di una serie di
  *metodi statici di utilità* per *oggetti* e *array* e le interfacce per la
  *comparazione*;
* una introduzione al ["Collections Framework"](#il-collections-framework),
  focalizzata in particolare su aspetti pratici inerenti la prova pratica.

:::{admonition} Esempi di codice e output
Gli esempi di codice riportati di seguito con un bordo verde si intendono
*attivi*; dopo alcuni di essi è presente l'*output* che sarebbe prodotto
dall'esecuzione dei medesimi (preceduta dall'esecuzione di tutti gli esempi
attivi che lo precedono, nell'ordine in cui compaiono). Se l'ultima (o unica)
riga di un esempio di codice attivo è una espressione, se il suo valore è
diverso da `null` sarò presente sotto di essa (come se l'ultima espressione
fosse stata avvolta da `System.out.println(…)` e tale codice fosse stato
eseguito).
:::

## Classi e interfacce di uso pratico

### La classe `Objects`

La classe
[`java.util.Objects`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html)
contiene alcuni metodi statici di utilità generale che riguardano tutti gli
oggetti, indipendentemente dal loro tipo.

#### Sovrascrivere `hashCode`

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

#### Gestire i `null`

Il metodo
[`requireNonNull`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html#requireNonNull(T,java.lang.String))
consente di verificare se una espressione è `null` e, nel caso, sollevare una
`NullPointerException` col messaggio indicato; ad esempio:
```{code-block} java
Objects.requireNonNull(espressione, "Messaggio");
```
può essere usato come abbreviazione di:
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
può essere usato come abbreviazione di:
```{code-block} java
if (espressione == null)
  throw new NullPointerException("Messaggio");
variabile = espressione;
```
e similmente
```{code-block} java
Objects.requireNonNull(espressione, "Messaggio").metodo();
```
può essere usato come abbreviazione di:
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
boolean uguali = Objects.euqla(questo, quello);
int hash = Objects.hashCode(oggetto);
```
possono essere rispettivamente usati come abbreviazione di
```{code-block} java
String stringa = oggetto == null ? "null" : oggetto.toString();
boolean uguali = questo == null ? quello == null : questo.equals(quello);
int hash = oggetto == null ? 0 : oggetto.hashCode();
```

#### Controllare indici e intervalli

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

### Le interfacce `Comparable` e `Comparator`

Se si è interessati a definire una [*relazione
d'ordine*](https://www.wikiwand.com/it/Relazione_d%27ordine)sugli oggetti di una
certo tipo sono possibili due strategie:

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

#### Un esempio di uso

Si consideri una classe che rappresenti un orario della mattina (a prescindere
dall'opportunità di sviluppare un tipo del genere, accennato qui a solo a titolo
esemplificativo). Una implementazione minimale di tale tipo è data da
```{code-cell}
class OrarioMattina implements Comparable<OrarioMattina> {
  private static final String[] NUMERO_A_PAROLE = {"mezzanote", "una", "due", "tre", "quattro", "cinque", "sei", "sette", "otto", "nove", "dieci", "undici", "dodici"};
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

### La classe `Arrays`

La classe
[`java.util.Arrays`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html)
contiene alcuni metodi statici di utilità generale che riguardano gli array.

Per i metodi illustrati di seguito sono state scelte le segnature con argomenti
di tipo *generico* o `Object`, osservate però che di ciascuno di essi esiste
(per ragioni di semplicità ed efficienza) una versione sovraccaricata per
ciascun *tipo primitivo* di argomento (come è ovvio sarà il compilatore a
scegliere la segnatura adatta di volta in volta, sulla scorta del tipo apparente
degli argomenti).

#### Il metodo `toString`

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

#### Riempire o copiare

Usando il metodo [`fill`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#fill(java.lang.Object%5B%5D,int,int,java.lang.Object)) è possibile riempire (un segmento) di un array con un valore di *default*; ad esempio
```{code-cell}
String[] slot = new String[6];
Arrays.fill(slot, 0, 3, "primi tre");
Arrays.fill(slot, 4, 6, "ultimi due");
Arrays.toString(slot)
```

Esistono diversi metodi per ottenere una copia di un array (non tutti realizzati
tramite la classe `Arrays`); è necessario ricordare che nel se gli elementi
dell'array non sono di tipo primitivo, tutti effettuano una [*copia per
indirizzo*](https://www.wikiwand.com/it/Copia_di_un_oggetto), ossia copiano solo
i riferimenti degli oggetti dall'array d'origine a quello copiato, senza però
copiare gli oggetti stessi. Questo significa, tra l'altro, che se gli oggetti
sono *mutabili* attraverso la copia dell'array è possibile modificare gli
elementi dell'array di origine (ovviamente a prescindere dal fatto che i
riferimenti in cui sono memorizzati gli array siano dichiarati come `final`!).

Con il metodo
[`copyOfRange`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#copyOfRange(T%5B%5D,int,int))
è invece possibile ottenere una copia di (un segmento) di un array; da esempio
```{code-cell}
String[] subslot = Arrays.copyOfRange(slot, 2, 5);
Arrays.toString(subslot)
```

Il metodo
[`copyOf`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#copyOf(T%5B%5D,int))
può produrre una copia più grande dell'originale (riempiendo di valori nulli le
posizioni aggiuntive). Questo può essere molto utile nel caso in cui,
memorizzando valori in un array, si stia per eccederne la dimensione: sarà
sufficiente copiarlo in uno di dimensione doppia e quindi procedere. Di ciascun
metodo esistono anche delle versioni sovraccaricate senza i limiti del segmento
(che vengono assunti coincidere con l'inizio e la fine dell'array).

Una delle versioni sovraccaricate di
[`copyOf`](https://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#copyOf(U%5B%5D,%20int,%20java.lang.Class))
può essere usata per effettuare una sorta di "casting" tra array i cui elementi
siano l'uno il sottotipo dell'altro. Come è ben noto, anche se `S` è sottotipo
di `T` ed è certo che gli elementi di un array `t` di tipo `T[]` siano in realtà
tutti di tipo `S`, non è possibile effettuare il cast di tale array come
`(S[])t`; per fare un esempio
```{code-cell}
Number[] numeri = new Number[] {1, 2, 3};
try {
  Integer[] interi = (Integer[])numeri;
} catch (ClassCastException e) {
  System.err.println(e);
}
```
evidentemente, il cast non può avvenire solo sul riferimento, dovrebbe essere
applicato anche elemento per elemento (ad esempio con un ciclo); ma si può anche
usare `copyOf` nella versione che accetta una istanza di
[`Class`](https://docs.oracle.com/javase/7/docs/api/java/lang/Class.html) per
determinare il tipo degli elementi
```{code-cell}
Integer[] interi = Arrays.copyOf(numeri, numeri.length, Integer[].class);
Arrays.toString(interi)
```

Per concludere, vale la pena ricordare anche due modi di ottenere una copia indipendenti dalla classe `Arrays`.

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
##### Adattare la dimensione di un array

Si supponga di voler raccogliere in un array i `long` minori di un bilione
(convenientemente espresso con la costante letterale `1_000_000_000_000L`, dove
`_` è adoperato come separatore delle migliaia) che siano potenze di 2 con
esponente pari; supponendo di non conoscerne il numero a priori, è possibile
riempire iterativamente l'array `pows`, inizialmente di dimensione 1,
raddoppiandone la dimensione ogni volta che il numero di potenze individuate ne
uguaglia la lunghezza:
```{code-cell}
long[] pows = new long[1];
long n = 0;
int i = 0, e = 0;
while ((n = (long)Math.pow(2, 2 * e++)) < 1_000_000_000_000L) {
  if (i == pows.length) pows = Arrays.copyOf(pows, pows.length * 2);
    pows[i++] = n;
}
pows = Arrays.copyOf(pows, i);
Arrays.toString(pows)
```
Osservate come, al termine del riempimento, è possibile copiare il risultato in
un array di dimensione pari al numero di potenze individuate.

#### Confrontare

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

#### Ordinare e cercare

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
li ordina secondo l'ordine lessicografico dell'ora (in parole).

Dato un vettore ordinato, è possibile cercare la posizione di un elemento nel
vettore (o scoprire se non è contenuto nel vettore), usando la [*ricerca
dicotomica*](https://www.wikiwand.com/it/Ricerca_dicotomica), tramite il metodo
[`binarySearch`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#binarySearch(java.lang.Object[],java.lang.Object))
che si basa sull'ordine naturale, o la versione sovraccaricata di
[`binarySearch`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#binarySearch(T[],T,java.util.Comparator))
che consente di specificare un comparatore (che, evidentemente, deve essere il
medesimo usato per ordinare l'array).
##### Un esempio di ricerca e inserimento

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
ovviamente rende `idx` sempre negativo); usando questa informazione è possibile sistemare la mancanza
```{code-cell}
int pos = -idx - 1;
pos
```
A questo punto è sufficiente allocare un nuovo array `corretto` con una
posizione in più, copiare dall'array `cifreInParoleOrdinate` errato le posizioni fino a
`pos` esclusa, aggiungere in tale posizione di `corretto` la stringa `"tre"` e
quindi copiare le rimanenti `cifreInParoleOrdinate.length - pos` posizioni da `cifreInParoleOrdinate` a partire da `pos + 1` di `corretto`
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
dimensione adattabile facendo uso soltanto di concetti noti (dagli insegnamenti
di "Programmazione" e "Algoritmi e strutture dati") che, peraltro, possono
essere molto facilmente implementati anche usando esclusivamente array e cicli
`for`.
:::

## Il "Collections Framework"

Questa sezione presenta in modo molto succinto alcune interfacce e classi del
"Collections Framework" con particolare attenzione agli usi delle medesime che
possono risultare utili per la prova pratica.

Come per il caso delle interfacce di comparazione, una discussione esaustiva di
questo argomento esula dagli scopi di questo documento, chi volesse approfondire
è invitato a iniziare la sua esplorazione dalla [documentazione nel
JDK](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/doc-files/coll-index.html)
e il (pur datato) [tutorial
ufficiale](https://docs.oracle.com/javase/tutorial/collections/).

Le *collezioni* che possono rivelarsi utili all'esame sono organizzate in due
famiglie

* i sottotipi dell'interfaccia
  [`Collection`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collection.html), limitatamente a
  [`List`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/List.html),
  [`Set`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Set.html)
  e
  [`SortedSet`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/SortedSet.html);
* i sottotipo delle interfacce
  [`Map`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Map.html)
  e
  [`SortedMap`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/SortedMap.html).

Osservate che in ossequio alla consuetudine comune, come si evince da quanto
sopra, in seguito chiameremo *collezioni* non solo i sottotipi di `Collection`,
ma anche quelli di `Map`.

Per ciascuna di queste interfacce, le API contano diverse implementazioni che si
distinguono tra loro per vari aspetti, per quel che concerne la prova d'esame la
differenza principale riguarda l'efficienza rispetto a determinate operazioni.

Le versioni *ordinate* delle interfacce (quelle il cui nome inizia per
`Sorted`), dipendono dall'*ordine naturale* degli elementi (derivante dal fatto
che siano *comparabili*), oppure da un eventuale altro loro ordinamento,
specificato tramite un *comparatore* alla costruzione della collezione.

Per le *liste* le due implementazioni maggiormente utili sono

* [`ArrayList`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ArrayList.html)
  che è basata su un array e perciò consente un efficiente *accesso causale*, e
* [`LinkedList`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/LinkedList.html)
  che è basata sulle [*liste doppiamente
  concatenate*](https://www.wikiwand.com/it/Lista_concatenata) e perciò consente
  efficienti operazioni di inserimento e cancellazione.

Per *insiemi* e *mappe* le implementazioni maggiormente utili sono

* [`HashSet`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/HashSet.html)
  e
  [`HashMap`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/HashMap.html),
  basate su [*hash table*](https://www.wikiwand.com/it/Hash_table);

* [`TreeSet`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeSet.html)
  e
  [`TreeMap`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeMap.html),
  basate su [*alberi rosso-nero*](https://www.wikiwand.com/it/RB-Albero);

evidentemente le implementazioni basate su *hash table* non sono ordinate e
richiedono che i metodi `equals` e `hashCode` degli elementi siano
opportunamente sovrascritti, similmente le implementazioni basate su *alberi
rosso-nero* sono ordinate e richiedono che gli elementi siano ordinati (nel
senso di cui sopra).

Si rimanda alle conoscenze acquisite dall'insegnamento di "Algoritmi e strutture
dati" per le questioni inerenti l'efficienza delle varie operazioni a seconda
delle implementazioni scelte.

### Immutabilità e viste

Iniziamo con alcune considerazioni di carattere generale, che pongono in
relazione le collezioni con una delle nozioni centrali dell'insegnamento:
l'*immutabilità*.
#### Collezioni non modificabili

Una collezione è *immutabile* se:

* gli elementi che contiene sono a loro volta *immutabili* e
* non può essere *strutturalmente modificata* (non possono essere aggiunti,
  eliminati, o riordinati i suoi elementi).

Riguardo al primo punto, è responsabilità del progettista del tipo degli
elementi decidere se e come renderli immutabili (o se e come "proteggerli",
quando le collezioni entrano a far parte della rappresentazione di un oggetto).

Riguardo al secondo punto, viceversa, esistono varie implementazioni delle
collezioni che garantiscono la [*non
modificabilità*](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collection.html#unmodifiable);
dato che evidentemente non è possibile che non implementino i metodi
*mutazionali* presenti nelle interfacce, la soluzione adottata è che essi,
qualora invocati, sollevino l'eccezione `UnsupportedOperationException`.

Alcune implementazioni non modificabili sono ottenute tramite una [*vista non
modificabile*](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collection.html#unmodview),
ragion per cui nella prossima sezione sarà illustrato il concetto generale di
*vista di una collezione*.

#### Viste

La [*vista di una
collezione*](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collection.html#view)
è una implementazione di una collezione che invece di gestire direttamente la
memorizzazione dei suoi elementi fa uso di una collezione (o array) di appoggio
per immagazzinarli concretamente; le operazioni che non possono essere
implementate direttamente dalla vista sono delegate alla collezione (o array)
d'appoggio. Occorre osservare che, per come sono costruite, i cambiamenti delle
collezioni d'appoggio si riflettono però sempre nella viste!

Le viste non occupano alcuno spazio per memorizzare gli elementi (nemmeno quello
dei riferimenti), quindi hanno un basso costo in termini di spazio; d'altro
canto, la necessità di delegare molti comportamenti alla collezione (o array)
d'appoggio determina un piccolo costo in termini di tempo.

### Copie e viste non modificabili

Ci sono diversi modi di ottenere una collezione non modificabile:

* fabbricandone una ex-novo a partire da un elenco di elementi (o coppie chiave
  e valore) passati come argomento a un opportuno metodo statico, oppure
* fabbricando una *copia* o una *vista* non modificabili di una collezione
  esistente.

Ogni interfaccia contiene una serie di metodi statici `of` (di arietà crescente,
fino a quello variadico) per fabbricare una collezione del suo tipo; ad esempio
```{code-cell}
List<String> lista = List.of("uno", "due", "due");
Set<String> insieme = Set.of("uno", "due", "tre");
Map<String, Integer> mappa = Map.of("uno", 1, "due", 2, "tre", 3);
lista + "; " + insieme + "; " + mappa
```
nel caso delle mappe c'è anche il metodo statico
[`ofEntries`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Map.html#ofEntries(java.util.Map.Entry...))
che può essere comodamente usato importando staticamente `java.util.Map.entry`
```{code-cell}
import static java.util.Map.entry;

Map<String, Integer> altraMappa = Map.ofEntries(
  entry("quattro", 4),
  entry("cinque", 5),
  entry("sei", 6)
);
altraMappa
```

Ogni interfaccia contiene inoltre i metodi `copyOf` e `copyOfRange` per
fabbricare una collezione copiando i riferimenti agli elementi dalla collezione
passata come argomento (similmente al caso degli omonimi metodi della classe
`Arrays` — anche in questo caso, non vengono copiati gli elementi, ma solo i
loro riferimenti); ad esempio
```{code-cell}
List<String> lista = List.of("uno", "due", "due");
Set<String> insieme = Set.of("uno", "due", "tre");
Map<String, Integer> mappa = Map.of("uno", 1, "due", 2, "tre", 3);
lista + "; " + insieme + "; " + mappa
```

:::{hint}
Le collezioni fabbricate con `of` e `copyOf` non possono contenere `null` nel
 senso che se esiste un tale valore tra i riferimenti, verrà sollevata una
 `NullPointerException`; questo può essere molto comodo quando si vuole
 assegnare ad un attributo di una classe una collezione che sia non nulla e non
 contenga elementi nulli; se poi gli elementi sono immutabili, ciò basta per
 garantire l'immutabilità delle collezioni copia.
:::

Per finire, la classe di metodi statici di utilità
[`Collections`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collections.html)
(che incontreremo di nuovo in seguito), contiene i metodi per fabbricare viste
non modificabili delle varie collezioni, essi hanno nome `unmodifiableT` dove
`T` è uno delle possibili interfacce per le collezioni; ad esempio
[`unmodifiableSet`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collections.html#unmodifiableSet(java.util.Set)) consente di ottenere un insieme non modificabile
```{code-cell}
Set<String> mutabile = new HashSet<>();
mutabile.add("primo");
mutabile.add("secondo");
Set<String> immutabile = Collections.unmodifiableSet(mutabile);
try {
  immutabile.add("nuovo");
} catch (UnsupportedOperationException e) {
  System.err.println("Modifica non consentita!");
}
```
mostra come l'invocazione di un metodo mutazionale sulla vista sollevi in
effetti l'eccezione attesa; attenzione però: come illustrato parlando delle
viste, se la collezione sottostante cambia, la modifica si riflette
necessariamente anche nella vista
```{code-cell}
mutabile.add("ultimo");
immutabile
```

:::{hint}
Le viste non modificabili possono essere molto utili nel caso uno degli
attributi di una classe sia una collezione modificabile e si intenda rendere la
classe un *iterabile* degli elementi di tale collezione. Restituire direttamente
l'iteratore ottenuto dalla collezione potrebbe esporre la rappresentazione della
classe (alcuni iteratori implementano il metodo `remove` che consente di
elimianre gli elementi della collezione durante l'iterazione); ciò è evitabile
restituendo invece l'iteratore della vista non modifdicabile. Ad esempio
```{code-block}
class AClass implements Iterable<AType> {
  private final Collection<AType> aModifiableCollection;

  // qui i costruttori e altri metodi di AClass

  @Override
  public Iterator<AType> iterator() {
    return Collections.unmodifiableCollection(aModifiableCollection).iterator();
  }
}
```
:::

### Collezioni costruite da altre collezioni

Talvolta può essere utile costruire una collezione modificabile a partire dagli
elementi contenuti in un'altra collezione. Ci sono due modi molto pratici per
ottenere una tale collezione:

* costruirla tramite un *costruttore copia*,
* costruire una collezione vuota ed aggiungergli tutti gli elementi di quella
  esistente.

Ogni collezione ha un costruttore copia che prende una
[`Collection`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collection.html)
per argomento e costruisce una nuova collezione che contiene un nuovo
riferimento per ciascun elemento (ma non dell'elemento) della collezione da cui
è copiata; ad esempio
```{code-cell}
List<String> lista = new ArrayList<>();
lista.add("uno");
lista.add("due");
lista.add("tre");
List<String> copia = new LinkedList(lista);
lista.add("quattro");
copia.remove("tre");
lista + "; " + copia
```
una diversa strategia è quella di usare il metodo `addAll`, come la precedente
può essere usata anche nel caso in cui la destinazione sia di tipo diverso dalla
sorgente della copia; ad esempio
```{code-cell}
SortedSet<String> vuoto = new TreeSet<>();
vuoto.addAll(lista);
vuoto
```

Come è facile dedurre (anche osservando gli esempi), i costruttori copia possono
essere usati anche per "cambiare" il tipo di una collezione (ad esempio da
*lista* a *insieme*, oppure tra implementazioni diverse dello stesso tipo).

### Array e `Collection`

Come è ovvio attendersi, c'è un notevole legame tra array e collezioni (mappe
escluse).

#### Da array a liste

In un verso, la classe `Arrays` ha il metodo variadico
[`asList`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Arrays.html#asList(T...))
che può essere usato per costruire una lista a partire da un array di
riferimenti (ossia non di tipi primitivi); tale lista si comporta come una vista
non modificabile. Ad esempio
```{code-cell}
String[] mksUnits = new String[] {"metro", "kilo", "secondo"};
List<String> comeLista = Arrays.asList(mksUnits);
comeLista
```
Attenzione che, come è comune nelle viste, se cambia l'array allora cambia la
lista
```{code-cell}
mksUnits[1] = "kilogrammi";
comeLista.get(1)
```

Si osservi per inciso che l'uso delle viste costruite a partire da un array
offre la possibilità di effettuare in modo conveniente tramite il metodo
[`indexOf`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/List.html#indexOf(java.lang.Object))
una [*ricerca sequenziale*](https://www.wikiwand.com/it/Ricerca_sequenziale) tra
i suoi elementi (basata sul loro metodo `equals`).

Rivisitando l'esempio delle cifre, l'inversa della funzione che mappa `i` in
`cifreInParole[i]`, ossia la funzione `cifraAValore` che mappa la parola `cifra`
corrispondente a una cifra in parole nel suo valore `i` è data da
```{code-cell}
static final int cifraAValore(final String cifra) {
  int valore = Arrays.asList(cifreInParole).indexOf(cifra);
  if (valore < 0) throw new IllegalArgumentException();
  return valore;
}
```
che (certamente ad un costo lineare) è però in grado di convertire parole in
valori
```{code-cell}
cifraAValore("due")
```

Occorre prestare però molta attenzione al metodo `asList` nel caso di argomenti
che siano di tipo primitivo, sopratutto array con elementi di tipo primitivo! Se
è evidente che, non potendo istanziare tipi generici con tipi primitivi,
l'invocazione di
```{code-cell}
List<Integer> listaDiInteger = Arrays.asList(1, 2, 3);
listaDiInteger
```
non può che restituire una lista di `Integer`. Osservando che un metodo
variadico può essere equivalentemente invocato oltre che con un elenco di
argomenti con un array di tali elementi, è del tutto atteso che
```{code-cell}
Integer[] arrayDiInteger = new Integer[] {4, 5, 6};
Arrays.asList(arrayDiInteger)
```
produca lo stesso risultato del codice precedente. Il risultato del seguente
codice
```{code-cell}
int[] arrayDiInt = new int[] {4, 5, 6};
Arrays.asList(arrayDiInt)
```
a prima vista non può però che destare un certo stupore se ci si attendeva che
avesse ancora lo stesso comportamento dei casi precedenti. Quel che accade, è
che
* nel primo caso,
  l'[*autoboxing*](https://docs.oracle.com/javase/tutorial/java/data/autoboxing.html)
  fa si che l'invocazione su un elenco di parametri `int` venga di fatto
  indirizzata al metodo in cui il parametro di tipo corrisponde ad `Integer` (di
  arietà 3);
* nel secondo caso, l'array di `Integer` gioca esattamente lo stesso ruolo
  dell'elenco di argomenti (e l'invocazione è indirizzata alla segnatura di
  arietà 1);
* nell'ultimo caso non interviene alcun *autoboxing* e quel che accade è che
  l'invocazione viene indirizzata alla segnatura di arietà 1 costruendo una
  lista di un solo elemento… dato da un array di `int`!

Si può facilmente verificare che tale è il caso con
```{code-cell}
Arrays.asList(arrayDiInt).get(0)[1]
```

#### Da collezioni ad array

Nella direzione opposta, osserviamo che ciascun sottotipo di `Collection` ha un
metodo (ereditato da)
[`toArray`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collection.html#toArray(T[]))
che consente di ottenere un array di riferimenti agli elementi che contiene; la
segnatura del metodo prevede che venga passato come argomento un array (anche
vuoto) del tipo dell'array che si intende ottenere (questo è dovuto ad alcune
particolarità del modo in cui interagiscono array e metodi generici). L'uso di
tale metodo è elementare
```{code-cell}
List<Integer> interi = List.of(1, 2, 3);
Integer[] comeArray = interi.toArray(new Integer[0]);
Arrays.toString(comeArray)
```
Attenzione perché omettendo l'argomento sarà selezionato il metodo
sovraccaricato
[`toArray`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collection.html#toArray())
che restituisce un `Object[]` e non è possibile effettuare alcun cast diretto
che lo renda un array di elementi di tipo diverso, come mostra l'esempio
seguente
```{code-cell}
try {
  Integer[] comeArray = (Integer[])interi.toArray();
} catch (ClassCastException e) {
  System.err.println(e);
}
```

Le mappe (che non sono sottotipi di `Collection`) non hanno un metodo che
consenta di ottenerne direttamente il contenuto sotto forma di array; ogni mappa
però può restituire l'insieme delle sue `Map.Entry` (ossia delle coppie chiave e
valore), da cui può essere quindi un array; ad esempio
```{code-cell}
Map.Entry[] entries = mappa.entrySet().toArray(new Map.Entry[0])
```
tale array però non ha traccia dei parametri con cui era istanziata la mappa
generica; per questa ragione accedere a chiavi e valori richiede dei cast
espliciti (invece di godere delle usuali garanzie offerte dai generici)
```{code-cell}
String chiave = (String)(entries[0].getKey());
Integer valore = (Integer)(entries[0].getValue());
chiave + "; " + valore
```

### La classe `Collections`

Per finire, analogamente al caso di `Objects` e `Arrays`, nella classe
[`Collections`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collections.html)
ci sono una messe di metodi statici di utilità che possono risultare molto
comodi nella prova pratica.