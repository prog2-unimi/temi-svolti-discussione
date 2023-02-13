# Fatal error!

Il titolo di questa sezione richiama il messaggio d'errore con cui alcuni
software, tra i quali alcuni compilatori, segnalano l'impossibilità di portare a
termine la propria computazione.

Per analogia, la presenza anche in un solo sorgente di uno o più dei difetti
elencati di seguito, sopratutto se ripetuta al punto da non poter essere
giustificata con la semplice distrazione, può sospendere il processo di
valutazione da parte del docente e condurre alla bocciatura.

Alcuni difetti sono, senza appello, l'indicazione di una profonda lacuna o
incomprensione di uno o più tra gli argomenti centrali dell'insegnamento; in
alcuni casi, certe scelte possono essere accettabili solo se *motivate in modo
esplicito e convincente* nella documentazione allegata all'implementazione.

## Programmazione di base

Mancanze che riguardano la programmazione in sé, ancor prima che orientata agli
oggetti, che sono da considerarsi molto gravi:

* l'incapacità di leggere l'input dal *flusso di ingresso standard*;
* l'incapacità di trattare gli *argomenti sulla linea di comando*;
* confondere gli argomenti sulla linea di comando col flusso di ingresso
  standard;
* confondere il concetto di funzione o metodo che *restituisce un valore*
  (tramite l'istruzione `return`) da quello di funzione o metodo che *emette un
  valore* nel flusso di uscita standard (ad esempio tramite l'invocazione del
  metodo `System.out.println`).

## Trascurare i `null`

Quando si ha a che fare con tipi di dato che funzionano da "contenitori", come
ad esempio gli array (di tipi non primitivi) o i tipi offerti dal "Collections
Framework", oltre ad assicurarsi che gli attributi, parametri o variabili
locali, che si riferiscono a essi non abbiano valore `null`, è generalmente
necessario accertarsi che non contengano riferimenti a `null`. Sono rari i casi
in cui la presenza di `null` nel contenitore sia legittima e, nel caso, vanno
debitamente documentati nell'invariante di rappresentazione e nella funzione di
astrazione.

Ad esempio, onde evitare che il parametro `unaLista` di tipo `List<T>` non si
riferisca e non contenga riferimenti a `null` è necessario fare

```java
unaLista != null && !unaLista.contains(null)
```

Omettere il controllo sulla presenza di riferimenti a `null` tra gli elementi
del "contenitore", se non esplicitamente consentiti, è un errore estremamente
grave.

## Tipi primitivi e `null`

D'altro canto, è assolutamente evidente che gli attributi, parametri o variabili
locali di tipo primitivo (`boolean`, `byte`, `short`, `int`, `char`, `long`,
`float` e `double`) non possano avere valore `null`, pertanto è un errore
estremamente grave controllare che ciò non accada. 

Ad esempio, se `unIntero` è un attributo di tipo `int` l'espressione

```java
Objects.requireNonNull(unIntero)
```

costituisce un errore estremamente grave, al punto che `unIntero != null` è
addirittura un errore sintattico!

### Le classi *wrapper*

Parte della confusione relativa ai tipi primitivi e il valore `null` può
derivare dall'uso delle classi *wrapper* (`Boolean`, `Byte`, `Short`, `Integer`,
`Character`, `Long`, `Float` e `Double`) in luogo dei corrispondenti tipi
primitivi, magari "nascosto" grazie al *boxing* e *unboxing* automatico.

L'uso delle classi *wrapper* al di fuori dell'uso che le rende necessarie, come
ad esempio nei parametri di tipo nei generici (come nel "Collections
Framework"), è da considerarsi errore grave.

Ad esempio, un metodo con la seguente segnatura
```java
Boolean isPositive(Integer x) { ... }
```
è da considerarsi errato a prescindere da come sia effettivamente implementato.

## Confronti con `==`

L'operatore binario `==` può essere usato per confrontare sia espressioni di
tipo primitivo che per i riferimenti. In questo caso, però, si comporta in modo
spesso diverso da come ce lo si aspetta.

In particolare, è un errore estremamente grave confrontare espressioni di tipo
`String` con `==` invece che col metodo `equals` se l'intento è verificare che
si tratti di stringhe uguali.

Ad esempio, se `unaStirnga` è una variabile locale di tipo `String`
l'espressione

```java
unaStringa == "un valore"
```

costituisce un errore estremamente grave.

## I metodi `equals` e `hashCode` (e `compareTo`)

Sono errori estremamente gravi:

* sovrascrivere (*override*) l'implementazione di `equals`, ma non di `hashCode`,
* sovrascrivere (*override*) l'implementazione di `hashCode`, ma non di `equals`, 
* sovrascrivere (*override*) l'implementazione di `equals` e `hashCode` senza
  rispettare i contratti definiti per entrambi i metodi in `Object`.

Sono generalmente da considerarsi errori:

* sovrascrivere (*override*) senza fare uso di `Objects.hash` o seguendo la
  *best practice* illustrata a lezione e nel libro di testo,
* sovrascrivere (*override*) l'implementazione di `hashCode` facendo uso di
  `Object.getClass`;
* qualora si intenda implementare l'interfaccia `Comparable<T>`, implementare il
  metodo `compareTo` senza attenersi al suo contratto che raccomanda fortemente
  che il comportamento di tale metodo sia *consistente* con quello di `equals`.

In sintesi: sovrascrivere (*override*) in modo corretto i metodi `equals`,
`hashCode` (e `compareTo`) è molto complesso; sarebbe meglio evitare di farlo a
meno che ciò corrisponda ad una esplicita richiesta del tema, o una necessità
cogente della vostra soluzione.

## Eccezioni

Dato lo scarso tempo a disposizione per lo svolgimento dell'esame, è da
considerarsi discutibile l'implementazione di eccezioni che non siano
strettamente necessarie. In ogni caso, come per tutte le altre classi, è errore
grave non scriverne la specificazione (*overview*).

In particolare, è comunque errore grave *definire* eccezioni che siano copia
esatta (per semantica e circostanze di utilizzo) di eccezioni presenti nella
gerarchia delle API standard del linguaggio.

Riguardo alla *gestione* delle eccezioni, è errore particolarmente grave gestire
le eccezioni mettendo nel corpo del `catch` delle istruzioni che hanno il solo
scopo di produrre output (nel flusso d'uscita, o d'errore standard), o causare
l'arresto del programma (tramite l'invocazione del metodo `System.exit`).

## Rappresentazione dello stato

Una corretta scelta della rappresentazione è tra gli obiettivi centrali
dell'insegnamento. 

### Visibilità e osservabili

L'errore più grave è consentire che lo stato venga alterato dall'esterno della
classe senza controllo, quando questo può causare una violazione dell'invariante
di rappresentazione.

Per questa ragione è necessario prestare assoluta attenzione alla visibilità
degli attributi, in particolar modo di quelli mutabili; in generale, per gli
attributi che non siano di tipo primitivo o `String` la scelta migliore è l'uso
della visibilità `private` e l'aggiunta dei necessari metodi osservazionali.

Un errore estremamente grave è però implementare classi che abbiano parte del
proprio stato non osservabile, o "osservabile" solo attraverso il metodo
`toString`; non è accettabile che, una volta introdotta dell'informazione in una
istanza, non ci sia modo di accederla convenientemente!

Per esempio, la seguente classe

```java
public class Persona {
  private final String nome, cognome;
  public Persona(String nome, String cognome) {
    if (Objects.requireNonNull(nome).isEmpty()) throw new IllegalArgumentException();
    if (Objects.requireNonNull(cognome).isEmpty()) throw new IllegalArgumentException();
    this.nome = nome
    this.cognome = cognome;
  }
  @Override String toString() {
    return cognome + " " + nome;
  }
}
```

è inaccettabile dal momento che è impossibile ottenere il cognome o il nome
della persona (che succede se contengono uno spazio)?

### Esporre la rappresentazione

Ci sono due "punti" critici in cui la rappresentazione interna alla classe può
"sfuggire" dal controllo.

Quando lo stato viene ricevuto dall'esterno (come in un costruttore, o in un
metodo mutazionale) e quando lo stato viene mostrato all'esterno (come in un
metodo osservazionale, in particolare in un iteratore).

Immagazzinare un riferimento ad un oggetto mutabile esterno alla classe, così
come cedere un riferimento ad un oggetto mutabile interno alla classe è in
genere un errore grave.

Ad esempio, il frammento di codice

```java
class AClass {
  private final List<String> unaLista;
  public AClass(List<String> unaLista) {
    if (Objects.requireNonNull(unaLista).contains(null)) throw new IllegalArgumentException();
    this.unaLista = unaLista;
  }
}
```

non offre alcuna possibilità di garantire che l'assenza di riferimenti a `null`
nell'attributo `unaLista` sia preservato dopo la costruzione; si consideri
infatti il seguente uso della classe

```java
List<String> unaLista = new ArrayList<>();
unaLista.add("uno");
AClass aClass = new AClass(unaLista);
unaLista.add(null);
```

che mostra come, nonostante i controlli in costruzione, la lista interna alla
fine sarà `["uno", null]`.

In modo analogo, il frammento di codice

```java
class ABetterClass {
  private final List<String> unaLista;
  public ABetterClass(List<String> unaLista) {
    if (Objects.requireNonNull(unaLista).contains(null)) throw new IllegalArgumentException();
    this.unaLista = new ArrayList<>(unaLista);
  }
  public List<String> unaLista() {
    return unaLista;
  }
}
```

non offre comunque alcuna possibilità di garantire che l'assenza di riferimenti
a `null` nell'attributo `unaLista` sia preservato dopo la costruzione; si
consideri infatti il seguente uso della classe

```java
List<String> unaLista = new ArrayList<>();
unaLista.add("uno");
ABetterClass aBetterClass = new ABetterClass(unaLista);
unaLista = aBetterClass.unaLista();
unaLista.add(null);
```

che mostra come, nonostante i controlli in costruzione, la lista interna alla
fine sarà `["uno", null]`.

Particolare attenzione va rivolta agli *iteratori*, ricordando che essi
comprendono anche il metodo mutazionale `remove()`, che può consentire modifiche
inattese.

Ad esempio, il frammento di codice

```java
class ANonEmptyClass implements Iterable<String> {
  private final List<String> unaLista;
  public ANonEmptyClass(List<String> unaLista) {
    if (Objects.requireNonNull(unaLista).isEmpty()) throw new IllegalArgumentException();
    this.unaLista = new ArrayList<>(unaLista);
  }
  public Iterator<String> iterator() {
    return unaLista.iterator();
  }
}
```

non offre alcuna possibilità di garantire che la presenza di almeno un elemento
nell'attributo `unaLista` sia preservato dopo la costruzione; si consideri
infatti il seguente uso della classe

```java
ANonEmptyClass aNonEmptyClass = new ANonEmptyClass(List.of("uno"));
Iterator<String> it = aNonEmptyClass.iterator();
it.next();
it.remove();
```

che mostra come, nonostante i controlli in costruzione, la lista interna alla
fine sarà vuota.

### Appropriatezza dei tipi di dato

Tranne che in casi eccezionali, l'uso del tipo `String` per rappresentare
informazioni che non siano stringhe è errore grave.

Similmente, sebbene sia possibile comporre arbitrariamente le strutture dati
offerti dal "Collections Framework", l'uso di imporbabili matrioske è indicativo
di scelte spesso discutibili.

Tanto per citare un esempio, una dichiarazione come

```java
Map<List<String>, Set<List<String>>>
```

molto improbabilmente è una ragionevole rappresentazione dello stato di una
classe.


## Classi astratte, interfacce ed ereditarietà

Sono in genere errori gravi:

* descrivere *interfacce* che non abbiano metodi (astratti, o di default);
* implementare *classi astratte* che non abbiano metodi astratti (in altre
  parole, che potrebbero essere dichiarate concrete senza che che avvengano
  altre modifiche nel codice, pur continuando a funzionare);
* implementare *sottoclassi* "vuote" che non aggiungano attributi o metodi e non
  sovrascrivano (*override*) alcun metodo della classe che estendono o
  dell'interfaccia che implementano;
* implementare metodi di istanza che non accedono a attributi o metodi
  d'istanza (in altre parole, che potrebbero essere dichiarati `static` senza
  che il corpo ne venga modificato, pur continuando a funzionare);
* implementare *inner class* (classi annidate non `static`) che non accedono ad
  attributi o metodi dell'istanza in cui sono annidate (in altre parole, che
  potrebbero essere dichiarate `static` senza che il corpo ne venga modificato,
  pur continuando a funzionare);
* implementare classi annidate che non hanno restrizioni di visibilità (in altre
  parole, che potrebbero essere spostate in un file come classi non annidate
  senza che avvengano altre modifiche al codice, pur continuando a funzionare);
  unica eccezione può essere la necessità (esplicitata e giustificata) di
  restringere la denominazione.
