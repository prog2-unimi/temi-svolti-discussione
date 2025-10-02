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

/**
 * Classe astratta e immutabile che rappresenta una <i>piastrella</i>; è una implementa parzialmente
 * l'interfaccia {@link Rivestimento}, il cui stato è dato dal <i>costo</i>.
 */
public abstract class Piastrella implements Rivestimento {

  /** Il costo della piastrella, è sempre positivo. */
  private final int costo;

  /**
   * Costruisce una piastrella dato il suo costo.
   *
   * @param costo il costo.
   * @throws IllegalArgumentException se il costo non è positivo.
   */
  public Piastrella(final int costo) {
    // SOF: rappr
    if (costo <= 0) throw new IllegalArgumentException("Il costo dev'essere positivo.");
    // EOF: rappr
    this.costo = costo;
  }

  @Override
  public int costo() {
    return costo;
  }
}
