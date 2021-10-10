public interface Vettore {

  // SOF: signatures
  int dim();
  int val(final int i);
  Vettore per(final int alpha);
  Vettore più(final Vettore v);
  // EOF: signatures

  // SOF: default
  default boolean conforme(final Vettore v) {
    return dim() == v.dim();
  }

  default boolean conforme(final Matrice M) {
    return dim() == M.dim();
  }
  // EOF: default

}