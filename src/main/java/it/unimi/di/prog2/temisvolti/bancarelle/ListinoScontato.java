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

import java.util.Map;

/**
 * Classe concreta che implementa un listino in cui il prezzo totale di
 * <samp>n</samp> giocattoli di un dato tipo è pari a <samp>n</samp> volte il
 * prezzo unitario di tale giocattolo (eventualmente scontato per i valori di
 * <samp>n</samp> che eccedono una certa soglia).
 *
 * <p>Ad esempio, se la soglia fosse 20 e lo sconto 30%, acquistando un giocattolo di
 * prezzo unitario 12, i primi 20 giocattoli avrebbero un prezzo di 12 l'uno, quelli dal 21
 * in poi avrebbero prezzo pari a 8 (che è 12 a cui viene applicata una riduzione del
 * 30%).
 *
 */
public class ListinoScontato extends AbstracListinoUnitario {

  /** La soglia e lo sconto che caratterizzano questo scontrino. */
  private final int soglia, sconto;

  // RI: 0 < soglia e 0 < sconto < 100

  /**
   * Costruisce un listino a partire da una mappa tra giocattoli e prezzi
   * unitari e i valori di soglia e sconto.
   *
   * @param prezzoUnitario la mappa.
   * @param soglia la soglia.
   * @param sconto lo sconto.
   * @throws IllegalArgumentException se la soglia non è positiva, o lo sconto
   * non è compreso tra 1 e 100 (estremi inclusi).
   * @see AbstracListinoUnitario
   */
  public ListinoScontato(
      final Map<Giocattolo, Integer> prezzoUnitario, final int soglia, final int sconto) {
    super(prezzoUnitario);
    if (soglia <= 0) throw new IllegalArgumentException("La soglia deve essere positiva.");
    this.soglia = soglia;
    if (sconto < 1 || sconto > 100) throw new IllegalArgumentException("Lo sconto dev'essere compreso tra 1 e 100.");
    this.sconto = sconto;
  }

  @Override
  public int prezzo(int num, Giocattolo giocattolo) {
    if (num <= 0) throw new IllegalArgumentException("Il numero deve essere positivo");
    if (num < soglia) return prezzoUnitario(giocattolo) * num;
    else {
      int p = prezzoUnitario(giocattolo);
      return soglia * p + (int) (((num - soglia) * p * (100 - sconto)) / 100.0);
    }
  }
}
