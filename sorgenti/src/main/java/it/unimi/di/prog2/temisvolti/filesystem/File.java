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

/** Classe immutabile che rappresenta un <em>file</em>. */
public class File extends Entry {

  // SOF: rep
  /** La dimensione del file. */
  // non è pubblico, sebbene immutabile, perché esiste un metodo osservazionale
  // (che nelle directory non può essere sostituito da un intero!)
  private final int size;

  // RI: size > 0

  /**
   * Costruisce un <em>file</em> dato il suo <em>nome</em> e <em>dimensione</em>.
   *
   * @param name il nome.
   * @param size la dimensione.
   * @throws IllegalArgumentException se la dimensione non è positiva, o il nome è {@code null} o
   *     vuoto.
   */
  public File(final String name, final int size) {
    super(name);
    if (size <= 0) throw new IllegalArgumentException("La dimensione deve essere positiva.");
    this.size = size;
  }
  // EOF: rep

  // SOF: methods
  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isDir() {
    return false;
  }

  @Override
  public String toString() {
    return String.format("%s(%d)", name, size);
  }
  // EOF: methods

}
