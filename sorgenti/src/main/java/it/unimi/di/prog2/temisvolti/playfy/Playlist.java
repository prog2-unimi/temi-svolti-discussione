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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/** Una classe mutabile che rappresenta una playlist. */
public class Playlist implements Iterable<Album.Brano> {

  // SOF: rep
  /** Il nome della playlist. */
  public String nome;

  /** La durata complessiva della playlist. */
  private Durata durata = new Durata(0);

  /** L'elenco di brani contenuti nella playlist. */
  private final List<Album.Brano> brani = new ArrayList<>();
  // EOF: rep

  /**
   * Costruisce una playlist di nome dato.
   *
   * @param nome il nome.
   * @throws NullPointerException se il nome è <code>null</code>.
   * @throws IllegalArgumentException se il nome è vuoto.
   */
  public Playlist(final String nome) {
    nome(nome);
  }

  // SOF: nome
  /**
   * Restituisce il nome della playlist.
   *
   * @return li nome.
   */
  public String nome() {
    return nome;
  }

  /**
   * Cambia il nome della playlst.
   *
   * @param nome il nome.
   * @throws NullPointerException se il nome è <code>null</code>.
   * @throws IllegalArgumentException se il nome è vuoto.
   */
  public void nome(final String nome) {
    if (Objects.requireNonNull(nome, "Il nome non può essere null.").isEmpty())
      throw new IllegalArgumentException("Il nome non può essere null o vuoto.");
    this.nome = nome;
  }
  // EOF: nome

  // SOF: durata
  /**
   * Restituisce la durata complessiva della playlist.
   *
   * @return la durata.
   */
  public Durata durata() {
    return durata;
  }
  // EOF: durata

  // SOF: pos
  /**
   * Restituisce il numero di brani della playlist.
   *
   * @return il numero di brani di questa playlist.
   */
  public int numeroBrani() {
    return brani.size();
  }

  /**
   * Restituisce il brano che ha nella playlist la posizione data.
   *
   * @param pos la posizione.
   * @return il brano.
   * @throws IndexOutOfBoundsException se la posizione non è compresa tra 1 e il numero di brani
   *     della playlist.
   */
  public Album.Brano brano(final int pos) {
    try {
      return brani.get(pos - 1);
    } catch (IndexOutOfBoundsException e) {
      throw new IndexOutOfBoundsException(
          "Il numero di brano non è compreso tra 1 e " + brani.size());
    }
  }

  /**
   * Restituisce la posizione nella playlist del brano dato.
   *
   * @param brano il brano.
   * @return la sua posizione nella playlist, o 0 se il brano non appartiene all'album.
   * @throws NullPointerException se il brano è <code>null</code>
   */
  public int posizione(final Album.Brano brano) {
    return 1 + brani.indexOf(Objects.requireNonNull(brano, "Il brano non può essere null."));
  }
  // EOF: pos

  // SOF: addrm
  /**
   * Aggiunge il brano dato alla playlist.
   *
   * @param brano il brano.
   * @throws NullPointerException se il brano è <code>null</code>.
   */
  public void accoda(final Album.Brano brano) {
    brani.add(Objects.requireNonNull(brano, "Il brano non può essere null."));
    durata = durata.somma(brano.durata);
  }

  /**
   * Rimuove il brano dato dalla playlist.
   *
   * @param brano il brano.
   * @throws NullPointerException se il brano è <code>null</code>.
   */
  public void rimuovi(final Album.Brano brano) {
    if (brani.remove(Objects.requireNonNull(brano, "Il brano non può essere null.")))
      durata = durata.sottrai(brano.durata);
  }
  // EOF: addrm

  // SOF: fondi
  /**
   * Fonde questa playlist con quella data.
   *
   * <p>La fusione tra questa playlist ed una data è una nuova playlist (di nome dato) che contiene
   * tutti i brani di questa playlist (eventualmente ripetuti) a cui sono accodati i brani
   * dell'altra playlist nell'ordine in cui compaiono in essa, omettendo i brani contenuti in questa
   * playlist.
   *
   * @param nome il nome della playlist risultante.
   * @param altra l'altra playlist con cui fondere questa.
   * @return la playlist risultante.
   * @throws NullPointerException se il nome, o l'altra playlist sono <code>null</code>.
   * @throws IllegalArgumentException se il nome è vuoto.
   */
  public Playlist fondi(final String nome, final Playlist altra) {
    final Playlist fusa = new Playlist(nome);
    for (final Album.Brano brano : this) fusa.accoda(brano);
    // SOF: dup
    for (final Album.Brano brano :
        Objects.requireNonNull(altra, "La playlist non puà essere null."))
      if (posizione(brano) == 0) fusa.accoda(brano);
    // EOF: dup
    return fusa;
  }
  // EOF: fondi

  // SOF: filteriter
  /**
   * Restituisce un iteratore che enumera tutti i brani della playlist che provengono dall'album
   * dato.
   *
   * @param album l'album.
   * @return l'iteartore.
   * @throws NullPointerException se l'album è <code>null</code>.
   */
  public Iterator<Album.Brano> brani(final Album album) {
    Objects.requireNonNull(album, "L'album non può essere null.");
    return new Iterator<Album.Brano>() {

      // SOF: firep
      /** Un iteratore su tutti i brani della playlist. */
      private final Iterator<Album.Brano> it = iterator();

      /** Il prossimo brano da restituire. */
      private Album.Brano next = null;
      // EOF: firep

      // SOF: fihas
      @Override
      public boolean hasNext() {
        if (next != null) return true;
        while (it.hasNext()) {
          next = it.next();
          if (next.appartiene(album)) return true;
        }
        next = null;
        return false;
      }
      // EOF: fihas

      // SOF: finxt
      @Override
      public Album.Brano next() {
        if (!hasNext()) throw new NoSuchElementException();
        final Album.Brano ret = next;
        next = null;
        return ret;
      }
      // EOF: finxt
    };
  }
  // EOF: filteriter

  // SOF: albumiter
  /**
   * Restituisce un iteratore che enumera (senza ripetizioni) gli album di cui esiste un brano in
   * questa playlist.
   *
   * @return l'itertore.
   */
  public Iterator<Album> album() {
    return new Iterator<Album>() {

      // SOF: airep
      /** Un iteratore su tutti i brani della playlist. */
      private final Iterator<Album.Brano> it = iterator();

      /** Il prossimo album da restituire. */
      private Album next = null;

      /** L'insieme degli album restituiti da {@link #next()}. */
      private final Set<Album> restituiti = new HashSet<>();
      // EOF: airep

      // SOF: aihas
      @Override
      public boolean hasNext() {
        if (next != null) return true;
        while (it.hasNext()) {
          next = it.next().album();
          if (!restituiti.contains(next)) {
            restituiti.add(next);
            return true;
          }
        }
        next = null;
        return false;
      }
      // EOF: aihas

      // SOF: ainxt
      @Override
      public Album next() {
        if (!hasNext()) throw new NoSuchElementException();
        final Album ret = next;
        next = null;
        return ret;
      }
      // EOF: ainxt
    };
  }
  // EOF: albumiter

  @Override
  public String toString() {
    final StringBuilder s = new StringBuilder();
    s.append("Nome playlist: " + nome + "\n");
    for (int i = 0; i < brani.size(); i++)
      s.append(String.format("%d - %s\n", i + 1, brani.get(i).asString(true)));
    s.append("Durata totale: " + durata);
    return s.toString();
  }

  // SOF: iter
  @Override
  public Iterator<Album.Brano> iterator() {
    return Collections.unmodifiableCollection(brani).iterator();
  }
  // EOF: iter
}
