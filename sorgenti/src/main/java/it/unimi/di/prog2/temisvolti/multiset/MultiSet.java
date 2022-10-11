package it.unimi.di.prog2.temisvolti.multiset;

/**
 * Interfaccia che descrive il contratto di un <em>multiset</em>.
 *
 * Una breve definizione e discussione sulle proprietà dei <em>multiset</em> si
 * trova ad esempio su <a href="https://en.wikipedia.org/wiki/Multiset">Wikipedia</a>.
 *
 * <p>I metodi che hanno per argomento un <em>elemento</em> sono: 
 *
 * <ul>
 * <li> {@link #add(E e)}, {@link #remove(Object)} e {@link #contains(Object)},
 *      questi sono tipici di collezioni come ad esempio {@link java.util.Set} 
 *      per cui è bene che ne imitino la semantica (con particolare riguardo alle
 *      eccezioni); 
 * <li> {@link #multiplicity(Object)} che, pur non comune ad altre collezioni, 
 *      è ragionevole ne segua le convenzioni per uniformità con i precedenti.
 *
 * Dato che assumeremo che un multiset non possa contenere riferimenti
 * <code>null</code>, l'unico metodo tra i precedenti che sollverà eccezioni
 * {@link #add(E e)}, tale metodo potrebbe sollevare anche l'eccezione
 * {@link ClassCastException}. Gli altri metodi accetteranno senza sollevare
 * eccezioni sia riferimenti <code>null</code> che elementi non presenti
 * nell'insieme.
 * 
 * <p> Le implementazioni di questa classe sono <em>iterabili</em>, l'iteratore
 * {@link #iterator()} deve restituire gli elementi del supporto del multiset 
 * (ossia gli elementi del multiset <em>senza</em> ripetizioni).
 * 
 */
interface MultiSet<E> extends Iterable<E> {
 
  /**
   * Aggiunge un elemento al multiset.
   * 
   * @param e l'elemento da aggiungere.
   * @return la molteplicità dell'elemento dopo l'inserimento.
   * @throws NullPointerException se l'elemento è <code>null</code>.
   * @throws ClassCastException se l'elemento è di tipo tale da non poter essere aggiunto.
   */
  int add(E e) throws NullPointerException, ClassCastException;
 
  /**
   * Rimuove l'elemento (se presente) dal multiset.
   * 
   * @param o l'elemento da rimuovere.
   * @return la molteplicità dell'elemento prima della rimozione.
   */
  int remove(Object o);

  /**
   * Resituisce <code>true</code> se il multiset contiene l'elemento
   * specificato.
   *
   * Più formalmente, resituisce <code>true</code> se e solo se il multiset
   * contiene un elemento <code>e</code> tale che <code>Objects.equals(o,
   * e)</code>.
   * 
   * <p>Questo metodo ammette una implementazione di <em>default</em> basata 
   * sul metodo {@link #multiplicity(Object)}.
   *
   * @param o l'elemento la cui presenza è da verificare.
   * @return <code>true</code> se l'elemento appartiene al multiset.
   */
  // SOF: contains
  default boolean contains(Object o) {
    return multiplicity(o) > 0;
  }
  // EOF: contains
 
  /**
   * Restituisce la molteplicità dell'elemento nel multiset.
   *
   * Se l'elemento non appartiene al multiset, resituisce convenzionalmente
   * <code>0</code> (senza sollevare eccezioni).
   *
   * @param o l'elemento di cui resituire la molteplicità.
   * @return la molteplicità dell'elemento nell'inzieme, o <code>0</code> se
   * l'eleemnto non appartiene ad esso.
   */
  int multiplicity(Object o);
 
  /**
   * Restituisce la cardinalità del multiset.
   * 
   * <p>Questo metodo ammette una implementazione di <em>default</em> basata 
   * sul metodo {@link #multiplicity(Object)} e sul fatto che gli elementi
   * del supporto sono itearbili.
   *
   * @return la cardinalità.
   */
  // SOF: size
  default int size() {
    int size = 0;
    for (E e: this) size += multiplicity(e);
    return size;
  }
  // EOF: size
 
  /**
   * Resistuisce un nuovo multiset corrispondente all'unione tra questo e l'argomento.
   * 
   * Questo metodo non modifica questo multiset, o il suo argomento.
   * 
   * @param o l'altro multiset con cui costruire l'unione.
   * @return l'unione tra i due multiset.
   * @throws NullPointerException se l'argomento è <code>null</code>.
   */
  MultiSet<E> union(MultiSet<? extends E> o);
 
  /**
   * Resistuisce un nuovo multiset corrispondente all'intersezione tra questo e l'argomento.
   * 
   * Questo metodo non modifica questo multiset, o il suo argomento.
   * 
   * @param o l'altro multiset con cui costruire l'intersezione.
   * @return l'intersezione tra i due multiset.
   * @throws NullPointerException se l'argomento è <code>null</code>.
   */
  MultiSet<E> intersection(MultiSet<? extends E> o);
}