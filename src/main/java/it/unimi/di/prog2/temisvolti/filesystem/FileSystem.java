package it.unimi.di.prog2.temisvolti.filesystem;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Iterator;

public class FileSystem {

  public final Directory root = new Directory("ROOT");

  public Entry find(final Path path) throws FileNotFoundException {
    final Iterator<String> it = path.iterator();
    Directory d = root;
    Entry e = d;
    while (it.hasNext()) {
      final String p = it.next();
      e = d.find(p);
      if (e == null)
        throw new FileNotFoundException("File non trovato " + p);
      if (it.hasNext()) {
        if (e.isDir()) d = (Directory) e;
        else throw new FileNotFoundException("Non è una directory " + p);
      }
    }
    return e;
  }

  public Directory findDir(final Path path) throws FileNotFoundException {
    final Entry e = find(path);
    if (!e.isDir()) throw new FileNotFoundException("Non è una directory " + path);
    return (Directory) e;
  }

  public Iterable<Entry> ls(final Path path) throws FileNotFoundException {
    return findDir(path);
  }

  public int size(final Path path) throws FileNotFoundException {
    return find(path).size();
  }

  public void mkdir(final Path path) throws FileNotFoundException, FileAlreadyExistsException {
    final String name = path.name();
    if (name == null) throw new IllegalArgumentException("Il percorso non può essere vuoto");
    findDir(path.parent()).add(new Directory(name));
  }

  public void mkfile(final Path path, final int size) throws FileNotFoundException, FileAlreadyExistsException {
    final String name = path.name();
    if (name == null) throw new IllegalArgumentException("Il percorso non può essere vuoto");
    findDir(path.parent()).add(new File(name, size));
  }

}
