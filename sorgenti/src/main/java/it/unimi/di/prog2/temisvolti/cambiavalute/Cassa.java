package it.unimi.di.prog2.temisvolti.cambiavalute;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/** Classe che implementa una cassa multi-valuta.
 *
 * La cassa consente di iterare sui propri importi diversi da zero.
 */
public class Cassa implements Iterable<Importo> {
  
  // SOF: rep
  /** Mappa che associa a ciascuna valuta il suo importo in cassa, valute e
   * importi non devono essere <code>null</code>, gli importi devono essere
   * positivi e avere valuta corrispondente alla chiave. */
  private final Map<Valuta, Importo> valuta2importo = new EnumMap<>(Valuta.class);
  // EOF: rep
  
  // SOF: tot
  /**
   * Restituisce l'importo totale presente in cassa in una data valuta.
   *
   * @param valuta la valuta.
   * @return l'importo.
   * @throws NullPointerException se la valuta è <code>null</code>.
   */
  public Importo totale(Valuta valuta) {
    Importo totale = valuta2importo.get(Objects.requireNonNull(valuta, "La valuta non può essere null."));
    return totale == null ? Importo.zero(valuta) : totale;
  }
  // EOF: tot

  // SOF: add
  /**
   * Versa un importo in cassa.
   * 
   * @param importo l'importo da versare.
   * @throws NullPointerException se l'importo è <code>null</code>.
   * @throws IllegalArgumentException se l'importo è negativo.
   */
  public void versa(Importo importo) {
    if (!Objects.requireNonNull(importo, "L'importo non può essere null.").isZero()) return;
    if (!importo.isPositive()) throw new IllegalArgumentException("Non si possono depositare importi negativi.");
    valuta2importo.put(importo.valuta, totale(importo.valuta).somma(importo));
  }
  // EOF: add

  // SOF: sub
  /**
   * Preleva un importo dalla cassa.
   *
   * @param importo l'importo da prelevare.
   * @throws NullPointerException se l'importo è <code>null</code>.
   * @throws IllegalArgumentException se l'importo è negativo, o superiore al
   * totale in cassa per la sua valuta.
   */
  public void preleva(Importo importo) {
    if (!Objects.requireNonNull(importo, "L'importo non può essere null.").isZero()) return;
    if (!importo.isPositive()) throw new IllegalArgumentException("Non si possono depositare importi negativi.");
    final Importo resto = totale(importo.valuta).differenza(importo);
    if (resto.isZero()) valuta2importo.remove(resto.valuta);
    else if (!resto.isPositive()) throw new IllegalArgumentException("La cassa non contiene abbastanza fondi.");
    else valuta2importo.put(importo.valuta, resto);
  }
  // EOF: sub

  // SOF: iter
  @Override
  public Iterator<Importo> iterator() {
    return Collections.unmodifiableCollection(valuta2importo.values()).iterator();
  }
  // EOF: iter
  
  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer("Cassa:\n");
    Iterator<Importo> it = iterator();
    while (it.hasNext())
      sb.append(it.next() + (it.hasNext() ? "\n" : ""));      
    return sb.toString();
  }

}
