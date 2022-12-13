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

package it.unimi.di.prog2.temisvolti.boolvect;

import java.util.Objects;

/** Classe concreta che implementa un {@link BoolVect} denso di taglia pari a {@link Long#SIZE}. */
public class LongBoolVect extends AbstractBoolVect {

  // SOF: rep
  /** I bit che rappresentano il BoolVector. */
  private long bits = 0;
  // EOF: rep

  // RI: (vuoto)
  // AF: l'i-esimo valore di verità del BoolVect è vero se e solo se l'i-esimo bit di bits è 1.

  // SOF: trivial
  @Override
  public int taglia() {
    return Long.SIZE;
  }

  @Override
  public int dimensione() {
    return Long.SIZE - Long.numberOfLeadingZeros(bits);
  }

  @Override
  public void pulisci() {
    bits = 0;
  }
  // EOF: trivial

  // SOF: partial
  @Override
  public boolean leggiParziale(final int pos) {
    return (bits & (1L << pos)) != 0;
  }

  @Override
  public void scriviParziale(final int pos, final boolean val) {
    final long mask = 1L << pos;
    if (val) bits |= mask;
    else bits &= ~mask;
  }
  // EOF: partial

  // SOF: op
  @Override
  public void and(BoolVect other) throws NullPointerException {
    Objects.requireNonNull(other, "L'argomento non può essere null.");
    if (other instanceof LongBoolVect) bits &= ((LongBoolVect) other).bits;
    else super.and(other);
  }

  @Override
  public void or(BoolVect other) throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(other, "L'argomento non può essere null.");
    if (other instanceof LongBoolVect) bits |= ((LongBoolVect) other).bits;
    else super.or(other);
  }

  @Override
  public void xor(BoolVect other) throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(other, "L'argomento non può essere null.");
    if (other instanceof LongBoolVect) bits ^= ((LongBoolVect) other).bits;
    else super.xor(other);
  }
  // EOF: op

  // SOF: obj
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof LongBoolVect) return bits == ((LongBoolVect) obj).bits;
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return Long.hashCode(bits);
  }
  // EOF: obj
}
