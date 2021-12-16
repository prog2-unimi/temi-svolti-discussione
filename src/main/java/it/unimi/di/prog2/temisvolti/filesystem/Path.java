package it.unimi.di.prog2.temisvolti.filesystem;

import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Path implements Iterable<String> {

  /** Carattere separatore delle parti di un percorso */
  public static final String SEPARATOR = ":";

  /** Costante corrispondente al path radice (path assoluto, senza parti) */
  public static final Path ROOT = new Path(true, Collections.emptyList());

  /** Costante corrispondente al path vuoto (path relativo, senza parti) */
  public static final Path EMPTY = new Path(false, Collections.emptyList());

  /** Indica se il path è assouto. */
  private final boolean isAbsolute;

  /** Contiene le componenti del path. */
  private final List<String> parts;

  // RI: parts non è null, non contiene null o stringhe vuote, o che contengano il separatore

  /**
   * Costruisce un <em>path</em> a partire da un elenco di stringhe e dall'informazione se sia <em>assoluto</em> o meno.
   *
   * @param isAbsolute indica se il path è assoluto.
   * @param parts elenco di stringhe che costiuiscono le parti del percorso.
   * @throws NullPointerException se le <code>parts</code> è o contiene <code>null</code>.
   * @throws InvalidPathException se una della parti è vuota, o contiene il separatore.
   */
  private Path(final boolean isAbsolute, final List<String> parts) {
    this.isAbsolute = isAbsolute;
    this.parts = List.copyOf(parts);
    for (String p: parts) {
      if (p.isEmpty()) throw new InvalidPathException(p, "La componente è vuota.");
      if (p.indexOf(SEPARATOR) != -1) throw new InvalidPathException(p, "La componente contiene il separatore.");
    }
  }

  /**
   * Metodo di fabbricazione che restituisce un <em>path</em> a partire da una stringa.
   *
   * <p> Alcuni esempi di percorso sono:
   * <ul>
   *  <li>"<samp>:A:B:C</samp>", percorso assoluto con parti <samp>A</samp>, <samp>B<samp> e <samp>C</samp>, </li>
   *  <li>"<samp>A:B:C</samp>", percorso relativo con parti <samp>A</samp>, <samp>B<samp> e <samp>C</samp>, </li>
   *  <li>"<samp>:</samp>", percorso assoluto corrispondente alla radice del filesystem, </li>
   *  <li>"<samp></samp>", percorso vuoto (relativo). </li>
   * </ul>
   *
   *
   * @param path la stringa che corrisponde alla rappresentazione testuale del path.
   * @return il path corrispondente alla stringa.
   * @throws NullPointerException se path è <code>null</code>
   * @throws InvalidPathException se nella stringa compaiono due separatori immediatamente consecutivi.
   */
  public static Path fromString(final String path) {
    Objects.requireNonNull(path);
    if (path.isEmpty()) return EMPTY;
    final String[] parts = path.split(SEPARATOR);
    if (parts.length == 0) return ROOT;
    if (parts[0].isEmpty()) return new Path(true, Arrays.asList(parts).subList(1, parts.length));
    return new Path(false, Arrays.asList(parts));
  }

  /**
   * Consente di sapere se il <em>path</em> è <em>assoluto</em>.
   *
   * @return <code>true</code> sse il path è assoluto.
   */
  public boolean isAbsolute() {
    return isAbsolute;
  }

  /**
   * Restituisce il prefisso di questo <em>path</em> a meno dell'ultima
   * componente (o quello vuoto, se questo è vuoto).
   *
   * @return un path corrispondente a questo, ma privato dell'ultima componente (se presente).
   */
  public Path parent() {
    if (parts.isEmpty()) return this;
    return new Path(isAbsolute, parts.subList(0, parts.size() - 1));
  }

  /**
   * Restituisce l'ultima componente di questo <em>path</em>.
   *
   * @return l'ultima componente di questo path, o <code>null</code> se il path è vuoto.
   */
  public String name() {
    if (parts.isEmpty()) return null;
    return parts.get(parts.size() - 1);
  }

  public Path resolve(final Path other) {
    if (Objects.requireNonNull(other).isAbsolute()) return other;
    final List<String> parts = new ArrayList<>(this.parts);
    parts.addAll(other.parts);
    return new Path(isAbsolute, parts);
  }

  public Path relativize(final Path other) {
    Objects.requireNonNull(other);
    if (!isAbsolute() && other.isAbsolute) throw new IllegalArgumentException("Non si può relativizzare un path assoluto rispetto ad un relativo.");
    if (!parts.equals(other.parts.subList(0, parts.size()))) throw new IllegalArgumentException("Il percorso non ha un prefisso in comune con questo.");
    return new Path(false, other.parts.subList(parts.size(), other.parts.size()));
  }

  @Override
  public String toString() {
    return (isAbsolute ? SEPARATOR : "") + String.join(SEPARATOR, parts);
  }

  @Override
  public Iterator<String> iterator() {
    return parts.iterator();
  }

}
