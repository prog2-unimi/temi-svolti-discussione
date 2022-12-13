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

/** Interfaccia che descrive il contratto di un vettore immutabile a valori interi. */
public interface Vettore {

  // SOF: signatures
  /**
   * Restituisce la dimensione di questo vettore, è un valore sempre positivo.
   *
   * @return la dimensione.
   */
  int dim();

  /**
   * Restituisce il valore di coordinata data di questo vettore.
   *
   * @param i la coordinata.
   * @return il valore.
   * @throws IndexOutOfBoundsException se la coordinata è negativa, o maggiore o uguale alla
   *     dimensione di questo vettore.
   */
  int val(final int i);

  /**
   * Restituisce un nuovo vettore ottenuto moltiplicando questo vettore per lo scalare dato.
   *
   * @param alpha lo scalare.
   * @return il nuovo vettore.
   */
  Vettore per(final int alpha);

  /**
   * Restituisce un nuovo vettore ottenuto sommando questo vettore al vettore dato.
   *
   * @param v il vettore.
   * @return la nuova matrice.
   * @throws NullPointerException se la il vettore è {@code null}.
   * @throws IllegalArgumentException se i vettori non sono conformi.
   */
  Vettore più(final Vettore v);
  // EOF: signatures

  // SOF: default
  /**
   * Restituisce {@code true} se e solo se il vettore dato ha la stessa dimensione di questo
   * vettore.
   *
   * @param v il vettore.
   * @return {@code true} se e solo se il vettore è conforme a questo.
   * @throws NullPointerException se il vettore è {@code null}.
   */
  default boolean conforme(final Vettore v) {
    return dim() == v.dim();
  }

  /**
   * Restituisce {@code true} se e solo se questo vettore dato ha la stessa dimensione della matrice
   * data.
   *
   * @param M la matrice.
   * @return {@code true} se e solo se questo vettore è conforme alla matrice.
   * @throws NullPointerException se la matrice è {@code null}.
   */
  default boolean conforme(final Matrice M) {
    return dim() == M.dim();
  }
  // EOF: default

}
