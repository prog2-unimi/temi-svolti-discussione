public interface Matrice {

  // SOF: signatures
  int dim();
  int val(final int i, final int j);
  Matrice per(final int alpha);
  Matrice più(final Matrice B);
  Matrice per(final Matrice B);
  // EOF: signatures

  // SOF: pervec
  Vettore per(final Vettore v);
  // EOF: pervec

  // SOF: default
  default boolean conforme(final Vettore v) {
    return dim() == v.dim();
  }

  default boolean conforme(final Matrice M) {
    return dim() == M.dim();
  }

  default void requireValidIJ(final int i, final int j) {
    if (0 <= i && i < dim() && 0 <= j && j < dim()) return;
    throw new IndexOutOfBoundsException("Gli indici eccedono la dimensione della matrice.");
  }
  // EOF: default

}