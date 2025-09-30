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

# Input/output

Questa sezione contiene alcuni suggerimenti su come effettuare l'input/output
(in senso lato) usando le API di Java (con esempi di codice sorgente che possono
essere liberamente copiati e adattati nella soluzione della prova pratica).

## Argomenti sulla linea di comando

Per *argomenti sulla linea di comando* si intendono tutte le parole (stringhe
massimali non contenenti spazio) che seguono il nome della classe
nell'invocazione della JVM. Ad esempio, se avete compilato una classe di nome
`Soluzione` e ne invocate l'esecuzione tramite l'interprete come
```{code-block} shell
java Soluzione uno          2 tr_e
```
gli argomenti saranno le tre parole: `uno`, `2` e `tr_e`.

La funzione `main` che ha segnatura
```{code-block}
public static void main(String[] args);
```
può accedere a tali parole tramite l'array `args` il cui $i$-esimo puntatore
punta alla stringa corrispondente all'$i$-esimo argomento (l'argomento di posto
0 è la prima parola).

Osservate che gli argomenti sono *stringhe*, qualora sia richiesto trattare
alcuni di essi come numeri sarà necessario usare una funzione di conversione,
come ad esempio `parseT` delle varie sottoclassi di
[`Number`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Number.html)
(dove `T` è uno dei tipi primitivi), come ad esempio con il metodo
[`parseInt`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Integer.html#parseInt(java.lang.String))
di
[`Integer`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Integer.html).

Si riporta, a titolo di esempio, un programma che, dati per argomenti
alcuni numeri interi, ne stampa la somma
```{code-cell}
public class SommaArgs {
  public static void main(String[] args) {
    int somma = 0;
    for (String arg : args)
      somma += Integer.parseInt(arg);
    System.out.println(somma);
  }
}
```
che, invocato ad come `java SommaArgs 1 2 3`, produce l'output
```{code-cell}
:tags: [remove-input]
SommaArgs.main(new String[] {"1", "2", "3"})
```

## Input/Output

Di seguito sono riportati alcuni scampoli di codice Java necessari a gestire
l'input in formato testuale che tipicamente è richiesto dalla soluzione degli
esercizi di laboratorio e d'esame, nonché un breve accenno a come formattare
l'output.

### Input

La gestione di tale input può essere organizzata secondo due coppie indipendenti
di varianti a seconda

1. di come viene consumato

    1.  come sequenza di linee,
    2.  *tokenizzato* come sequenza di tipi primitivi (`int`, `float`, ...) e *stringhe*;

2. che provenga

    1. dal flusso standard
       ([`System.in`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/System.html#in)),
    2. da un file (indicato tramite il suo *path*).

Facendo uso dell'istruzione
[try-with-resources](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html)
(che consente, tra l'altro, di gestire in modo automatico il rilascio delle
risorse in caso di errore) il codice ha in generale la seguente struttura
```{code-block}
α try (... in = new ...(...)) {
β  while (/* c'è input */)
β    /* consuma l'input */
α }
```
ed è organizzato in due parti
* istanziazione (e gestione) di un oggetto che rappresenti l'input (α),
* ciclo che consuma (ed elabora) l'input (β).

Secondo l'organizzazione logica discussa all'inizio, il modo in cui sarà
consumato (1.) e l'origine dell'input (2.) daranno luogo a quattro diverse
implementazioni della parte (α), mentre la modalità in cui l'input sarà
consumato (1.) darà luogo a due diverse implementazioni della parte (β).

#### Parte (α): istanziare l'oggetto usato per l'input

Per leggere l'input una linea dopo l'altra (1.1.) è sufficiente usare un
[`BufferedReader`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/io/BufferedReader.html).
Il costruttore di tale classe ha per parametro un
[`Reader`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/io/Reader.html),
che può essere istanziato (2.1.) come un
[`InputStreamReader`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/io/InputStreamReader.html)
che a sua volta avvolga
[`System.in`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/System.html#in),
o (2.2.) come un
[`FileReader`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/io/FileReader.html).

D'altro canto, per *tokenizzare* l'input (1.2.) è sufficiente usare uno
[`Scanner`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Scanner.html).
Il costruttore di tale classe ha per parametro un
[`InputStream`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/io/InputStream.html),
che può essere direttamente (2.1.) un
[`System.in`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/System.html#in),
o istanziato (2.2.) come
[`FileInputStream`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/io/FileInputStream.html).

Le quattro versioni della parte α del codice sono pertanto:
```{code-block}
(1.1., 2.1.)  BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
(1.1., 2.2.)  BufferedReader in = new BufferedReader(new FileReader(path));
(1.2., 2.1.)  Scanner in = new Scanner(System.in);
(1.2., 2.2.)  Scanner in = new Scanner(new FileInputStream(path));
```
dove si assume che `path` sia una variabile di tipo stinga che contiene il
*path* del file che contiene l'input.

#### Parte (β): consumare l'input

Per consumare (ed elaborare) l'input, sono sufficienti due solite
implementazioni della parte (β), dal momento che il tipo dell'oggetto `in` può
essere solo un
[`BufferedReader`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/io/BufferedReader.html)
o uno
[`Scanner`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Scanner.html),
a seconda di (1.), ma indipendentemente da (2.).

Per leggere una sequenza di linee (1.1.) si può utilizzare il metodo
[`readLine`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/io/BufferedReader.html#readLine());
per di più, tale metodo è in grado di segnalare la fine dell'input restituendo
il valore speciale `null`. Il ciclo che consuma l'input, in questo caso, è
```{code-block}
String linea = null;
while ((linea = in.readLine()) != null)
  /* consuma l'input */
```

##### Tipi primitivi

Per leggere una sequenza di tipi primitivi (1.2.) si possono utilizzare i metodi
`nextT` (dove `T` è uno dei tipi primitivi), ad esempio, per gli interi, si può
usare il metodo
[`nextInt`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Scanner.html#nextInt());
per sapere se l'input è finito (o se ci sono ancora a disposizione altri
elementi), si può usare il metodo `hasNextT` (dove `T` è, come sopra, uno dei
tipi primitivi), ad esempio, ancora nel caso degli interi
[`hasNextInt`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Scanner.html#hasNextInt()).
Il ciclo che consuma l'input, sempre nel caso degli interi, è
```{code-block}
while (in.hasNextInt()) {
  int intero = in.nextInt();
  /* consuma l'input */
}
```

##### Stringhe

Qualora sia necessario leggere delle stringhe (1.2.), intese come delle sequenze
massimali di caratteri diversi da *whitespace* (che sono spazio, segno di
tabulazione orizzontale e verticale e a-capo), si possono usare i metodi
[`next`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Scanner.html#next())
e
[`hasNext`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Scanner.html#hasNext())
in modo del tutto analogo al caso precedente
```{code-block}
while (in.hasNext()) {
  String stringa = in.next();
  /* consuma l'input */
}
```

#### Osservazioni ed esempi

Mettendo assieme gli esempi di codice delle parti (α) e (β) è possibile
elaborare l'input, come sequenza di linee o tipi primitivi, sia che provenga dal
flusso standard che da un file.

Un dettaglio utile da ricordare è che nella lettura del flusso standard da
console (senza redirezione, cioè), la **terminazione del flusso** va *segnalata
esplicitamente* tramite l'immissione dell'apposito carattere di controllo `^D`
denominato `EOF` (**end of file**), che si ottiene usualmente premendo assieme i
tasti `ctrl` e `d` (minuscolo).

Altro dettaglio importante è che *alcuni dei costruttori e metodi invocati
possono sollevare eccezioni* di tipo
[`IOException`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/io/IOException.html)
(o sue sottoclassi), che devono essere *opportunamente gestite* (sia che il
codice sia avvolto dalla `try-with-resources` o meno). Nel contesto della prova
d'esame, qualora tali metodi fossero invocati all'interno del metodo `main`, una
soluzione plausibile è quella di aggiungere `throws IOException` alla
dichiarazione di tale metodo (come nel codice riportato di seguito).

A titolo di esempio, riportiamo due piccoli programmi. Il primo legge l'input
dal flusso standard ed emette ogni riga preceduta dal suo numero progressivo
```{code-cell}
public class NumeraLinee {
  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
      int n = 0;
      String linea;
      while ((linea = in.readLine()) != null)
        System.out.println(String.format("%02d: %s", ++n, linea));
    }
  }
}
```
Per provare il suo funzionamento è possibile usare il comando `java java
NumeraLinee < NumeraLinee.java` che, facendo uso della *redirezione* dell'input,
numererà le linee del programma stesso producendo l'output
```{code-cell}
:tags: [remove-input]
InputStream is = new ByteArrayInputStream((
  "public class NumeraLinee {\n" +
  "  public static void main(String[] args) throws IOException {\n" +
  "    try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {\n" +
  "      int n = 0;\n" +
  "      String linea;\n" +
  "      while ((linea = in.readLine()) != null)\n" +
  "        System.out.println(String.format(\"%02d: %s\", ++n, linea)));\n" +
  "    }\n" +
  "  }\n" +
  "}\n").getBytes());
StringBuffer sb = new StringBuffer();
try (BufferedReader in = new BufferedReader(new InputStreamReader(is))) {
  int n = 0;
  String linea;
  while ((linea = in.readLine()) != null)
    sb.append(String.format("%02d: %s\n", ++n, linea));
}
sb.toString()
```

Il secondo legge una sequenza di numeri in virgola mobile da un file il cui
*path* è specificato come parametro (all'invocazione della JVM), e ne stampa la
somma
```{code-cell}
public class SommaInput {
  public static void main(String[] args) throws IOException {
    String path = args[0];
    float somma = 0.0f;
    try (Scanner in = new Scanner(new FileInputStream(path))) {
      while (in.hasNextFloat()) {
        float numero = in.nextFloat();
        somma += numero;
      }
    }
    System.out.println(somma);
  }
}
```
Assumendo che esista un file `input.txt` che contenga:
```
1
2.5
3
```
eseguendo il programma con il comando `java SommaInput input.txt`, verrà
prodotto l'output
```{code-cell}
:tags: [remove-input]
InputStream is = new ByteArrayInputStream("1\n2.5\n3".getBytes());
float somma = 0.0f;
try (Scanner in = new Scanner(is)) {
  while (in.hasNextFloat()) {
    float numero = in.nextFloat();
    somma += numero;
  }
}
somma
```

Per concludere osservate che la classe `Scanner` ha un
[costruttore](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Scanner.html#%3Cinit%3E(java.lang.String))
che accetta una stringa come argomento (e quindi attingerà da tale stringa per
rispondere alle varie chiamate di `nextT` e `hasNextT`); considerate ad esempio
l'esecuzione di
```{code-cell}
try (Scanner linea = new Scanner("somma 1 e 3.2")) {
  System.out.println(
    "Prima parola: " + linea.next() + ",\n" +
    "doppio del primo intero: " + 2 * linea.nextInt() + ",\n" +
    "seconda parola: " + linea.next() + ",\n" +
    "metà dell'ultimo float: " + linea.nextFloat() / 2
  );
}
```
che mostra che chiamate consecutive dei metodi `next` consentono di
"decodificare" le parti della stringa `somma 1 e 3.2` a seconda del loro tipo
(primitivo o stringa).

:::{hint}
Grazie al fatto che può essere costruita a partire da una stringa, la classe
`Scanner` può essere utilizzata per realizzare una sorta di "parser" in grado di
decodificare un input costituito da linee ciascuna delle quali sia a sua volta
costituita da parti (separate da spazi) corrispondenti a tipi primitivi (o
stringhe) secondo un assegnato "formato"; ad esempio
```{code-block}
try (Scanner in = new Scanner(System.in)) {
  while (in.hasNextLine())
    try (Scanner linea = new Scanner(in.nextLine())) {
      /* consuma le parti della linea */
    }
}
```
leggerà il flusso standard una linea alla volta dallo scanner `in` e per
ciascuna di esse costruirà lo scanner `linea` che potrà essere usato come
nell'esempio precedente per "decodificare" le parti della linea che
corrispondono ai vari tipi primitivi (e stringhe) indicati dal "formato"
specificato.
:::

#### Altri approcci

La ricchezza delle API di Java rende possibile risolvere il problema descritto
in questa guida in molti altri modi. Questo è certamente una ricchezza, ma
produce anche molta confusione in chi si avvicina per la prima volta al
linguaggio e alle sue librerie.

Ad esempio, l'input di tipi primitivi potrebbe anche essere implementato
leggendo l'input per linea, suddividendo poi la linea con uno
[`StringTokenizer`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/StringTokenizer.html),
o con il metodo
[`split`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/String.html#split(java.lang.String))
di
[`String`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/String.html),
traducendo in fine le singole parti nei tipi primitivi con i metodi `parseT`
delle varie sottoclassi
[`Number`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Number.html)
(dove `T` è uno dei tipi primitivi), come ad esempio con il metodo
[`parseInt`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Integer.html#parseInt(java.lang.String))
di
[`Integer`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Integer.html).
Evidentemente, l'uso della classe
[`Scanner`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Scanner.html)
appare una soluzione molto più elementare a questo tipo di problema. In ogni
modo, una soluzione alternativa, in questo senso, dell'esercizio due potrebbe
essere la seguente:
```{code-cell}
public class SommaInputBis {
  public static void main(String[] args) throws IOException {
    String path = args[0];
    float somma = 0.0f;
    try (BufferedReader in = new BufferedReader(new FileReader(path))) {
      String linea = null;
      while ((linea = in.readLine()) != null) {
        float numero = Float.parseFloat(linea);
        somma += numero;
      }
    }
    System.out.println(somma);
  }
}
```

D'altro canto, a ben guardare, c'è un metodo
[`nextLine`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Scanner.html#nextLine())
tra quelli di
[`Scanner`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Scanner.html)
che si comporta sostanzialmente come il metodo
[`readLine`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/io/BufferedReader.html#readLine())
di
[`BufferedReader`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/io/BufferedReader.html);
in linea di principio, quindi, tutta la discussione si potrebbe di gran lunga
semplificare limitandosi ad utilizzare la classe
[`Scanner`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Scanner.html)
sia per leggere l'input linea per linea che in modo *tokenizzato*. Ma è altresì
vero che l'uso di una classe complessa come
[`Scanner`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Scanner.html)
per uno scopo così banale come quello di leggere l'input per linee sembra del
tutto sproporzionato; inoltre, tale classe ha fatto la sua comparsa solo nelle
versioni più recenti di Java, ragion per cui è bene conoscere anche alternative
che siano praticabili nel caso in cui si abbia a disposizione sono una versione
meno recente del linguaggio.

##### Java 25

Nelle versioni più recenti di java al pacchetto `java.lang` è stata aggiunta la
classe
[`IO`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/IO.html)
che provvede diversi metodi *statici* tra cui
[`readln`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/IO.html#readln())
e
[`println`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/IO.html#println())
che consentono rispettivamente di leggere e scrivere sullo standard input e
output in modo molto semplice.

Tramite tali metodi è possibile semplificare ulteriormente la lettura delle linee, come nell'esempio seguente
```{code-cell}
public class NumeraLineeSemplificato {
  public static void main(String[] args) throws IOException {
    int n = 0;
    String linea;
    while ((linea = IO.readln()) != null)
      IO.println(String.format("%02d: %s", ++n, linea));
  }
}
```

:::{warning} 
Proprio a causa del fatto che tali metodi sono *statici* non è possibile
utilizzarli per il testing se si è interessati a redirigere in modo
programmatico lo standard input. Per questa ragione **se ne sconsiglia l'uso**
qualora si adoperi [Jubbiot](https://github.com/prog2-unimi/jubbiot), come 
avviene **sia per le esercitazioni che per la prova d'esame**.
:::


#### Dati non testuali

Come ultima osservazione, si noti che in questa guida (per brevità e semplicità)
si è trattato solo il caso di file in formato, per così dire, testuale. Le API
di Java mettono a disposizione anche classi e metodi per il trattamento di dati
in formato binario (ad esempio, tramite le interfacce
[`DataInput`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/io/DataInput.html)
e
[`DataOutput`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/io/DataOutput.html)
e relative implementazioni), che meritano una discussione a se stante.

Una interessante aggiunta nelle API delle nuove versioni di Java è la classe
[`Files`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/nio/file/Files.html)
che mette a disposizione una serie di metodi statici per leggere (e scrivere)
con una sola chiamata l'intero contenuto di un file, come ad esempio il metodo
[`readAllBytes`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/nio/file/Files.html#readAllBytes(java.nio.file.Path))
che restituisce un array di `byte`, o il metodo
[`readAllLines`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/nio/file/Files.html#readAllLines(java.nio.file.Path))
che restituisce una
[`List`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/List.html)
di stringhe.

### Output (formattato)

Il modo più semplice di produrre output è attraverso il metodo
[``println``](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/io/PrintStream.html#println())
del flusso standard
[`System.out`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/System.html#out)
come mostrato negli esempi precedenti. 

Talvolta può essere utile formattare l'output in modo più sofisticato, ad esempio stabilendo un numero minimo di caratteri per ciascun campo. In questi casi può essere utile il metodo
[`String.format`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/String.html#format(java.lang.String,java.lang.Object...))
che consente di costruire una stringa formattata secondo un formato specificato secondo la classe [`Formatter`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Formatter.html).

Ad esempio, se `cents` è una variabile intera che rappresenta una valuta in
centesimi di euro, il comando
```{code-block}
System.out.println(String.format("Euro: %d.%02d", cents / 100, cents % 100));
```
stamperà il valore di `cents` in euro con due cifre decimali.

Per conoscere le possibilità di formattazione si rimanda alla documentazione che offre in particolare un [breve sommario](https://download.java.net/java/early_access/loom/docs/api/java.base/java/util/Formatter.html#summary) della sintassi della *stringa di formato*.

