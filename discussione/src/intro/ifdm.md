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

Questa sezione contiene alcuni suggerimenti sull'uso delle [Java Development Kit
API](https://docs.oracle.com/en/java/javase/17/docs/api/) che sono a
disposizione durante lo sviluppo; si osservi che **nessuna** delle informazioni
contenute in questo documento è *strettamente necessaria* per il superamento
dell'esame, tuttavia alcune dei suggerimenti seguenti possono *rendere molto più
rapido* lo sviluppo delle soluzioni e *aiutare ad evitare errori di
programmazione*.

## La classe `Objects`

La classe [java.util.Objects](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html) contiene alcuni metodi di utilità generale che riguardano gli oggetti in generale.

### Il calcolo dell'`hashCode`

Nel caso in cui si intendano sovrascrivere i metodi `equals` e `hashCode` di un
oggetto, il metodo [hash](
https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html#hash(java.lang.Object...)) (che è  [*variadico*](https://www.wikiwand.com/en/Variadic_function)) può risultare molto comodo.

Se `equals` viene sovrascritto come congiunzione dell'uguaglianza di (un
sottoinsieme degli) attributi dell'oggetto (diciamo `attr_1`, `attr_2`…
`attr_N`), allora `hashCode` può essere sovrascritto come
```{code-block} java
@Override
public int hashCode() {
  return Objects.hash(attr_1, attr_2… attr_N);
}
```
piuttosto che implementare la ricetta proposta nell'Item 11 del Capitolo 3 del libro di testo "Effective Java".

### Supporto nella gestione di `null`

Il metodo
[requireNonNull](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html#requireNonNull(T,java.lang.String))
consente di verificare se una espressione è `null` e, nel caso, sollevare una
`NullPointerException` col messaggio indicato; ad esempio:
```{code-block} java
Objects.requireNonNull(espressione, "Messaggio");
```
può essere usato come abbreviazione di:
```{code-block} java
if (espressione == null)
  throw new NullPointerException("Messaggio")
```
Dal momento che, qualora l'espressione non sia `null`, il metodo ne restituisce
il valore, esso può essere convenientemente usato in un assegnamento, o
invocazione di metodo; ad esempio
```{code-block} java
variabile = Objects.requireNonNull(espressione, "Messaggio");
```
può essere usato come abbreviazione di:
```{code-block} java
if (espressione == null)
  throw new NullPointerException("Messaggio")
variabile = espressione;
```
e similmente
```{code-block} java
Objects.requireNonNull(espressione, "Messaggio").metodo();
```
può essere usato come abbreviazione di:
```{code-block} java
if (espressione == null)
  throw new NullPointerException("Messaggio")
espressione.metodo();
```

Possono risultare comodi anche i metodi
[equals](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html#equals(java.lang.Object,java.lang.Object)),
[toString](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html#toString(java.lang.Object)) e [hashCode](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html#hashCode(java.lang.Object))
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

### Il controllo di appartenenza ad un intervallo

In molte circostanze può capitare di dover controllare se un indice (o un
intervallo di indici interi, che può essere specificato dandone gli estremi,
oppure l'estremo sinistro e la dimensione) appartiene ad un segmento iniziale
dei numeri naturali (specificato tramite la sua dimensione).

Il metodo
[checkIndex](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html#checkIndex(int,int))
e le sue varianti possono essere comodamente utilizzati a tale scopo.



```{code-cell}
int x = 3;
System.out.println(x);
```
