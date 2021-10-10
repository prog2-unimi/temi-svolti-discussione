import java.util.Objects;

public class MatriceNulla extends AbsMatrice {

  // SOF: rapcostr
  private final int dim;

  public MatriceNulla(final int dim) {
    if (dim < 0) throw new IllegalArgumentException();
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
    return 0;
  }
  // EOF: dimval

  // SOF: ops
  @Override
  public Matrice per(final int alpha) {
    return this;
  }

  @Override
  public Matrice per(final Matrice B) {
    Objects.requireNonNull(B);
    if (!conforme(B)) throw new IllegalArgumentException("Le matrici non sono conformi.");
    return this;
  }

  @Override
  public Matrice più(final Matrice B) {
    Objects.requireNonNull(B);
    if (!conforme(B)) throw new IllegalArgumentException("Le matrici non sono conformi.");
    return B;
  }
  // EOF: ops

  // SOF: pervec
  @Override
  public VettoreNullo per(final Vettore v) {
    Objects.requireNonNull(v);
    if (!conforme(v)) throw new IllegalArgumentException("Il vettore e la matrice non sono conformi.");
    return new VettoreNullo(dim);
  }
  // EOF: pervec

}