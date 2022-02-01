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

/**
 * Classe astratta che implementa parzialmente una matrice al solo scopo di provvedere il metodo
 * {@link Object#toString()};
 */
public abstract class AbsMatrice implements Matrice {
  // SOF: tostring
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < dim(); i++) {
      for (int j = 0; j < dim(); j++) sb.append(val(i, j) + (j < dim() - 1 ? ", " : ""));
      if (i < dim() - 1) sb.append("; ");
    }
    sb.append("]");
    return sb.toString();
  }
  // EOF: tostring
}
