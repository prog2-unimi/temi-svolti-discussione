/*

Copyright 2022 Massimo Santini

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

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Una bancarella ha un proprietario ed espone un certo insieme di giocattoli che può vendere.
 *
 * <p>È un {@link Iterable} dei giocattoli che contiene il suo inventario, in ordine lessicografico
 * della loro rappresentazione testuale.
 *
 * <p>Dal momento che le bancarelle entreranno in diverse *collections* sono stati sovrascritti i
 * metodi `equals` e `hashCode` in modo che siano uguali (ai fini del progetto) bancarelle col
 * medesimo propirietario (indipendentemente da inventaroi e listino).
 */
public class Bancarella implements Iterable<Giocattolo> {

  // SOF: rep
  /** Il proprietario della bancarella. */
  public final String proprietario;

  /** Il listino della bancarella. */
  private final Listino listino;

  /** L'inventario della bancarella. */
  private final Inventario inventario;

  // RI: gli attributi non sono null, il proprietario non è vuoto; il listino
  // conosce il prezzo di ogni giocattolo presente nell'inventario.

  /**
   * Costruisce una bancarella dato il proprietario, l'inventario e i listino.
   *
   * @param proprietario il proprietario.
   * @param inventario l'inventario.
   * @param listino il listino.
   * @throws NullPointerException se uno dei parametri è {@code null}
   * @throws IllegalArgumentException se l'inventario contiene un giocattolo il cui prezzo non è
   *     presente nel listino.
   */
  public Bancarella(final String proprietario, final Inventario inventario, final Listino listino) {
    this.proprietario =
        Objects.requireNonNull(proprietario, "Il proprietario non può essere null.");
    if (proprietario.isEmpty())
      throw new IllegalArgumentException("Il proprietario non deve essere vuoto.");
    this.listino = Objects.requireNonNull(listino, "Il listino non può essere null.");
    // SOF: ri
    this.inventario =
        new Inventario(Objects.requireNonNull(inventario, "L'inventario non può essere null."));
    for (final Giocattolo g : inventario)
      if (!listino.conosce(g))
        throw new IllegalArgumentException("Il listino manca del prezzo per: " + g);
    // EOF: ri
  }
  // EOF: rep

  // SOF: mod
  /**
   * Effettua la vendita del numero richiesto del giocattolo indicato.
   *
   * @param num il numero di giocattoli da vendere.
   * @param giocattolo il giocattolo da vendere.
   * @return il numero rimanente di giocattoli del tipo appena venduto ancora presenti
   *     nell'inventario della bancarella.
   * @throws NullPointerException se il giocattolo è {@code null}.
   * @throws IllegalArgumentException se il numero non è positivo, o eccede il numero di giocattoli
   *     di quel tipo presenti nell'inventario della bancarella.
   */
  public int vende(final int num, final Giocattolo giocattolo) {
    return inventario.rimuovi(num, giocattolo);
  }
  // EOF: mod

  // SOF: obs
  /**
   * Restituisce la quantità del giocattolo specificato nell'inventario della bancarella.
   *
   * @param giocattolo il giocattolo.
   * @return il numero di giocattoli specificato nell'inventario della bancarella (eventualmente 0).
   * @throws NullPointerException se giocattolo è {@code null}
   */
  public int quantità(final Giocattolo giocattolo) {
    return inventario.quantità(giocattolo);
  }

  /**
   * Restituisce il prezzo della quantità indicata del giocattolo specificato.
   *
   * @param num la quantità di cui determinare il prezzo.
   * @param giocattolo il giocattolo da vendere.
   * @return il prezzo della quantità indicata del giocattolo specificato.
   * @throws NullPointerException se giocattolo è {@code null}
   * @throws NoSuchElementException se al listino della bancarella non è noto il prezzo del
   *     giocattolo.
   */
  public int prezzo(final int num, final Giocattolo giocattolo) {
    return listino.prezzo(num, giocattolo);
  }

  @Override
  public Iterator<Giocattolo> iterator() {
    return inventario.iterator();
  }
  // EOF: obs

  // SOF: eqhash
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Bancarella)) return false;
    return ((Bancarella) other).proprietario.equals(proprietario);
  }

  @Override
  public int hashCode() {
    return proprietario.hashCode();
  }
  // EOF: eqhash

  @Override
  public String toString() {
    final StringBuilder result = new StringBuilder();
    result.append("Bancarella di: " + proprietario + "\n");
    for (final Giocattolo g : inventario)
      result.append(
          "num. " + inventario.quantità(g) + " " + g + ", prezzo: " + listino.prezzo(1, g) + "\n");
    return result.toString();
  }
}
