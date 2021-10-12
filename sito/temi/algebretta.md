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
sol = Solution('algebretta')
```
# Algebretta

## La traccia

Scopo della prova è progettare e implementare una gerarchia di oggetti utili a
rappresentare una *calcolatrice* per operazioni su *vettori* e *matrici
quadrate* a valori interi.
### I vettori

Una plausibile **interfaccia** per un *vettore* a valori interi potrebbe avere i
seguenti metodi

```{code-cell}
:tags:  [remove-input]
sol.show('Vettore', 'signatures')
```

dove `dim` restituisce la dimensione del vettore (ossia il numero delle sue
componenti), `val` restituisce il valore dell'*i*-esima componente del vettore,
`più` restituisce la somma vettoriale tra il vettore corrente e quello dato (che
è ovviamente possibile solo se i vettori sono *conformi*, ossia della stessa
dimensione), mentre `per` restituisce il prodotto del vettore corrente per lo
scalare `alpha`.

Scrivete la **classe** (concreta) `VettoreDenso` che la implementi memorizzando
le componenti del vettore in un *array* di `int`. Tale classe deve avere un
costruttore che prenda un *array* di interi come argomento (e costruisca il
vettore avente come componenti tutti e soli gli elementi dell'array); infine,
sovrascrivete il metodo `toString` in accordo a quanto mostrato negli esempi
seguenti.

### Le matrici

Prendendo spunto da quanto fatto per i vettori, definite ora la parte
riguardante le **matrici**, limitatamente al caso di *matrici quadrate* a
*valori interi*. Le operazioni che tali matrici devono consentire (oltre a
quelle utili a conoscere le loro dimensioni e componenti) sono quelle utili a
calcolare:

* il *prodotto per scalare*,
* la *somma matriciale*,
* il *prodotto matriciale*.

Osservate che, come è noto dall'algebra lineare, ci sono diverse matrici con
proprietà particolari, ad esempio:

* la matrice *nulla* (che ha tutti i componenti pari a 0),
* le matrici *diagonali* (che hanno i componenti fuori dalla diagonale pari a
  0),
* la matrice *identità* (che ha i componenti sulla diagonale principale pari 1 e
  tutti gli altri pari a 0).

Per tali matrici è sensato provvedere delle implementazioni specializzate.

### Estensioni facoltative

Una volta implementate le operazioni precedenti, potete aggiungere l'operazione
di *prodotto di una matrice per un vettore* osservando che, per realizzarla,
potrebbe aver senso provvedere una implementazione specializzata del *vettore
nullo* (che risulta, ad esempio, dal prodotto della matrice nulla per qualunque
altro vettore).

Un'altra possibile estensione consiste nelle implementazioni specializzate al
caso di matrici *sparse* (che abbiano cioè un numero di elementi diversi da 0
dell'ordine della loro dimensione).

### Comportamento esibito

Scrivete un metodo statico `main` nella classe che ritenete più opportuna che
legga dal flusso di ingresso una (rappresentazione testuale di una) serie di
semplici operazioni tra matrici e vettori e ne stampi (qualora sia possibile) il
risultato.

Le operazioni da considerare sono solo quelle tra *due soli operandi* (scalari,
vettori e matrici), limitatamente ai caso di:

* prodotto scalare vettore,
* somma tra vettori,
* prodotto scalare matrice,
* somma tra matrici,
* prodotto tra matrici,
* prodotto tra matrice e vettore.

Somme e prodotti sono indicati rispettivamente da `+` e `*`, i vettori sono
rappresentati come un elenco di interi separati da virgole e racchiusi tra
parentesi tonde, mentre le matrici sono rappresentate, in generale, da un
elenco di righe separate da punti e virgola e racchiuse tra parentesi quadre,
dove ogni riga è data da un elenco di interi separati da virgole.
Pertanto, la stringa `(1, 2, 3, 4, 5, 6, 7, 8, 9)` rappresenta il vettore

	1	2	3	4	5	6	7	8	9

mentre la stringa `[1, 2, 3; 4, 5, 6; 7, 8, 9]` rappresenta la matrice

	1	2	3
	4	5	6
	7	8	9

Alcune matrici hanno una rappresentazione particolare:

* la matrice nulla è rappresentata dalla lettera `Z` seguita dalla dimensione
  (racchiusa tra quadre),
* le matrici diagonali sono rappresentate dalla lettera `D` seguita dall'elenco
  delle componenti sulla diagonale principale (racchiuse tra quadre e separate
  da virgole),
* la matrice identità è rappresentata dalla lettera `I` seguita dalla dimensione
  (racchiusa tra quadre),

#### Decodifica dell'input

Estrarre operandi, operatore, scalari, vettori e matrici (comprese quelle con
rappresentazione particolare) da ciascuna stringa non è affatto banale. Per
questa ragione avete a disposizione una classe (statica) denominata `Parser`,
che offre una serie di metodi (statici, dotati di commenti Javadoc) che
consentono di estrarre le informazioni necessarie a costruire gli opportuni
oggetti a partire dalla stringa corrispondente a una delle operazioni da
trattare.
### Esempio

Eseguendo `soluzione` e avendo

    2 * (3, 4)
    (5, 6) + (7, 8)
    2 * [3, 4; 5, 6]
    [3, 4; 5, 6] + [3, 4; 5, 6]
    [3, 4; 5, 6] * (3, 5)
    [3, 4; 5, 6] * (4, 6)
    [3, 4; 5, 6] * [3, 4; 5, 6]

nel flusso d'ingresso, il programma emette

    (6, 8)
    (12, 14)
    [6, 8; 10, 12]
    [6, 8; 10, 12]
    (29, 45)
    (36, 56)
    [29, 36; 45, 56]

nel flusso d'uscita. Eseguendo `soluzione` e avendo

    2 * I[2]
    2 * Z[2]
    2 * D[3, 4]
    I[2] * (3, 4)
    Z[2] * (3, 4)
    D[3, 4] * (3, 4)

nel flusso d'ingresso, il programma emette

    [2, 0; 0, 2]
    [0, 0; 0, 0]
    [6, 0; 0, 8]
    (3, 4)
    (0, 0)
    (9, 16)

nel flusso d'uscita.

### Suggerimenti

Si ricorda che il prodotto matriciale tra *M* ed *N* è la matrice *MN* tale che
*(MN)ᵢⱼ = ∑ᵣ MᵢᵣNᵣⱼ*. Inoltre, il prodotto della matrice *M* con il vettore *v* è il
vettore *Mv* tale che *(Mv)ᵢ = ∑ᵣ Mᵢᵣvᵣ*. Si noti che, in entrambi i casi, *n* è la
dimensione delle matrici e dei vettori coinvolti e la somma è per *r* che va da
0 a *n*-1. Ovviamente, fatta eccezione per il prodotto per scalare, le altre
operazioni sono possibili solo se le matrici, o la matrice e il vettore, hanno
la stessa dimensione (ossia sono conformi).

## La soluzione

### I vettori

Iniziamo con una osservazione sull'interfaccia: prima di effettuare una
operazione tra vettori o matrici è necessario sapere se due vettori sono
*conformi*, o se un vettore è *conforme* ad una matrice; dato che tale
informazione dipende solo dalla dimenzione (che è una competenza espressa dalle
interfacce), può aver senso aggiugnere due metodi di *default*

```{code-cell}
:tags:  [remove-input]
sol.show('Vettore', 'default')
```

Ora, la classe concreta da implementare è completamente specificata, non solo
astrattamente, ma anche dal punto di vista della rappresentazione; resta solo da
stabilire che (come è plausibile dato il tipo d'uso descritto dal progetto) il
vettore sia *immutabile*. Date le scelte fatte è immediato concludere che gli
invarianti sono che l'array non sia un riferimento nullo e che contenga almeno
un elemento e che tali invarianti possono essere controllati solo in costruzione

```{code-cell}
:tags:  [remove-input]
sol.show('VettoreDenso', 'rapcostr')
```

Osservate l'uso del metodo `clone` che ha l'obiettivo di costruire una copia
dell'array passato come argomento; questo accorgimento serve ad evitare che chi
ha invocato li costruttore possa mantenere un riferimento all'array che
costituisce la rappresentazione del vettore.

L'implementazione dei metodi prescritti dall'interfaccia è ovvia, se ne rimanda
la presentazione alla sezione sulle estensioni (dato cha l'aggiunta del vettore
nullo rende in parte più sofisticati anche i metodi del vettore qui sviluppato).

### Le matrici

#### L'interfaccia (e classe astratta)

Per iniziare, è opportuno sviluppare una *interfaccia* `Matrice` che esprima le
competenze richieste per tutte le matrici

```{code-cell}
:tags:  [remove-input]
sol.show('Matrice', 'signatures')
```

Come nel caso dei vettori, può aver senso aggiungere dei metodi di *default* che
consentano di valutare la conformità e (per comodità) se due coordinate siano
valide.

```{code-cell}
:tags:  [remove-input]
sol.show('Matrice', 'default')
```

Sarebbe utile sovrascrivere qui anche il metodo `toString` (che dipende solo
dalle competenze nell'interfaccia), ma sfortunatamente non è possibile usare un
metodo di default per farlo; a tale sopo può essere introdotta una *classe
astratta* che implementi (parzialmente) l'intefacciae di fatto soltanto
sovrascrivendo `toString`

```{code-cell}
:tags:  [remove-input]
sol.show('AbsMatrice', 'tostring')
```

Ora non manca che sviluppare le classi per una matrice densa e per quelle
speciali; ad alto livello ci stiamo apprestando a sviluppare questa gerarchia

:::{mermaid}
:align: center

classDiagram
class Matrice {
  <<interface>>
  dim()
  val(int, int);
  Matrice per(int);
  Matrice per(Matrice);
  Matrice più(Matrice);
}
class AbsMatrice {
  <<abstract>>
  toString()
}
class MatriceDensa
class MatriceNulla
class MatriceDiagonale
class MatriceIdentità
Matrice <|-- AbsMatrice
AbsMatrice <|-- MatriceDensa
AbsMatrice <|-- MatriceNulla
AbsMatrice <|-- MatriceDiagonale
AbsMatrice <|-- MatriceIdentità
:::

Si potrebbe essere tentati di ritenere la matrice identità come un sottotipo di
matrice diagonale, questo ha senz'altro senso dal punto di vista algebrico, ma
non da quello implementativo, come sarà più chiaro in seguito.

#### La matrice densa

Iniziamo dalla matrice densa, la cui rappresentazione sarà data da un array
bidimensionale; anche in questo caso, essendo sensato che le matrici siano
immutabili, gli invarianti sono che l'array non sia un riferimento a null, sia
"quadrato" e di dimensione almeno 1 x 1 e possono essere controllati solo in
costruzione.

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceDensa', 'rapcostr', 'copy')
```

Si osservi il "costruttore copia" che potrà risultare comodo per costruire
matrici densa a partire da altre matrici (in particolare, quelle speciali).

Nel soddisfare l'interfaccia, osserviamo che alcuni metodi sono banali da
implementare:

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceDensa', 'dimval')
```

Nel caso delle operazioni, occorre prestare attenzione che in alcune circostanze
può essere vantaggioso restituire matrici speciali, come ad esempio nel caso di
moltiplicazione per lo scalare zero (evidenziato nella seguente porzione di
codice):

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceDensa', 'peralpha', 'perzero')
```

o di somma con la matrice nulla, nel qual caso va restituita la matrice stessa
(soluzione resa possibile dall'immutabilità):

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceDensa', 'piumat', 'piuzero')
```

o di prodotto sia con la matrice nulla (che risulta nella matrice nulla), o con
la matrice identità (che risulta la matrice stessa):

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceDensa', 'permat', 'perspeciale')
```

#### La matrice nulla

Caso del tutto banale è quello della matrice nulla. La sua rappresentazione
coincide esclusivamente con la sua dimensione, quindi l'invariante e i
costruttori sono cosa ovvia:

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceNulla', 'rapcostr')
```

Alcune competenze prescritte dall'interfaccia sono immediati da implementare:

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceNulla', 'dimval')
```

Nel caso delle operazioni, le proprietà algebriche delle matrici si riflettono
in modo ovvio nel codice:

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceNulla', 'ops')
```

Unica accortezza è sollevare le necessari eccezioni in ottemperanza alle
specifiche dell'interfaccia.

#### La matrice diagonale

Nel caso della matrice diagonale, essendo sufficiente ricordare solo i valori
lungo la diagonale, la rappresentazione è un array di interi (per cui valgono
considerazioni analoghe alle precedenti per costruttori e invariante):

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceDiagonale', 'rapcostr')
```

Alcuni metodi prescritti dall'interfaccia hanno al solito implementazioni ovvie:

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceDiagonale', 'dimval')
```

Come per la matrice densa, nel caso delle operazioni, in alcuni circostanze può
essere vantaggioso restituire matrici speciali, come ad esempio nel caso di
moltiplicazione per lo scalare zero (evidenziato nella seguente porzione di
codice):

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceDiagonale', 'peralpha', 'perzero')
```

o di somma con la matrice nulla, nel qual caso va ancora restituita la matrice
stessa:

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceDiagonale', 'piumat', 'piuzero')
```

o di prodotto sia con la matrice nulla (che risulta nella matrice nulla), o con
la matrice identità (che risulta la matrice stessa):

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceDiagonale', 'permat', 'perspeciale')
```

#### La matrice identità

Come nel caso della matrice nulla, anche per l'identità è sufficiente ricordare
la sola dimensione, quindi rappresentazione, invariante e costruttori sono:

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceIdentità', 'rapcostr')
```

Al solito, alcune competenze prescritte dall'interfaccia sono immediate da
implementare:

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceIdentità', 'dimval')
```

Il prodotto per scalare e la somma trattano al solito in modo speciale il caso
dello zero:

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceIdentità', 'peralpha', 'perzero')
```

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceIdentità', 'piumat', 'piuzero')
```

mentre il caso del prodotto è elementare per via delle proprietà algebriche:

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceIdentità', 'permat')
```

### Le estensioni

#### Il vettore nullo

Nell'implementazione dei prodotti matrice vettore, avendo a che fare con la
matrice nulla, può risultare utile avere una implementazione del vettore nullo. Questo completa la gerarchia relativa ai vettori che diventa:

:::{mermaid}
:align: center

classDiagram
class Vettore {
  <<interface>>
  dim()
  val(int, int);
  Vettore per(int);
  Vettore più(Vettore);
}
class VettoreDenso
class VettoreNullo
Vettore <|-- VettoreDenso
Vettore <|-- VettoreNullo
:::

Iniziamo con la rappresentazione che, come nel caso della matrice nulla, è data
soltanto dalla dimensione:

```{code-cell}
:tags:  [remove-input]
sol.show('VettoreNullo', 'rapcostr')
```

Le competenze più immediate da implementare sono:

```{code-cell}
:tags:  [remove-input]
sol.show('VettoreNullo', 'dimval')
```

Le operazioni sono anch'esse semplici, date le proprietà algebriche:

```{code-cell}
:tags:  [remove-input]
sol.show('VettoreNullo', 'ops')
```

#### La moltiplicazione matrice vettore

Per aggiungere la funzionalità della moltiplicazione tra matrice e vettore è
sufficiente aggiungere una competenza all'interfaccia

```{code-cell}
:tags:  [remove-input]
sol.show('Matrice', 'pervec')
```

che poi sarà implementata in modo semplice nei vari tipi di matrice, a partire
dalla densa:

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceDensa', 'pervec', 'pervzero')
```

alla nulla:

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceNulla', 'pervec')
```

per passare alla diagonale:

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceDiagonale', 'pervec', 'pervzero')
```

e infine all'identità:

```{code-cell}
:tags:  [remove-input]
sol.show('MatriceIdentità', 'pervec')
```

Si osservi il codice evidenziato che tratta i casi speciali dovuti al vettore
nullo (che sono rilevanti solo per la matrice densa e diagonale).