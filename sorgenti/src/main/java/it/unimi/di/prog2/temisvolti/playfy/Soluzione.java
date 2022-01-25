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

package it.unimi.di.prog2.temisvolti.playfy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Soluzione {
  public static void main(String[] args) {
    final List<Album> album = new ArrayList<>();
    final List<Playlist> playlist = new ArrayList<>();
    try (final Scanner s = new Scanner(System.in)) {
      while (s.hasNextLine()) {
        final String line = s.nextLine();
        if (line.startsWith("ALBUM")) {
          final String titolo = line.substring(6);
          final List<String> titoli = new ArrayList<>();
          final List<Durata> durate = new ArrayList<>();
          while (s.hasNextLine()) {
            final String aLine = s.nextLine();
            if (aLine.equals(".")) {
              album.add(new Album(titolo, titoli, durate));
              break;
            }
            final String[] p = aLine.split("-", 2);
            durate.add(Durata.valueOf(p[0].strip()));
            titoli.add(p[1].strip());
          }
        } else if (line.startsWith("PLAYLIST")) {
          final Playlist pl = new Playlist(line.substring(9));
          playlist.add(pl);
          while (s.hasNextLine()) {
            final String plLine = s.nextLine();
            if (plLine.equals(".")) break;
            final String[] p = plLine.split(" ", 2);
            final int aIdx = Integer.parseInt(p[0].strip()) - 1;
            final int bIdx = Integer.parseInt(p[1].strip());
            pl.accoda(album.get(aIdx).brano(bIdx));
          }
        }
      }
    }
    for (final Album a : album) System.out.println(a);
    Playlist fusa = new Playlist("<VUOTA>");
    for (final Playlist p : playlist) {
      fusa = fusa.fondi("Fusa", p);
      System.out.println(p);
    }
    System.out.println(fusa);
    final Iterator<Album> ait = fusa.album();
    while (ait.hasNext()) {
      final Album a = ait.next();
      System.out.println(a.titolo);
      final Iterator<Album.Brano> bit = fusa.brani(a);
      while (bit.hasNext()) System.out.println("\t" + bit.next());
    }
  }
}
