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

package it.unimi.di.prog2.temisvolti.algebretta;

import java.util.Collections;
import java.util.Objects;

/** Classe che implementa il vettore null (anche detto zero). */
public class VettoreNullo implements Vettore {

  /** La dimensione del vettore. */
  // SOF: rapcostr
  private final int dim;

  // AF: vettore con dim elementi tutti nulli.
  // RI: dim > 0.

  /**
   * Costruttore che crea un vettore nullo di data dimensione.
   *
   * @param dim la dimensione del vettore.
   * @throws IllegalArgumentException se la dimensione non è positiva.
   */
  public VettoreNullo(final int dim) {
    if (dim <= 0) throw new IllegalArgumentException("La dimensione dev'essere positiva.");
    this.dim = dim;
  }

  // EOF: rapcostr

  // SOF: dimval
  @Override
  public int dim() {
    return dim;
  }

  @Override
  public int val(int i) {
    if (i < 0 || i >= dim)
      throw new IndexOutOfBoundsException("L'indice eccede la dimensione del vettore.");
    return 0;
  }

  // EOF: dimval

  // SOF: ops
  @Override
  public VettoreNullo per(int alpha) {
    return this;
  }

  @Override
  public Vettore più(Vettore v) {
    Objects.requireNonNull(v, "Il vettore non può essere null.");
    if (!conforme(v)) throw new IllegalArgumentException("Il vettore non è conforme a questo.");
    return v;
  }

  // EOF: ops

  @Override
  public String toString() {
    return Collections.nCopies(dim, "0").toString().replace("[", "(").replace("]", ")");
  }
}
