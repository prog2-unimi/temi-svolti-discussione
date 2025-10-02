/*

Copyright 2025 Massimo Santini

This file is part of "Programmazione 2 @ UniMI" teaching material.

This is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This material is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this file.  If not, see <https://www.gnu.org/licenses/>.

*/

package it.unimi.di.prog2.temisvolti.filesystem;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/** Classe mutabile che rappresenta una <i>directory</i>. */
public class Directory extends Entry implements Iterable<Entry> {

  // SOF: rep
  /** Il contenuto della directory. */
  private final List<Entry> entries = new ArrayList<>();

  // RI: entries non è null e non contiene null, non contiene due entry con lo stesso nome

  /**
   * Costruisce una <i>directory</i> vuota dato il suo <i>nome</i>
   *
   * @param name il nome.
   * @throws IllegalArgumentException se il nome è {@code null} o vuoto.
   */
  public Directory(final String name) {
    super(name);
  }

  // EOF: rep

  // SOF: methods
  /**
   * Restituisce l'<i>entry</i> dato il suo <i>nome</i> se presente (altrimenti restituisce {@code
   * null}).
   *
   * @param name il nome dell'entry.
   * @return l'entry di dato nome (o {@code null} se nessuna entry ha il nome dato).
   * @throws NullPointerException se il {@code name} è {@code null}.
   */
  public Entry find(final String name) {
    Objects.requireNonNull(name, "Il nome non può essere null.");
    for (Entry e : entries) if (e.name.equals(name)) return e;
    return null;
  }

  /**
   * Aggiunge una <i>entry</i>.
   *
   * @param entry l'entry da aggiungere.
   * @throws NullPointerException se l'entry è {@code null}.
   * @throws FileAlreadyExistsException se la directory contiene una entry omonima.
   */
  void add(final Entry entry) throws FileAlreadyExistsException {
    Objects.requireNonNull(entry, "L'entry non può essere null.");
    if (find(entry.name) != null)
      throw new FileAlreadyExistsException(
          "La directory contiene già una entry con lo stesso nome.");
    entries.add(entry);
  }

  // EOF: methods

  // SOF: override
  @Override
  public boolean isDir() {
    return true;
  }

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
