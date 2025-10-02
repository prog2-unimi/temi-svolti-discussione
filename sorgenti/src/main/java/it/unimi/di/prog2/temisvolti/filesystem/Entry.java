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

import java.util.Objects;

/** Classe astratta che rappresenta una <i>entry</i> del filesystem con assegnato <i>nome</i>. */
public abstract class Entry {

  /** Il nome dell'entry */
  // SOF: rep
  // ha senso che sia pubblico dato che è final e String è immutabile
  public final String name;

  // RI: name != null e non è vuoto (ossia contiene almeno un carattere)

  /**
   * Costruisce una <i>entry</i> dato il <i>nome</i>.
   *
   * @param name il nome dell'entry.
   * @throws IllegalArgumentException se il nome è {@code null} o vuoto.
   */
  // il costruttore è protetto perché sarà usato solo dalle sottoclassi
  protected Entry(final String name) {
    if (Objects.requireNonNull(name, "Il nome non può essere null.").isEmpty())
      throw new IllegalArgumentException("Il nome non può essere vuoto.");
    this.name = name;
  }

  // EOF: rep

  // SOF: methods
  /**
   * Consente di sapere se una <i>entry</i> è una <i>directory</i>.
   *
   * @return {@code true} sse l'entry è una directory.
   */
  public abstract boolean isDir();

  /**
   * Restituisce la dimensione dell'<i>entry</i>.
   *
   * @return la dimensione.
   */
  public abstract int size();
  // EOF: methods
}
