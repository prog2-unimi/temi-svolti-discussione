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

package it.unimi.di.prog2.temisvolti.algebretta;

import java.util.Scanner;

public class Soluzione {

  // SOF: fabmat
  public static Matrice valueOf(final char tipo, final int[][] arr) {
    switch (tipo) {
      case ' ':
        return new MatriceDensa(arr);
      case 'Z':
        return new MatriceNulla(arr[0][0]);
      case 'I':
        return new MatriceIdentità(arr[0][0]);
      case 'D':
        return new MatriceDiagonale(arr[0]);
    }
    throw new IllegalArgumentException("Tipo non riconosciuto.");
  }
  // EOF: fabmat

  public static void main(String[] args) {
    try (final Scanner s = new Scanner(System.in)) {
      while (s.hasNextLine()) {
        final String[] lor = Parser.partiOperazione(s.nextLine());
        final char op = lor[1].charAt(0);
        final String left = lor[0], right = lor[2];
        if (op == '+') {
          // SOF: add
          if (Parser.èVettore(left) && Parser.èVettore(right)) {
            Vettore u = new VettoreDenso(Parser.valoriVettore(left));
            Vettore v = new VettoreDenso(Parser.valoriVettore(right));
            System.out.println(u.più(v));
          } else if (Parser.èMatrice(left) && Parser.èMatrice(right)) {
            Matrice M = valueOf(Parser.tipoMatrice(left), Parser.valoriMatrice(left));
            Matrice N = valueOf(Parser.tipoMatrice(right), Parser.valoriMatrice(right));
            System.out.println(M.più(N));
          }
          // EOF: add
        } else { // op == '*', altrimenti partiOperazione solleva eccezione
          if (Parser.èScalare(left)) {
            // SOF: alphamul
            int alpha = Parser.valoreScalare(left);
            if (Parser.èVettore(right)) {
              Vettore v = new VettoreDenso(Parser.valoriVettore(right));
              System.out.println(v.per(alpha));
            } else if (Parser.èMatrice(right)) {
              Matrice M = valueOf(Parser.tipoMatrice(right), Parser.valoriMatrice(right));
              System.out.println(M.per(alpha));
            }
            // EOF: alphamul
          } else if (Parser.èMatrice(left)) {
            // SOF: mul
            Matrice M = valueOf(Parser.tipoMatrice(left), Parser.valoriMatrice(left));
            if (Parser.èMatrice(right)) {
              Matrice N = valueOf(Parser.tipoMatrice(right), Parser.valoriMatrice(right));
              System.out.println(M.per(N));
            } else if (Parser.èVettore(right)) {
              Vettore v = new VettoreDenso(Parser.valoriVettore(right));
              System.out.println(M.per(v));
            }
            // EOF: mul
          }
        }
      }
    }
  }
}
