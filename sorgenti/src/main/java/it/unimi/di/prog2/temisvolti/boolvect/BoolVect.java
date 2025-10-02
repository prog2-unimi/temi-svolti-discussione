/*

Copyright 2025 Massimo Santini

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
   * Restituisce la <i>dimensione</i> del BoolVect.
   *
   * <p>La <i>dimensione</i> del BoolVect è il più grande intero <i>d</i> tale che il valore di
   * verità in posizione <i>d - 1</i> del BoolVect è è {@code true}. La <i>dimensione</i> ha un
   * valore compreso tra 0 e la <i>taglia</i> (estremi inclusi).
   *
   * @return la dimensione.
   */
  int dimensione();

  /**
   * Restituisce la <i>taglia</i> del BoolVect.
   *
   * <p>La <i>taglia</i> del BoolVect è il massimo valore possibile per la sua <i>dimensione</i>;
   * detto altrimenti, è il più grande intero <i>d</i> per cui il valore di verità di posizione <i>d
   * - 1</i> può essere {@code true}. La <i>taglia</i> è un numero positivo sempre maggiore o uguale
   * alla <i>dimensione</i> e vale convenzionalmente {@link Integer#MAX_VALUE} se la
   * <i>dimensione</i> non è limitata.
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
   * Rende questo BoolVect uguale all'<i>and componente a componente</i> di questo BoolVect e quello
   * specificato.
   *
   * <p>Si osservi che, sebbene la dimensione degli operandi può essere diversa, in nessun caso la
   * dimensione del risultato può eccedere la minore tra le due; l'<i>and</i> infatti è senz'altro
   * {@code false} per tutte le posizioni che sono maggiori della dimensione di uno, o dell'altro,
   * BoolVect booleano.
   *
   * @param other l'altro BoolVect.
   * @throws NullPointerException se l'argomento è {@code null}.
   */
  void and(final BoolVect other) throws NullPointerException;

  /**
   * Rende questo BoolVect uguale all'<i>or componente a componente</i> di questo BoolVect e quello
   * specificato.
   *
   * <p>Si osservi che se la dimensione dell'altro BoolVect è maggiore della taglia (e quindi
   * dimensione) di questo, allora il valore di verità in posizione pari alla dimensione dell'altro
   * BoolVect sarà {@code true}, ma non potrà essere memorizzato in questo BoolVect per via della
   * sua taglia.
   *
   * @param other l'altro BoolVect.
   * @throws IllegalArgumentException se la taglia di questo BoolVect è minore della dimensione del
   *     risultato.
   * @throws NullPointerException se l'argomento è {@code null}.
   */
  void or(final BoolVect other) throws NullPointerException, IllegalArgumentException;

  /**
   * Rende questo BoolVect uguale allo <i>xor componente a componente</i> di questo BoolVect e
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
   * @throws NullPointerException se l'argomento è {@code null}.
   */
  void xor(final BoolVect other) throws NullPointerException, IllegalArgumentException;

  // EOF: bop

  // SOF: init
  /** Rende {@code false} tutti i valori di verità del BoolVect. */
  void pulisci();

  /**
   * Rende il BoolVect uguale ai valori di verità specificati nella stringa data.
   *
   * <p>La stringa può contenere qualunque carattere, l'<i>i</i>-esimo valore di verità del BoolVect
   * sarà {@code true} se e solo se l'<i>i</i>-esimo carattere della stringa (contando da destra) è
   * `V`.
   *
   * @param vals la stringa dei valori di verità.
   * @throws IllegalArgumentException se la stringa è più lunga della taglia del BoolVect.
   * @throws NullPointerException se l'argomento è {@code null}.
   */
  default void daString(final String vals) throws NullPointerException, IllegalArgumentException {
    pulisci();
    final int len = vals.length();
    for (int i = 0; i < len; i++) scrivi(i, vals.charAt(len - i - 1) == 'V');
  }
  // EOF: init
}
