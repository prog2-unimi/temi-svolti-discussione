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
sol = Solution('multiset')
```
# Multiset

## Descrizione

Scopo della prova è progettare e implementare una gerarchia di oggetti utili a
rappresentare dei *multiset*.

In matematica un **multiset** (detto anche *bag*) è una sorta di insieme in cui
però, a differenza che in quest'ultimo, ciascun elemento può essere contenuto
più di una volta; il numero di volte che un elemento compare in un multiset è
detto **molteplicità** (di quell'elemento nel multiset).

Una conseguenza di questo fatto è che dati due elementi `a` e `b` si possono
avere infiniti multiset distinti che li contengono:

* `{a, b}` contiene ciascun elemento con molteplicità 1 (e può essere quindi
  visto anche come insieme);
* in `{a, a, b}` l'elemento `a` ha molteplicità 2, mentre `b` ha ancora
  molteplicità 1;
* in `{a, a, a, b, b, b}`, sia `a` che `b` hanno molteplicità 3, e così via…

Come negli insiemi, ma diversamente dalle ennuple, o sequenze, l'ordine non
conta così, ad esempio, `{a, a, b}` e `{a, b, a}` denotano lo stesso multiset.

La **cardinalità** di un multiset è la somma delle molteplicità dei suoi
elementi; mentre il **supporto** di un multiset è l'insieme (senza ripetizioni)
dei suoi elementi.

Molte operazioni e proprietà degli insiemi possono essere opportunamente
definite per i multiset. In particolare, l'**unione** di due multiset `A` e `B`
è il multiset `U` che ha per supporto l'unione dei supporti di `A` e `B` tale
per cui la molteplicità di ciascuno elemento `u` in `U` è pari alla *massima*
tra la molteplicità di `u` in `A` e in `B`; similmente l'**intersezione** di `A`
e `B` è il multiset `I` che ha per supporto l'intersezione dei supporti di `A` e
`B` tale per cui la molteplicità di ciascuno elemento `u` in `U` è pari alla
*minima* tra la molteplicità di `u` in `A` e in `B`

### Cosa dovete implementare

Può scegliere se implementare un *multiset* di `String` oppure di tipo
*generico* (maggiori dettagli a questo riguardo saranno dati in seguito). 

Nel primo caso dovrà produrre almeno due implementazioni distinte
dell'interfaccia

```java
interface StringMultiSet extends Iterable<String> {
  int add(String s);
  int remove(String s);
  boolean contains(String s);
  int multiplicity(String s);
  int size();
  StringMultiSet union(StringMultiSet o);
  StringMultiSet intersection(StringMultiSet o);
}
```

dopo aver specificato nel dettaglio i vari metodi che, rispettivamente, devono:

* aggiungere un elemento a questo multiset, restituendo la molteplicità di tale
  elemento *dopo* l'inserimento;
* rimuovere un elemento da questo multiset, restituendo la molteplicità di tale
  elemento *prima* della rimozione (ignorando le richieste di rimuovere
  elementi non presenti nel multiset);
* restituire `true` se e solo se l'elemento specificato appartiene a questo
  multiset;
* restituire la *molteplicità* dell'elemento in questo multiset (restituendo 0
  se l'elemento non appartiene al multiset);
* restituire la *cardinalità* di questo multiset;
* restituire il multiset ottenuto come *unione* di questo multiset con quello
  indicato come argomento (senza modificare questo multiset, o quello passato come argomento);
* restituire il multiset ottenuto come *intersezione* di questo multiset con
  quello indicato come argomenti (senza modificare questo multiset, o quello passato come argomento);

noti che inoltre il multiset deve poter iterare gli elementi del suo *supporto*
(senza ripetizioni).

Le diverse implementazioni devono essere basate su rappresentazioni tra loro
diverse del multiset (a titolo d'esempio su una lista con ripetizioni e su una
mappa da stringhe a interi e così via — o sulle strutture dati che riterrete più
opportune). 

L'efficienza delle implementazioni (in termini di costo computazionale in spazio
e tempo) non costituirà elemento di valutazione, altrimenti detto,
implementazioni poco efficienti *non* saranno penalizzate. 

Non è richiesto l'*overriding* dei metodi `euqals` e `hashCode`, se nonostante
questa indicazione decidesse di fornire una implementazione presti grande
attenzione alla sua correttezza: implementazioni incomplete, o errate, saranno
penalizzate nella valutazione!

#### Multiset generici

Al fine di migliorare la valutazione, se ha familiarità con i tipi generici, può
scegliere di implementare una versione generica del *multiset* che soddisfi la
seguente interfaccia (invece di quella specificata in precedenza):

```java
interface MultiSet<E> extends Iterable<E> {
  int add(E e);
  int remove(Object o);
  default boolean contains(Object o);
  int multiplicity(Object o);
  int size();
  MultiSet<E> union(MultiSet<? extends E> o);
  MultiSet<E> intersection(MultiSet<? extends E> o);
}
```

i cui metodi, a meno del tipo `E` dell'elemento che in questo caso non è
specificato in quanto parametrico, hanno lo stesso significato di quelli
dell'interfaccia precedente.

Anche in questo caso, è richiesta l'implementazione di almeno due versioni
basate su rappresentazioni distinte; è però fortemente sconsigliato procedere
con entrambe le interfacce (provvedendo quindi almeno quattro implementazioni
distinte): scelga da principio se seguire solo la strada del tipo concerto
(*multiset* di stringhe) o generico.

### La classe di test

Scrivete una classe che abbia un metodo statico `main`  che legga dal flusso di
ingresso due linee contenenti ciascuna una sequenza di parole separate da spazi
e costruisca due multiset a partire da essi; una volta letto l'ingresso, la
classe deve emettere sul flusso d'uscita, uno per linea, i due insiemi e la loro
intersezione e unione, precedendo ogni insieme dall'indicazione della sua
cardinalità.
### Esempio

Leggendo dal flusso di ingresso

    tre uno due uno tre tre 
    quattro due tre tre due

il programma emette

    6 {tre: 3, uno: 2, due: 1}
    5 {due: 2, tre: 2, quattro: 1}
    8 {tre: 3, uno: 2, due: 2, quattro: 1}
    3 {tre: 2, due: 1}

a meno dell'ordine in cui sono riportati gli elementi del supporto (ciascuno dei
quali è seguito dalla sua molteplicità).

## Soluzione

Data l'estrema semplicità del tema qui svilupperemo esclusivamente la soluzione
basata sui *generici*; la soluzione per le sole stringhe può essere ottenuta a
partire dal codice della presente soluzione sostanzialmente a patto di fare
qualche banale sostituzione (del tipo parametrico `E` col tipo `String`).

Riguardo alla documentazione dell'interfaccia, oltre a tradurre in commenti le
specifiche del testo, dato che è assolutamente evidente che i metodi debbano
essere *totali*, è bene occuparsi delle eventuali *eccezioni* che dovranno
essere sollevate per i valori ritenuti "inaccettabili" come argomenti.

