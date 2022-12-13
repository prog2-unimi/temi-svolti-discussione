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

package it.unimi.di.prog2.temisvolti.algebretta;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Classe contenitore di metodi statici per il parsing dell'input. */
public class Parser {

  // le costanti seguenti sono legare ad una serie di espressioni regolari utili al parsing e non
  // vanno modificate

  private static final Pattern OPERAZIONE =
      Pattern.compile("\\s*(?<sinistra>.*\\S)\\s*(?<operatore>\\+|\\*)\\s*(?<destra>.*\\S)\\s*");
  private static final Pattern MATRICE =
      Pattern.compile("\\s*(?<tipo>[ZID]?)\\s*\\[(?<array>[-+0-9,;\n ]+)\\]\\s*");
  private static final Pattern RIGA = Pattern.compile("([-+0-9,\n ]+);?");
  private static final Pattern VETTORE = Pattern.compile("\\s*\\((?<array>[-+0-9,\n ]+)\\)\\s*");
  private static final Pattern SCALARE = Pattern.compile("\\s*(?<alpha>[-+0-9]+)\\s*");

  private Parser() {} // impedisce l'istanziazione di questa classe

  /**
   * Divide una linea corridpondente ad una operazione binaria nelle sue parti.
   *
   * @param linea la linea da considerare.
   * @return un array di tre {@link String} la prima ed ultima delle quali sono i due operandi e
   *     quella di posto 1 è l'opertore (ossia {@code '+''} o {@code '*'}).
   * @throws IllegalArgumentException se la linea non ha la forma {@code operando (+|*) operando}.
   */
  public static String[] partiOperazione(final String linea) {
    Matcher m = OPERAZIONE.matcher(linea);
    if (m.matches())
      return new String[] {m.group("sinistra"), m.group("operatore"), m.group("destra")};
    throw new IllegalArgumentException("Errore nel formato.");
  }

  /**
   * Decide se un operando è una matrice.
   *
   * @param operando la stringa contenente l'operando da analizzare.
   * @return true sse l'operando è una matrice.
   */
  public static boolean èMatrice(final String operando) {
    return MATRICE.matcher(operando).matches();
  }

  /**
   * Restituisce il tipo di matrice contenuta nell'opearndo.
   *
   * @param operando la stringa contenente la matrice da analizzare.
   * @return un carattere che indica il tipo di matrice, {@code ' '} per matrici generiche, oppure
   *     {@code 'Z'}, {@code 'D'} o {@code 'I'} rispettivamente per le matrici nulla, diagnoali o
   *     identità.
   * @throws IllegalArgumentException se l'operando non corrisponde ad una matrice.
   */
  public static char tipoMatrice(final String operando) {
    Matcher m = MATRICE.matcher(operando);
    if (m.matches()) {
      final String tipo = m.group("tipo");
      return tipo.length() > 0 ? tipo.charAt(0) : ' ';
    }
    throw new IllegalArgumentException("Errore nel formato.");
  }

  /**
   * Restituisce un array bidimensionale di interi contenente i valori nella matrice contenuta
   * nell'operando.
   *
   * <p>Si osservi che il formato della matrice (righe racchiuse tra quadre e separate da punti e
   * virgola con ciascuna riga data da interi separati da virgola) non garantisce che l'array
   * bidimensionale sia "quadrato" (ossia abbia ciascuna riga della stessa dimensione del numero di
   * righe). Ad esempio se il parametro è {@code [1; 2, 3]}, questo metodo restituirà l'array {@code
   * new int[][] {{1}, {2, 3}}}.
   *
   * @param operando la stringa contenente la matrice da analizzare.
   * @return un array bidimensionale (non necessariamente quadrato) di interi contenente i valori
   *     della matrice.
   * @throws IllegalArgumentException se l'operando non corrisponde ad una matrice.
   */
  public static int[][] valoriMatrice(final String operando) {
    Matcher m = MATRICE.matcher(operando);
    if (m.matches()) {
      return RIGA.matcher(m.group("array"))
          .results()
          .map(
              r ->
                  SCALARE
                      .matcher(r.group(1))
                      .results()
                      .map(c -> c.group(1))
                      .mapToInt(Integer::parseInt)
                      .toArray())
          .toArray(n -> new int[n][]);
    }
    throw new IllegalArgumentException("Errore nel formato.");
  }

  /**
   * Decide se un operando è un vettore.
   *
   * @param operando la stringa contenente l'operando da analizzare.
   * @return true sse l'operando è una vettore.
   */
  public static boolean èVettore(final String operando) {
    return VETTORE.matcher(operando).matches();
  }

  /**
   * Restituisce un array di interi contenente i valori nel vettore contenuto nell'operando.
   *
   * @param operando la stringa contenente il vettore da analizzare.
   * @return un array bidimensionale di interi contenente i valori del vettore.
   * @throws IllegalArgumentException se l'operando non corrisponde ad un vettore.
   */
  public static int[] valoriVettore(final String operando) {
    Matcher m = VETTORE.matcher(operando);
    if (m.matches())
      return SCALARE
          .matcher(m.group("array"))
          .results()
          .map(c -> c.group(1))
          .mapToInt(Integer::parseInt)
          .toArray();
    throw new IllegalArgumentException("Errore nel formato.");
  }

  /**
   * Decide se un operando è uno scalare.
   *
   * @param operando la stringa contenente l'operando da analizzare.
   * @return true sse la stringa è uno scalare.
   */
  public static boolean èScalare(final String operando) {
    return SCALARE.matcher(operando).matches();
  }

  /**
   * Restituisce un intero corrispondente allo scalare contenuto nell'operando.
   *
   * @param operando la stringa contenente lo scalare da analizzare.
   * @return un intero corrispondente allo scalare.
   * @throws IllegalArgumentException se l'operando non corrisponde ad uno scalare.
   */
  public static int valoreScalare(final String operando) {
    Matcher m = SCALARE.matcher(operando);
    if (m.matches()) return Integer.parseInt(m.group("alpha"));
    throw new IllegalArgumentException("Errore nel formato.");
  }
}
