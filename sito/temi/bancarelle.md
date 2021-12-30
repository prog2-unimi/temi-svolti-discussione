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
sol = Solution('bancarelle')
```
# Bancarelle

:::{warning}
Questa versione della soluzione è **preliminare** e potrebbe subire modifiche.
:::

## La traccia

### Descrizione

Se siete andati in vacanza al mare, vi sarà senz'altro capitato di vedere che
(specialmente alla fine della stagione) i bambini organizzano sulle spiagge, o
nelle piazzette, delle bancarelle per vendere i giocattoli usati. Spesso si
trova lo stesso giocattolo in bancarelle diverse e ciascun bambino decide a modo
suo i prezzi, talvolta inventandosi sconti e offerte per attrarre i compratori.

Scopo del progetto è modellare le entità coinvolte in questa attività in modo da
poter rappresentare più giocattoli offerti in diverse bancarelle secondo diverse
politiche di prezzo e dei compratori che acquistino, potenzialmente seguendo
differenti strategie d'acquisto, un certo numero di giocattoli.

Ad esempio, date queste bancarelle

    Bancarella di: Massimo
    num. 2 bilia di vetro, prezzo: 2
    num. 3 cane di pezza, prezzo: 10
    num. 10 elastico di gomma, prezzo: 1
    num. 1 soldatino di stagno, prezzo: 3

    Bancarella di: Carlotta
    num. 4 bilia di vetro, prezzo: 1
    num. 10 braccialetti di perline, prezzo: 3
    num. 1 soldatino di stagno, prezzo: 5

    Bancarella di: Federico
    num. 10 bilia di vetro, prezzo: 3
    num. 1 cane di pezza, prezzo: 5
    num. 10 soldatino di stagno, prezzo: 2

un compratore che volesse acquistare `11` giocattoli `soldatino di stagno`
potrebbe, ad esempio, effettuare il seguente acquisto

    Acquisto di: soldatino di stagno, per un costo di: 23, numero: 11 di cui:
    10 da Federico
    1 da Massimo

#### La bancarella: giocattolo, inventario e listino prezzi

Per semplicità assumeremo che ciascun **giocattolo** abbia un nome
(rappresentato con una *stringa*) e sia fatto di un dato materiale (anch'esso
rappresentato da una *stringa*); ad esempio, una bambola di pezza, una bilia di
vetro e una bilia di marmo sono tre giocattoli gli ultimi due differiscono nel
materiale, ma non nel nome. Due giocattoli sono uguali se e solo se hanno lo
stesso nome e sono fatti dello stesso materiale.

Ogni bancarella offre un certo insieme di giocattoli, per tener traccia di
quanti e quali giocattoli offra in un certo momento è utile usare un
**inventario**: una classe in grado di tener traccia dei giocattoli man mano
aggiunti ed eliminati (ad esempio perché venduti) dalla bancarella.

Il gestore di ogni bancarella può decidere diverse *politiche di prezzo* per
ciascun giocattolo: ad esempio, può fissare un prezzo unitario `U` per un dato
giocattolo e stabilire che il prezzo di `N` giocattoli identici a esso sia dato
*moltiplicativamente* da `U * N`, oppure applicare degli *sconti* (per esempio,
se `N` supera la decina, applicare un 15% di sconto sulle unità eccedenti la
decina, in modo che il prezzo finale sia `10 * U + (N - 10) * U * 85 / 100`), o
vendere "tre giocattoli al prezzo di due", e così via.

Un modo ragionevole di rappresentare queste politiche è definire un **listino**
che, dato un giocattolo e la quantità da acquistare, restituisca il prezzo
complessivo; più precisamente, descrivete una *interfaccia* `Listino` e almeno
una *classe* che la implementi (ad esempio, quella che descriva la semplice
politica moltiplicativa).

Ogni **bancarella** è identificata da un *proprietario* (che è rappresentato
tramite una stringa) e ha i suoi *inventario* e *listino*; evidentemente il
listino deve permettere di conoscere il prezzo di ciascun giocattolo presente
nell'inventario.

Una bancarella deve poter indicare quanti giocattoli di un certo tipo è in grado
di vendere e a che prezzo, nonché procedere alla vendita (aggiornando
l'inventario).

#### Compratore e acquisto

Se più bancarelle offrono lo stesso giocattolo, il *compratore* che intenda
acquistarne una certa quantità, può comporre il suo **acquisto** in modi
diversi, decidendo di acquistare un diverso numero di giocattoli dalle varie
bancarelle che lo offrono, magari cercando di minimizzare il prezzo totale.

L'acquisto (di un determinato giocattolo) è pertanto caratterizzato da: il
*giocattolo* stesso, la *quantità* acquistata e il *prezzo* pagato, nonché
dall'elenco delle *bancarelle*, ciascuna accompagnata dal numero di giocattoli
che ha venduto.

Implementate la classe `Acquisto` che consenta di gestire tali informazioni. Un
esempio di acquisto potrebbe essere

    Acquisto di: soldatino di stagno, per un costo di: 23, numero: 11 di cui:
    10 da Federico
    1 da Massimo

Finalmente è arrivato il momento di occuparsi del **compratore**. Questo, una
volta noto l'insieme di *bancarelle* da cui fare acquisti, può comprare un certo
numero di giocattoli di un dato tipo seguendo diverse strategie: comprando dalla
bancarella che esibisce il minor prezzo unitario, o dalle bancarelle che hanno
maggior disponibilità del giocattolo, o scegliendo a caso da quali bancarelle
comprare.

:::{seealso} Osservate che determinare una strategia "ottima" è in generale
molto difficile e non è affatto richiesto per portare a termine il progetto, gli
studenti curiosi possono farsi una idea della questione sfogliando ad esempio
l'articolo [Allocating procurement to capacitated suppliers with concave
quantity
discounts](https://www.sciencedirect.com/science/article/abs/pii/S0167637707000648)
o li rapporto tecnico [An exact method for the Capacitated Total Quantity
Discount Problem](https://or-dii.unibs.it/doc/tr/RT_2011-02-66.pdf).
:::

Potrebbe aver senso raccogliere alcune competenze comuni a tutti i compratori in
una *classe astratta* fornendo poi delle implementazioni concrete che realizzino
in modo diverso le varie strategie d'acquisto.

In ogni modo, la classe concreta dovrà avere almeno un costruttore che riceva un
parametro di tipo `Collection<Bancarella>` e un metodo di segnatura

    public Acquisto compra(final int num, final Giocattolo giocattolo)

che sarà usata per effettuare l'acquisto.


#### La classe di test

Per ottenere la classe di test di questo esercizio, partite dalla *bozza di
sorgente* della funzione `main` così definita

```{code-block} java
  public static void main(final String[] args) {

    /* Lettura dei parametri dalla linea di comando */
    final int numDaComprare = Integer.parseInt(args[0]);
    final Giocattolo giocattoloDaComprare = new Giocattolo(args[1], args[2]);

    /* Lettura del flusso di ingresso */
    final Scanner s = new Scanner(System.in);

    final int numBancarelle = s.nextInt();
    final List<Bancarella> bancarelle = new ArrayList<>(numBancarelle);
    final Map<Giocattolo, Integer> giocattolo2prezzo = new HashMap<>();
    final Inventario inventario = new Inventario();

    for (int b = 0; b < numBancarelle; b++) {
      /* Lettura di una bancarella */
      final String proprietario = s.next();
      final int numGiochi = s.nextInt();
      for (int g = 0; g < numGiochi; g++) {
        /* Lettura dei giochi della bancarella */
        final int num = s.nextInt();
        final String nome = s.next();
        final String materiale = s.next();
        final int prezzo = s.nextInt();
        final Giocattolo giocattolo = new Giocattolo(nome, materiale);
        inventario.aggiungi(num, giocattolo);
        giocattolo2prezzo.put(giocattolo, prezzo);
      }

      /*
        MODIFICARE: aggiungere l'istanziazione del listino, es:

        final Listino listino = new ListinoConcreto(giocattolo2prezzo);
      */

      final Bancarella bancarella = new Bancarella(proprietario, inventario, listino);
      bancarelle.add(bancarella);
    }

    s.close();

    /*
      MODIFICARE: aggiungere l'istanziazione del compratore, es:

      final Compratore compratore = new Compratore(bancarelle);
    */

    final Acquisto ordine = compratore.compra(numDaComprare, giocattoloDaComprare);
    System.out.println(ordine);
  }
```

L'*input* a tale classe è fornito sia dai parametri sulla linea di comando (che
indicano quale giocattolo comprare e in che quantità), che dal flusso di
ingresso (che contiene una descrizione delle bancarelle).

I tre parametri sulla linea di comando indicano rispettivamente il numero di
giocattoli da comprare, il nome e il materiale del giocattolo. Il flusso di
ingresso contiene i seguenti elementi (separati da *white-space*):

* un intero positivo corrispondente al numero di bancarelle;
* per ciascuna bancarella:
  * una stringa corrispondente al nome del proprietario,
  * il numero di giocattoli della bancarella,
  * una quaterna di valori per ciascun giocattolo corrispondenti al:
    * numero,
    * nome,
    * materiale e
    * prezzo unitario.

Una volta costruito il compratore, esso dovrà effettuare l'acquisto specificato
ed emetterlo nel flusso d'uscita (secondo il formato dato dall'esempio).

##### Esempio

Eseguendo `soluzione 11 soldatino stagno` e avendo

    3
    Massimo 4
    3 cane pezza 10
    2 bilia vetro 2
    10 elastico gomma 1
    1 soldatino stagno 3
    Carlotta 2
    10 braccialetti perline 3
    4 bilia vetro 1
    Federico 3
    10 soldatino stagno 2
    10 bilia vetro 3
    1 cane pezza 5

nel flusso d'ingresso, il programma emette

    Acquisto di: soldatino di stagno, numero: 11, per un costo di: 23
    Federico 10
    Massimo 1

nel flusso d'uscita.

## Soluzione

