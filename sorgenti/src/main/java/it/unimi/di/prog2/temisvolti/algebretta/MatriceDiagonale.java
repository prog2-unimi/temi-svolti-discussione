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

package it.unimi.di.prog2.temisvolti.algebretta;

import java.util.Objects;

/** Classe che implementa una matrice diagonale. */
public class MatriceDiagonale extends AbsMatrice {

  // SOF: rapcostr
  /** I valori lungo la diagonale. */
  private final int[] diagonale;

  // AF: l'i-esimo elemento della diagonale della matrice coincide con l'i-esimo
  // elemento dell'array
  // RI: diagonale non è null

  /**
   * Costruisce una matrice diagonale dati i valori lungo la diagonale.
   *
   * @param diagonale i valori.
   * @throws NullPointerException se la diagonale è {@code null}
   */
  public MatriceDiagonale(final int[] diagonale) {
    Objects.requireNonNull(diagonale);
    if (diagonale.length == 0)
      throw new IllegalArgumentException("La diagonale deve contenere almeno un valore.");
    this.diagonale = diagonale.clone();
  }
  // EOF: rapcostr

  // SOF: dimval
  @Override
  public int dim() {
    return diagonale.length;
  }

  @Override
  public int val(final int i, final int j) {
    requireValidIJ(i, j);
    return i == j ? diagonale[i] : 0;
  }
  // EOF: dimval

  // SOF: peralpha
  @Override
  public Matrice per(final int alpha) {
    // SOF: perzero
    if (alpha == 0) return new MatriceNulla(dim());
    // EOF: perzero
    int[] tmp = new int[diagonale.length];
    for (int i = 0; i < diagonale.length; i++) tmp[i] = alpha * diagonale[i];
    return new MatriceDiagonale(tmp);
  }
  // EOF: peralpha

  // SOF: piumat
  @Override
  public Matrice più(final Matrice B) {
    Objects.requireNonNull(B);
    if (!conforme(B)) throw new IllegalArgumentException("Le matrici non sono conformi.");
    // SOF: piuzero
    if (B instanceof MatriceNulla) return this;
    // EOF: piuzero
    return new MatriceDensa(this).più(B);
  }
  // EOF: piumat

  // SOF: permat
  @Override
  public Matrice per(final Matrice B) {
    Objects.requireNonNull(B);
    if (!conforme(B)) throw new IllegalArgumentException("Le matrici non sono conformi.");
    // SOF: perspeciale
    if (B instanceof MatriceNulla) return B;
    if (B instanceof MatriceIdentità) return this;
    // EOF: perspeciale
    return new MatriceDensa(this).per(B);
  }
  // EOF: permat

  // SOF: pervec
  @Override
  public Vettore per(final Vettore v) {
    Objects.requireNonNull(v);
    if (!conforme(v))
      throw new IllegalArgumentException("Il vettore e la matrice non sono conformi.");
    // SOF: pervzero
    if (v instanceof VettoreNullo) return v;
    // EOF: pervzero
    final int[] temp = new int[diagonale.length];
    for (int i = 0; i < diagonale.length; i++) temp[i] = diagonale[i] * v.val(i);
    return new VettoreDenso(temp);
  }
  // EOF: pervec

}
