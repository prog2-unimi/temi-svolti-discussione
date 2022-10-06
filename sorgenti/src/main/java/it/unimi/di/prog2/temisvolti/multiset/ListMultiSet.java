package it.unimi.di.prog2.temisvolti.multiset;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implementazione di {@link MultiSet} basata su una lista con ripetizioni.
 */
public class ListMultiSet<E> extends AbstractMultiSet<E> {

  /**
   * La lista degli elementi del multiset.
   */
  private final List<E> elems = new LinkedList<>();

  /* L'invariante di rappresentazione è semplicemente dato dal fatto che la
   * lista non è <code>null</code> (che è vero in cosruzione e non può mutare in
   * quanto finale) e che non contenga elementi <code>null</code> (come
   * verificato nell'unico metodo che vi aggiunge elementi).
   */

  @Override
  public int add(E e) {
    Objects.requireNonNull(e);
    elems.add(e);
    return multiplicity(e);
  }

  @Override
  public int remove(Object o) {
    final int m = multiplicity(o); 
    elems.remove(o);
    return m;
  }

  @Override
  public int multiplicity(Object o) {
    return Collections.frequency(elems, o);
  }

  /* Le due implemementazioni seguenti hanno senso solo perché più efficienti
   * di quella di default (in particolare la seconda che ne cambia il costo
   * da quadratico a cosante).
   */

  @Override
  public boolean contains(Object o) {
    return elems.contains(o); 
  }

  @Override
  public int size() {
    return elems.size(); 
  }

  @Override
  public MultiSet<E> union(MultiSet<? extends E> o) {
    Objects.requireNonNull(o);
    final ListMultiSet<E> result = new ListMultiSet<>();
    result.elems.addAll(elems);
    for (final E elem: o) {
      final int mult = o.multiplicity(elem) - multiplicity(elem);
      if (mult > 0) result.elems.addAll(Collections.nCopies(mult, elem));
    }
    return result;
  }

  @Override
  public MultiSet<E> intersection(MultiSet<? extends E> o) {
    Objects.requireNonNull(o);
    final ListMultiSet<E> result = new ListMultiSet<>();
    for (final E elem: this) {
      final int mult = Math.min(multiplicity(elem), o.multiplicity(elem));
      if (mult > 0) result.elems.addAll(Collections.nCopies(mult, elem));
    }
    return result;
  }

  @Override
  public Iterator<E> iterator() {
    return new Iterator<>() {
      
      private final Iterator<E> it = elems.iterator();
      private E next = null; 
      private int idx = -1; 
      
      @Override
      public boolean hasNext() {
        if (next != null) return true;
        while (it.hasNext()) {
          final E candidate = it.next();
          idx++;
          if (elems.indexOf(candidate) == idx) {
            next = candidate;
            return true;
          }
        }
        return false;
      }

      @Override
      public E next() {
        if (!hasNext()) throw new NoSuchElementException();
        final E result = next;
        next = null;
        return result;
      }
      
    };
  }
}
