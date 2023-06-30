package it.unimi.di.prog2.temisvolti.cambiavalute;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/** Classe che rappresenta l'elenco dei tassi noti al cambiavalute. */
public class Cambi implements Iterable<Cambi.Tasso>{

    /** Record che rappresenta una coppia di importi che descrivono un tasso di
     * cambio. */
    public record Tasso(Importo da, Importo a) {
  
    // SOF: tasso
    /**
     * Crea un nuovo tasso di cambio.
     *
     * @param da l'importo di partenza.
     * @param a l'importo di arrivo
     * @throws NullPointerException se uno dei due importi è <code>null</code>.
     * @throws IllegalArgumentException se uno dei due importi non è positivo o
     *         se i due importi sono nella stessa valuta.
     */
    public Tasso(Importo da, Importo a) {
      if (!Objects.requireNonNull(da, "Il primo importo non può essere null.").isPositive()) throw new IllegalArgumentException("Il primo importo deve essere positivo.");
      if (!Objects.requireNonNull(a, "Il secodo importo non può essere null.").isPositive()) throw new IllegalArgumentException("Il secondo importo deve essere positivo.");
      if (a.valuta.equals(da.valuta)) throw new IllegalArgumentException("Impossibile definire un tasso di cambio tra valute identiche");
      this.da = da;
      this.a = a;
    }
    // EOF: tasso

    @Override
    public String toString() {
      return da + " = " + a;
     }
  
  }

  // SOF: rep
  /** L'elenco di cambi. Non deve essere o contenere <code>null</code> e non
   * deve contenere più di un tasso tra due importi con una data coppia di
   * valute. I tassi sono mantenuti in ordine di aggiunta/aggiornamento. */
  private final List<Tasso> tassi = new LinkedList<>();
  // EOF: rep 

  // SOF: cerca
  /** Cerca tra i tassi noti un tasso tra le valute date.
   *
   * @param da la valuta del primo importo.
   * @param a la valuta del secondo importo.
   * @return il tasso, oppure <code>null</code> se non è noto alcun tasso tra
   * importi nelle valute date.
   */
  public Tasso cerca(Valuta da, Valuta a) {
    for (Tasso t : tassi) 
      if (t.da().valuta == da && t.a().valuta == a) 
      return t;
    return null;
  }
  // EOF: cerca

  // SOF: aggiorna
  /**
   * Aggiorna, o aggiunge, il tasso di cambio tra due valute all'elenco dei
   * tassi noti.
   *
   * <p>Nel caso fosse noto un tasso tra le due stesse valute del tasso da aggioranre, questo
   * sostituirà il precedente tasso; viceversa il tasso da aggiornare è di fatto "nouovo" e verrà 
   * semplicemente aggiunto alla lista dei tassi noti.
   *
   *
   * @param tasso il tasso da aggiornare.
   * @return <code>true</code> se il tasso è stato aggiornato,
   *         <code>false</code> viceversa.
   */
  public boolean aggiorna(Tasso tasso) {
    Tasso precedente = cerca(tasso.da().valuta, tasso.a().valuta);
    if (precedente != null) tassi.remove(precedente);
    tassi.add(tasso);
    return precedente != null;
  }
  // EOF: aggiorna

  // SOF: iter
  @Override
  public Iterator<Tasso> iterator() {
    return Collections.unmodifiableList(tassi).iterator();
  }
  // EOF: iter

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer("Tassi:\n");
    Iterator<Tasso> it = tassi.iterator();
    while (it.hasNext())
      sb.append(it.next() + (it.hasNext() ? "\n" : ""));
    return sb.toString();
  }
}

