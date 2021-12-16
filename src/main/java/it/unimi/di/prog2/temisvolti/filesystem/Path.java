/*

Copyright 2021 Massimo Santini

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

import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/** Classe immutabile che rappresenta un <em>path</em>. */
public class Path implements Iterable<String> {

  // SOF: const
  /** Carattere separatore delle parti di un percorso */
  public static final String SEPARATOR = ":";

  /** Costante corrispondente al path radice (path assoluto, senza parti) */
  public static final Path ROOT = new Path(true, Collections.emptyList());

  /** Costante corrispondente al path vuoto (path relativo, senza parti) */
  public static final Path EMPTY = new Path(false, Collections.emptyList());
  // EOF: const

  // SOF: rep
  /** Indica se il path è assouto. */
  private final boolean isAbsolute;

  /** Contiene le componenti del path. */
  private final List<String> parts;

  // RI: parts non è null, non contiene null o stringhe vuote, o che contengano il separatore

  /**
   * Costruisce un <em>path</em> a partire da un elenco di stringhe e dall'informazione se sia
   * <em>assoluto</em> o meno.
   *
   * @param isAbsolute indica se il path è assoluto.
   * @param parts elenco di stringhe che costiuiscono le parti del percorso.
   * @throws NullPointerException se le <code>parts</code> è o contiene <code>null</code>.
   * @throws InvalidPathException se una della parti è vuota, o contiene il separatore.
   */
  private Path(final boolean isAbsolute, final List<String> parts) {
    this.isAbsolute = isAbsolute;
    this.parts = List.copyOf(parts);
    for (String p : parts) {
      if (p.isEmpty()) throw new InvalidPathException(p, "La componente è vuota.");
      if (p.indexOf(SEPARATOR) != -1)
        throw new InvalidPathException(p, "La componente contiene il separatore.");
    }
  }
  // EOF: rep

  // SOF: fab
  /**
   * Metodo di fabbricazione che restituisce un <em>path</em> a partire da una stringa.
   *
   * <p>Alcuni esempi di percorso sono:
   *
   * <ul>
   *   <li>"<samp>:A:B:C</samp>", percorso assoluto con parti <samp>A</samp>, <samp>B</samp> e
   *       <samp>C</samp>,
   *   <li>"<samp>B:C</samp>", percorso relativo con parti <samp>A</samp>, <samp>B</samp> e
   *       <samp>C</samp>,
   *   <li>"<samp>:</samp>", percorso assoluto corrispondente alla radice del filesystem,
   *   <li>"<samp></samp>", percorso vuoto (relativo).
   * </ul>
   *
   * @param path la stringa che corrisponde alla rappresentazione testuale del path.
   * @return il path corrispondente alla stringa.
   * @throws NullPointerException se path è <code>null</code>
   * @throws InvalidPathException se nella stringa compaiono due separatori immediatamente
   *     consecutivi.
   */
  public static Path fromString(final String path) {
    Objects.requireNonNull(path);
    if (path.isEmpty()) return EMPTY;
    final String[] parts = path.split(SEPARATOR);
    if (parts.length == 0) return ROOT;
    if (parts[0].isEmpty()) return new Path(true, Arrays.asList(parts).subList(1, parts.length));
    return new Path(false, Arrays.asList(parts));
  }
  // EOF: fab

  // SOF: simple
  /**
   * Consente di sapere se il <em>path</em> è <em>assoluto</em>.
   *
   * @return <code>true</code> sse il path è assoluto.
   */
  public boolean isAbsolute() {
    return isAbsolute;
  }

  /**
   * Restituisce il prefisso di questo <em>path</em> a meno dell'ultima componente (o quello vuoto,
   * se questo è vuoto).
   *
   * @return un path corrispondente a questo, ma privato dell'ultima componente (se presente).
   */
  public Path parent() {
    if (parts.isEmpty()) return this;
    return new Path(isAbsolute, parts.subList(0, parts.size() - 1));
  }

  /**
   * Restituisce l'ultima componente di questo <em>path</em>.
   *
   * @return l'ultima componente di questo path, o <code>null</code> se il path è vuoto.
   */
  public String name() {
    if (parts.isEmpty()) return null;
    return parts.get(parts.size() - 1);
  }
  // EOF: simple

  // SOF: rr
  /**
   * Risolve il <em>path</em> dato rispetto a questo.
   *
   * <p>Se il <em>path</em> dato come parametro è assoluto esso viene banalmente restituito, se è
   * vuoto, viene invece restituito questo <em>path</em>. In ogni altro caso, questo <em>path</em>
   * viene considearato una directory alla quale sono aggiunte le parti de <em>path</em> passato
   * come argomento (che è relativo); il risultato è assoluto sse lo è questo <em>path</em>.
   *
   * <p>Per esempio, se questo <em>path</em> è <samp>:A:B</samp> e il parametro è <samp>C:D</samp>
   * la risoluzione è <samp>:A:B:C:D</samp>.
   *
   * @param other il percorso da risolvere.
   * @return il percorso risolto.
   */
  public Path resolve(final Path other) {
    if (Objects.requireNonNull(other).isAbsolute()) return other;
    final List<String> parts = new ArrayList<>(this.parts);
    parts.addAll(other.parts);
    return new Path(isAbsolute, parts);
  }

  /**
   * Costruisce un <em>path</em> relativo tra questo e quello dato.
   *
   * <p>La relativizzazione è l'inverso di {@link #resolve(Path)}, restituisce un <em>path</em> che
   * identifica lo stesso file se risolto rispetto a questo.
   *
   * <p>Per esempio, se questo <em>path</em> è <samp>:A:B</samp> e il parametro è
   * <samp>:A:B:C:D</samp> la relativizzazione è <samp>C:D</samp>.
   *
   * @param other l'altro path.
   * @return il path relativizzato.
   * @throws IllegalArgumentException se questo path non è assoluto, ma lo è l'argomento, o se
   *     l'elenco di parti di quest questo path non è (come lista) prefisso di quelle
   *     dell'argomento.
   */
  public Path relativize(final Path other) {
    Objects.requireNonNull(other);
    if (!isAbsolute() && other.isAbsolute)
      throw new IllegalArgumentException(
          "Non si può relativizzare un path assoluto rispetto ad un relativo.");
    if (!parts.equals(other.parts.subList(0, parts.size())))
      throw new IllegalArgumentException("Il percorso non ha un prefisso in comune con questo.");
    return new Path(false, other.parts.subList(parts.size(), other.parts.size()));
  }
  // EOF: rr

  // SOF: override
  @Override
  public String toString() {
    return (isAbsolute ? SEPARATOR : "") + String.join(SEPARATOR, parts);
  }

  @Override
  public Iterator<String> iterator() {
    return Collections.unmodifiableList(parts).iterator();
  }
  // EOF: override

}
