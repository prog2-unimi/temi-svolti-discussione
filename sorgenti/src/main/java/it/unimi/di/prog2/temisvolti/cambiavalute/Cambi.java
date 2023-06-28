package it.unimi.di.prog2.temisvolti.cambiavalute;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Cambi implements Iterable<Cambi.Tasso>{

  public record Tasso(Importo da, Importo a) {
  
    public Tasso(Importo da, Importo a) {
      // controllare null e zeri
      if (a.valuta.equals(da.valuta)) throw new IllegalArgumentException("Impossibile definire un tasso di cambio tra valute identiche");
      this.da = da;
      this.a = a;
    }
  
    @Override
    public String toString() {
      return da + " = " + a;
     }
  
  }

  List<Tasso> tassi = new LinkedList<>();

  public boolean aggiorna(Tasso tasso) {
    Tasso precedente = cerca(tasso.da().valuta, tasso.a().valuta);
    if (precedente != null) tassi.remove(precedente);
    tassi.add(tasso);
    return precedente != null;
  }

  public Tasso cerca(Valuta da, Valuta a) {
    for (Tasso t : tassi) 
      if (t.da().valuta == da && t.a().valuta == a) 
      return t;
    return null;
  }

  @Override
  public Iterator<Tasso> iterator() {
    return Collections.unmodifiableList(tassi).iterator();
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer("Tassi:\n");
    Iterator<Tasso> it = tassi.iterator();
    while (it.hasNext())
      sb.append(it.next() + (it.hasNext() ? "\n" : ""));
    return sb.toString();
  }
}

