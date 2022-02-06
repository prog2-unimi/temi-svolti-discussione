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

/** Classe di singoletti che implementano gli operatori logici and, or e xor. */
public class BooleanOperators {

  private BooleanOperators() {
  } // rende la classe non istanziabile

  // SOF: op
  /** Interfaccia che descrive un operatore logico binario. */
  public interface BooleanOperator {
    boolean apply(final boolean a, final boolean b);
  }
  // EOF: op

  // SOF: singletons
  /** Singoletto dell'operatore logico and. */
  public static BooleanOperator AND = new BooleanOperator() {
    @Override
    public boolean apply(boolean a, boolean b) {
      return a & b;
    }
  };

  /** Singoletto dell'operatore logico or. */
  public static BooleanOperator OR = new BooleanOperator() {
    @Override
    public boolean apply(boolean a, boolean b) {
      return a | b;
    }
  };

  /** Singoletto dell'operatore logico xor. */
  public static BooleanOperator XOR = new BooleanOperator() {
    @Override
    public boolean apply(boolean a, boolean b) {
      return a ^ b;
    }
  };

  // EOF: singletons

  // SOF: bitwise
  /**
   * Funzione parziale che, dato un operatore booleano e due BoolVect applica
   * l'operazione
   * componente a componente ottenuta dall'operatore booleano ai due BoolVect e ne
   * salva il
   * risultato nel primo BoolVect.
   *
   * <p>
   * Gli argomenti non devono essere {@code null} (questa è una funzione
   * parziale).
   *
   * @param op l'operatore booleano.
   * @param t  il primo BoolVect.
   * @param u  il secondo BoolVect.
   */
  public static void componenteAComponente(
      BooleanOperator op, BoolVect t, BoolVect u) throws IllegalArgumentException {
    final int maxDimension = Math.max(t.dimensione(), u.dimensione());
    for (int pos = 0; pos <= maxDimension; pos++)
      t.scrivi(pos, op.apply(t.leggi(pos), u.leggi(pos)));
  }
  // EOF: bitwise

}
