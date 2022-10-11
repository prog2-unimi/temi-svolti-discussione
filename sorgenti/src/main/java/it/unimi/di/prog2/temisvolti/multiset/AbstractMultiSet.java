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
