package it.unimi.di.prog2.temisvolti.cambiavalute;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Soluzione {

  public static void main(String[] args) {
    List<Importo> versamenti = new ArrayList<>();
    try (Scanner s = new Scanner(System.in)) {
      String line = null;
      while (s.hasNextLine()) {
        line = s.nextLine();
        if (line.charAt(0) == 'A' || line.charAt(0) == 'C' || line.charAt(0) == 'P')
          break;
        versamenti.add(Importo.valueOf(line));
      }
      CambiaValute cv = new CambiaValute(versamenti);
      String parts[] = null;
      for (;;) {
        if (line.length() == 0) return;
        if (line.length() > 2) {
          parts = line.substring(2).split("=");
          parts[0] = parts[0].trim();
          parts[1] = parts[1].trim();
        }
        try {
        switch (line.charAt(0)) {
          case 'A':
            cv.aggiorna(new Cambi.Tasso(Importo.valueOf(parts[0]), Importo.valueOf(parts[1])));
            break;
          case 'C':
            System.out.println(
              cv.cambia(Importo.valueOf(parts[0]), Valuta.valueOf(parts[1].charAt(0)))
            );
            break;
          case 'P':
            System.out.println("[\n" + cv + "\n]");
            break;
        }
      } catch (IllegalArgumentException e) { 
        System.out.println("ERRORE: " + e.getMessage());
      }
        if (!s.hasNextLine()) break;
        line = s.nextLine();
      }
    }
  }
}
