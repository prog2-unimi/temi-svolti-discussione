package it.unimi.di.prog2.temisvolti.filesystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Classe mutabile che rappresenta una <em>directory</em>.
 */
public class Directory extends Entry implements Iterable<Entry> {

  // SOF: rep
  /** Il contenuto della directory. */
  private final List<Entry> entries = new ArrayList<>();

  // RI: entries non è null e non contiene null, non contiene due entry con lo stesso nome

  /** Costruisce una <em>directory</em> vuota dato il suo <em>nome</em>
   *
   * @param name il nome.
   * @throws IllegalArgumentException se il nome è <code>null</code> o vuoto.
   */
  public Directory(final String name) {
    super(name);
  }
  // EOF: rep


  /**
   * Restituisce l'<em>entry</em> dato il suo <em>nome</em> se presente
   * (altrimenti restituisce <code>null</code>).
   *
   * @param name il nome dell'entry.
   * @return l'entry di dato nome (o <code>null</code> se nessuna entry ha il
   * nome dato).
   * @throws NullPointerException se il <code>name</code> è <code>null</code>.
   */
  public Entry find(final String name) {
    Objects.requireNonNull(name);
    for (Entry e: entries) if (e.name.equals(name)) return e;
    return null;
  }

  /**
   * Aggiunge una <em>entry</em>.
   *
   * @param e l'entry da aggiungere.
   * @throws NullPointerException se l'entry è <code>null</code>
   * @thrwos {@link IllegalArgumentException} se la directory contiene una entry
   * con lo stesso nome di quella da aggiungere.
   */
  void add(final Entry e) {
    Objects.requireNonNull(e);
    if (find(e.name) != null) throw new IllegalArgumentException("La directory contiene già una entry con lo stesso nome.");
    entries.add(e);
  }

  @Override
  public boolean isDir() {
    return true;
  }

  // SOF: override
  @Override
  public int size() {
    int sum = 0;
    for (Entry e : entries) sum += e.size();
    return sum;
  }

  @Override
  public String toString() {
    return String.format("%s*", name);
  }

  @Override
  public Iterator<Entry> iterator() {
    return Collections.unmodifiableList(entries).iterator();
  }
  // EOF: override

}
