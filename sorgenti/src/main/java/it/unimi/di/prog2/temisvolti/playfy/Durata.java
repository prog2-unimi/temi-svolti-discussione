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

package it.unimi.di.prog2.temisvolti.playfy;

import java.util.Objects;

/**
 * Record (immutabile) che rappresenta una durata (non negativa).
 *
 * <p>Le istanze possono essere costruite a partire dalla durata espressa in secondi, oppure tramite
 * un metodo di fabbricazione che accetta stringhe del formato <samp>HH:MM:SS</samp>.
 */
public record Durata(int secondi) {

  /**
   * Costruisce una durata.
   *
   * @param secondi la durata espressa in secondin.
   * @throws IllegalArgumentException se la durata è negativa.
   */
  public Durata {
    if (secondi < 0) throw new IllegalArgumentException("La durata non può essere negativa.");
  }

  /**
   * Metodo di utilità che effettua la conversione da una componente della stringa
   * <samp>HH:MM:SS</samp> al valore numerico corrispondete.
   *
   * <p>Questo metodo può essere usato per convertire una delle parti ottenute dividendo la stringa
   * <samp>HH:MM:SS</samp> ove occorre il carattere <samp>:</samp>, il parametro <code>bounded
   * </code> consente di specificare se il massimo valore accettabile sia 60 (per le componenti
   * <samp>MM</samp> e <samp>SS</samp>) o non se c'è limite (per la componente <samp>HH</samp>).
   *
   * @param componente la componente dell'orario.
   * @param bounded se il il valore numerico della componente dev'essere minore di 60.
   * @return il valore numerico della componente.
   * @throws NullPointerException se la componente è <code>null</code>.
   * @throws IllegalArgumentException se la componente è vuota, se non può essere converita in un
   *     intero, se il suo valore non è compreso tra 0 (compreso) e il bound specificato (escluso).
   */
  // SOF: util
  private static int toHMS(final String componente, final boolean bounded) {
    if (Objects.requireNonNull(componente, "La componente non può essere null.").isEmpty())
      throw new IllegalArgumentException("La componente non può essere vuota.");
    int hms;
    try {
      hms = Integer.parseInt(componente);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("La componente \"" + componente + "\" non è un numero.");
    }
    if (hms < 0)
      throw new IllegalArgumentException(
          "Il valore della componente \"" + componente + "\" non può essere negativo.");
    if (bounded && hms > 59)
      throw new IllegalArgumentException(
          "Il valore della componente \"" + componente + "\" deve essere minore di 60.");
    return hms;
  }
  // EOF: util

  /**
   * Fabbrica una durata data una stringa del formato <samp>HH:MM:SS</samp>, <samp>MM:SS</samp> o
   * <samp>SS</samp>.
   *
   * @param durata la durata nel formato specificato.
   * @return una {@link Durata}.
   * @throws NullPointerException se la durata è <code>null</code>.
   * @throws IllegalArgumentException se la stringa è nulla, o non è nel formato specificato.
   */
  // SOF: fab
  static Durata valueOf(final String durata) {
    if (Objects.requireNonNull(durata, "La durata non può essere null.").isEmpty())
      throw new IllegalArgumentException("La durata non può essere vuota.");
    final String[] parti = durata.split(":");
    final int numParti = parti.length;
    if (numParti > 3)
      throw new IllegalArgumentException("L'orario non può comprendere \":\" più di due volte.");
    try {
      // SOF: conv
      int ore = numParti < 3 ? 0 : toHMS(parti[numParti - 3], false);
      int minuti = numParti < 2 ? 0 : toHMS(parti[numParti - 2], true);
      int secondi = toHMS(parti[numParti - 1], true);
      return new Durata(3600 * ore + 60 * minuti + secondi);
      // EOF: conv
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Formato della durata invalito. " + e.getMessage());
    }
  }
  // EOF: fab

  /**
   * Restituisce una nuova durata pari alla somma di questa durata con l'argomento.
   *
   * @param altra la durata da sommare a questa.
   * @return la somma delle durate.
   * @throws NullPointerException se la durata è <code>null</code>.
   */
  // SOF: sum
  public Durata somma(final Durata altra) {
    return new Durata(
        this.secondi + Objects.requireNonNull(altra, "La durata non può essere null.").secondi);
  }
  // EOF: sum

  /**
   * Restituisce una nuova durata pari alla differenza tra questa durata con l'argomento.
   *
   * @param altra la durata da sottrarre da questa.
   * @return la differenza delle durate.
   * @throws NullPointerException se la durata è <code>null</code>.
   * @throws IllegalArgumentException se l'altra durata è maggiore di questa.
   */
  // SOF: diff
  public Durata sottrai(final Durata altra) {
    return new Durata(
        this.secondi - Objects.requireNonNull(altra, "La durata non può essere null.").secondi);
  }
  // EOF: diff

  @Override
  // SOF: toString
  public String toString() {
    final int hh = secondi / 3600;
    return String.format("%s%02d:%02d", hh > 0 ? hh + ":" : "", (secondi / 60) % 60, secondi % 60);
  }
  // EOF: toString

}
