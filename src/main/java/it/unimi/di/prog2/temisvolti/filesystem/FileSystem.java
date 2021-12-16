package it.unimi.di.prog2.temisvolti.filesystem;

import java.util.Iterator;
import java.util.List;

public class FileSystem {
  @SuppressWarnings("serial")
  public static class Exception extends RuntimeException {
    public Exception(final String msg) {
      super(msg);
    }
  }

  public final Directory root = new Directory("ROOT");

  public Entry find(final Path path) {
    final Iterator<String> it = path.iterator();
    Directory d = root;
    Entry e = d;
    while (it.hasNext()) {
      final String p = it.next();
      e = d.find(p);
      if (e == null)
        throw new FileSystem.Exception("not found: " + p);
      if (it.hasNext()) {
        if (e.isDir()) d = (Directory) e;
        else throw new FileSystem.Exception("not a dir: " + p);
      }
    }
    return e;
  }

  public Directory findDir(final Path path) {
    final Entry e = find(path);
    if (!e.isDir()) throw new FileSystem.Exception("not a dir: " + path);
    return (Directory) e;
  }

  public List<Entry> ls(final Path path) {
    return findDir(path).ls();
  }

  public int size(final Path path) {
    return find(path).size();
  }

  public void mkdir(final Path path) {
    findDir(path.parent()).add(new Directory(path.name()));
  }

  public void mkfile(final Path path, final int size) {
    findDir(path.parent()).add(new File(path.name(), size));
  }

}
