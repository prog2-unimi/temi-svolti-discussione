import java.util.Objects;

public class MatriceDiagonale extends AbsMatrice {

  // SOF: rapcostr
  private final int[] diagonale;

  public MatriceDiagonale(final int[] diagonale) {
    Objects.requireNonNull(diagonale);
    if (diagonale.length == 0) throw new IllegalArgumentException("La diagonale deve contenere almeno un valore.");
    this.diagonale = diagonale.clone();
  }
  // EOF: rapcostr

  // SOF: dimval
  @Override
  public int dim() {
    return diagonale.length;
  }

  @Override
  public int val(final int i, final int j) {
    requireValidIJ(i, j);
    return i == j ? diagonale[i] : 0;
  }
  // EOF: dimval


  // SOF: peralpha
  @Override
  public Matrice per(final int alpha) {
    // SOF: perzero
    if (alpha == 0) return new MatriceNulla(dim());
    // EOF: perzero
    int[] tmp = new int[diagonale.length];
    for (int i = 0; i < diagonale.length; i++)
      tmp[i] = alpha * diagonale[i];
    return new MatriceDiagonale(tmp);
  }
  // EOF: peralpha

  // SOF: piumat
  @Override
  public Matrice più(final Matrice B) {
    Objects.requireNonNull(B);
    if (!conforme(B)) throw new IllegalArgumentException("Le matrici non sono conformi.");
    // SOF: piuzero
    if (B instanceof MatriceNulla) return this;
    // EOF: piuzero
    return new MatriceDensa(this).più(B);
  }
  // EOF: piumat

  // SOF: permat
  @Override
  public Matrice per(final Matrice B) {
    Objects.requireNonNull(B);
    if (!conforme(B)) throw new IllegalArgumentException("Le matrici non sono conformi.");
    // SOF: perspeciale
    if (B instanceof MatriceNulla) return B;
    if (B instanceof MatriceIdentità) return this;
    // EOF: perspeciale
    return new MatriceDensa(this).per(B);
  }
  // EOF: permat

  // SOF: pervec
  @Override
  public Vettore per(final Vettore v) {
    Objects.requireNonNull(v);
    if (!conforme(v)) throw new IllegalArgumentException("Il vettore e la matrice non sono conformi.");
    // SOF: pervzero
    if (v instanceof VettoreNullo) return v;
    // EOF: pervzero
    final int[] temp = new int[diagonale.length];
    for (int i = 0; i < diagonale.length; i++) temp[i] = diagonale[i] * v.val(i);
    return new VettoreDenso(temp);
  }
  // EOF: pervec


}