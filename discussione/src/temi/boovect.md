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
sol = Solution('boolvect')
```
# BoolVect

:::{warning}
Lo svolgimento di questo tema è ancora in **versione preliminare**, non dovrebbe
contenere errori grossolani e probabilmente non sarà radicalmente modificato, ma
è possibile che in futuro vengano apportate correzioni e piccole revisioni.
:::

## La traccia

Scopo della prova è progettare e implementare una gerarchia di oggetti utili a
rappresentare dei *vettori di valori booleani* e un insieme di operazioni che li
coinvolgano.

### I vettori di valori booleani

Un *vettore di valori booleani*, di seguito **BoolVect**, è una sequenza di
*valori di verità* ciascuno dei quali può essere *vero* o *falso*; le posizioni
della sequenza sono indicizzate dai numeri naturali (rappresentabili con un
`int`) e la *dimensione* di un BoolVect è definita come 1 più la posizione più
grande in cui si trova un valore di verità uguale a vero (o 0 se tutti i valori
di verità sono falsi); la *taglia* di un BoolVect è la massima dimensione che
esso può raggiungere e può essere infinita (nel senso che, per convenzione, può
coincidere con `Integer.MAX_VALUE`). Se il numero di valori di verità uguali a
vero è molto inferiore rispetto alla taglia, il BoolVect si dice *sparso*,
viceversa si dice *denso*.

La *rappresentazione testuale* di un BoolVect elenca i suoi valori di verità, da
quello di posizione più grande a quello di posizione 0. Ad esempio, di seguito è
riportato il BoolVect `FFFFVFFVVV` a cui è sovrapposto l'elenco delle posizioni
dei suoi bit:

    posizione 9876543210
     BoolVect FFFFVFFVVV

la dimensione di tale BoolVect è 6, dato che il valore vero di posizione
maggiore (l'unico `V` circondato da due `F`) è in posizione 5.

Dato un BoolVect è possibile *leggere* e *scrivere* il suo *i*-esimo valore di
verità; può essere plausibile che leggere un valore di verità in una posizione
oltre la dimensione del BoolVect restituisca convenzionalmente il valore di
verità falso.

**Nota bene**: implementare correttamente e in modo ragionevolmente efficiente i
metodi `equals` (e quindi `hashCode`) per i BoolVect non è cosa banale,
sopratutto in presenza di una gerarchia di sottotipi; per questa ragione
l'implementazione di tali metodi è del tutto facoltativa.

### Operatori logici e loro estensione ai BoolVect

Ci sono vari modi di definire *operazioni* binarie tra BoolVect; un modo molto
pratico è partire dagli operatori logici binari, come ad esempio gli usuali
*and*, *or* e *xor*, definiti rispettivamente come segue

* il valore di verità di `a & b` è vero se e solo se entrambi i valori di verità
  `a` e `b` sono veri;
* il valore di verità di `a | b` è vero se e solo se almeno uno dei valori di
  verità `a` e `b` è vero;
* il valore di verità di `a ^ b` è vero se e solo se esattamente uno dei valori
  di verità `a` e `b` è vero;

e quindi derivare le operazioni *componente a componente* tra BoolVect
corrispondenti.

Più formalmente l'operatore logico binario `·`, è esteso componente a componente
all'operazione binaria tra BoolVect `̅̅·` tale che l'*i*-esimo valore di verità
di `u ̅· v` è dato dal risultato dell'operatore logico `·` tra l'*i*-esimo
valore di verità di `u` e l'*i*-esimo di `v`.

**Nota bene**: osservi che non è necessario che i due argomenti di una
operazione binaria tra BoolVect abbiano la medesima dimensione; d'altro canto, a
seconda del tipo di operazione binaria, potrebbe essere dimostrabile che la
taglia, o dimensione del risultato, siano in qualche modo controllate da quelle
degli operandi (se questo fosse necessario per la correttezza dei metodi che
implementerà, non scordi di annotarlo esplicitamente nella documentazione dei
medesimi).

### Cosa è necessario implementare

Dovrà riflettere sulla mutabilità dei BoolVect e quindi specificare con le
opportune segnature e comportamenti le varie operazioni (con particolare
riferimento al ruolo di dimensione e taglia) e dovrà quindi realizzare *almeno
due implementazioni distinte* di tali specifiche che siano adeguate l'una al
caso di BoolVect sparsi e l'altra a quelli densi.

Osservi che è possibile provvedere ben più di una sola implementazione per i
casi denso e sparso. Riguardo al primo, sono possibili diverse scelte riguardo
alla taglia: essa può essere fatta ad esempio coincidere col numero di bit di un
`byte`, `int` o `long` (il che suggerisce l'uso di una rappresentazione basata
su una variabile di tipo primitivo), ma è certamente possibile avere anche
taglie arbitrarie (ottenute con rappresentazioni basate su array, o liste). Nel
caso dei BoolVect sparsi, viceversa, è plausibile scegliere una rappresentazione
che consenta una taglia infinita (le posizioni dei pochi valori di verità veri
potrebbero essere molto grandi, anche nell'ordine di `Integer.MAX_VALUE`).

Per verificare il comportamento del suo codice le può essere utile implementare
una *classe di test* che, leggendo dal flusso di ingresso un elenco di azioni,
le realizzi (creando le necessarie istanze di oggetti d'appoggio).

Le azioni, indicate una per riga, sono specificate da un carattere seguito da
uno o più parametri; ciascuna azione produce un BoolVect come risultato,
che va emesso nel flusso di uscita.

Le azioni sono:

* `S` seguita da una posizione intera `p`, un valore di verità `a` e un vettore
  booleani `u`; il risultato corrisponde al BoolVect `u` in cui il
  `p`-esimo valore di verità è `a`;
* `G` seguita da una posizione intera `p`, e un BoolVect `u`; il
   risultato corrisponde al `p`-esimo valore di verità di `u`;
* `&` seguito da due BoolVect `u` e `v`; il risultato corrisponde a `u & v`;
* `|` seguito da due BoolVect `u` e `v`; il risultato corrisponde a `u | v`;
* `^` seguito da due BoolVect `u` e `v`; il risultato corrisponde a `u ^ v`.

Ad esempio, avendo

    S 0 V FFF
    S 1 F VVV
    G 0 FFV
    G 1 FFF
    G 1 FVF
    & FVFV VVFF
    | FVFV VVFF
    ^ FVFV VVFF

nel flusso di ingresso, la classe di test emette

    V
    VFV
    V
    F
    V
    VFF
    VVFV
    VFFV

nel flusso d'uscita (sono stati omessi gli `F` non significativi, ossia quelli
di posizione maggiore rispetto a quella della `V` di posizione maggiore).

**Nota bene**: le implementazioni scelte devono poter funzionare, quantomeno nel
caso di BoolVect sparsi, su dimensioni molto maggiori di quelle dell'esempio
precedente!

## La soluzione

Per prima cosa occorre progettare una *interfaccia* che rappresenti le
competenze dei BoolVect; valuteremo in un secondo momento se è il caso di
interporre una *classe astratta* tra questa interfaccia e le implementazioni
concrete.

Va invece categoricamente esclusa l'idea di sviluppare un tipo per rappresentare
i valori di verità del vettore: per tale scopo è più che sufficiente il tipo
primitivo `boolean` o, eventualmente, il corrispondete tipo *boxed* `Boolean`.
Non c'è alcuna ragione plausibile per progettare un ulteriore tipo. L'esigenza
di "convertire" dai caratteri `V` e `F` ai valori di verità e viceversa è
banalmente soddisfatta rispettivamente dalle espressioni `c == 'V'` e `v ? 'V' :
'F'` (dove `c` è di tipo `char` e `v` è di tipo booleano).

### L'interfaccia BoolVect

Le competenze informalmente descritte nella traccia sono:

* indicare la propria *taglia*,
* indicare la propria *dimensione*,
* *leggere* il valore di verità di data posizione,
* *scrivere* il valore di verità dato nella posizione assegnata,
* effettuare l'*and* con un altro vettore,
* effettuare l'*or* con un altro vettore e
* effettuare l'*xor* con un altro vettore.

Per tradurre questa descrizione informale in una specifica precisa, descritta da
una interfaccia, è necessario fare alcune scelte soffermandosi a riflettere
sulle conseguenze di esse.

Per quanto riguarda le prime tre competenze, essere saranno rappresentate da tre
metodi osservazionali

```{code-cell}
:tags: [remove-input]
sol.show('BoolVect', 'obs')
```

Unica osservazione degna di nota è l'eccezione sollevata dal metodo di lettura,
che accadrà senz'altro se la posizione richiesta è negativa; riguardo alle
posizioni che eccedono la dimensione è plausibile che tale funzione restituisca
il valore `false`. Si può decidere cosa fare nel caso in cui sia addirittura
ecceduta la taglia (sollevare eccezione, o restituire sempre `false`).

Maggior attenzione è richiesta dalle competenze che possono produrre cambiamento
nel BoolVect; è necessario riflettere sulla mutabilità dei BoolVect:
l'operazione di scrittura può essere pensata sia come una mutazione dello stato,
che come una produzione che restituisca un nuovo vettore ad ogni invocazione. La
seconda scelta appare però troppo onerosa: una sequenza di invocazioni su un
BoolVect di dimensione elevata produrrebbe una grande quantità di valori
intermedi di grandi dimensioni, probabilmente destinati ad avere una vita molto
corta. Appare quindi più ragionevole che l'interfaccia sia pensata per la
mutabilità.

```{code-cell}
:tags: [remove-input]
sol.show('BoolVect', 'write')
```

L'eccezione in questo caso accade sia se la posizione è negativa che se si tenta
di scrivere un valore di verità vero in posizione maggiore o uguale alla taglia:
la scrittura di tale valore infatti determinerebbe l'aumento della dimensione
oltre il valore della taglia (che è impossibile per definizione)…

Scelta la mutabilità, anche le operazioni booleane sono specificate in modo che
modifichino il BoolVect su cui sono invocate rendendolo uguale al risultato
dell'operazione.

```{code-cell}
:tags: [remove-input]
sol.show('BoolVect', 'bop')
```

A prescindere dalle ovvie eccezioni legate alla nullità dell'argomento, è
necessario tener conto di un problema legato alla taglia: non è detto che il
risultato possa sempre essere rappresentato modificando il primo operando.

Con un po' di riflessione risulta evidente che i metodi relativi alle operazioni
booleane dovrebbero sollevare eccezione quando la taglia del primo operando è
minore della dimensione del risultato: ad esempio l'or tra un BoolVect di taglia
2 e uno di dimensione 3 produce un risultato di dimensione 3, che evidentemente
non può essere memorizzato nel primo operando di taglia 2. Questo certamente non
può accadere nel caso dell'and (in cui il risultato non può in nessun caso avere
dimensione maggiore di quella del primo operando).

Per concludere, può essere utile aggiungere un metodo per rendere un BoolVect
uguale ai valori specificati tramite una stringa; tale metodo può essere comodo
per poter effettuarea una inizializzare in modo uniforme (nelle varie
implementazioni); tale metodo ammette una elementare implementazione di
*default* a partire da un metodo che renda tutti i valori di verità pari al
valore falso.

```{code-cell}
:tags: [remove-input]
sol.show('BoolVect', 'init')
```

### Una implementazione parziale

A ben pensare, alcuni dei metodi dell'interfaccia possono essere sviluppati a
partire dai soli metodi `leggi` e `scrivi`, per questa ragione sarebbe possibile
aggiungere all'interfaccia stessa alcune implementazioni di *default*; va però
osservato che anche i metodi `toString` e `equals` potrebbero essere scritti a
partire dai soli `leggi` e `scrivi`, ma tali metodi non possono essere
realizzati come metodi di *default* (una interfaccia non può sovrascrivere i
metodi di `Object`). Per tale ragione può aver senso introdurre una classe
astratta (priva di stato, fatto positivo dal punto di vista
dell'incapsulamento).

Ma c'è di più. È verosimile che le versioni *totali* di `leggi` e `scrivi`
dell'interfaccia (che si devono prendere cura del valore della posizione e, nel
caso, sollevare eccezioni) possano essere realizzate in modo molto semplice a
patto di avere a disposizione due metodi *parziali* (che potremmo chiamare
rispettivamente `leggiParziale` e `scriviParziale`) che operino sotto la
pre-condizione che la posizione sia sempre compresa tra 0 (incluso) e la taglia
(esclusa); inoltre, implementare tali versioni parziali per i sottotipi è più
semplice che implementare le versioni totali dell'interfaccia.

Il codice di questa parte della classe astratta è elementare

```{code-cell}
:tags: [remove-input]
sol.show('AbstractBoolVect', 'leggiscrivi')
```

Un'altra cosa di cui è possibile occuparsi a questo livello sono le operazioni
booleane; ciascuna di esse può essere implementata con un ciclo della forma

```{code-block} Java
final int maxDimension = Math.max(t.dimensione(), u.dimensione());
  for (int pos = 0; pos <= maxDimension; pos++)
    t.scrivi(pos, OP(t.leggi(pos), u.leggi(pos)));
```

dove `OP` è una "funzione" che realizza uno degli operatori booleani richiesti e
i BoolVect `t` e `u` sono gli operandi.

Sebbene a questo punto sia assolutamente plausibile ripetere questo ciclo tre
volte, sostituendo ogni volta un metodo statico che realizzi la funzione `OP`
che realizzi uno dei tre operatori and, or e xor (implementato nella classe
stessa, o in una classe apposita), sarebbe ancora più comodo poter
parametrizzare il ciclo rispetto a tale funzione.

#### Parametrizzare gli operatori booleani

Per fare una cosa del genere, in Java è sufficiente definire una interfaccia che
contenga un metodo che rappresenti la funzione

```{code-cell}
:tags: [remove-input]
sol.show('BooleanOperators', 'op')
```

e quindi, ad esempio usando le [classi
anonime](https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html),
predisporre delle istanze per ciascun operatore logico

```{code-cell}
:tags: [remove-input]
sol.show('BooleanOperators', 'singletons')
```

Grazie a questa impostazione (che mima ad esempio quella vista per gli
ordinamenti, basati su implementazioni dell'interfaccia `Comparable` che
contiene il solo metodo `compareTo`), è possibile scrivere un metodo statico
parziale che tramuti un operatore booleano in una operazione componente a
componente tra due BoolVect

```{code-cell}
:tags: [remove-input]
sol.show('BooleanOperators', 'bitwise')
```

Questo metodo può essere infine usato per dare una implementazione delle
operazioni tra BoolVect a livello della classe astratta

```{code-cell}
:tags: [remove-input]
sol.show('AbstractBoolVect', 'defop')
```

Si noti come, nel caso delle due operazioni il cui risultato potrebbe eccedere
la taglia del primo operando, l'eccezione `IndexOutOfBoundsException` sollevata
dal metodo `scrivi` venga sollevata al livello logico di una opportuna
`IllegalArgumentException`.

La classe astratta si chiude coi metodi che sovrascrivono quelli di `Object`

```{code-cell}
:tags: [remove-input]
sol.show('AbstractBoolVect', 'obj')
```

Il contratto di `equals` (e l'overview della classe astratta) devono specificare
molto chiaramente che è responsabilità delle sottoclassi provvedere una
plausibile implementazione di `hashCode` (che non ha senso implementare al
livello della classe astratta).

### Le implementazioni concrete

Avendo costruito con tanta attenzione la classe astratta appena descritta, il
compito di implementare (e documentare) delle sottoclassi concrete risulta
immensamente semplificato (anche perché è sostanzialmente possibile evitare ogni
ripetizione di codice).

Le implementazioni concrete devono provvedere i soli metodi (già documentati):

* `dimensione`, `taglia` e `pulisci`,
* `leggiParziale` e `scriviParziale`,
* `hashCode`;

inoltre possono (in modo del tutto facoltativo, se si ritiene utile e semplice
farlo) provvedere delle implementazioni ottimizzate per i metodi:

* `and`, `or`, `xor`,
* `equals`.

La decisione di lasciare alle classi concrete l'implementazione del metodo
`dimensione` è legata al fatto che, come vedremo, avere a disposizione la
rappresentazione rende molto efficiente il calcolo.

La traccia richiede (almeno) due implementazioni, una adatta al caso *denso* e
una a quello *sparso*; seguendo l'esempio dei polinomi, presentato nel libro di
testo di Liskov et. al. e durante le lezioni ed esercitazioni, si possono
individuare due rappresentazioni adatte, rispettivamente, ai due casi:

* un *array* di valori booleani, uno per ciascun valore di verità del BoolVect;
* un *insieme* di posizioni corrispondenti a tutti e soli i valori di verità
  veri del BoolVect.

La rappresentazione con un array condurrà ad un BoolVect di taglia *finita*
(pari al numero di elementi dell'array), mentre quella basata sull'insieme ad un
BoolVect di taglia *illimitata*.

Le due rappresentazioni, oltre che per la loro semplicità, risultano
particolarmente interessanti anche perché consentono di confrontarsi (e mostrare
la propria comprensione della traccia) in due casi distinti secondo la taglia.

Sostituendo una *lista* all'array è possibile trattare il caso denso con taglia
illimitata, aumentando di poco la complessità del codice (rischiando di
complicarsi la vita, senza mostrare però competenze più avanzate che nel caso
dell'array)

Viceversa, sostituire una *lista* (o un array) all'insieme nella
rappresentazione sparsa pone notevoli complessità implementative (ad esempio: la
necessità di mantenere distinti gli elementi e di effettuare ricerche efficienti
per ottenere la lettura e scrittura). Avendo a disposizione gli insiemi, l'uso
di una lista appare una inutile complicazione.

Per finire, le *mappe* sono una pessima rappresentazione sia per il caso denso
(se la chiave è la posizione) che quello sparso (se tra i valori vengono
memorizzati anche quelli falsi).

#### Caso denso basato su array

La classe concreta più elementare è quella che deriva dalla classe astratta e
rappresenta i valori del BoolVect in un array di tipo `boolean[]`. Per comodità
aggiungiamo alla rappresentazione anche il valore precalcolato della dimensione

```{code-cell}
:tags: [remove-input]
sol.show('ArrayBoolVect', 'rep')
```

l'invariante (oltre alla banale questione della nullità di `valore`) dovrà
garantire che `dimensione` coincida sempre con la dimensione del BoolVect, ossia
che in `dimensione - 1` si trovi l'ultimo `true` dell'array.

I primi tre metodi da implementare sono del tutto banali

```{code-cell}
:tags: [remove-input]
sol.show('ArrayBoolVect', 'trivial')
```

ma anche letture e scritture (che possono assumere, dato che sono parziali, che
la posizione sia sempre valida) sono molto semplici da scrivere

```{code-cell}
:tags: [remove-input]
sol.show('ArrayBoolVect', 'partial')
```

l'unico accorgimento è che la scrittura si occupi di tenere aggiornata la
dimensione precalcolata (che può crescere se viene aggiunto un valore di verità
vero, o deve essere diminuita se viene posto a falso il valore di verità che era
in posizione più alta).

Data questa rappresentazione, per le operazioni booleane è difficile fare meglio
di quanto implementato nel supertipo; per questa ragione la classe si può
concludere con la sola ottimizzazione dei metodi di `Object`

```{code-cell}
:tags: [remove-input]
sol.show('ArrayBoolVect', 'obj')
```

#### Caso sparso bastato su insieme

Se i valori di verità veri sono pochi, si può usare una struttura dati ben più
onerosa di un array, sfruttando però il fatto che (appunto) conterrà pochi
elementi. Gli interi sono naturalmente ordinati e conoscere la posizione più
grande è comodo per calcolare la dimensione; la rappresentazione più pratica è
quindi quella di un insieme ordinato

```{code-cell}
:tags: [remove-input]
sol.show('SetBoolVect', 'rep')
```

L'invariante è banalmente quello della nullità; i primi tre metodi (come nel
caso denso) sono di immediata implementazione

```{code-cell}
:tags: [remove-input]
sol.show('SetBoolVect', 'trivial')
```

Il fatto di non dover precalcolare la dimensione, rende molto semplici anche i
metodi parziali di lettura e scrittura

```{code-cell}
:tags: [remove-input]
sol.show('SetBoolVect', 'partial')
```

In questo caso, vale però la pena di ottimizzare le operazioni booleane almeno
nel caso in cui anche gli operandi siano sparsi; in tali circostanze, i metodi
and, or e xor si riducono a operazioni su insiemi che possono essere
implementate in modo molto efficiente

```{code-cell}
:tags: [remove-input]
sol.show('SetBoolVect', 'op')
```

Anche i metodi di `Object` si prestano a simili ottimizzazioni

```{code-cell}
:tags: [remove-input]
sol.show('SetBoolVect', 'obj')
```

#### Caso denso basato su `long`

Sebbene una implementazione per ciascun genere di BoolVect sia sufficiente a
superare la prova, riflettendo sull'opportunità di ottimizzare le operazioni
booleane non si può non riflettere che il linguaggio Java mette a disposizione
operatori booleani bit-a-bit per tutti i tipi numerici primitivi.

Questo suggerisce l'idea di usare un `long` per rappresentare BoolVect di taglia
64 (tanti sono i bit che tale tipo può memorizzare in Java)

```{code-cell}
:tags: [remove-input]
sol.show('LongBoolVect', 'rep')
```

L'invariante è sempre soddisfatto; i primi tre metodi sono ancora di immediata
implementazione

```{code-cell}
:tags: [remove-input]
sol.show('LongBoolVect', 'trivial')
```

la dimensione può essere per pigrizia calcolata col metodo statico
[`Long.numberOfLeadingZeros`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Long.html#numberOfLeadingZeros(long));
questo rende molto banali anche i metodi parziali di lettura e scrittura

```{code-cell}
:tags: [remove-input]
sol.show('LongBoolVect', 'partial')
```

L'ottimizzazione delle operazioni booleane (tra BoolVect basati su `long`) si
riduce all'uso degli operatori corrispondenti previsti dal linguaggio Java

```{code-cell}
:tags: [remove-input]
sol.show('LongBoolVect', 'op')
```

Per finire, anche i metodi di `Object` sono similmente banali

```{code-cell}
:tags: [remove-input]
sol.show('LongBoolVect', 'obj')
```

### La classe di test

In questo tema la classe di test è del tutto banale per via del formato
dell'input. Si tratta semplicemente di leggere una linea alla volta, separando
poi ogni linea nelle sue parti e decidendo le operazioni da compiere sulla
scorta del primo carattere della linea; per ciascuna linea è sufficiente
istanziare i BoolVect necessari e (se necessario), inizializzarne il valore col
metodo `daString` previsto dall'interfaccia.
