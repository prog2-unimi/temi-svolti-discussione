import java.util.Collections;
import java.util.Objects;

public class VettoreNullo implements Vettore {

  // SOF: rapcostr
  private final int dim;

  public VettoreNullo(final int dim) {
    if (dim <= 0) throw new IllegalArgumentException("La dimensione dev'essere positiva.")
    this.dim = dim;
  }
  // EOF: rapcostr

  // SOF: dimval
  @Override
  public int dim() {
    return dim;
  }

  @Override
  public int val(int i) {
    if (i < 0 || i >= dim) throw new IndexOutOfBoundsException("L'indice eccede la dimensoine del vettore.");
    return 0;
  }
  // EOF: dimval

  // SOF: ops
  @Override
  public VettoreNullo per(int alpha) {
    return this;
  }

  @Override
  public Vettore più(Vettore v) {
    Objects.requireNonNull(v);
    if (!conforme(v)) throw new IllegalArgumentException();
    return v;
  }
  // EOF: ops

  @Override
  public String toString() {
    return Collections.nCopies(dim, "0").toString().replace("[", "(").replace("]", ")");
  }
}