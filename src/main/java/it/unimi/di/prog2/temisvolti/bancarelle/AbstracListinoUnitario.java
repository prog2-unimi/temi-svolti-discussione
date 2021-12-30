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

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Classe astratta che rappresenta i listini in cui il prezzo di un certo numero di giocattoli di un
 * certo tipo dipende dal prezzo unitario di un giocattolo di tale tipo.
 */
public abstract class AbstracListinoUnitario implements Listino {

  /** Mappa che tiene traccia del costo prezzo unitario di ciascun giocattolo noto al listino. */
  private final Map<Giocattolo, Integer> prezzoUnitario;

  // RI: le chiavi sono diverse da null, i valori sono positivi.

  /**
   * Costruisce un listino data una mappa da giocattoli al loro prezzo unitario.
   *
   * @param prezzoUnitario mappa avente per chiav ii giocattoli e per valori i prezzi unitari.
   * @throws NullPointerException se la mappa è, o contiene chiavi o valori <code>null</code>.
   * @throws IllegalArgumentException se uno dei prezzi non è postivio.
   */
  public AbstracListinoUnitario(final Map<Giocattolo, Integer> prezzoUnitario) {
    this.prezzoUnitario = new HashMap<>();
    for (Map.Entry<Giocattolo, Integer> e : prezzoUnitario.entrySet()) {
      final Giocattolo giocattolo = Objects.requireNonNull(e.getKey());
      final Integer num = Objects.requireNonNull(e.getValue());
      if (num <= 0)
        throw new IllegalArgumentException("Il prezzp di " + giocattolo + " deve essere positivo");
      this.prezzoUnitario.put(giocattolo, num);
    }
  }

  /**
   * Restituisce il prezzo unitario di un oggetto.
   *
   * @param giocattolo il giocattolo.
   * @return il suo prezzo unitario.
   * @throws NullPointerException se il giocattolo è <code>null</code>
   * @throws NoSuchElementException se il listino ignora il prezzo del giocattolo.
   */
  public int prezzoUnitario(final Giocattolo giocattolo) {
    Objects.requireNonNull(giocattolo);
    final Integer prezzo = prezzoUnitario.get(giocattolo);
    if (prezzo == null) throw new NoSuchElementException("Giocattolo non trovato: " + giocattolo);
    return prezzo;
  }

  @Override
  public boolean contiene(final Giocattolo giocattolo) {
    return prezzoUnitario.containsKey(Objects.requireNonNull(giocattolo));
  }

  @Override
  public abstract int prezzo(final int num, final Giocattolo giocattolo);
}
