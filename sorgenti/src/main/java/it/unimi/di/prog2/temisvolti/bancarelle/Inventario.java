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

/**
 * Classe concreta mutabile che rappresenta un inventario.
 *
 * <p>L'inventario tiene traccia della numerosità di ciascun giocattolo in una collezione di essi;
 * ad esso è possibile aggiungere o rimuovere giocattoli e conoscere quanti giocattoli di un certo
 * tipo contenga.
 *
 * <p>È un {@link Iterable} dei giocattoli che contiene, in ordine lessicografico della loro
 * rappresentazione testuale.
 */
public class Inventario implements Iterable<Giocattolo> {

  // SOF: rep
  /** Mappa che tiene traccia della numerosità di ciascun giocattolo presente nell'inventario */
  private final Map<Giocattolo, Integer> inventario = new HashMap<>();

  // EOF: rep

  // RI: le chiavi sono diverse da null, i valori sono positivi; detto
  // altrimenti, la mappa non contiene i giocattoli presenti con numerosità 0.

  /** Costruisce un inventario vuoto. */
  public Inventario() {}

  /**
   * Costruisce un inventario a partire da una mappa che, per ciascun giocattolo, indica quanti ne
   * debba contenere l'inventario.
   *
   * @param inventario una mappa tra giocattoli e le loro numerosità.
   * @throws NullPointerException se inventario è null, o contiene chiavi o valori uguali a {@code
   *     null}.
   * @throws IllegalArgumentException se uno dei valori non è positivo.
   */
  public Inventario(final Map<Giocattolo, Integer> inventario) {
    Objects.requireNonNull(
        inventario, "La mappa che rappresenta l'inventario non può essere null.");
    for (Map.Entry<Giocattolo, Integer> e : inventario.entrySet()) {
      if (e.getKey() == null) throw new NullPointerException("Il giocattolo non può essere null");
      if (e.getValue() == null || e.getValue() <= 0)
        throw new IllegalArgumentException("Il numero deve essere positivo");
      inventario.put(e.getKey(), e.getValue());
    }
  }

  // SOF: copyc
  /**
   * Costruisce una copia dell'inventario dato.
   *
   * @param originale l'inventario di cui effettuare una copia.
   * @throws NullPointerException se inventario è {@code null}.
   */
  public Inventario(final Inventario originale) {
    this(originale.inventario);
  }

  // EOF: copyc

  // SOF: add
  /**
   * Aggiunge un certo numero di giocattoli dello stesso tipo all'inventario. Se sono già presenti
   * giocattoli di quel tipo, ne aggiorna il numero.
   *
   * @param num il numero di giocattoli da aggiungere.
   * @param giocattolo il giocattolo da aggiungere.
   * @return il numero totale dei giocattoli del tipo appena aggiunto presenti nell'inventario.
   * @throws NullPointerException se il giocattolo è {@code null}.
   * @throws IllegalArgumentException se il numero non è positivo.
   */
  public int aggiungi(final int num, final Giocattolo giocattolo) {
    Objects.requireNonNull(giocattolo, "Il giocattolo non può essere null");
    if (num <= 0) throw new IllegalArgumentException("Il numero deve essere positivo");
    int totale = num;
    if (inventario.containsKey(giocattolo)) totale += inventario.get(giocattolo);
    inventario.put(giocattolo, totale);
    return totale;
  }

  /**
   * Aggiunge un giocattolo all'inventario, se il giocattolo era già presente ne aumenta di uno la
   * numerosità.
   *
   * @param giocattolo il giocattolo da aggiungere.
   * @return il numero totale dei giocattoli del tipo appena aggiunto presenti nell'inventario.
   * @throws NullPointerException se il giocattolo è {@code null}.
   */
  public int aggiungi(final Giocattolo giocattolo) {
    return aggiungi(1, giocattolo);
  }

  // EOF: add

  // SOF: rem
  /**
   * Rimuove (se possibile) il numero indicato di giocattoli di un certo tipo dall'inventario.
   *
   * @param num il numero di giocattoli da rimuovere.
   * @param giocattolo il giocattolo da rimuovere.
   * @return il numero rimanente di giocattoli del tipo appena rimosso ancora presenti
   *     nell'inventario.
   * @throws NullPointerException se il giocattolo è {@code null}.
   * @throws IllegalArgumentException se il numero non è positivo, o eccede il numero di giocattoli
   *     di quel tipo presenti nell'inventario.
   */
  public int rimuovi(final int num, final Giocattolo giocattolo) {
    Objects.requireNonNull(giocattolo, "Il giocattolo non può essere null.");
    if (num <= 0) throw new IllegalArgumentException("Il numero deve essere positivo");
    if (!inventario.containsKey(giocattolo))
      throw new NoSuchElementException("Giocattolo non presente: " + giocattolo);
    // SOF: zero
    final int totale = inventario.get(giocattolo) - num;
    if (totale < 0)
      throw new IllegalArgumentException("Non ci sono abbastanza giocattoli: " + giocattolo);
    if (totale == 0) inventario.remove(giocattolo);
    else inventario.put(giocattolo, totale);
    // EOF: zero
    return totale;
  }

  // EOF: rem

  // SOF: quanti
  /**
   * Restituisce il numero di giocattoli del tipo indicato presenti nell'inventario.
   *
   * @param giocattolo il giocattolo.
   * @return il numero di giocattoli di tale tipo presenti nell'inventario, (eventualmente 0 se
   *     l'inventario non contiene il giocattolo indicato).
   * @throws NullPointerException se giocattolo è {@code null}
   */
  public int quantità(final Giocattolo giocattolo) {
    Objects.requireNonNull(giocattolo, "Il giocattolo non può essere null.");
    if (!inventario.containsKey(giocattolo)) return 0;
    return inventario.get(giocattolo);
  }

  // EOF: quanti

  // SOF: iter
  @Override
  public Iterator<Giocattolo> iterator() {
    final List<Giocattolo> giocattoli = new ArrayList<>(inventario.keySet());
    Collections.sort(
        giocattoli,
        new Comparator<Giocattolo>() {
          @Override
          public int compare(Giocattolo o1, Giocattolo o2) {
            return o1.toString().compareTo(o2.toString());
          }
        });
    return giocattoli.iterator();
  }

  // EOF: iter

  @Override
  public String toString() {
    final StringBuilder result = new StringBuilder();
    for (final Giocattolo g : this) result.append(inventario.get(g) + " " + g + "\n");
    return result.toString();
  }
}
