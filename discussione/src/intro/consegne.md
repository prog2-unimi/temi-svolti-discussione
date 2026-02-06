# Le consegne comuni ad ogni tema

Ogni tema d'esame, dopo una breve descrizione dell'obiettivo del lavoro,
prosegue con la seguente raccomandazione generale:

> Per portare a termine il lavoro dovrà decidere se e quali classi (concrete o
> astratte) e quali interfacce implementare. Per ciascuna di esse **dovrà
> descrivere** (in formato Javadoc attraverso commenti presenti nel codice) le
> scelte relative alla **rappresentazione** dello stato (con particolare
> riferimento all'**invariante di rappresentazione** e alla **funzione di
> astrazione** così come definiti nel libro di testo dell'insegnamento e
> illustrati a lezione) e ai **metodi** (con particolare riferimento a
> *pre/post-condizioni* ed *effetti collaterali*, soffermandosi ad illustrare le
> ragioni della *correttezza* solo per le implementazioni che riterrà più
> critiche).

Il primo obiettivo del lavoro, quindi, è determinare quali siano le entità in
gioco e decidere come rappresentarle facendo uso delle possibilità offerte dal
linguaggio, ossia: le interfacce, oppure le classi astratte, o per finire le
classi concrete; inoltre sarà necessario valutare se e come organizzare le
entità in una gerarchia di tipi. Tale lavoro si svolge sul piano
dell'**astrazione per specificazione** (non è ancora il momento di mettere mano
all'implementazione). Evidentemente inoltre non è richiesto l'uso di tutte le
possibilità descritte: si tratta di valutare quale sia la scelta più adatta a
rappresentare le informazioni necessarie alla soluzione del problema assegnato.

In questa fase, soprattutto nella determinazione di quali siano le competenze
*adeguate* per ciascuna entità, è bene non limitarsi strettamente all'obiettivo
di implementare la classe di test descritta al termine del tema d'esame, ma
riflettere per ciascuna entità su quali siano le competenze minime perché la sua
specifica sia ragionevole; a questo riguardo, in particolare, se una entità
raccoglie certe informazioni, è necessario che sia possibile accedervi (con
opportuni metodi osservazionali).

Una volta ottenuta una specificazione ragionevole, è il momento di dedicarsi al
passo successivo (più implementativo): si tratterà di scegliere la
*rappresentazione* delle informazioni necessarie al funzionamento di ciascuna
classe (riflettendo attentamente sull'**invariante di rappresentazione** e sulla
**funzione di astrazione**) e di precisare ulteriormente la specificazione dei
metodi (annotando le **pre**-, **post-condizioni** e **effetti collaterali**).
Ci si può quindi dedicare all'implementazione del corpo dei *costruttori* e dei
*metodi*; nel caso in cui qualche metodo sia particolarmente complesso, può
essere il caso di annotare una bozza di prova di correttezza.

Il tema procede con questa raccomandazione:

> Osservi che l'esito di questa prova, che le consentirà di accedere o
> meno all'orale, si baserà tanto su questa documentazione quanto sul codice
> sorgente.

è inutile ribadire che l'obiettivo non è quello di ottenere del codice tale che
la classe di test produca un output verosimile: tale risultato può essere
ottenuto anche prescindendo completamente dalla conoscenza ed applicazione delle
metodiche impartite nell'insegnamento! Il punto cruciale è testimoniare, sia
attraverso la documentazione e il codice (se sufficientemente esplicito), la
propria competenza nell'utilizzo del paradigma di programmazione orientata agli
oggetti.
