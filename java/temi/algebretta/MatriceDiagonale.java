import java.util.Objects;

public class MatriceDiagonale extends AbsMatrice {

  private final int[] diagonale;

  public MatriceDiagonale(final int[] diagonale) {
    this.diagonale = diagonale.clone();
  }

  @Override
  public int dim() {
    return diagonale.length;
  }

  @Override
  public int val(final int i, final int j) {
    requireValidIJ(i, j);
    return i == j ? diagonale[i] : 0;
  }

  @Override
  public Matrice per(final int alpha) {
    if (alpha == 0) return new MatriceNulla(dim());
    int[] tmp = new int[diagonale.length];
    for (int i = 0; i < diagonale.length; i++)
      tmp[i] = alpha * diagonale[i];
    return new MatriceDiagonale(tmp);
  }

  @Override
  public Matrice per(final Matrice B) {
    Objects.requireNonNull(B);
    if (!conforme(B)) throw new IllegalArgumentException();
    if (B instanceof MatriceNulla) return B;
    if (B instanceof MatriceIdentità) return this;
    return new MatriceDensa(this).per(B);
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
    if (v instanceof VettoreNullo) return v;
    final int[] temp = new int[diagonale.length];
    for (int i = 0; i < diagonale.length; i++) temp[i] = diagonale[i] * v.val(i);
    return new VettoreDenso(temp);
  }

}