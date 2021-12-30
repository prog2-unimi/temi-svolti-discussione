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

package it.unimi.di.prog2.temisvolti.bancarelle;

/**
 * Interfaccia che rappresenta un listino prezzi.
 *
 * Il listino indica il prezzo di un certo numero di giocattoli di dato tipo e
 * se è a conoscenza del prezzo di un certo giocattolo.
 */
public interface Listino {

  /**
   * Indica se il listino conosce il prezzo di un dato giocattolo.
   *
   * @param giocattolo il giocattolo.
   * @return se il listino conosce, o meno, il prezzo del giocattolo.
   */
  public boolean contiene(final Giocattolo giocattolo);

  /**
   * Indica il prezzo di un certo numero di giocattoli di un dato tipo.
   *
   * @param num il numero di giocattoli.
   * @param giocattolo il giocattolo.
   * @return il prezzo del dato numero di giocattoli indicati.
   * @throws NullPointerException se il giocattolo è <code>null</code>.
   * @throws IllegalArgumentException se il numero non è positivo.
   * @throws NoSuchElementException se il giocattolo non è noto al listino.
   */
  public int prezzo(final int num, final Giocattolo giocattolo);
}
