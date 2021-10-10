import java.util.Objects;

public class MatriceNulla extends AbsMatrice {

  private final int dim;

  public MatriceNulla(final int dim) {
    if (dim < 0) throw new IllegalArgumentException();
    this.dim = dim;
  }

  @Override
  public int dim() {
    return dim;
  }

  @Override
  public int val(final int i, final int j) {
    requireValidIJ(i, j);
    return 0;
  }

  @Override
  public Matrice per(final int alpha) {
    return this;
  }

  @Override
  public Matrice per(final Matrice B) {
    Objects.requireNonNull(B);
    if (!conforme(B)) throw new IllegalArgumentException();
    return this;
  }

  @Override
  public Matrice più(final Matrice B) {
    Objects.requireNonNull(B);
    if (!conforme(B)) throw new IllegalArgumentException();
    return B;
  }

  @Override
  public VettoreNullo per(final Vettore v) {
    Objects.requireNonNull(v);
    if (!conforme(v)) throw new IllegalArgumentException();
    return new VettoreNullo(dim);
  }

}