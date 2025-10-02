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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * Classe concreta che rappresenta un compratore che acquista da ciascuna bancarella il massimo
 * numero di giocattoli scegliendo per prime le bancarelle che offrono il minor prezzo unitario.
 */
public class CompratoreCasuale extends AbstractCompratore {

  /** Il generatore di numeri casuali usato da questa classe. */
  private final Random rng = new Random();

  /**
   * Costruisce un compratore (permettendo di specificare il seme del generatore, per consentire la
   * riproducibilità dell'esecuzione).
   *
   * @param bancarelle le bancarelle.
   * @param seed il seme del generatore casuale.
   * @see AbstractCompratore
   */
  public CompratoreCasuale(Set<Bancarella> bancarelle, final long seed) {
    super(bancarelle);
    rng.setSeed(seed);
  }

  /**
   * Costruisce un compratore (il seme è dato dal tempo corrente all'esecuzione).
   *
   * @param bancarelle le bancarelle.
   * @see AbstractCompratore
   */
  public CompratoreCasuale(Set<Bancarella> bancarelle) {
    this(bancarelle, System.currentTimeMillis());
  }

  // SOF: compra
  @Override
  public Acquisto compra(int num, Giocattolo giocattolo) {
    Objects.requireNonNull(giocattolo, "Il giocattolo non può essere null.");
    if (num <= 0) throw new IllegalArgumentException("Il numero deve essere positivo");
    if (quantità(giocattolo) < num)
      throw new IllegalArgumentException("Non ci sono abbastanza: " + giocattolo);
    final Acquisto acquisto = new Acquisto(giocattolo);
    int rimanenti = num;
    // SOF: rng
    final List<Bancarella> aCaso = new ArrayList<>(bancarelle);
    Collections.shuffle(aCaso, rng);
    // EOF: rng
    for (final Bancarella b : aCaso) {
      if (rimanenti == 0) break;
      final int daComprare = Math.min(b.quantità(giocattolo), rimanenti);
      if (daComprare == 0) continue;
      b.vende(daComprare, giocattolo);
      acquisto.aggiungi(daComprare, b);
      rimanenti -= daComprare;
    }
    return acquisto;
  }
  // EOF: compra

}
