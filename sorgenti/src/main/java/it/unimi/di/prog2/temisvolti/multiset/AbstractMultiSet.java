package it.unimi.di.prog2.temisvolti.multiset;

import java.util.Iterator;

/**
 * Classe astratta che implementa il metodo {@link #toString() toString} standard.
 */
public abstract class AbstractMultiSet<E> implements MultiSet<E> {
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("{");
    final Iterator<E> ie = iterator();
    while (ie.hasNext()) {
      final E e = ie.next();
      sb.append(e + ": " + multiplicity(e));
      if (ie.hasNext()) sb.append(", ");
    } 
    sb.append("}");
    return sb.toString();
  }
}
