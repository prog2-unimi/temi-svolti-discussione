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

package it.unimi.di.prog2.temisvolti.multiset;

import java.util.Scanner;

/** Classe che implementa la lettura dei file di test. */
public final class Soluzione {

  /** Costruttore privato per impedire l'istanziazione. */
  private Soluzione() {}

  /**
   * Metodo di utilità per stampare un MultiSet.
   *
   * @param s il MultiSet da stampare.
   */
  private static void print(MultiSet<?> s) {
    System.out.println(s.size() + " " + s);
  }

  /**
   * Metodo principale per l'esecuzione del programma.
   *
   * @param args gli argomenti della linea di comando (non usati).
   */
  public static void main(String[] args) {
    final MultiSet<String> a = new ListMultiSet<>(), b = new HashMapMultiSet<>();
    try (Scanner sc = new Scanner(System.in)) {
      try (Scanner fl = new Scanner(sc.nextLine())) {
        while (fl.hasNext()) a.add(fl.next());
      }
      try (Scanner sl = new Scanner(sc.nextLine())) {
        while (sl.hasNext()) b.add(sl.next());
      }
    }
    print(a);
    print(b);
    print(a.union(b));
    print(a.intersection(b));
  }
}
