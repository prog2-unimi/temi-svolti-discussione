public abstract class AbsMatrice implements Matrice {
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < dim(); i++) {
      for (int j = 0; j < dim(); j++)
        sb.append(val(i, j) + (j < dim() - 1 ? ", " : ""));
      if (i < dim() - 1)
        sb.append("; ");
    }
    sb.append("]");
    return sb.toString();
  }
}