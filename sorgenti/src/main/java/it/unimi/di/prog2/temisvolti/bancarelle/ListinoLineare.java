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

package it.unimi.di.prog2.temisvolti.bancarelle;

import java.util.Map;

/**
 * Classe concreta che implementa un listino in cui il prezzo totale di <samp>n</samp> giocattoli di
 * un dato tipo è pari a <samp>n</samp> volte il prezzo unitario di tale giocattolo.
 */
public class ListinoLineare extends AbstracListinoUnitario {

  /**
   * Costruisce un listino a partire da una mappa tra giocattoli e prezzi unitari.
   *
   * @param prezzoUnitario la mappa.
   * @see AbstracListinoUnitario
   */
  public ListinoLineare(final Map<Giocattolo, Integer> prezzoUnitario) {
    super(prezzoUnitario);
  }

  // SOF: override
  @Override
  public int prezzo(final int num, final Giocattolo giocattolo) {
    if (num <= 0) throw new IllegalArgumentException("Il numero deve essere positivo");
    return prezzoUnitario(giocattolo) * num;
  }
  // EOF: override
}
