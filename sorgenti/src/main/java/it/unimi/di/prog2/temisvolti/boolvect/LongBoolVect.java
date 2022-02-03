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

/** Classe concreta che implementa un {@link BoolVect} denso di taglia pari a {@link Long#SIZE}. */
public class LongBoolVect extends AbstractBoolVect {

  /** I bit che rappresentano il BoolVector. */
  private long bits = 0;

  // RI: (vuoto)
  // AF: l'i-esimo valore di verità del BoolVect è vero se e solo se l'i-esimo bit di bits è 1.

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

  @Override
  public boolean leggiParziale(final int pos) {
    return (bits & (1L << pos)) != 0;
  }

  @Override
  public void scriviParziale(final int pos, final boolean val) {
    if (val) bits |= 1L << pos;
    else bits &= ~(1L << pos);
  }

  /*
   * I prossimi metodi potrebbero essere tutti eliminati senza modificare il
   * comportamento "esterno" di questa classe.
   *
   * Certamente un LongBoolVect può far meglio che ricorrere all'implementazione
   * di default per fare and/or/not: usare gli operatori del linguaggio!
   *
   * Si potrebbe sovraccaricare, ma il metodo sovraccaricato verrebbe invocato
   * esclusivamente nel caso in cui il tipo apparente del "chiamante" e
   * dell'argomento siano entrambi BoolVectLong.
   *
   * Per aumentare l'utilità dell'implementazine più specifica, il metodo
   * sovrascrito usa uno stratagemma (<code>getClass()</code>) per indirizzare
   * l'esecuzione all'implementazione più specifica se è il caso.
   *
   * Il metodo con l'implementazione specifica, a questo punto, non ha motivo di
   * sovraccaricare quello ereditato, anzi: un nome diverso rende meno facile che
   * un estensore di questa classe lo sovrascriva erroneamente.
   *
   * Questo approccio è ispirato all'<a href="https://github.com/openjdk/jdk/blob/be9f984caec32c3fe1deef30efe40fa115409ca0/src/java.base/share/classes/java/util/ArrayList.java#L528-L530">equals di ArrayList</a> (specializzato)
   *
   */

  @Override
  public void and(BoolVect other) throws IndexOutOfBoundsException {
    if (other.getClass() == LongBoolVect.class) andLong((LongBoolVect) other);
    else super.and(other);
  }

  public void andLong(LongBoolVect other) throws IndexOutOfBoundsException {
    bits &= other.bits;
  }

  @Override
  public void or(BoolVect other) throws IndexOutOfBoundsException {
    if (other.getClass() == LongBoolVect.class) orLong((LongBoolVect) other);
    else super.or(other);
  }

  public void orLong(LongBoolVect other) throws IndexOutOfBoundsException {
    bits |= other.bits;
  }

  @Override
  public void xor(BoolVect other) throws IndexOutOfBoundsException {
    if (other.getClass() == LongBoolVect.class) xorLong((LongBoolVect) other);
    else super.xor(other);
  }

  public void xorLong(LongBoolVect other) throws IndexOutOfBoundsException {
    bits ^= other.bits;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof LongBoolVect) return bits == ((LongBoolVect) obj).bits;
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return Long.hashCode(bits);
  }
}
