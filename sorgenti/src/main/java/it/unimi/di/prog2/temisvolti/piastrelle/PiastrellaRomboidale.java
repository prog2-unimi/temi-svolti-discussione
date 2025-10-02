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
 * Classe immutabile che rappresenta una <i>piastrella romboidale</i>, lo stato è costituito dalla
 * lunghezza (sempre positiva) delle sue diagonali <i>minore</i> e <i>maggiore</i>.
 */
public class PiastrellaRomboidale extends Piastrella {

  /** La diagonale minore, è sempre positiva (e non maggiore della diagonale maggiore). */
  public final int minore;

  /** La diagonale maggiore, è sempre positiva (e non minore della diagonale minore). */
  public final int maggiore;

  /**
   * Costruisce una piastrella dato il suo <i>costo</i> e la lunghezza delle due <i>diagonali</i>;
   * non è necessario specificare le diagonali in ordine di grandezza.
   *
   * @param prima una delle diagonali.
   * @param seconda l'altra diagonale.
   * @param costoUnitario il costo.
   * @throws IllegalArgumentException se il costo, o una delle diagonali, non sono positivi.
   */
  public PiastrellaRomboidale(final int prima, final int seconda, final int costoUnitario) {
    super(costoUnitario);
    if (prima <= 0) throw new IllegalArgumentException("La prima diagonale dev'essere positiva.");
    if (seconda <= 0)
      throw new IllegalArgumentException("La seconda diagonale dev'essere positiva.");
    // SOF: rappr
    if (prima < seconda) {
      minore = prima;
      maggiore = seconda;
    } else {
      minore = seconda;
      maggiore = prima;
    }
    // EOF: rappr
  }

  @Override
  public int superficie() {
    return (minore * maggiore) / 2;
  }
}
