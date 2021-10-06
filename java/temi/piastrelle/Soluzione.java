import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * La classe di <em>test</em>.
 */
public class Soluzione {

  public static void main(String[] args) {
    try (final Scanner s = new Scanner(System.in)) {
      // SOF: rappr
      final List<Rivestimento> rivestimento = new ArrayList<>();
      final List<Pavimentazione> pavimentazione = new ArrayList<>();
      // EOF: rappr
      while (s.hasNextLine())
      try (final Scanner line = new Scanner(s.nextLine())) {
          // SOF: switch
          switch (line.next().charAt(0)) {
            case 'Q':
              rivestimento.add(new PiastrellaQuadrata(line.nextInt(), line.nextInt()));
              break;
            case 'R':
              rivestimento.add(new PiastrellaRomboidale(line.nextInt(), line.nextInt(), line.nextInt()));
              break;
            case 'T':
              rivestimento.add(new PiastrellaTriangolare(line.nextInt(), line.nextInt(), line.nextInt()));
              break;
            case 'P':
              // SOF: mkpav
              final List<Pavimentazione.Componente> componenti = new ArrayList<>();
              while (line.hasNextInt())
                componenti.add(new Pavimentazione.Componente(line.nextInt(), rivestimento.get(line.nextInt())));
              final Pavimentazione p = new Pavimentazione(componenti);
              // EOF: mkpav
              pavimentazione.add(p);
              rivestimento.add(p);
              break;
            default:
              throw new IllegalArgumentException();
          }
          // EOF: switch
        }
      for (final Pavimentazione p : pavimentazione)
        System.out.println(p.superficie() + "\t" + p.costo());
    }
  }
}