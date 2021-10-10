import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class MatriceDensa extends AbsMatrice {

  private final int[][] mat;

  private MatriceDensa(final int dim) {
    if (dim < 0) throw new IllegalArgumentException();
    mat = new int[dim][dim];
  }

  public MatriceDensa(final Matrice A) {
    this(Objects.requireNonNull(A).dim());
    for (int i = 0; i < dim(); i++)
      for (int j = 0; j < dim(); j++)
        mat[i][j] = A.val(i, j);
  }

  public MatriceDensa(final int[][] mat) {
    Objects.requireNonNull(mat);
    if (mat.length == 0) throw new IllegalArgumentException();
    final int dim = mat.length;
    this.mat = new int[dim][dim];
    for (int i = 0; i < dim; i++) {
      if (mat[i].length != dim) throw new IllegalArgumentException();
      for (int j = 0; j  < dim; j++)
        this.mat[i][j] = mat[i][j];
    }
  }

  @Override
  public int dim() {
    return mat.length;
  }

  @Override
  public int val(final int i, final int j) {
    requireValidIJ(i, j);
    return mat[i][j];
  }

  @Override
  public Matrice per(final int alpha) {
    if (alpha == 0) return new MatriceNulla(dim());
    final MatriceDensa N = new MatriceDensa(dim());
    for (int i = 0; i < dim(); i++)
      for (int j = 0; j < dim(); j++)
        N.mat[i][j] = alpha * mat[i][j];
    return N;
  }

  @Override
  public Matrice per(final Matrice B) {
    Objects.requireNonNull(B);
    if (!conforme(B)) throw new IllegalArgumentException();
    if (B instanceof MatriceNulla) return B;
    if (B instanceof MatriceIdentità) return this;
    final MatriceDensa C = new MatriceDensa(dim());
    for (int i = 0; i < dim(); i++)
      for (int j = 0; j < dim(); j++)
        for (int k = 0; k < dim(); k++)
          C.mat[i][j] += mat[i][k] * B.val(k, j);
    return C;
  }

  @Override
  public MatriceDensa più(final Matrice B) {
    Objects.requireNonNull(B);
    if (!conforme(B)) throw new IllegalArgumentException();
    if (B instanceof MatriceNulla) return this;
    final MatriceDensa C = new MatriceDensa(dim());
    for (int i = 0; i < dim(); i++)
      for (int j = 0; j < dim(); j++)
        C.mat[i][j] = mat[i][j] + B.val(i, j);
    return C;
  }

  @Override
  public Vettore per(final Vettore v) {
    Objects.requireNonNull(v);
    if (!conforme(v)) throw new IllegalArgumentException();
    if (v instanceof VettoreNullo) return v;
    final int[] temp = new int[mat.length];
    for (int i = 0; i < mat.length; i++)
      for (int j = 0; j < mat.length; j++)
        temp[i] += mat[i][j] * v.val(j);
    return new VettoreDenso(temp);
  }

  @Override
  public String toString() {
    return "[" + Arrays.stream(mat).map(r -> Arrays.stream(r).mapToObj(Integer::toString).collect(Collectors.joining(", "))).collect(Collectors.joining("; ")) + "]";
  }
}