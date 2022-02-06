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

import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/** Classe concreta che implementa un {@link BoolVect} sparso di taglia illimitata. */
public class SetBoolVect extends AbstractBoolVect {

  // SOF: rep
  /** L'insieme delle posizioni dei valori di verità veri del BoolVect. */
  private final SortedSet<Integer> positions = new TreeSet<>();
  // EOF: rep

  // RI: positions != null e non contiene null
  // AF: l'i-esimo valore di verità del BoolVect è verso se e solo se i appartiene a positions.

  // SOF: trivial
  @Override
  public int taglia() {
    return Integer.MAX_VALUE;
  }

  @Override
  public int dimensione() {
    return positions.size() > 0 ? 1 + positions.last() : 0;
  }

  @Override
  public void pulisci() {
    positions.clear();
  }
  // EOF: trivial

  // SOF: partial
  @Override
  public boolean leggiParziale(final int pos) {
    return positions.contains(pos);
  }

  @Override
  public void scriviParziale(final int pos, final boolean val) {
    if (val) positions.add(pos);
    else positions.remove(pos);
  }
  // EOF: partial

  // SOF: op
  @Override
  public void and(BoolVect other) throws NullPointerException {
    Objects.requireNonNull(other, "L'argomento non può essere null.");
    if (other instanceof SetBoolVect) positions.retainAll(((SetBoolVect) other).positions);
    else super.and(other);
  }

  @Override
  public void or(BoolVect other) throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(other, "L'argomento non può essere null.");
    if (other instanceof SetBoolVect) positions.addAll(((SetBoolVect) other).positions);
    else super.or(other);
  }

  @Override
  public void xor(BoolVect other) throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(other, "L'argomento non può essere null.");
    if (other instanceof SetBoolVect) {
      Set<Integer> intersection = new TreeSet<>(positions);
      intersection.retainAll(((SetBoolVect) other).positions);
      positions.addAll(((SetBoolVect) other).positions);
      positions.removeAll(intersection);
    } else super.xor(other);
  }
  // EOF: op

  // SOF: obj
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof SetBoolVect) return positions.equals(((SetBoolVect) obj).positions);
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return positions.hashCode();
  }
  // EOF: obj
}
