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

import java.util.Objects;

/** Classe concreta immutabile che rappresenta un giocattolo. */
public class Giocattolo {

  // SOF: rep
  /** Il nome e materiale di cui è costituito il giocattolo. */
  public final String nome, materiale;

  // RI: nome e materiale sono non null e non vuoti.

  /**
   * Costruisce un giocattolo dato nome e materiale.
   *
   * @param nome il nome del giocattolo, deve essere una stringa non vuota.
   * @param materiale il materiale di cui è costituito il giocattolo, deve essere una stringa non
   *     vuota.
   * @throws NullPointerException se nome o materiale sono <code>null</code>
   * @throws IllegalArgumentException se nome o materiale sono stringhe vuote.
   */
  public Giocattolo(final String nome, final String materiale) {
    // SOF: inv
    this.nome = Objects.requireNonNull(nome);
    this.materiale = Objects.requireNonNull(materiale);
    if (nome.isEmpty() || materiale.isEmpty()) throw new IllegalArgumentException();
    // EOF: inv
  }
  // EOF: rep

  @Override
  public int hashCode() {
    return Objects.hash(nome, materiale);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Giocattolo)) return false;
    final Giocattolo tmp = (Giocattolo) obj;
    return tmp.nome.equals(nome) && tmp.materiale.equals(materiale);
  }

  @Override
  public String toString() {
    return nome + " di " + materiale;
  }
}
