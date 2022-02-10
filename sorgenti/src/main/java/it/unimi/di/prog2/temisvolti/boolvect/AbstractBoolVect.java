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

/**
 * Classe astratta che provvede l'implementazione di alcuni dei metodi dell'inferfaccia {@link
 * BoolVect}.
 *
 * <p>Questa classe implementa i metodi totali {@link BoolVect#leggi(int)} e {@link
 * BoolVect#scrivi(int, boolean)} grazie ai metodi parziali {@link #leggiParziale(int)} e {@link
 * #scriviParziale(int, boolean)} che devono essere implementati dalle sue sottoclassi; il vantaggio
 * è che le sottoclassi, potendo evitare i controlli sulla posizione, potranno fornire una
 * implementazione molto snella.
 *
 * <p>Implementa inoltre i metodi {@link BoolVect#and(BoolVect)}, {@link BoolVect#or(BoolVect)} e
 * {@link BoolVect#xor(BoolVect)} a partire dagli operatori booleani definiti in base
 * all'interfaccia {@link AbstractBoolVect.BooleanOperator}, grazie alla funzione {@link
 * #componenteAComponente(BooleanOperator, BoolVect)} che li estende componente a componente.
 *
 * <p>Infine, provvede una implementazione elementare di {@link #toString()} ed una molto
 * inefficiente per {@link #equals(Object)}, le sottoclassi concrete devono implementare se
 * possibile una versione ottimizzata di tale medoto, ma in ogni caso una versione coerente di
 * {@link Object#hashCode()}.
 */
public abstract class AbstractBoolVect implements BoolVect {

  /* Implementaizone dei metodi leggi e scrivi attraverso i corrispondenti non totali */

  // SOF: leggiscrivi
  /**
   * Funzione parziale che restituisce il valore di verità di posizione specificata.
   *
   * @param pos la posizione, deve essere compresa tra 0 (incluso) e la taglia (esclusa).
   * @return il valore di verità.
   */
  protected abstract boolean leggiParziale(final int pos);

  /**
   * Funzione parziale che scrive il valore di verità dato nella posizione specificata.
   *
   * @param pos la posizione, deve essere compresa tra 0 (incluso) e la taglia (esclusa).
   * @param val il valore.
   */
  protected abstract void scriviParziale(final int pos, final boolean val);

  @Override
  public boolean leggi(final int pos) throws IndexOutOfBoundsException {
    if (pos < 0) throw new IndexOutOfBoundsException("La posizione non può essere negativa.");
    return pos < dimensione() ? leggiParziale(pos) : false;
  }

  @Override
  public void scrivi(final int pos, final boolean val) throws IndexOutOfBoundsException {
    if (pos < 0) throw new IndexOutOfBoundsException("La posizione non può essere negativa.");
    if (pos >= taglia() && val)
      throw new IndexOutOfBoundsException(
          "Non è possibile scrivere un valore di verità vero in posizione maggiore o uguale alla taglia.");
    scriviParziale(pos, val);
  }
  // EOF: leggiscrivi

  /* Implementazione delle operazioni booleane, tramite l'introduzione di una
   * interfaccia per parametrizzare la costruzione della versione componente a
   * componente.
   */

  // SOF: opint
  /** Interfaccia che descrive l'applicazoine di un operatore logico binario questo BoolVect. */
  public interface BooleanOperator {
    boolean apply(final boolean a, final boolean b);
  }
  // EOF: opint

  // SOF: bitwise
  /**
   * Metodo parziale che, dato un operatore booleano e un BoolVect applica l'operazione componente a
   * componente ottenuta dall'operatore booleano a questo e il BoolVect dato e ne salva il risultato
   * in questo.
   *
   * <p>Gli argomenti non devono essere {@code null} (questa è una funzione parziale).
   *
   * @param op l'operatore booleano.
   * @param other il primo BoolVect.
   */
  public void componenteAComponente(BooleanOperator op, BoolVect other)
      throws IndexOutOfBoundsException {
    final int dimensioneMax = Math.max(dimensione(), other.dimensione());
    for (int pos = 0; pos <= dimensioneMax; pos++)
      scrivi(pos, op.apply(leggi(pos), other.leggi(pos)));
  }
  // EOF: bitwise

  // SOF: defop
  @Override
  public void and(final BoolVect other) throws NullPointerException {
    componenteAComponente(
        new BooleanOperator() {
          @Override
          public boolean apply(boolean a, boolean b) {
            return a & b;
          }
        },
        Objects.requireNonNull(other, "L'argomento non può essere null."));
  }

  @Override
  public void or(final BoolVect other) throws NullPointerException, IllegalArgumentException {
    try {
      componenteAComponente(
          new BooleanOperator() {
            @Override
            public boolean apply(boolean a, boolean b) {
              return a | b;
            }
          },
          Objects.requireNonNull(other, "L'argomento non può essere null."));
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException(
          "La taglia di questo vettore è minore della dimensione del risultato.");
    }
  }

  @Override
  public void xor(final BoolVect other) throws NullPointerException, IllegalArgumentException {
    try {
      componenteAComponente(
          new BooleanOperator() {
            @Override
            public boolean apply(boolean a, boolean b) {
              return a ^ b;
            }
          },
          Objects.requireNonNull(other, "L'argomento non può essere null."));
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException(
          "La taglia di questo vettore è minore della dimensione del risultato.");
    }
  }
  // EOF: defop

  /* Implementazione non specializzata dei metodi ereditati da {@code Object}. */

  // SOF: obj
  /**
   * Restituisce la versione stringa di questo BoolVect.
   *
   * <p>Se la dimensione del BoolVect è 0 restituisce "F", altrimenti restituisce una stringa che
   * comprende un numero di caratteri pari alla dimensione del BoolVect, detto altrimenti i valori
   * di verità {@code false} che hanno posizione maggiore della dimensione non sono parte della
   * stringa; l'<em>i</em>-esimo carattere della stringa (contando da destra) è `V` se
   * l'<em>i</em>-esima posizione del BoolVect ha valore di verità {@code true}, o `F` se vale
   * {@code false}.
   *
   * @return la stringa corrispondente a questo BoolVect.
   */
  @Override
  public String toString() {
    if (dimensione() == 0) return "F";
    final StringBuilder b = new StringBuilder();
    for (int i = dimensione() - 1; i >= 0; i--) b.append(leggiParziale(i) ? 'V' : 'F');
    return b.toString();
  }

  /**
   * Implementazione non specializzata di equals.
   *
   * <p>Questa implementazione è molto inefficiente per il caso sparso; le sottoclassi dovrebbero
   * implementare una versione ottimizzata e in ogni caso sovrascrivere {@link Object#hashCode()} in
   * modo coerente.
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof BoolVect)) return false;
    BoolVect altro = (BoolVect) obj;
    if (dimensione() != altro.dimensione()) return false;
    for (int i = dimensione() - 2; i >= 0; i--) // a dimensione() - 1 sono entrambi true
    if (leggi(i) != altro.leggi(i)) return false;
    return true;
  }
  // EOF: obj

}
