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
:tags:  [remove-input]
from tslib import Solution
sol = Solution('cambiavalute')
```
# Cambiavalute

## La traccia

Scopo della prova è progettare e implementare una gerarchia di oggetti utili a
rappresentare il comportamento di un *cambiavalute*.

### Valute e importi

Una **valuta** è caratterizzata da un *nome* (non vuoto) e da un *simbolo*
(carattere), per semplicità considereremo solo le seguenti valute:

    Dollaro ($)
    Euro (€)
    Franco (₣)
    Lira (₺)
    Rupia (₹)
    Sterlina (£)
    Yen (¥)

Le valute sono definite nell'ordine in cui sono elencate qui; tale *ordine* sarà
rilevante quando si tratterà di enumerare importi di valute differenti.

Un **importo** è caratterizzato da un *valore* (espresso in unità e centesimi) e
da una valuta; sono ad esempio importi €-3, $123.32, ₹1000000. Due importi della
stessa valuta possono essere *sommati*, *sottratti* o *confrontati* (per sapere
chi è il maggiore, o minore, o se sono uguali) l'uno con l'altro; può essere
comodo poter produrre (data una valuta) l'importo *zero* in tale valuta, così
come determinare se un importo è pari a *zero*.

Si presti particolare attenzione alla rappresentazione del valore di un importo,
i tipi in virgola mobile (*double* e *float*) *non sono adatti* a causa della
loro incapacità a rappresentare in modo esatto le frazioni decimali. Ad esempio
`0.10 + 0.20` in Java è uguale a `0.30000000000000004` il che è "sostanzialmente
corretto" dal punto di vista dei numeri reali, ma non è quello che ci si aspetta
da degli importi! Non sarà ritenuto accettabile risolvere questo problema
effettuando dei troncamenti (neppure nella conversione a stringa).

### Cassa (multi-valuta)

Una **cassa** è un "contenitore" di importi, in essa è possibile *versare*
importi (in qualunque valuta) e *prelevare* importi (purché la cassa contenga un
importo sufficiente nella valuta richiesta). La cassa deve consentire di
*iterare* sui propri importi diversi da zero nell'ordine in cui sono state
definite le valute; tale capacità è particolarmente utile nel fornire una
rappresentazione testuale di una cassa che deve contenere solo gli importi
diversi da zero in ordine di valuta; ad esempio:

    Cassa:
    $55.30
    €87.79
    ₣89.50
    ₹11000.00
    £200.00
    ¥24.95

che non elenca la Lira turca dal momento che non è presente in cassa alcun
importo (diverso da zero) in tale valuta.

### Tassi di cambio

Un **tasso** di cambio è specificato da due importi (con valute diverse) da
intendersi "equivalenti" nel senso che è possibile convertire qualunque multiplo
del primo importo nello stesso multiplo del secondo.

Ad esempio, il cambio 

    $2 = €2.40 
    
significa che 2 Dollari sono equivalenti a 2.40 Euro.

Usando le proporzioni imparate alla scuola elementare, un importo in una valuta
è quindi in grado di determinare, dato un tasso di cambio tra la sua valuta e
un'altra valuta, il suo *equivalente* nell'altra valuta (se diversa dalla
prima).

Nel caso precedente, il tasso comporterà ad esempio che 3 Dollari siano
equivalenti 3.60 Euro, o 1 Dollaro a 1.20 Euro.

### Cambiavalute

Un **cambiavalute** è un servizio dotato di una *cassa* che, presa conoscenza di
una serie di tassi di cambio, può *cambiare* a richiesta un importo (in una data
valuta) in una valuta differente.

In maggior dettaglio:

* nel momento in cui inizia a operare, il cambiavalute riceve una serie di
  importi (di varie valute) che deposita in cassa; successivamente la cassa non
  può essere più modificata direttamente (ma solo tramite le operazioni di
  cambio);

* quando sta operando può:

  * ricevere degli *aggiornamenti* sui tassi di cambio che memorizza; se riceve
    un tasso di cambio tra due valute di cui ne era già memorizzato uno, il
    nuovo tasso rimpiazza il precedente;

  * ricevere delle richieste di cambio di un dato importo in una nuova valuta;
    se ha memorizzato il tasso di cambio relativo e ha in cassa l'equivalente
    dell'importo nella nuova valuta procede a: (1) versare in cassa l'importo
    nella valuta originaria e (2) prelevare l'importo equivalente nella nuova
    valuta; viceversa segnala opportunamente gli errori relativi alla mancanza
    di conoscenza del tasso, o di disponibilità dei fondi.

In aggiunta, un cambiavalute deve consentire di *iterare* sui propri tassi di
cambio nell'ordine in cui sono stati aggiornati (ossia inseriti e, nel caso,
successivamente modificati); tale capacità è particolarmente utile nel fornire
una rappresentazione testuale di un cambiavalute.

### Cosa dovete implementare

Dovete implementare una gerarchia di classi atta a rappresentare il
cambiavalute, la cassa, i tassi, gli importi e le valute.

Prestate particolare attenzione a *mutabilità* e *immutabilità*, così come (se
necessaria) all'implementazione dei metodi `equals`, `hashCode` e `compareTo`
(dall'interfaccia `Comparable`) e degli *iteratori* delle classi che
realizzerete; osservate che, in alcuni casi, *record* ed *enum* possono essere
molto comodi in questo lavoro.

#### La classe di test

La classe di test deve istanziare un cambiavalute dopo aver letto una sequenza
di importi (uno per linea) dal flusso di ingresso standard; quindi, proseguendo
nella lettura del flusso di ingresso, deve interpretare i seguenti comandi:

* `A` seguito da due importi, che comporta l'aggiornamento del tasso di cambio
  definito da tali importi;
* `C` seguito da un importo e una valuta, che comporta il cambio del primo
  importo nella seconda valuta e l'emissione del risultato nel flusso d'uscita;
* `P` che comporta l'emissione nel flusso d'uscita dello stato del cambiavalute
  (dato dall'elenco dei tassi e dal contenuto della cassa).

l'esecuzione termina al termine del flusso d'ingresso.

Se l'esecuzione del comando comporta un errore (ad esempio perché le due valute
nel comando `A` o `C` sono identiche, oppure perché il cambio richiesto dal
comando `C` non è possibile per mancanza di fondi nella cassa, o perché non è
noto il tasso) il programma deve emettere nel flusso d'uscita un opportuno
messaggio d'errore.

Ad esempio, eseguendo la classe di test e avendo nel flusso d'ingresso:

    $100
    €90.50
    £200
    ¥100
    ₣80.50
    ₹10000
    ₺95000
    A $1 = €1.07
    C $3 = €
    C €10 = ¥
    P
    A €0.10 = ¥15.01
    C €0.50 = ¥
    A ₣0.50 = ₺11.53
    A ₺200 = $9.54
    A ₹100 = ₣1.10
    C ₹1000 = ₣
    C €1 = €
    C ₣10 = ₺
    C €100 = ¥
    C ₺1000 = $
    P
    A ₣1.50 = ₺34.80
    P
    A ₣1.50 = ₣2
    C ₣10 = ₺
    A €1 = ¥149.46
    P

il programma emette

    €3.21
    ERRORE: Tasso non disponibile
    [
    Tassi:
    $1.00 = €1.07
    Cassa:
    $103.00
    €87.29
    ₣80.50
    ₺95000.00
    ₹10000.00
    £200.00
    ¥100.00
    ]
    ¥75.05
    ₣11.00
    ERRORE: Impossibile cambiare tra valute identiche
    ₺230.60
    ERRORE: Fondi non sufficienti
    $47.70
    [
    Tassi:
    $1.00 = €1.07
    €0.10 = ¥15.01
    ₣0.50 = ₺11.53
    ₺200.00 = $9.54
    ₹100.00 = ₣1.10
    Cassa:
    $55.30
    €87.79
    ₣79.50
    ₺95769.40
    ₹11000.00
    £200.00
    ¥24.95
    ]
    [
    Tassi:
    $1.00 = €1.07
    €0.10 = ¥15.01
    ₺200.00 = $9.54
    ₹100.00 = ₣1.10
    ₣1.50 = ₺34.80
    Cassa:
    $55.30
    €87.79
    ₣79.50
    ₺95769.40
    ₹11000.00
    £200.00
    ¥24.95
    ]
    ERRORE: Impossibile definire un tasso di cambio tra valute identiche
    ₺208.80
    [
    Tassi:
    $1.00 = €1.07
    ₺200.00 = $9.54
    ₹100.00 = ₣1.10
    ₣1.50 = ₺34.80
    €1.00 = ¥149.46
    Cassa:
    $55.30
    €87.79
    ₣89.50
    ₺95560.60
    ₹11000.00
    £200.00
    ¥24.95
    ]

nel flusso d'uscita.

## La soluzione

### Le valute

Partiamo dalla classe più elementare, che come suggerito è opportunamente
realizzata tramite un *enum*. La rappresentazione è ovvia, avendo due soli
attributi (di cui solo il primo non è primitivo e quindi richiede il controllo
di non nullità):

```{code-cell}
:tags: [remove-input]
sol.show('Valuta', 'rap')
```

Unica accortezza può essere quella di dotare la classe di una mappa che colleghi
ciascun simbolo alla corrispondente valuta:

```{code-cell}
:tags: [remove-input]
sol.show('Valuta', 'map')
```

il popolamento della mappa consente anche di controllare l'unicità dei simboli.

Questo renderà particolarmente elementare ed efficiente implementare il metodo
`valueOf` che consente di recuperare (se nota) la valuta corrispondente ad un
dato simbolo:

```{code-cell}
:tags: [remove-input]
sol.show('Valuta', 'valueOf')
```

:::{note} 

Una soluzione alternativa all'uso di una `enum` (non solo più complessa da
realizzare, ma anche meno efficace) potrebbe essere basata su classe concreta;
in tal caso è però assolutamente necessario che:

* la classe implementi in modo opportuno `equals` e `hashCode`,
* il costruttore sia privato e venga usato per popolare una struttura dati
  interna utile a contenere tutte e soltanto le valute descritte dalla traccia;
* ci sia un metodo di fabbricazione che consenta di ottenere una di tali istanze
  a partire dal simbolo e/o dal nome.

Una classe che consenta all'utente di definire qualunque valuta e non sia in
grado di stabilire se due valute siano uguali è inutile ai fini della soluzione.
:::

### Gli importi

La rappresentazione degli importi richiede un minimo di attenzione in più, dal
momento che, come è messo in luce dalla traccia, l'uso dei numeri in virgola
mobile non costituisce una rappresentazione adeguata.

La cosa più semplice da fare è quella di rappresentare gli importi in
*centesimi*, ad esempio `$3.20` sarà rappresentato come `320` centesimi (di
Dollaro). Questo consente di svolgere tutte le operazioni aritmetiche in modo
elementare.

Oltre al valore così rappresentato, occorrerà memorizzare anche la valuta, con
l'accortezza che non sia nulla:

```{code-cell}
:tags: [remove-input]
sol.show('Importo', 'rep')
```

Piuttosto che rendere pubblico un costruttore che accetti un valore (in virgola
mobile), è preferibile avere un costruttore privato che accetti il valore
espresso in centesimi e rendere pubblico all'utente un metodo di fabbricazione
che costruisca un importo a partire da una stringa:

```{code-cell}
:tags: [remove-input]
sol.show('Importo', 'valueOf')
```

i controlli sono relativi al formato accettato, ad esempio sono validi:
`$-3.20`, `$3` e `$.20`; nell'implementazione si nota l'utilità del metodo
`valueOf` della classe `Valuta` (che potrebbe sollevare una eccezione
documentata implicitamente assieme alle altre `IllegalArgumentException` del
metodo).

Come atteso, la scelta della rappresentazione, rende elementare la scrittura dei
metodi di somma e differenza:

```{code-cell}
:tags: [remove-input]
sol.show('Importo', 'addsub')
```

unica cosa a cui prestare attenzione, a prescindere dall'ovvia richiesta che
l'altro importo non sia `null`, è che i due importi siano espressi nella stessa
valuta.

Può essere comodo avere una istanza di importo con valore zero per ogni valuta,
a tale fine si può preparare una mappa che colleghi ciascuna valuta con il
relativo importo zero:

```{code-cell}
:tags: [remove-input]
sol.show('Importo', 'zeromap')
```

che rende banale realizzare il metodo:

```{code-cell}
:tags: [remove-input]
sol.show('Importo', 'zero')
```

Raccogliere gli importi (che sono immutabili) in una mappa consente di non avere
istanze multiple di un valore costante comune come lo zero.

Altri metodi di utilità sono:

```{code-cell}
:tags: [remove-input]
sol.show('Importo', 'zp')
```

che torneranno comodi nell'implementazione della cassa.

Per finire aggiungiamo un po' di sovrascritture di metodi che rendono
confrontabili gli importi:

```{code-cell} 
:tags: [remove-input]
sol.show('Importo', 'ehc')
```

di nuovo, a prescindere dalla nullità, occorre controllare le valute e prestare
attenzione che `equals` e `compareTo` siano coerenti (ma questo è di nuovo
elementare data la rappresentazione scelta).

:::{note}
Si potrebbe pensare che la classe
[BigDecimal](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigDecimal.html)
possa essere usata per rappresentare gli importi, ciò è vero a patto di
occuparsi con estrema attenzione del fatto che il numero di cifre decimali sia
sempre esattamente uguale a due; ciò è possibile, ma richiede una conoscenza
abbastanza approfondita del funzionamento di tale classe e una notevole
attenzione. Questo rende la soluzione proposta, basata sull'uso dei soli
centesimi, notevolmente più elementare e pratica da implementare.
:::

### La cassa

La cassa è una collezione di importi, che può essere realizzata tramite una
mappa:

```{code-cell} 
:tags: [remove-input]
sol.show('Cassa', 'rep')
```

l'*invariante di rappresentazione* richiede, oltre alle banali condizioni di non
nullità, che l'importo associato ad una valuta sia in tale valuta. 

Dal punto di vista dell'implementazione, è possibile usare una [EnumMap](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/EnumMap.html) che offre diversi vantaggi:

* non consente di inserire chiavi `null` (che non avrebbero senso);
* è iterabile nell'ordine in cui sono definiti gli elementi dell'enum (che torna
  utile per implementare l'iteratore richiesto dalla traccia);

per rendere elementare l'implementazione dell'iteratore, l'unica accortezza è
aggiungere all'invariante di rappresentazione il vincolo che la mappa non
contenga mai importi di valore zero.

Il primo metodo da implementare è quello che consente di conoscere il totale per
una data valuta:

```{code-cell}
:tags: [remove-input]
sol.show('Cassa', 'tot')
```

poiché non è errato chiedere il totale per una valuta non presente in cassa,
gestiremo questo caso restituendo un importo a valore zero (senza segnalare
eccezione).

Aggiungere fondi è semplice:

```{code-cell}
:tags: [remove-input]
sol.show('Cassa', 'add')
```

oltre alla nullità dell'importo o alla negatività del suo valore (che causeranno
una eccezione), eviteremo di aggiungere importi di valore pari a zero (per non
violare l'invariante di rappresentazione).

Il prelievo è similmente facile:

```{code-cell}
:tags: [remove-input]
sol.show('Cassa', 'sub')
```

di nuovo, oltre alla nullità dell'importo o alla negatività del suo valore (che
causeranno una eccezione), dovremo prestare attenzione al caso in cui il
prelievo ecceda la disponibilità (fatto che segnaleremo con una eccezione), o
annulli il totale in cassa: in tal caso dovremo eliminare il valore relativo
alla valuta (sempre per non violare l'invariante di rappresentazione).

A questo punto l'iteratore è del tutto banale da scrivere:

```{code-cell}
:tags: [remove-input]
sol.show('Cassa', 'iter')
```

### I tassi di cambio

I tassi sono dati da una coppia di importi che, come illustrato nella traccia,
consente di calcolare per ciascun importo nella valuta del primo importo del
tasso il valore dell'importo equivalente nella seconda valuta del tasso.

Per rappresentare un singolo tasso può bastare un `record`, il costruttore:

```{code-cell}
:tags: [remove-input]
sol.show('Cambi', 'tasso')
```

deve occuparsi di assicurare che valga l'invariante di rappresentazione, ossia
che i due importi non siano nulli, siano positivi e siano relativi a due valute
diverse.

La competenza di ottenere l'importo equivalente dato un opportuno tasso può essere assegnata all'importo stesso:

```{code-cell}
:tags: [remove-input]
sol.show('Importo', 'conv')
```

A questo punto può risultare conveniente raccogliere i tassi noti in una classe
(che chiameremo `Cambi` di cui i record `Tasso` sia interno, per mere questioni
di *naming*). 

La rappresentazione di tale classe è data da una lista:

```{code-cell}
:tags: [remove-input]
sol.show('Cambi', 'rep')
```

l'invariate, a parte le banali questioni di nullità, dovrà garantire che, per
una assegnata coppia di valute distinte, ci sia al più un tasso tra importi di
tali valute nella lista; dato che i tassi vanno riportati in ordine di aggiunta,
l'implementazione migliore sembra essere quella della `LinkedList` che
consentirà di eliminare il vecchio tasso in modo efficiente.

La prima competenza della classe dei cambi è di trovare, se presente, il tasso
tra due valute assegnate, tale competenza è una banale ricerca lineare:

```{code-cell}
:tags: [remove-input]
sol.show('Cambi', 'cerca')
```

questa competenza sarà utile non solo al cambiavalute, ma anche internamente,
per aggiornare i tassi. Tale operazione, infatti, deve dapprima determinare
l'evenutale presenza di un tasso tra le medesime valute di quello da aggiornare,
che andrà nel caso eliminato, e quindi accodare il nuovo tasso all'elenco di
quelli noti:

```{code-cell}
:tags: [remove-input]
sol.show('Cambi', 'aggiorna')
```

La scelta della rappresentazione e del suo invariante, rendono anche in questo
caso del tutto banale la scrittura dell'iteratore che realizzi il comportamento
richiesto dalla traccia:

```{code-cell}
:tags: [remove-input]
sol.show('Cambi', 'iter')
```

### Il cambiavalute

Il cambiavalute è una entità molto semplice, ha una sola competenza, quella di
cambiare importi tra valute di cui conosce i tassi di cambio. Il suo stato sarà
data da una istanza di `Cassa` e un di `Cambi` a cui delegherà il compito di
gestire i suoi fondi e la sua conoscenza dei tassi di cambio:

```{code-cell}
:tags: [remove-input]
sol.show('CambiaValute', 'rep')
```

Il costruttore riceve una lista di importi (non necessariamente di valute distinte) che verserà in cassa, delegando a quest'ultima il compito di accumulare gli importi per valuta e verificare che non ci siano importi di valore negativo:

```{code-cell}
:tags: [remove-input]
sol.show('CambiaValute', 'costruttore')
```

La competenza di cambiare importi tra valute è data dal metodo:

```{code-cell}
:tags: [remove-input]
sol.show('CambiaValute', 'cambia')
```

a prescindere dai vari controlli, la logica è molto semplice: calcolato
l'importo equivalente si versa in cassa quello da cambiare e si preleva quello
equivalente.

Il cambiavalute può aggiornare i suoi tassi, delegando tale compito ai cambi:

```{code-cell}
:tags: [remove-input]
sol.show('CambiaValute', 'aggiorna')
```

Infine, al fine di consentire l'ispezione dello stato del cambiavalute, è
sufficiente delegare alla cassa e ai cambi il compito di offrire il loro
iteratore:

```{code-cell}
:tags: [remove-input]
sol.show('CambiaValute', 'iter')
```

### La classe di test

Svolgere il test è piuttosto elementare, dapprima andranno letti gli importi
(ossia le righe che non iniziano con uno dei caratteri `A`, `C` o `P`):

```{code-cell}
:tags: [remove-input]
sol.show('Soluzione', 'importi')
```

Quindi si può passare ad elaborare i comandi, uno per riga, avendo l'accortezza
di avvolgere l'esecuzione dei metodi del cambiavalute in un `try`/`catch` per poter emettere l'eventuale messaggio d'errore contenuto nell'eccezione sollevata:

```{code-cell}
:tags: [remove-input]
sol.show('Soluzione', 'comandi')
```