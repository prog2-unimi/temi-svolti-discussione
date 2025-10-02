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

package it.unimi.di.prog2.temisvolti.bancarelle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/**
 * Descrizione di un possibile acquisto di un giocattolo effettuato tra varie bancarelle.
 *
 * <p>È un {@link Iterable} delle bancarelle coinvolte.
 */
public class Acquisto implements Iterable<Bancarella> {

  // SOF: rep
  /** Il giocattolo acquistato. */
  public final Giocattolo giocattolo;

  /** Una mappa che associa a ciascuna bancarella il numero di giocattoli acquistati da essa. */
  private final Map<Bancarella, Integer> descrizione;

  /** Il prezzo totale e la quantità complessiva di giocattoli nell'acquisto. */
  private int prezzo = 0, quantità = 0;

  // EOF: rep

  // RI: giocatolo e descrizione non sono nulli, la mappa non contiene null e
  // gli interi sono tutti positivi; quantità ha per valore la somma degli
  // interi della mappa e prezzo la somma dei prezzi (di ciascuna bancarella, a
  // fronte di un acquisto del numero di giocattoli acquistati presso di essa).

  /**
   * Costruisce un acquisto del dato giocattolo.
   *
   * @param giocattolo il giocattolo da acquistare.
   * @throws NullPointerException se il giocattolo è {@code null}.
   */
  public Acquisto(final Giocattolo giocattolo) {
    this.giocattolo = Objects.requireNonNull(giocattolo, "Il giocattolo non può essere null.");
    this.descrizione = new HashMap<>();
  }

  // SOF: mod
  /**
   * Aggiunge alla descrizione dell'acquisto l'intenzione di comprare un certo numero di giocattoli
   * da una data bancarella.
   *
   * @param num il numero di giocattoli.
   * @param bancarella la bancarella.
   * @throws NullPointerException se la bancarella è {@code null}.
   * @throws IllegalArgumentException se il numero non è positivo, o la bancarella è già presente
   *     nella descrizione.
   */
  public void aggiungi(final int num, final Bancarella bancarella) {
    if (num <= 0) throw new IllegalArgumentException("Il numero deve essere positivo");
    Objects.requireNonNull(bancarella, "La bancarella non può essere null.");
    // SOF: ri
    if (descrizione.containsKey(bancarella))
      throw new IllegalArgumentException("La bancarella è già elencata nell'acquisto.");
    prezzo += bancarella.prezzo(num, giocattolo);
    quantità += num;
    descrizione.put(bancarella, num);
    // EOF: ri
  }

  // EOF: mod

  // SOF: tivialobs
  /**
   * Restituisce il prezzo complessivo dell'acquisto.
   *
   * @return il prezzo.
   */
  public int prezzo() {
    return prezzo;
  }

  /**
   * Restituisce la quantità totale di giocattoli acquistati.
   *
   * @return la quantità.
   */
  public int quantità() {
    return quantità;
  }

  // EOF: tivialobs

  // SOF: obs
  /**
   * Restituisce la quantità di giocattoli da acquistare dalla data bancarella.
   *
   * @param bancarella la bancarella.
   * @return la quantità di giocattoli da acquistare.
   * @throws NullPointerException se la bancarella è {@code null}.
   * @throws NoSuchElementException se l'acquisto non riguarda la bancarella specificata.
   */
  public int quantità(final Bancarella bancarella) {
    Objects.requireNonNull(bancarella, "La bancarella non può essere null.");
    if (!descrizione.containsKey(bancarella))
      throw new NoSuchElementException("L'acquisto non riguarda la bancarella specificata.");
    return descrizione.get(bancarella);
  }

  @Override
  public Iterator<Bancarella> iterator() {
    Set<Bancarella> bancarelle = Collections.unmodifiableSet(descrizione.keySet());
    return bancarelle.iterator();
  }

  // EOF: obs

  @Override
  public String toString() {
    final StringBuilder result = new StringBuilder();
    result.append(
        "Acquisto di: "
            + giocattolo
            + ", per un costo di: "
            + prezzo
            + ", numero: "
            + quantità
            + " di cui:\n");
    final List<Bancarella> bancarelle = new ArrayList<>(descrizione.keySet());
    Collections.sort(
        bancarelle,
        new Comparator<Bancarella>() {
          @Override
          public int compare(final Bancarella o1, final Bancarella o2) {
            return o1.proprietario.compareTo(o2.proprietario);
          }
        });
    for (final Bancarella b : bancarelle)
      result.append(descrizione.get(b) + " da " + b.proprietario + "\n");
    return result.toString();
  }
}
