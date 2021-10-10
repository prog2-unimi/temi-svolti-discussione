import java.util.Objects;

public class MatriceIdentità extends AbsMatrice {

  private final int dim;

  public MatriceIdentità(final int dim) {
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
    return i == j ? 1 : 0;
  }

  @Override
  public Matrice per(final int alpha) {
    if (alpha == 0) return new MatriceNulla(dim());
    int[] tmp = new int[dim];
    for (int i = 0; i < dim; i++)
      tmp[i] = alpha;
    return new MatriceDiagonale(tmp);
  }

  @Override
  public Matrice per(final Matrice B) {
    Objects.requireNonNull(B);
    if (!conforme(B)) throw new IllegalArgumentException();
    return B;
  }

  @Override
  public Matrice più(final Matrice B) {
    Objects.requireNonNull(B);
    if (!conforme(B)) throw new IllegalArgumentException();
    if (B instanceof MatriceNulla) return this;
    return new MatriceDensa(this).più(B);
  }

  @Override
  public Vettore per(final Vettore v) {
    Objects.requireNonNull(v);
    if (!conforme(v)) throw new IllegalArgumentException();
    return v;
  }

}