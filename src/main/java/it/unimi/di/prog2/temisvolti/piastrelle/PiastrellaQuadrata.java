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

package it.unimi.di.prog2.temisvolti.piastrelle;

/**
 * Una <em>piastrella quadrata</em> immutabile, la cui rappresentazione è costituita dalla lunghezza
 * (sempre positiva) del suo <em>lato</em> (memorizzata in un attributo intero, non modificabile e
 * pubblico, fatto che rende non necessario un <em>getter</em>).
 */
public class PiastrellaQuadrata extends Piastrella {

  /** Il lato della piastrella, è sempre positivo. */
  public final int lato;

  /**
   * Costruisce una piastrella dato il suo <em>costo</em> e <em>lato</em>.
   *
   * @param lato il lato.
   * @param costo il costo.
   * @throws IllegalArgumentException se il costo, o il lato, non sono positivi.
   */
  public PiastrellaQuadrata(final int lato, final int costo) {
    super(costo);
    if (lato <= 0) throw new IllegalArgumentException("Il lato dev'essere positivo.");
    this.lato = lato;
  }

  @Override
  public int superficie() {
    return lato * lato;
  }
}
