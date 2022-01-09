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
 * Una <em>piastrella triangolare</em> immutabile, la cui rappresentazione è costituita dalla
 * lunghezza (sempre positiva) della sua <em>base</em> ed <em>altezza</em> (memorizzate in due
 * attributi intero, non modificabili e pubblici, fatto che rende non necessari i <em>getter</em>)..
 */
public class PiastrellaTriangolare extends Piastrella {

  /** La base, è sempre positiva. */
  public final int base;

  /** L'altezza, è sempre positiva. */
  public final int altezza;

  /**
   * Costruisce una piastrella dato il suo <em>costo</em> e la lunghezza della sua <em>base</em> e
   * <em>altezza</em>.
   *
   * @param base la base.
   * @param altezza l'altezza.
   * @param costo il costo.
   * @throws IllegalArgumentException se il costo, la base, o l'altezza, non sono positivi.
   */
  public PiastrellaTriangolare(final int base, final int altezza, final int costo) {
    super(costo);
    if (base <= 0) throw new IllegalArgumentException("La base dev'essere positiva.");
    if (altezza <= 0) throw new IllegalArgumentException("L'altezza dev'essere positiva.");
    this.base = base;
    this.altezza = altezza;
  }

  @Override
  public int superficie() {
    return (base * altezza) / 2;
  }
}
