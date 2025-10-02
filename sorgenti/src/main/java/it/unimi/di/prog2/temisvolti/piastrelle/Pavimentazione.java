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

package it.unimi.di.prog2.temisvolti.piastrelle;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Classe immutabile che rappresenta una <i>pavimentazione</i> data da una collezione di {@link
 * Componente} ciascuno dei quali rappresenta una certa <i>quantità</i> di un <i>rivestimento</i>
 * (sia esso una <i>piastrella</i> o un'altra <i>pavimentazione</i>); è possibile accedere al suo
 * contenuto tramite <i>iterazione</i>.
 */
public class Pavimentazione implements Rivestimento, Iterable<Pavimentazione.Componente> {

  // SOF: componente
  /**
   * Un <i>componente</i> di una <i>pavimentazione</i>, ossia una certa <i>quantità</i> di un dato
   * <i>rivestimento</i>.
   */
  public static class Componente implements Rivestimento {

    /** Il rivestimento di cui è costituito questo componente, non è mai {@code null}. */
    public final Rivestimento rivestimento;

    /** La quantità di rivestimenti di cui è costituito il componente, è sempre positiva. */
    public final int quantità;

    /**
     * Costruisce una pavimentazione, data una <i>quantità</i> del <i>rivestimento</i> che lo
     * costituisce. Implementa {@link Rivestimento} nel modo ovvio: costo e superficie sono ottenuti
     * moltiplicando quelle del rivestimento per la quantità in cui è presente.
     *
     * @param quantità la quantità.
     * @param rivestimento il rivestimento.
     * @throws IllegalArgumentException se la quantità non è positiva, o il rivestimento è {@code
     *     null}.
     */
    public Componente(final int quantità, final Rivestimento rivestimento) {
      this.rivestimento =
          Objects.requireNonNull(rivestimento, "Il rivestimento non può essere null.");
      if (quantità <= 0) throw new IllegalArgumentException("La quantità dev'essere positiva");
      this.quantità = quantità;
    }

    // SOF: componente_rivestimento
    @Override
    public int costo() {
      return quantità * rivestimento.costo();
    }

    @Override
    public int superficie() {
      return quantità * rivestimento.superficie();
    }
    // EOF: componente_rivestimento

  }

  // EOF: componente

  // SOF: rappr
  /**
   * La collezione di componenti compresi in questa pavimentazione, non è {@code nulL}, non è vuota
   * e non contiene {@code null}.
   */
  private final Collection<Componente> componenti;

  /**
   * Costruisce una pavimentazione data la collezione di componenti che comprende.
   *
   * @param componenti una collezione di componenti.
   * @throws NullPointerException se la collezione è o contiene {@code null}
   * @throws IllegalArgumentException se la collezione è vuota.
   */
  public Pavimentazione(final Collection<Componente> componenti) {
    // SOF: copyof
    this.componenti = List.copyOf(componenti);
    // EOF: copyof
    if (componenti.isEmpty())
      throw new IllegalArgumentException("Ci deve essere sempre almeno una componente.");
  }

  // EOF: rappr

  // SOF: rivestimento
  @Override
  public int costo() {
    int totale = 0;
    for (final Rivestimento r : componenti) totale += r.costo();
    return totale;
  }

  @Override
  public int superficie() {
    int totale = 0;
    for (final Rivestimento r : componenti) totale += r.superficie();
    return totale;
  }

  // EOF: rivestimento

  // SOF: iterable
  @Override
  public Iterator<Pavimentazione.Componente> iterator() {
    return componenti.iterator();
  }
  // EOF: iterable
}
