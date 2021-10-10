import java.util.Objects;

public class MatriceIdentità extends AbsMatrice {

  // SOF: rapcostr
  private final int dim;

  public MatriceIdentità(final int dim) {
    if (dim < 0) throw new IllegalArgumentException("La dimensoine dev'essere positiva.");
    this.dim = dim;
  }
  // EOF: rapcostr

  // SOF: dimval
  @Override
  public int dim() {
    return dim;
  }

  @Override
  public int val(final int i, final int j) {
    requireValidIJ(i, j);
    return i == j ? 1 : 0;
  }
  // EOF: dimval

  // SOF: peralpha
  @Override
  public Matrice per(final int alpha) {
    // SOF: perzero
    if (alpha == 0) return new MatriceNulla(dim());
    // EOF: perzero
    int[] tmp = new int[dim];
    for (int i = 0; i < dim; i++)
      tmp[i] = alpha;
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
    return B;
  }
  // EOF: permat

  @Override
  public Vettore per(final Vettore v) {
    Objects.requireNonNull(v);
    if (!conforme(v)) throw new IllegalArgumentException("Il vettore e la matrice non sono conformi.");
    return v;
  }

}