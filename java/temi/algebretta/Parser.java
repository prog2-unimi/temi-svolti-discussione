import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

  static private final Pattern OPERAZIONE = Pattern.compile("\\s*(?<sinistra>.*\\S)\\s*(?<operatore>\\+|\\*)\\s*(?<destra>.*\\S)\\s*");
  static private final Pattern MATRICE = Pattern.compile("\\s*(?<tipo>[ZID]?)\\s*\\[(?<array>[-+0-9,;\n ]+)\\]\\s*");
  static private final Pattern RIGA = Pattern.compile("([-+0-9,\n ]+);?");
  static private final Pattern VETTORE = Pattern.compile("\\s*\\((?<array>[-+0-9,\n ]+)\\)\\s*");
  static private final Pattern SCALARE = Pattern.compile("\\s*(?<alpha>[-+0-9]+)\\s*");

  public static boolean èOperazone(final String linea) {
    return OPERAZIONE.matcher(linea).matches();
  }

  public static String[] partiOperazione(final String linea) {
    Matcher m = OPERAZIONE.matcher(linea);
    if (m.matches())
      return new String[] { m.group("sinistra"), m.group("operatore"), m.group("destra") };
    throw new IllegalArgumentException();
  }

  public static boolean èMatrice(final String operando) {
    return MATRICE.matcher(operando).matches();
  }

  public static char tipoMatrice(final String operando) {
    Matcher m = MATRICE.matcher(operando);
    if (m.matches()) {
      final String tipo = m.group("tipo");
      return tipo.length() > 0 ? tipo.charAt(0) : ' ';
    }
    throw new IllegalArgumentException();
  }

  public static int[][] valoriMatrice(final String operando) {
    Matcher m = MATRICE.matcher(operando);
    if (m.matches()) {
      return RIGA.matcher(m.group("array")).results()
          .map(r -> SCALARE.matcher(r.group(1)).results().map(c -> c.group(1)).mapToInt(Integer::parseInt).toArray())
          .toArray(n -> new int[n][]);
    }
    throw new IllegalArgumentException();
  }

  public static boolean èVettore(final String operando) {
    return VETTORE.matcher(operando).matches();
  }

  public static int[] valoriVettore(final String operando) {
    Matcher m = VETTORE.matcher(operando);
    if (m.matches())
      return SCALARE.matcher(m.group("array")).results().map(c -> c.group(1)).mapToInt(Integer::parseInt).toArray();
    throw new IllegalArgumentException();
  }

  public static boolean èScalare(final String operando) {
    return SCALARE.matcher(operando).matches();
  }

  public static int valoreScalare(final String operando) {
    Matcher m = SCALARE.matcher(operando);
    if (m.matches())
      return Integer.parseInt(m.group("alpha"));
    throw new IllegalArgumentException();
  }

}