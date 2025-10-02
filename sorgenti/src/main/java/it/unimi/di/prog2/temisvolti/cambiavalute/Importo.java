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

package it.unimi.di.prog2.temisvolti.cambiavalute;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe immutabile che rappresenta un importo in una data valuta.
 *
 * <p>È possibile ottenere la {@link #somma(Importo) somma}, {@link #differenza(Importo) differenza}
 * e {@link #compareTo(Importo) comparazione} (in base al valore) di due importi se e solo se sono
 * espressi nella stessa valuta.
 */
public class Importo implements Comparable<Importo> {

  // SOF: rep
  /** Il valore dell'importo espresso in centesimi. */
  private final int centesimi;

  /** La valuta dell'importo, non può essere <code>null</code> */
  public final Valuta valuta;

  // EOF: rep

  // SOF: zeromap
  /** Mappa che associa a ciascuna valuta l'importo zero in tale valuta. */
  private static final Map<Valuta, Importo> ZERO = new EnumMap<>(Valuta.class);

  static {
    for (Valuta v : Valuta.values()) ZERO.put(v, new Importo(0, v));
  }

  // EOF: zeromap

  /**
   * Costruttore ad uso interno (non controlla che valuta sia diverso da <code>null</code>).
   *
   * @param centesimi i centesimi.
   * @param valuta la valuta.
   */
  private Importo(int centesimi, Valuta valuta) {
    this.centesimi = centesimi;
    this.valuta = valuta;
  }

  // SOF: valueOf
  /**
   * Metodo di fabbricazione che restituisce un importo data la sua rappresentazione come stringa.
   *
   * <p>Il <i>formato</i> di un importo è dato dal simbolo della valuta seguito senza spazi dal
   * valore dell'importo (eventualmente preceduto dal segno <samp>-</samp>), con la parte decimale
   * separata dalla parte dei centesimi da un punto; entrambe le parti sono opzionali e la parte dei
   * centesimi, se presente, deve essere lunga esattamente due caratteri.
   *
   * @param importo la string che descrive l'importo.
   * @return l'importo.
   * @throws NullPointerException se la stringa è <code>null</code>.
   * @throws IllegalArgumentException se la stringa non è nel formato corretto.
   */
  public static Importo valueOf(String importo) {
    Objects.requireNonNull(importo, "L'importo non può essere null.");
    Valuta valuta = Valuta.valueOf(importo.charAt(0));
    String[] parti = importo.substring(1).split("\\.");
    if (parti.length > 2)
      throw new IllegalArgumentException("L'importo contiene più di un punto decimale.");
    int centesimi = 0;
    try {
      centesimi = parti[0].isEmpty() ? 0 : Integer.parseInt(parti[0]) * 100;
      if (parti.length == 2)
        if (parti[1].length() == 2) centesimi += Integer.parseInt(parti[1]);
        else
          throw new IllegalArgumentException(
              "La parte dei centesimi deve essere lunga due caratteri.");
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("L'importo contiene parti non convertibili ad un numero.");
    }
    return new Importo(centesimi, valuta);
  }

  // EOF: valueOf

  // SOF: zero
  /**
   * Metodo di fabbricazione che restituisce l'importo nullo nella valuta assegnata.
   *
   * @param valuta la valuta.
   * @return l'importo.
   * @throws NullPointerException se la valuta è <code>null</code>.
   */
  public static Importo zero(Valuta valuta) {
    return ZERO.get(Objects.requireNonNull(valuta, "La valuta non può essere null."));
  }

  // EOF: zero

  // SOF: addsub
  /**
   * Metodo di produzione che restituisce l'importo risultante dalla somma tra questo importo ed un
   * altro importo dato.
   *
   * @param altro l'altro importo.
   * @return la somma tra questo e l'altro importo.
   * @throws NullPointerException se l'altro importo è <code>null</code>.
   * @throws IllegalArgumentException se l'altro importo non è espresso nella stessa valuta.
   */
  public Importo somma(Importo altro) {
    if (Objects.requireNonNull(altro, "L'importo non può essere null.").valuta != valuta)
      throw new IllegalArgumentException("L'importo deve essere nella stessa valuta.");
    return new Importo(centesimi + altro.centesimi, valuta);
  }

  /**
   * Metodo di produzione che restituisce l'importo risultante dalla differenza tra questo importo
   * ed un altro importo dato.
   *
   * @param altro l'altro importo.
   * @return la differenza tra questo e l'altro importo.
   * @throws NullPointerException se l'altro importo è <code>null</code>.
   * @throws IllegalArgumentException se l'altro importo non è espresso nella stessa valuta.
   */
  public Importo differenza(Importo altro) {
    if (Objects.requireNonNull(altro, "L'importo non può essere null.").valuta != valuta)
      throw new IllegalArgumentException("L'importo deve essere nella stessa valuta.");
    return new Importo(centesimi - altro.centesimi, valuta);
  }

  // EOF: addsub

  // SOF: conv
  /**
   * Metodo che consente di convertire questo importo in uno equivalente dato un tasso di cambio tra
   * la sua valuta ed un'altra.
   *
   * @param tasso il tasso di cambio.
   * @return l'importo convertito.
   * @throws NullPointerException se il tasso è <code>null</code>.
   * @throws IllegalArgumentException se il tasso non va dalla valuta di questo importo.
   */
  public Importo equivalente(Cambi.Tasso tasso) {
    if (Objects.requireNonNull(tasso, "Il tasso non può essere null.").da().valuta != valuta)
      throw new IllegalArgumentException("Il tasso non parte dalla valuta di questo importo.");
    return new Importo((centesimi / tasso.da().centesimi) * tasso.a().centesimi, tasso.a().valuta);
  }

  // EOF: conv

  // SOF: zp
  /**
   * Metodo che consente di stabilire se questo importo ha valore zero.
   *
   * @return se questo importo vale zero.
   */
  public boolean isZero() {
    return centesimi == 0;
  }

  /**
   * Metodo che consente di stabilire se questo importo ha valore strettamente positivo.
   *
   * @return se questo importo ha valore strettamente positivo.
   */
  public boolean isPositive() {
    return centesimi > 0;
  }

  // EOF: zp

  @Override
  public String toString() {
    return String.format("%c%d.%02d", valuta.simbolo, centesimi / 100, centesimi % 100);
  }

  // SOF: ehc
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Importo)) return false;
    final Importo altro = (Importo) obj;
    return centesimi == altro.centesimi && valuta == altro.valuta;
  }

  @Override
  public int hashCode() {
    return Objects.hash(centesimi, valuta);
  }

  @Override
  public int compareTo(Importo o) {
    if (o.valuta != valuta)
      throw new ClassCastException("Non è possibile confrontare importi di valute diverse.");
    return Integer.compare(centesimi, o.centesimi);
  }
  // EOF: ehc

}
