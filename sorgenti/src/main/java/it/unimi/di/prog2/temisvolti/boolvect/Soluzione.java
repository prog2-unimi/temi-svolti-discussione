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

package it.unimi.di.prog2.temisvolti.boolvect;

import java.util.Scanner;

/** Classe che fornisce il main che realizza la soluzione al tema svolto. */
public class Soluzione {

  /** Costruttore privato per impedire l'istanziazione. */
  private Soluzione() {}

  /**
   * Metodo di utilità per convertire una stringa in un BoolVect.
   *
   * @param values la stringa da convertire.
   * @return il BoolVect corrispondente alla stringa.
   */
  public static BoolVect fromString(final String values) {
    final AbstractBoolVect u = new LongBoolVect();
    u.daString(values);
    return u;
  }

  /**
   * Metodo principale per l'esecuzione del programma.
   *
   * @param args gli argomenti della linea di comando (non usati).
   */
  public static void main(String[] args) {
    try (Scanner s = new Scanner(System.in)) {
      while (s.hasNextLine())
        try (Scanner t = new Scanner(s.nextLine())) {
          final char cmd = t.next().charAt(0);
          final int p;
          final boolean b;
          final BoolVect u, v;
          switch (cmd) {
            case 'S':
              p = t.nextInt();
              b = t.next().charAt(0) == 'V';
              u = fromString(t.next());
              u.scrivi(p, b);
              System.out.println(u);
              break;
            case 'G':
              p = t.nextInt();
              u = fromString(t.next());
              System.out.println(u.leggi(p) ? 'V' : 'F');
              break;
            case '&':
              u = fromString(t.next());
              v = fromString(t.next());
              u.and(v);
              System.out.println(u);
              break;
            case '|':
              u = fromString(t.next());
              v = fromString(t.next());
              u.or(v);
              System.out.println(u);
              break;
            case '^':
              u = fromString(t.next());
              v = fromString(t.next());
              u.xor(v);
              System.out.println(u);
              break;
          }
        }
    }
  }
}
