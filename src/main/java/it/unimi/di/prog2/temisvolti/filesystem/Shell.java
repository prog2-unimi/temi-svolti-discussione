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
import java.util.Objects;
import java.util.Scanner;

/** Classe mutabile che rappreseta una <em>shell</em> */
public class Shell {

  /** Prefisso per l'output. */
  private static final String PREFIX = ">>> ";

  // SOF: rep
  /** Il filesystem su cui opera la shell. */
  private final FileSystem fs;
  /** Il path assoluto della directory corrente */
  private Path cwd;

  /**
   * Costruisce una <em>shell</em> dato il filesystem.
   *
   * <p>La <em>directory corrente</em> è inzialmente la radice del <em>filesystem</em>.
   *
   * @param fs il filesystem
   * @throws NullPointerException se il filesystem è <code>null</code>.
   */
  public Shell(final FileSystem fs) {
    this.fs = Objects.requireNonNull(fs, "Il filesystem non può essere null.");
    cwd = Path.ROOT;
  }
  // EOF: rep

  // RI: fs non è null, cwd non è null ed è un path assoluto che indica una
  // directory nel filesystem

  // SOF: resolve
  /**
   * Risolve il <em>path</em> rispetto alla directory corrente.
   *
   * @param path il path.
   * @return il path assoluto ottenuto ri
   */
  private Path resolve(final String path) {
    return cwd.resolve(Path.fromString(path));
  }
  // EOF: resolve

  // SOF: tree
  /**
   * Emette nel flusso d'scita la <em>direcotry</em> data sotto forma di albero.
   *
   * <p>Questa funzione richiama ricorsivamente se stessa per emettere le sottodirectory in forma di
   * albero, usa il prefisso per gestire l'indentazione dei sottoalberi.
   *
   * @param prefix il prefisso da anteporre ad ogni linea emessa.
   * @param d la directory il cuil albero è da emettere.
   */
  private static void recursiveTree(final String prefix, final Directory d) {
    Iterator<Entry> it = d.iterator();
    while (it.hasNext()) {
      final Entry e = it.next();
      System.out.println(PREFIX + prefix + (it.hasNext() ? "├── " : "└── ") + e);
      if (e.isDir()) recursiveTree(prefix + (it.hasNext() ? "│   " : "    "), (Directory) e);
    }
  }

  /**
   * Emette nel flusso d'uscita la <em>directory</em> corrispondente al <em>path</em> dato.
   *
   * @param path il percorso di una directory del filesystem.
   * @throws FileNotFoundException se il percorso non individua una directory.
   */
  private void tree(final Path path) throws FileNotFoundException {
    recursiveTree("", fs.findDir(path));
  }
  // EOF: tree

  // SOF: interpreter
  /**
   * Esegue l'interprete di comandi.
   *
   * <p>Questa classe legge una linea alla volta dal {@link BufferedReader} fino a quando legge una
   * linea vuota. Per ciascuna linea, interpreta il comando che contiene, riportando gli errori
   * eventualmente riportati durante la sua esecuzione. Al termine dell'esecuzione, restituisce
   * l'elenco dei comandi ricevuti.
   *
   * @param con il {@link BufferedReader} da cui vengono letti le linee contenenti i comandi.
   * @return l'elenco di comandi ricevuti.
   * @throws IOException se avviene un errore durante la lettura delle linee.
   */
  public List<String> interpreter(final BufferedReader con) throws IOException {
    final List<String> history = new ArrayList<>();
    for (; ; ) {
      final String line = con.readLine();
      if (line == null) break;
      history.add(line);
      try (final Scanner s = new Scanner(line)) {
        final String cmd = s.next();
        switch (cmd) {
          case "mkdir":
            fs.mkdir(resolve(s.next()));
            break;
          case "mkfile":
            fs.mkfile(resolve(s.next()), s.nextInt());
            break;
          case "tree":
            tree(s.hasNext() ? resolve(s.next()) : cwd);
            break;
          case "ls":
            for (Entry e : fs.ls(s.hasNext() ? resolve(s.next()) : cwd))
              System.out.println(PREFIX + e);
            break;
          case "pwd":
            System.out.println(PREFIX + cwd);
            break;
          case "cd":
            if (s.hasNext()) {
              final Path nwd = resolve(s.next());
              fs.findDir(nwd);
              // se il path non esiste, o non è una directory
              // il metodo solleva una eccezione che preverrà
              // prossimo assegnamento
              cwd = nwd;
            } else cwd = Path.ROOT;
            break;
          case "size":
            System.out.println(PREFIX + fs.size(s.hasNext() ? resolve(s.next()) : cwd));
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
  // EOF: interpreter

  /**
   * Istanzia l'interprete in modo che legga il flusso di ingresso standard; se il programma è
   * invocato con degli argomenti, al termine dell'esecuzione emette la storia dei comandi ricevuti.
   *
   * @param args gli argomenti del comando, se presenti verrà emesso l'elenco dei comandi.
   * @throws IOException se avviene un errore durante la lettura del flusso di ingresso.
   */
  public static void main(String[] args) throws IOException {
    final Shell shell = new Shell(new FileSystem());
    List<String> history = shell.interpreter(new BufferedReader(new InputStreamReader(System.in)));
    if (args.length > 0) {
      System.out.println("History\n=======\n");
      for (String line : history) System.out.println(line);
    }
  }
}
