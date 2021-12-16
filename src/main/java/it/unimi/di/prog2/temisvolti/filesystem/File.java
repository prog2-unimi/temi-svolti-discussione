package it.unimi.di.prog2.temisvolti.filesystem;

/**
 * Classe immutabile che rappresenta un <em>file</em>.
 *
 */
public class File extends Entry {

  // SOF: rep
  /** La dimensione del file. */
  // non è pubblico, sebbene immutabile, perché esiste un metodo osservazionale
  // (che nelle directory non può essere sostituito da un intero!)
  private final int size;

  // RI: size > 0

  /**
   * Costruisce un <em>file</em> dato il suo <em>nome</em> e
   * <em>dimensione</em>.
   *
   * @param name il nome.
   * @param size la dimensione.
   * @throws IllegalArgumentException se la dimensione non è positiva, o il nome
   * è <code>null</code> o vuoto.
   */
  public File(final String name, final int size) {
    super(name);
    if (size <= 0) throw new IllegalArgumentException("La dimensione deve essere positiva.");
    this.size = size;
  }
  // EOF: rep

  // SOF: methods
  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isDir() {
    return false;
  }

  @Override
  public String toString() {
    return String.format("%s(%d)", name, size);
  }
  // EOF: methods

}
