/*

Copyright 2022 Massimo Santini

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

package it.unimi.di.prog2.temisvolti.bancarelle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Soluzione {

  public static void main(final String[] args) {

    /* Lettura dei parametri dalla linea di comando */
    final int numDaComprare = Integer.parseInt(args[0]);
    final Giocattolo giocattoloDaComprare = new Giocattolo(args[1], args[2]);

    /* Lettura del flusso di ingresso */
    final Scanner s = new Scanner(System.in);

    final int numBancarelle = s.nextInt();
    final Set<Bancarella> bancarelle = new HashSet<>(numBancarelle);
    final Map<Giocattolo, Integer> giocattolo2prezzo = new HashMap<>();
    final Inventario inventario = new Inventario();

    for (int b = 0; b < numBancarelle; b++) {
      /* Lettura di una bancarella */
      final String proprietario = s.next();
      final int numGiochi = s.nextInt();
      for (int g = 0; g < numGiochi; g++) {
        /* Lettura dei giochi della bancarella */
        final int num = s.nextInt();
        final String nome = s.next();
        final String materiale = s.next();
        final int prezzo = s.nextInt();
        final Giocattolo giocattolo = new Giocattolo(nome, materiale);
        inventario.aggiungi(num, giocattolo);
        giocattolo2prezzo.put(giocattolo, prezzo);
      }
      // SOF: listino
      final Listino listino = new ListinoLineare(giocattolo2prezzo);
      // EOF: listino
      final Bancarella bancarella = new Bancarella(proprietario, inventario, listino);
      bancarelle.add(bancarella);
    }
    s.close();
    // SOF: compratore
    final CompratoreMinimoUnitario compratore = new CompratoreMinimoUnitario(bancarelle);
    // EOF: compratore

    final Acquisto ordine = compratore.compra(numDaComprare, giocattoloDaComprare);
    System.out.println(ordine);
  }
}
