/*

Copyright 2021 Massimo Santini

This file is part of "Programmazione 2 @ UniMI" teaching material.

This is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This material is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this file.  If not, see <https://www.gnu.org/licenses/>.

*/

package it.unimi.di.prog2.temisvolti.filesystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Shell {

  private static final String PREFIX = ">>> ";

  private final FileSystem fs;
  private Path cwd;

  public Shell(final FileSystem fs) {
    this.fs = fs;
    cwd = Path.ROOT;
  }

  private Path path(final String path) {
    return cwd.resolve(Path.fromString(path));
  }

  private static void recursiveTree(final String prefix, final Directory d) {
    Iterator<Entry> it = d.iterator();
    while (it.hasNext()) {
      final Entry e = it.next();
      System.out.println(PREFIX + prefix + (it.hasNext() ? "├── " : "└── ") + e);
      if (e.isDir()) recursiveTree(prefix + (it.hasNext() ? "│   " : "    "), (Directory) e);
    }
  }

  private void tree(final Path path) throws FileNotFoundException {
    recursiveTree("", fs.findDir(path));
  }

  public List<String> interpreter(final BufferedReader con) throws IOException {
    List<String> history = new ArrayList<>();
    for (; ; ) {
      final String line = con.readLine();
      if (line == null) break;
      history.add(line);
      @SuppressWarnings("resource")
      final Scanner s = new Scanner(line);
      try {
        final String cmd = s.next();
        switch (cmd) {
          case "mkdir":
            fs.mkdir(path(s.next()));
            break;
          case "mkfile":
            fs.mkfile(path(s.next()), s.nextInt());
            break;
          case "tree":
            tree(s.hasNext() ? path(s.next()) : cwd);
            break;
          case "ls":
            for (Entry e : fs.ls(s.hasNext() ? path(s.next()) : cwd))
              System.out.println(PREFIX + e);
            break;
          case "pwd":
            System.out.println(PREFIX + cwd);
            break;
          case "cd":
            if (s.hasNext()) {
              final Path nwd = path(s.next());
              fs.findDir(nwd);
              // if the path is not valid, an exception on the above line
              // will prevent the following assignement
              cwd = nwd;
            } else cwd = Path.ROOT;
            break;
          case "size":
            System.out.println(PREFIX + fs.size(s.hasNext() ? path(s.next()) : cwd));
            break;
          default:
            System.err.println(PREFIX + "shell: " + cmd + ": command not found!");
        }
      } catch (NoSuchElementException e) {
        System.err.println(PREFIX + "shell: malformed command: " + line);
      } catch (IOException fse) {
        System.err.println(PREFIX + "shell: error: " + fse.getMessage());
      }
    }
    return history;
  }

  public static void main(String[] args) throws IOException {
    final Shell shell = new Shell(new FileSystem());
    List<String> history = shell.interpreter(new BufferedReader(new InputStreamReader(System.in)));
    if (args.length > 0) {
      System.out.println("History\n=======\n");
      for (String line : history) System.out.println(line);
    }
  }
}
