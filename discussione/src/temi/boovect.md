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