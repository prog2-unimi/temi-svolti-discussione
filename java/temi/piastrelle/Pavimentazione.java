import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Una <em>pavimentazione</em> immutabile, la cui rappresentazione è costituita
 * da una collezione di {@link Componenti} ciascuno dei quali rappresenta una
 * certa <em>quantità</em> di un <em>rivestimento</em> (sia esso una
 * <em>piastrella</em> o un'altra <em>pavimentazione</em>); è possibile accedere
 * al suo contenuto tramite <em>iterazione</em>.
 *
 */
public class Pavimentazione implements Rivestimento, Iterable<Pavimentazione.Componente> {

  // SOF: componente
  /**
   * Un <em>componente</em> di una <em>pavimentazione</em>, ossia una certa
   * <em>quantità</em> di un dato <em>rivestimento</em>.
   */
  public static class Componente implements Rivestimento {

    /**
     * Il rivestimento di cui è costituito questo componente, non è mai
     * <code>null</code>.
     */
    public final Rivestimento rivestimento;

    /**
     * La quantità di rivestimenti di cui è costituito il componente, è sempre
     * positiva.
     */
    public final int quantità;

    /**
     * Costruisce una pavimentazione, data una <em>quantità</em> del
     * <em>rivestimento</em> che lo costituisce. Implementa {@link Rivestimento}
     * nel modo ovvio: costo e superficie sono ottenuti moltiplicando quelle del
     * rivestimento per la quantità in cui è presente.
     *
     * @param quantità la quantità.
     * @param rivestimento il rivestimento.
     * @throws IllegalArgumentException se la quantità non è positiva, o il
     *         rivestimento è <code>null</code>.
     */
    public Componente(final int quantità, final Rivestimento rivestimento) {
      this.rivestimento = Objects.requireNonNull(rivestimento);
      if (quantità <= 0)
        throw new IllegalArgumentException("La quantità dev'essere positiva");
      this.quantità = quantità;
    }

    // SOF: componente_rivestimento
    @Override
    public int costo() {
      return quantità * rivestimento.costo();
    }

    @Override
    public int superficie() {
      return quantità * rivestimento.superficie();
    }
    // EOF: componente_rivestimento

  }
  // EOF: componente

	// SOF: rappr
  /**
   * La collezione di componenti compresi in questa pavimentazione, non è
   * <code>nulL</code>, non è vuota e non contiene <code>null</code>.
   */
  private final Collection<Componente> componenti;

  /**
   * Costruisce una pavimentazione data la collezione di componenti che
   * comprende.
   *
   * @param componenti una collezione di componenti.
   * @throws NullPointerException se la collezione è o contiene
   *         <code>null</code>
   * @throws IllegalArgumentException se la collezione è vuota.
   */
  public Pavimentazione(final Collection<Componente> componenti) {
		// SOF: copyof
    this.componenti = List.copyOf(componenti);
		// EOF: copyof
    if (componenti.isEmpty())
      throw new IllegalArgumentException("Ci deve essere sempre almeno una componente.");
  }
	// EOF: rappr

  // SOF: rivestimento
  @Override
  public int costo() {
    int totale = 0;
    for (final Rivestimento r : componenti)
      totale += r.costo();
    return totale;
  }

  @Override
  public int superficie() {
    int totale = 0;
    for (final Rivestimento r : componenti)
      totale += r.superficie();
    return totale;
  }
  // EOF: rivestimento

  // SOF: iterable
  @Override
  public Iterator<Pavimentazione.Componente> iterator() {
    return componenti.iterator();
  }
  // EOF: iterable
}