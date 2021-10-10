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