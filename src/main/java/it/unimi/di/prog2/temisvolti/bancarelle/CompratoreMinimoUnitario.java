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

import java.util.List;
import java.util.Objects;

/**
 * Classe concreta che rappresenta un compratore che acquista da ciascuna bancarella il massimo
 * numero di giocattoli scegliendo per prime le bancarelle che offrono il minor prezzo unitario.
 */
public class CompratoreMinimoUnitario extends AbstractCompratore {

  /**
   * Costruisce un compratore.
   *
   * @param bancarelle le bancarelle.
   * @see AbstractCompratore
   */
  public CompratoreMinimoUnitario(List<Bancarella> bancarelle) {
    super(bancarelle);
  }

  @Override
  public Acquisto compra(int num, Giocattolo giocattolo) {
    Objects.requireNonNull(giocattolo);
    if (num <= 0) throw new IllegalArgumentException("Il numero deve essere positivo");
    if (quantità(giocattolo) < num)
      throw new IllegalArgumentException("Non ci sono abbastanza: " + giocattolo);
    final Acquisto acquisto = new Acquisto(giocattolo);
    int rimanenti = num;
    while (rimanenti > 0) {
      int daComprare, minUnitario = Integer.MAX_VALUE;
      Bancarella min = null;
      for (final Bancarella b : bancarelle) {
        daComprare = Math.min(b.quanti(giocattolo), rimanenti);
        if (daComprare == 0) continue;
        int unitario = b.prezzo(daComprare, giocattolo) / daComprare;
        if (unitario < minUnitario) {
          min = b;
          minUnitario = unitario;
        }
      }
      daComprare = Math.min(min.quanti(giocattolo), rimanenti);
      min.vende(daComprare, giocattolo);
      acquisto.aggiungi(daComprare, min);
      rimanenti -= daComprare;
    }
    return acquisto;
  }
}
