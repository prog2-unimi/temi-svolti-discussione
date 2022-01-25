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

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/** Classe concreta che rappresenta un album. */
public class Album implements Iterable<Album.Brano> {

  /**
   * Classe interna che rappresenta un brano.
   *
   * <p>Gli oggetti di questa classe sono istanziati esclusivamente dal costruttore dell'{@link
   * Album#Album(String, List, List)} che provvede ad aggiungerli ai suoi brani.
   */
  public class Brano {

    // SOF: rep
    /** Il titolo del brano. */
    public final String titolo;

    /** La durata del brano. */
    public final Durata durata;

    // RI: titolo e durata non sono null, titolo non è vuoto, durata non è 0

    /**
     * Costruisce un brano.
     *
     * <p>Una volta creato, un brano deve essere aggiunto all'album che lo avvolge.
     *
     * @param titolo il titolo.
     * @param durata la durata.
     * @throws NullPointerException se titolo o durata sono nulli.
     * @throws IllegalArgumentException se il titolo è vuoto, o la durata è 0.
     */
    private Brano(final String titolo, final Durata durata) {
      // SOF: ri
      if (Objects.requireNonNull(titolo, "Il titolo non può essere null.").isEmpty())
        throw new IllegalArgumentException("Il titolo non può essere vuoto.");
      if (Objects.requireNonNull(durata, "La durata non può essere null.").secondi() == 0)
        throw new IllegalArgumentException("La durata non può essere pari a zero.");
      // EOF: ri
      this.titolo = titolo;
      this.durata = durata;
    }
    // EOF: rep

    /**
     * Restituisce un riferimento all'album a cui il brano appartiene.
     *
     * @return l'album di cui questo brano è parte.
     */
    // SOF: outer
    public Album album() {
      return Album.this;
    }
    // EOF: outer

    /**
     * Consente di determinare se questo brano appartiene al medesimo album di quello dato.
     *
     * @param album l'album del quale è da verificare l'appartenenza.
     * @return <code>true</code> se e solo se questo brano appartiene all'album dato.
     * @throws NullPointerException se l'album è <code>null</code>.
     */
    // SOF: appartiene
    public boolean appartiene(final Album album) {
      return Album.this.equals(Objects.requireNonNull(album, "L'album non può essere null."));
    }
    // EOF: appartiene

    /**
     * Restituisce una rappresentazione di questo brano come stringa.
     *
     * @param conAlbum se <code>true</code> alla rappresentazione viene aggiunto il titolo
     *     dell'album.
     * @return una rappresentazione testuale del brano.
     */
    // SOF: stringa
    public String asString(final boolean conAlbum) {
      return String.format(
          "\"%s\" (%s)%s", titolo, durata, conAlbum ? ", (da \"" + album().titolo + "\")" : "");
    }
    // EOF: stringa

    @Override
    public String toString() {
      return asString(false);
    }
  }

  // SOF: repa
  /** Il titolo dell'album. */
  public final String titolo;

  /** La durata complessiva dell'album. */
  public final Durata durata;

  /** I brani di cui è costituito l'album. */
  private final Brano[] brani;

  // RI:
  // - titolo, durata e brani non sono null,
  // - titolo non è vuoto,
  // - durata corrisponde alla somma delle durate degli elementi dell'array brani
  // - brani non contiene null, non contiene duplicati (riferimenti allo stesso
  // oggetto)
  // - brani contiene solo brani corrispondenti ad istanze di questo album

  /**
   * Costruisce un album a partire da due liste "parallele" di titoli e durate.
   *
   * @param titolo titolo dell'album.
   * @param titoli lista dei titoli.
   * @param durate lista delle durate.
   * @throws NullPointerException se uno dei parametri è <code>null</code>, o una delle liste
   *     contine un <code>null</code>.
   * @throws IllegalArgumentException se il titolo dell'album è vuoto, il numero di titoli è diverso
   *     da quello delle durate, il numero di titoli e durate è 0, uno dei titoli è vuoto, una delle
   *     durate è 0.
   */
  public Album(final String titolo, final List<String> titoli, final List<Durata> durate) {
    this.titolo = Objects.requireNonNull(titolo, "Il titolo non può essere null.");
    if (titolo.isEmpty()) throw new IllegalArgumentException("Il titolo non può essere vuoto.");
    Objects.requireNonNull(titoli, "L'elenco dei titoli non può essere null.");
    Objects.requireNonNull(durate, "L'elenco delle durate non può essere null.");
    if (titoli.size() != durate.size())
      throw new IllegalArgumentException("Titoli e durate devono essere nello stesso numero.");
    if (titoli.isEmpty())
      throw new IllegalArgumentException("Il numero dei brani non può essere 0.");
    brani = new Brano[titoli.size()];
    Durata durata = new Durata(0);
    for (int i = 0; i < titoli.size(); i++) {
      final Durata di = durate.get(i);
      try {
        brani[i] = new Brano(titoli.get(i), di);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(
            "Le specifiche del brano " + (i + 1) + " sono errate. " + e.getMessage());
      }
      durata = durata.somma(di);
    }
    this.durata = durata;
  }
  // EOF: repa

  // SOF: num
  /**
   * Restituisce il numero di brani dell'album.
   *
   * @return il numero di brani di questo album.
   */
  public int numeroBrani() {
    return brani.length;
  }
  // EOF: num

  // SOF: pos
  /**
   * Restituisce il brano che ha nell'album la posizione data.
   *
   * @param pos la posizione.
   * @return il brano.
   * @throws IndexOutOfBoundsException se la posizione non è compresa tra 1 e il numero di brani
   *     dell'album.
   */
  public Brano brano(final int pos) {
    try {
      return brani[pos - 1];
    } catch (IndexOutOfBoundsException e) {
      throw new IndexOutOfBoundsException(
          "Il numero di brano non è compreso tra 1 e " + brani.length);
    }
  }

  /**
   * Restituisce la posizione nell'album del brano dato.
   *
   * @param brano il brano.
   * @return la sua posizione nell'album, o 0 se il brano non appartiene all'album.
   * @throws NullPointerException se il brano è <code>null</code>
   */
  public int posizione(final Brano brano) {
    Objects.requireNonNull(brano, "Il brano non può essere null.");
    return 1 + Arrays.asList(brani).indexOf(brano);
  }
  // EOF: pos

  /**
   * Restituisce il primo brano con un dato titolo.
   *
   * <p>
   * Non è garantito che l'album contenga un solo brano con un dato titolo;
   * pertanto se il valore della stringa `titolo` corrisponde al titolo di
   * almeno un brano di quest'album, questo metodo è tale che:
   * <ul>
   * <li> <code>brano.titolo.equals(titolo)</code>,
   * <li>per ogni <code>pos &lt; posizione(brano)</code> si ha che
   * <code>!brano(pos).titolo.equals(titolo)</code>.
   * </ul>
   * dove <code>brano = brano(titolo)<code>.
   *
   * @param titolo il titolo.
   * @return un brano tale la posizione del primo brano con tale titolo, oppure
   *         <code>null</code> se nessun brano ha tale titolo.
   * @throws NullPointerException se il titolo è <code>null</code>.
   */
  // SOF: titolo
  public Brano brano(final String titolo) {
    Objects.requireNonNull(titolo, "Il titolo non può essere null.");
    for (final Brano b : brani) if (b.titolo.equals(titolo)) return b;
    return null;
  }
  // EOF: titolo

  @Override
  public String toString() {
    final StringBuilder s = new StringBuilder();
    s.append("Titolo album: " + titolo + "\n");
    for (int i = 0; i < brani.length; i++) s.append((1 + i) + " - " + brani[i] + "\n");
    s.append("Durata totale: " + durata);
    return s.toString();
  }

  // SOF: iter
  @Override
  public Iterator<Album.Brano> iterator() {
    return Arrays.asList(brani).iterator();
  }
  // EOF: iter
}
