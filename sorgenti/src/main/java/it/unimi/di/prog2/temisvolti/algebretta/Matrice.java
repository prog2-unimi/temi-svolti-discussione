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

package it.unimi.di.prog2.temisvolti.algebretta;

/** Interfaccia che descrive il contratto di una matrice immutabile, quadrata e a valori interi. */
public interface Matrice {

  // SOF: signatures
  /**
   * Restituisce la dimensione di questa matrice, è un valore sempre positivo.
   *
   * @return la dimensione.
   */
  int dim();

  /**
   * Restituisce il valore di posto dato di questa matrice.
   *
   * @param i la riga.
   * @param j la colonna.
   * @return il valore.
   * @throws IndexOutOfBoundsException se (almeno) uno degli indici è negativo, o maggiore o uguale
   *     alla dimensione di questa matrice.
   */
  int val(final int i, final int j) throws IndexOutOfBoundsException;

  /**
   * Restituisce una nuova matrice ottenuta moltiplicando questa matrice per lo scalare dato.
   *
   * @param alpha lo scalare.
   * @return la nuova matrice.
   */
  Matrice per(final int alpha);

  /**
   * Restituisce una nuova matrice ottenuta sommando questa matrice alla matrice data.
   *
   * @param B la matrice.
   * @return la nuova matrice.
   * @throws NullPointerException se la matrice è {@code null}.
   * @throws IllegalArgumentException se le matrici non sono conformi.
   */
  Matrice più(final Matrice B) throws NullPointerException, IllegalArgumentException;

  /**
   * Restituisce una nuova matrice ottenuta moltiplicando questa matrice per la matrice data.
   *
   * @param B la matrice.
   * @return la nuova matrice.
   * @throws NullPointerException se la matrice è {@code null}.
   * @throws IllegalArgumentException se le matrici non sono conformi.
   */
  Matrice per(final Matrice B) throws NullPointerException, IllegalArgumentException;
  // EOF: signatures

  // SOF: pervec
  /**
   * Restituisce il vettore ottenuto moltiplicando questa matrice per il vettore dato.
   *
   * @param v il vettore.
   * @return il risultato.
   * @throws NullPointerException se il vettore è {@code null}.
   * @throws IllegalArgumentException se le matrice e il vettore non sono confrmi.
   */
  Vettore per(final Vettore v) throws NullPointerException, IllegalArgumentException;
  // EOF: pervec

  // SOF: default
  /**
   * Restituisce {@code true} se e solo se il vettore dato ha la stessa dimensione di quetsa
   * matrice.
   *
   * @param v il vettore.
   * @return {@code true} se e solo se il vettore è conforme a questa matrice.
   * @throws NullPointerException se il vettore è {@code null}.
   */
  default boolean conforme(final Vettore v) throws NullPointerException {
    return dim() == v.dim();
  }

  /**
   * Restituisce {@code true} se e solo se la matrice data ha la stessa dimensione di quetsa.
   *
   * @param M la matrice.
   * @return {@code true} se e solo se la matrice è conforme a questa.
   * @throws NullPointerException se la matrice è {@code null}.
   */
  default boolean conforme(final Matrice M) throws NullPointerException {
    return dim() == M.dim();
  }

  /**
   * Consente di assicurare che la posizione data sia valida.
   *
   * @param i la riga.
   * @param j la colonna.
   * @throws IllegalArgumentException se (almeno) uno degli indici è negativo, o maggiore o uguale
   *     alla dimensione di questa matrice.
   */
  default void requireValidIJ(final int i, final int j) {
    if (0 <= i && i < dim() && 0 <= j && j < dim()) return;
    throw new IndexOutOfBoundsException("Gli indici eccedono la dimensione della matrice.");
  }
  // EOF: default

}
