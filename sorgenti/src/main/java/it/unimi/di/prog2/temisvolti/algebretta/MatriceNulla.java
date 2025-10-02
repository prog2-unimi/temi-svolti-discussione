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

import java.util.Objects;

/** Classe che implementa una matrice nulla (anche detta zero). */
public class MatriceNulla extends AbsMatrice {

  // SOF: rapcostr
  /** La dimensione della matrice. */
  private final int dim;

  // AF: è la matrice nulla di dimensione pari a dim
  // RI: dim > 0

  /**
   * Costruisce una matrice nulla data la sua dimensione.
   *
   * @param dim la dimensione.
   * @throws IllegalArgumentException se la dimensione non è positiva.
   */
  public MatriceNulla(final int dim) {
    if (dim <= 0) throw new IllegalArgumentException("La dimensione deve essere positiva.");
    this.dim = dim;
  }

  // EOF: rapcostr

  // SOF: dimval
  @Override
  public int dim() {
    return dim;
  }

  @Override
  public int val(final int i, final int j) {
    requireValidIJ(i, j);
    return 0;
  }

  // EOF: dimval

  // SOF: ops
  @Override
  public Matrice per(final int alpha) {
    return this;
  }

  @Override
  public Matrice per(final Matrice B) {
    Objects.requireNonNull(B, "La matrice non può essere null.");
    if (!conforme(B)) throw new IllegalArgumentException("Le matrici non sono conformi.");
    return this;
  }

  @Override
  public Matrice più(final Matrice B) {
    Objects.requireNonNull(B, "La matrice non può essere null.");
    if (!conforme(B)) throw new IllegalArgumentException("Le matrici non sono conformi.");
    return B;
  }

  // EOF: ops

  // SOF: pervec
  @Override
  public VettoreNullo per(final Vettore v) {
    Objects.requireNonNull(v, "Il vettore non può essere null.");
    if (!conforme(v))
      throw new IllegalArgumentException("Il vettore e la matrice non sono conformi.");
    return new VettoreNullo(dim);
  }
  // EOF: pervec

}
