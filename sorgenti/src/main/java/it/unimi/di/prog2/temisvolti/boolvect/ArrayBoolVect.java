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

package it.unimi.di.prog2.temisvolti.boolvect;

import java.util.Arrays;

/** Classe concreta che implementa un {@link BoolVect} denso di taglia assegnata. */
public class ArrayBoolVect extends AbstractBoolVect {

  /** La dimensione del BoolVect. */
  private int dimensione = 0;

  /** I valori del BoolVect. */
  private final boolean[] valore;

  // RI: * 0 <= dimensione <= valore.length
  //     * valore[dimensione - 1] == true && valore[p] == false se p >= dimensione
  //     * valore != null
  // AF: BoolVect di taglia valore.length il cui i-esimo valore di verità è l'i-esimo
  //     elemento dell'array valore

  /**
   * Costruisce un BoolVect di taglia assegnata.
   *
   * @param taglia la taglia
   * @throws IllegalArgumentException se la taglia non è positiva.
   */
  public ArrayBoolVect(final int taglia) {
    if (taglia <= 0) throw new IllegalArgumentException("La taglia deve essere positiva.");
    valore = new boolean[taglia];
  }

  /** Costruisce un BoolVect di taglia 1024. */
  public ArrayBoolVect() {
    this(1024);
  }

  @Override
  public int dimensione() {
    return dimensione;
  }

  @Override
  public int taglia() {
    return valore.length;
  }

  @Override
  public void pulisci() {
    Arrays.fill(valore, false);
  }

  @Override
  public boolean leggiParziale(final int pos) {
    return valore[pos];
  }

  @Override
  public void scriviParziale(final int pos, final boolean val) {
    valore[pos] = val;
    if (val && pos >= dimensione) dimensione = pos + 1;
    else if (!val && pos == dimensione - 1)
      while (dimensione > 0 && !valore[dimensione - 1]) dimensione--;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ArrayBoolVect) return Arrays.equals(valore, ((ArrayBoolVect) obj).valore);
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(valore);
  }
}
