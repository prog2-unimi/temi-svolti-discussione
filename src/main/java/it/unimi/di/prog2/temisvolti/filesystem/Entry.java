package it.unimi.di.prog2.temisvolti.filesystem;

import java.util.Objects;

/**
 * Una classe astratta che rappresenta una <em>entry<em> del filesystem con
 * assegnato <em>nome</em>.
 */
public abstract class Entry {

  /** Il nome dell'entry */
  // SOF: rep
  // ha senso che sia pubblico dato che è final e String è immutabile
  public final String name;

  // RI: name != null e non è vuoto (ossia contiene almeno un carattere)

  /**
   * Costruisce una <em>entry</em> dato il <em>nome</em>.
   *
   * @param name il nome dell'entry.
   * @throws IllegalArgumentException se il nome è <code>null</code> o vuoto.
   */
  // il costruttore è protetto perché sarà usato solo dalle sottoclassi
  protected Entry(final String name) {
    if (Objects.requireNonNull(name).isEmpty()) throw new IllegalArgumentException("Il nome non può essere vuoto.");
      this.name = name;
  }
  // EOF: rep

  // SOF: methods
  /**
   * Consente di sapere se una <em>entry</em> è una <em>directory</em>.
   *
   * @return <code>true</code> sse l'entry è una directory.
   */
  public abstract boolean isDir();

  /**
   * Restituisce la dimensione dell'<em>entry</em>.
   *
   * @return la dimensione.
   */
  public abstract int size();
  // EOF: methods
}