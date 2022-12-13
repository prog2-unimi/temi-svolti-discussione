/*

Copyright 2022 Massimo Santini

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

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Objects;

/**
 * Classe mutabile che rappresenta un <em>filesystem</em>.
 *
 * <p>Tutti i metodi di questa classe che accettano un <em>path</em> sollevano l'eccezione {@link
 * NullPointerException} se esso è nullo, e l'eccezione {@link IllegalArgumentException} se non è
 * assoluto; inoltre sollevano l'eccezione {@link FileNotFoundException} se il path non corrisponde
 * ad una <em>entry</em> adatta al contesto.
 */
public class FileSystem {

  // SOF: rep
  /** La radice del filesystem. */
  private final Directory root = new Directory("ROOT");
  // EOF: rep

  // RI: root non è null

  // SOF: find
  /**
   * Individua una <em>entry</em> dato un <em>path</em>.
   *
   * @param path il path dell'entry da trovare.
   * @return l'entry.
   * @throws FileNotFoundException se il path non corrisponde ad una entry del fliesystem.
   */
  public Entry find(final Path path) throws FileNotFoundException {
    if (!Objects.requireNonNull(path, "Il path non può essere null").isAbsolute())
      throw new IllegalArgumentException("Il path deve essere assoluto.");
    Directory d = root;
    Entry e = d;
    for (final String p : path) {
      if (d == null)
        throw new FileNotFoundException("La parte prima di " + p + " non è una directory");
      e = d.find(p);
      if (e == null) throw new FileNotFoundException("Impossibile trovare l'entry " + p);
      d = e.isDir() ? (Directory) e : null;
    }
    return e;
  }

  /**
   * Individua una <em>directory</em> dato un <em>path</em>.
   *
   * @param path il path della directory da trovare.
   * @return la directory.
   * @throws FileNotFoundException se il path non corrisponde and una directory.
   */
  public Directory findDir(final Path path) throws FileNotFoundException {
    if (!Objects.requireNonNull(path, "Il path non può essere null").isAbsolute())
      throw new IllegalArgumentException("Il path deve essere assoluto.");
    final Entry e = find(Objects.requireNonNull(path, "Il path non può essere null"));
    if (!e.isDir()) throw new FileNotFoundException("Non è una directory " + path);
    return (Directory) e;
  }
  // EOF: find

  // SOF: observe
  /**
   * Restituisce un {@link Iterable} sulle <em>entry</em> della directory con dato <em>path</em>.
   *
   * @param path il path della directory.
   * @return un iterabile sul contenuto della directory.
   * @throws FileNotFoundException se il path non corrisponde ad una directory.
   */
  public Iterable<Entry> ls(final Path path) throws FileNotFoundException {
    if (!Objects.requireNonNull(path, "Il path non può essere null.").isAbsolute())
      throw new IllegalArgumentException("Il path deve essere assoluto.");
    return findDir(path);
  }

  /**
   * Resituisce la dimensione di una <em>entry</em> con dato <em>path</em>.
   *
   * @param path il path dell'entry.
   * @return la dimenzione.
   * @throws FileNotFoundException se il path non corrisponde ad una entry.
   */
  public int size(final Path path) throws FileNotFoundException {
    if (!Objects.requireNonNull(path, "Il path non può essere null.").isAbsolute())
      throw new IllegalArgumentException("Il path deve essere assoluto.");
    return find(path).size();
  }
  // EOF: observe

  // SOF: make
  /**
   * Crea una <em>directory</em> dato il <em>path</em>.
   *
   * @param path il path della directory da creare.
   * @throws FileNotFoundException se il {@link Path#parent()} non è una directory.
   * @throws FileAlreadyExistsException se in {@link Path#parent()} esite già una entry con nome
   *     {@link Path#name()}.
   */
  public void mkdir(final Path path) throws FileNotFoundException, FileAlreadyExistsException {
    if (!Objects.requireNonNull(path, "Il path non può essere null.").isAbsolute())
      throw new IllegalArgumentException("Il path deve essere assoluto.");
    final String name = path.name();
    if (name == null) throw new IllegalArgumentException("Il percorso non può essere vuoto");
    findDir(path.parent()).add(new Directory(name));
  }

  /**
   * Crea una <em>file</em> dato il <em>path</em> e dimensione.
   *
   * @param path il path dell file da creare.
   * @param size la dimensione.
   * @throws IllegalArgumentException se la size non è positiva.
   * @throws FileNotFoundException se il {@link Path#parent()} non è una directory.
   * @throws FileAlreadyExistsException se in {@link Path#parent()} esite già una entry con nome
   *     {@link Path#name()}.
   */
  public void mkfile(final Path path, final int size)
      throws FileNotFoundException, FileAlreadyExistsException {
    if (!Objects.requireNonNull(path, "Il path non può essere null.").isAbsolute())
      throw new IllegalArgumentException("Il path deve essere assoluto.");
    final String name = path.name();
    if (name == null) throw new IllegalArgumentException("Il percorso non può essere vuoto");
    findDir(path.parent()).add(new File(name, size));
  }
  // EOF: make

}
