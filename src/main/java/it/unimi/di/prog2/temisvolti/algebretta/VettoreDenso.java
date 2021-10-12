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

import java.util.Arrays;
import java.util.Objects;

public class VettoreDenso implements Vettore {

  // SOF: rapcostr
  private final int[] val;

  public VettoreDenso(final int dim) {
    if (dim <= 0) throw new IllegalArgumentException("La dimensione deve essere positiva.");
    val = new int[dim];
  }

  public VettoreDenso(final int[] val) {
    Objects.requireNonNull(val);
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
    Objects.requireNonNull(v);
    if (!conforme(v)) throw new IllegalArgumentException();
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
