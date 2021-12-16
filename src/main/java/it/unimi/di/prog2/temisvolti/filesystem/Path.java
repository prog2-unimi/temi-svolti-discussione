import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Path implements Iterable<String> {

  private final boolean isAbsolute;
  private final List<String> parts;

  public static final String SEPARATOR = ":";

  public static final Path ROOT = new Path(true, Collections.emptyList());
  public static final Path EMPTY = new Path(false, Collections.emptyList());

  private Path(final boolean isAbsolute, final List<String> parts) {
    this.isAbsolute = isAbsolute;
    this.parts = new ArrayList<>(Objects.requireNonNull(parts));
    if (parts.contains(null)) throw new FileSystem.Exception("path can't contain null parts");
    for (String p: parts) if (p.equals("")) throw new FileSystem.Exception("path can't contain empty parts");
  }

  public static Path fromString(final String path) {
    Objects.requireNonNull(path);
    if (path.equals("")) return EMPTY;
    final String[] parts = path.split(SEPARATOR);
    if (parts.length == 0) return ROOT;
    if (parts[0].equals("")) return new Path(true, Arrays.asList(parts).subList(1, parts.length));
    return new Path(false, Arrays.asList(parts));
  }

  public boolean isAbsolute() {
    return isAbsolute;
  }

  public Path parent() {
    if (parts.size() == 0) return this;
    return new Path(isAbsolute, parts.subList(0, parts.size() - 1));
  }

  public String name() {
    if (parts.size() == 0) throw new FileSystem.Exception("path is empty, hance has no name");
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
    if (!isAbsolute() && other.isAbsolute) throw new FileSystem.Exception("can't relativize an absolute path against a relative one");
    if (!parts.equals(other.parts.subList(0, parts.size()))) throw new FileSystem.Exception("the given path does not have a commn prefix with this");
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
