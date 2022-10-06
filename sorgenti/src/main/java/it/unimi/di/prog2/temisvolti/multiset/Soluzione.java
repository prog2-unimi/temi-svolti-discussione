package it.unimi.di.prog2.temisvolti.multiset;

import java.util.Scanner;

/**
 * Classe che implementa la lettura dei file di test.
 */
public final class Soluzione {

  private Soluzione() {}

  private static void print(MultiSet<?> s) {
    System.out.println(s.size() + " " + s);
  }

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
