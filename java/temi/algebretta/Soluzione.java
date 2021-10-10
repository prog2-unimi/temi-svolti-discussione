import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Soluzione {

  public static Matrice matrice(final char tipo, final int[][] arr) {
    switch (tipo) {
    case ' ':
      return new MatriceDensa(arr);
    case 'Z':
      return new MatriceNulla(arr[0][0]);
    case 'I':
      return new MatriceIdentità(arr[0][0]);
    case 'D':
      return new MatriceDiagonale(arr[0]);
    }
    throw new IllegalArgumentException();
  }

  public static void main(String[] args) {
    try (final Scanner s = new Scanner(System.in)) {
      while (s.hasNextLine()) {
        final String[] op = Parser.partiOperazione(s.nextLine());
        int alpha;
        Vettore u, v;
        Matrice M, N;
        switch (op[1].charAt(0)) {
        case '+':
          if (Parser.èVettore(op[0]) && Parser.èVettore(op[2])) {
            u = new VettoreDenso(Parser.valoriVettore(op[0]));
            v = new VettoreDenso(Parser.valoriVettore(op[2]));
            System.out.println(u.più(v));
          } else if (Parser.èMatrice(op[0]) && Parser.èMatrice(op[2])) {
            M = matrice(Parser.tipoMatrice(op[0]), Parser.valoriMatrice(op[0]));
            N = matrice(Parser.tipoMatrice(op[2]), Parser.valoriMatrice(op[2]));
            System.out.println(M.più(N));
          }
          break;
        case '*':
          if (Parser.èScalare(op[0])) {
            alpha = Parser.valoreScalare(op[0]);
            if (Parser.èVettore(op[2])) {
              v = new VettoreDenso(Parser.valoriVettore(op[2]));
              System.out.println(v.per(alpha));
            } else if (Parser.èMatrice(op[2])) {
              M = matrice(Parser.tipoMatrice(op[2]), Parser.valoriMatrice(op[2]));
              System.out.println(M.per(alpha));
            }
          } else if (Parser.èMatrice(op[0])) {
            M = matrice(Parser.tipoMatrice(op[0]), Parser.valoriMatrice(op[0]));
            if (Parser.èMatrice(op[2])) {
              N = matrice(Parser.tipoMatrice(op[2]), Parser.valoriMatrice(op[2]));
              System.out.println(M.per(N));
            } else if (Parser.èVettore(op[2])) {
              v = new VettoreDenso(Parser.valoriVettore(op[2]));
              System.out.println(M.per(v));
            }
          }
          break;
        }
      }
    }
  }

}
