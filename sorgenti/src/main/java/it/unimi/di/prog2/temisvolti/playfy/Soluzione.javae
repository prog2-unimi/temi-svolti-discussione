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

    // SOF: rep
    final List<Album> album = new ArrayList<>();
    Playlist fusa = new Playlist("Fusa");
    // EOF: rep

    try (final Scanner s = new Scanner(System.in)) {
      while (s.hasNextLine()) {
        final String line = s.nextLine();

        if (line.startsWith("ALBUM")) {
          // SOF: album
          final String titolo = line.substring(6);
          final List<String> titoli = new ArrayList<>();
          final List<Durata> durate = new ArrayList<>();
          while (s.hasNextLine()) {
            final String aLine = s.nextLine();
            if (aLine.equals(".")) {
              // SOF: bralbum
              final Album a = new Album(titolo, titoli, durate);
              album.add(a);
              System.out.println(a);
              // EOF: bralbum
              break;
            }
            final String[] p = aLine.split("-", 2);
            durate.add(Durata.valueOf(p[0].strip()));
            titoli.add(p[1].strip());
          }
          // EOF: album
        } else if (line.startsWith("PLAYLIST")) {
          // SOF: playlist
          final Playlist pl = new Playlist(line.substring(9));
          while (s.hasNextLine()) {
            final String plLine = s.nextLine();
            if (plLine.equals(".")) {
              // SOF: brpl
              fusa = fusa.fondi("Fusa", pl);
              System.out.println(pl);
              // EOF: brpl
              break;
            }
            final String[] p = plLine.split(" ", 2);
            final int aIdx = Integer.parseInt(p[0].strip()) - 1;
            final int bIdx = Integer.parseInt(p[1].strip());
            pl.accoda(album.get(aIdx).brano(bIdx));
          }
          // EOF: playlist
        }
      }
    }

    // SOF: last
    System.out.println(fusa);
    final Iterator<Album> ait = fusa.album();
    while (ait.hasNext()) {
      final Album a = ait.next();
      System.out.println(a.titolo);
      final Iterator<Album.Brano> bit = fusa.brani(a);
      while (bit.hasNext()) System.out.println("\t" + bit.next());
    }
    // EOF: last

  }
}
