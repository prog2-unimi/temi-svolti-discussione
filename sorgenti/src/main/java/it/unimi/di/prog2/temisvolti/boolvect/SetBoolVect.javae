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

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/** Classe concreta che implementa un {@link BoolVect} sparso di taglia illimitata. */
public class SetBoolVect extends AbstractBoolVect {

  /** L'insieme delle posizioni dei valori di verità veri del BoolVect. */
  private final SortedSet<Integer> positions = new TreeSet<>();

  // RI: positions != null
  // AF: l'i-esimo valore di verità del BoolVect è verso se e solo se i appartiene a positions.

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

  @Override
  public boolean leggiParziale(final int pos) {
    return positions.contains(pos);
  }

  @Override
  public void scriviParziale(final int pos, final boolean val) {
    if (val) positions.add(pos);
    else positions.remove(pos);
  }

  /* Per una discussione dei seguenti sei metodi si veda il sorgente di {@link LongBoolVect}. */

  @Override
  public void and(BoolVect other) throws IndexOutOfBoundsException {
    if (other.getClass() == SetBoolVect.class) andSet((SetBoolVect) other);
    else super.and(other);
  }

  public void andSet(SetBoolVect other) throws IndexOutOfBoundsException {
    positions.retainAll(other.positions);
  }

  @Override
  public void or(BoolVect other) throws IndexOutOfBoundsException {
    if (other.getClass() == SetBoolVect.class) orSet((SetBoolVect) other);
    else super.or(other);
  }

  public void orSet(SetBoolVect other) throws IndexOutOfBoundsException {
    positions.addAll(other.positions);
  }

  @Override
  public void xor(BoolVect other) throws IndexOutOfBoundsException {
    if (other.getClass() == SetBoolVect.class) xorSet((SetBoolVect) other);
    else super.xor(other);
  }

  public void xorSet(SetBoolVect other) throws IndexOutOfBoundsException {
    Set<Integer> intersection = new TreeSet<>(positions);
    intersection.retainAll(other.positions);
    positions.addAll(other.positions);
    positions.removeAll(intersection);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof SetBoolVect) return positions.equals(((SetBoolVect) obj).positions);
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return positions.hashCode();
  }
}
