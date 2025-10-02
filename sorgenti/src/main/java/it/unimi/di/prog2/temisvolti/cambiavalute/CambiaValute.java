package it.unimi.di.prog2.temisvolti.cambiavalute;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/** Classe che rappresenta il cambiavalute. */
public class CambiaValute {
  
  // SOF: rep
  /** La cassa del cambiavalute. */
  private final Cassa cassa = new Cassa();

  /** L'elenco dei tassi di cambio noti al cambiavalute. */
  private final Cambi cambi = new Cambi();
  // EOF: rep

  // SOF: costruttore
  /**
   * Crea un cambiavalute versando in cassa gli importi dati.
   *
   * @param importi gli importi da versare.
   * @throws NullPointerException se importi è, o contiene, <code>null</code>.
   * @throws IllegalArgumentException se uno degli importi non è positivo.
   */
  public CambiaValute(List<Importo> importi) {
    for (Importo importo : importi) cassa.versa(importo);
  }
  // EOF: costruttore

  // SOF: cambia
  /**
   * Cambia un importo da una valuta a un'altra.
   *
   * <p> Per effetto del cambio, verrà versato in cassa l'importo dato e 
   * sarà prelevato l'importo equivalente nell'altra valuta. Se questo
   * non è possibile, perché la valuta d'arrivo è uguale a quella di partenza,
   * o per mancanza di fondi o perché non è noto il tasso di cambio necessario, 
   * verrà sollevata una eccezione.
   *
   * @param da l'importo da cambiare.
   * @param aValuta la valuta in cui cambiarlo.
   * @return l'importo equivalente nella valuta data.
   * @throws NullPointerException se da o aValuta sono <code>null</code>.
   * @throws IllegalArgumentException se da e aValuta sono uguali, se non è noto
   *         il tasso di cambio o se i fondi in cassa non sono sufficienti.
   */
  public Importo cambia(Importo da, Valuta aValuta) {
    if (da.valuta == aValuta) throw new IllegalArgumentException("Impossibile cambiare tra valute identiche.");
    Cambi.Tasso t = cambi.cerca(da.valuta, aValuta);
    if (t == null) throw new IllegalArgumentException("Tasso non disponibile.");
    Importo a = da.equivalente(t);
    if (cassa.totale(aValuta).compareTo(a) < 0) 
      throw new IllegalArgumentException("Fondi non sufficienti.");
    cassa.versa(da);
    cassa.preleva(a);
    return a;
  }
  // EOF: cambia

  // SOF: aggiorna
  /** Aggiorna (o aggiunge) un tasso di cambio.
   * 
   * @param tasso il tasso di cambio da aggiornare (o aggiungere).
   * @return <code>true</code> se il tasso sostituisce un tasso precedentemente noto,
   *         <code>false</code> viceversa.
   * @throws NullPointerException se tasso è <code>null</code>.
   * 
   * 
   */
  public boolean aggiorna(Cambi.Tasso tasso) {
    return cambi.aggiorna(Objects.requireNonNull(tasso, "Il tasso non può essere null."));
  }
  // EOF: aggiorna

  // SOF: iter
  /**
   * Consente di conoscere gli importi in cassa.
   * 
   * @return un iteratore sugli importi in cassa.
   */
  Iterator<Importo> importi() {
    return cassa.iterator();
  }

  /** Consente di conoscere i tassi noti.
   * 
   * @return un iteratore sui tassi noti.
   */
  Iterator<Cambi.Tasso> tassi() {
    return cambi.iterator();
  }
  // EOF: iter

  @Override
  public String toString() {
    return cambi.toString() + "\n" + cassa.toString();
  }
}
