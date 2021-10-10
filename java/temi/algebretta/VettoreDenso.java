import java.util.Arrays;
import java.util.Objects;

public class VettoreDenso implements Vettore {

  // SOF: rapcostr
  private final int[] val;

  public VettoreDenso(final int dim) {
    if (dim <= 0) throw new IllegalArgumentException("La dimensione deve essere positiva.");
    val = new int[dim];
  }

  public VettoreDenso(final int[] val) {
    Objects.requireNonNull(val);
    if (val.length == 0) throw new IllegalArgumentException("Il vettore deve comprendere almeno un valore.");
    this.val = val.clone(); // per proteggere la rappresentazione
  }
  // EOF: rapcostr

  @Override
  public int dim() {
    return val.length;
  }

  @Override
  public int val(final int i) {
    if (i < 0 || i >= val.length) throw new IndexOutOfBoundsException("L'indice eccede le dimensioni del vettore.");
    return val[i];
  }

  @Override
  public Vettore per(final int alpha) {
    if (alpha == 0) return new VettoreNullo(dim());
    final VettoreDenso res = new VettoreDenso(dim());
    for (int i = 0; i < val.length; i++) res.val[i] = val[i] * alpha;
    return res;
  }

  @Override
  public Vettore più(final Vettore v) {
    Objects.requireNonNull(v);
    if (!conforme(v)) throw new IllegalArgumentException();
    if (v instanceof VettoreNullo) return this;
    final VettoreDenso res = new VettoreDenso(dim());
    for (int i = 0; i < val.length; i++) res.val[i] = val[i] + v.val(i);
    return res;
  }

  @Override
  public String toString() {
    return Arrays.toString(val).replace("[", "(").replace("]", ")");
  }

}