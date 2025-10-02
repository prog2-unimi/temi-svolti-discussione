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

import java.util.Arrays;
import java.util.Objects;

/** Classe che rappresenta un vettore denso. */
public class VettoreDenso implements Vettore {

  // SOF: rapcostr
  /** I valori del vettore. */
  private final int[] val;

  // AF: l'i-esimo valore del vettore corrisponde all'i-esimo valore dell'array.
  // RI: val non è null ed ha almeno un elemento.

  /**
   * Costruttore che costruisce un vettore di dimensione data, con tutti i valori pari a 0.
   *
   * @param dim la dimensione.
   * @throws IllegalArgumentException se la dimensione non è positiva.
   */
  private VettoreDenso(final int dim) {
    if (dim <= 0) throw new IllegalArgumentException("La dimensione deve essere positiva.");
    val = new int[dim];
  }

  /**
   * Costruisce una vettore a partire da un array.
   *
   * @param val l'array.
   * @throws IllegalArgumentException se la val è {@code null} o ha zero elementi.
   */
  public VettoreDenso(final int[] val) {
    Objects.requireNonNull(val, "L'array val non può essere null.");
    if (val.length == 0)
      throw new IllegalArgumentException("Il vettore deve comprendere almeno un valore.");
    this.val = val.clone(); // per proteggere la rappresentazione
  }

  // EOF: rapcostr

  @Override
  public int dim() {
    return val.length;
  }

  @Override
  public int val(final int i) {
    if (i < 0 || i >= val.length)
      throw new IndexOutOfBoundsException("L'indice eccede le dimensioni del vettore.");
    return val[i];
  }

  @Override
  public Vettore per(final int alpha) {
    if (alpha == 0) return new VettoreNullo(dim());
    final VettoreDenso res = new VettoreDenso(dim());
    for (int i = 0; i < val.length; i++) res.val[i] = val[i] * alpha;
    return res;
  }

  @Override
  public Vettore più(final Vettore v) {
    Objects.requireNonNull(v, "Il vettore non può essere null.");
    if (!conforme(v)) throw new IllegalArgumentException("Il vettore non è conforme a questo.");
    if (v instanceof VettoreNullo) return this;
    final VettoreDenso res = new VettoreDenso(dim());
    for (int i = 0; i < val.length; i++) res.val[i] = val[i] + v.val(i);
    return res;
  }

  @Override
  public String toString() {
    return Arrays.toString(val).replace("[", "(").replace("]", ")");
  }
}
