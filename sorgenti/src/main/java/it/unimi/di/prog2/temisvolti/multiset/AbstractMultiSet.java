/*

Copyright 2022 Massimo Santini

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

package it.unimi.di.prog2.temisvolti.multiset;

import java.util.Iterator;

/**
 * Classe astratta che implementa il metodo {@link #toString() toString} illustrato nella traccia.
 */
public abstract class AbstractMultiSet<E> implements MultiSet<E> {
  // SOF: toString
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("{");
    final Iterator<E> ie = iterator();
    while (ie.hasNext()) {
      final E e = ie.next();
      sb.append(e + ": " + multiplicity(e));
      if (ie.hasNext()) sb.append(", "); // la virgola separa dal successivo elemento, se presente
    }
    sb.append("}");
    return sb.toString();
  }
  // EOF: toString
}
