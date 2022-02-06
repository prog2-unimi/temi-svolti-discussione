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

/** Interfaccia che stabilisce li contratto dei BoolVect. */
public interface BoolVect {

  // SOF: obs
  /**
   * Restituisce la <em>dimensione</em> del BoolVect.
   *
   * <p>La <em>dimensione</em> del BoolVect è il più grande intero <em>d</em> tale che il valore di
   * verità in posizione <em>d - 1</em> del BoolVect è è {@code true}. La <em>dimensione</em> ha un
   * valore compreso tra 0 e la <em>taglia</em> (estremi inclusi).
   *
   * @return la dimensione.
   */
  int dimensione();

  /**
   * Restituisce la <em>taglia</em> del BoolVect.
   *
   * <p>La <em>taglia</em> del BoolVect è il massimo valore possibile per la sua
   * <em>dimensione</em>; detto altrimenti, è il più grande intero <em>d</em> per cui il valore di
   * verità di posizione <em>d - 1</em> può essere {@code true}. La <em>taglia</em> è un numero
   * positivo sempre maggiore o uguale alla <em>dimensione</em> e vale convenzionalmente {@link
   * Integer#MAX_VALUE} se la <em>dimensione</em> non è limitata.
   *
   * @return la taglia.
   */
  int taglia();

  /**
   * Legge il valore di verità di posizione specificata.
   *
   * <p>Se la posizione eccede la dimensione (o la taglia) verrà restituito convenzionalmente il
   * valore {@code false}.
   *
   * @param pos la posizione.
   * @return il valore di verità.
   * @throws IndexOutOfBoundsException se la posizione è negativa.
   */
  boolean leggi(final int pos) throws IndexOutOfBoundsException;
  // EOF: obs

  // SOF: write
  /**
   * Scrive il valore di verità dato nella posizione specificata.
   *
   * @param pos la posizione.
   * @param val il valore di verità
   * @throws IndexOutOfBoundsException se la posizione è negativa, o il valore è {@code true} e la
   *     posizione è maggiore o uguale alla taglia.
   */
  void scrivi(final int pos, final boolean val) throws IndexOutOfBoundsException;
  // EOF: write

  // SOF: bop
  /**
   * Rende questo BoolVect uguale all'<em>and componente a componente</em> di questo BoolVect e
   * quello specificato.
   *
   * <p>Si osservi che, sebbene la dimensione degli operandi può essere diversa, in nessun caso la
   * dimensione del risultato può eccedere la minore tra le due; l'<em>and</em> infatti è senz'altro
   * {@code false} per tutte le posizioni che sono maggiori della dimensione di uno, o dell'altro,
   * BoolVect booleano.
   *
   * @param other l'altro BoolVect.
   * @throws NullPointerException se l'agromento è {@code null}.
   */
  void and(final BoolVect other) throws NullPointerException;

  /**
   * Rende questo BoolVect uguale all'<em>or componente a componente</em> di questo BoolVect e
   * quello specificato.
   *
   * <p>Si osservi che se la dimensione dell'altro BoolVect è maggiore della taglia (e quindi
   * dimensione) di questo, allora il valore di verità in posizione pari alla dimensione dell'altro
   * BoolVect sarà {@code true}, ma non potrà essere memorizzato in questo BoolVect per via della
   * sua taglia.
   *
   * @param other l'altro BoolVect.
   * @throws IllegalArgumentException se la taglia di questo BoolVect è minore della dimensione del
   *     risultato.
   * @throws NullPointerException se l'agromento è {@code null}.
   */
  void or(final BoolVect other) throws NullPointerException, IllegalArgumentException;

  /**
   * Rende questo BoolVect uguale allo <em>xor componente a componente</em> di questo BoolVect e
   * quello specificato.
   *
   * <p>Si osservi che se la dimensione dell'altro BoolVect è maggiore della taglia (e quindi
   * dimensione) di questo, allora il valore di verità in posizione pari alla dimensione dell'altro
   * BoolVect sarà {@code true}, ma non potrà essere memorizzato in questo BoolVect per via della
   * sua taglia.
   *
   * @param other l'altro BoolVect.
   * @throws IllegalArgumentException se la taglia di questo BoolVect è minore della dimensione del
   *     risultato.
   * @throws NullPointerException se l'agromento è {@code null}.
   */
  void xor(final BoolVect other) throws NullPointerException, IllegalArgumentException;
  // EOF: bop

  /** Rende {@code false} tutti i valori di verità del BoolVect. */
  void pulisci();

  // SOF: daString
  /**
   * Rende il BoolVect uguale ai valori di verità specificati nella stringa data.
   *
   * <p>La stringa può contenere qualunque carattere, l'<em>i</em>-esimo valore di verità del
   * BoolVect sarà {@code true} se e solo se l'<em>i</em>-esimo carattere della stringa (contando da
   * destra) è `V`.
   *
   * @param vals la stringa dei valori di verità.
   * @throws IllegalArgumentException se la stringa è più lunga della taglia del BoolVect.
   * @throws NullPointerException se l'agromento è {@code null}.
   */
  default void daString(final String vals) throws NullPointerException, IllegalArgumentException {
    pulisci();
    final int dim = vals.length();
    for (int i = 0; i < dim; i++) scrivi(i, vals.charAt(dim - i - 1) == 'V');
  }
  // EOF: daString
}
