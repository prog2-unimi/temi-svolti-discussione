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

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/** Implementazione di {@link MultiSet} basata su una mappa tra elementi e molteplicità. */
public class HashMapMultiSet<E> extends AbstractMultiSet<E> {

  /** La mappa che, per ciascun elemento del multiset, ne indica la molteplicità. */
  // SOF: rapp
  private final Map<E, Integer> elem2mult = new HashMap<>();
  // EOF: rapp

  /* L'invariante di rappresentazione è semplicemente dato dal fatto che la
   * mappa non sia <code>null</code> (che è vero in cosruzione e non può mutare in
   * quanto finale) e che non contenga chiavi <code>null</code> o valori non positivi
   * il che è garantito dai due metodi che ne mutano il contenuto.
   */

  // SOF: mutazionali
  @Override
  public int add(E e) {
    Objects.requireNonNull(e);
    final int m = multiplicity(e);
    elem2mult.put(e, m + 1);
    return m + 1;
  }

  @Override
  public int remove(Object o) {
    final int m = multiplicity(o);
    if (m == 1) elem2mult.remove(o);
    else if (m > 1) {
      @SuppressWarnings("unchecked")
      E e = (E) o;
      elem2mult.put(e, m - 1);
    }
    return m;
  }
  // EOF: mutazionali

  // SOF: multiplicity
  @Override
  public int multiplicity(Object o) {
    return elem2mult.containsKey(o) ? elem2mult.get(o) : 0;
  }
  // EOF: multiplicity

  /* Le due implemementazioni seguenti hanno senso solo perché poco più
   * efficienti di quella di default (di fatto evitano le chiamate di delega).
   */

  // SOF: overrides
  @Override
  public boolean contains(Object o) {
    return elem2mult.containsKey(o);
  }

  @Override
  public int size() {
    int size = 0;
    for (final int m : elem2mult.values()) size += m;
    return size;
  }
  // EOF: overrides

  // SOF: union
  @Override
  public MultiSet<E> union(MultiSet<? extends E> o) {
    Objects.requireNonNull(o);
    HashMapMultiSet<E> result = new HashMapMultiSet<>();
    for (Map.Entry<E, Integer> elemMult : elem2mult.entrySet()) {
      final E elem = elemMult.getKey();
      result.elem2mult.put(elem, Math.max(elemMult.getValue(), o.multiplicity(elem)));
    }
    for (E elem : o)
      if (!elem2mult.containsKey(elem)) result.elem2mult.put(elem, o.multiplicity(elem));
    return result;
  }
  // EOF: union

  // SOF: intersection
  @Override
  public MultiSet<E> intersection(MultiSet<? extends E> o) {
    Objects.requireNonNull(o);
    HashMapMultiSet<E> result = new HashMapMultiSet<>();
    for (Map.Entry<E, Integer> elemMult : elem2mult.entrySet()) {
      final E elem = elemMult.getKey();
      final int mult = Math.min(elemMult.getValue(), o.multiplicity(elem));
      if (mult > 0) result.elem2mult.put(elem, mult);
    }
    return result;
  }
  // EOF: intersection

  // SOF: iterator
  @Override
  public Iterator<E> iterator() {
    return Collections.unmodifiableSet(elem2mult.keySet()).iterator();
  }
  // EOF: iterator

}
