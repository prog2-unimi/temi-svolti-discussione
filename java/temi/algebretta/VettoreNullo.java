import java.util.Collections;
import java.util.Objects;

public class VettoreNullo implements Vettore {

  private final int dim;

  public VettoreNullo(final int dim) {
    this.dim = dim;
  }

  @Override
  public int dim() {
    return dim;
  }

  @Override
  public int val(int i) {
    if (i < 0 || i >= dim) throw new IllegalArgumentException();
    return 0;
  }

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

  @Override
  public String toString() {
    return Collections.nCopies(dim, "0").toString().replace("[", "(").replace("]", ")");
  }
}