/**
 * Una <em>piastrella romboidale</em> immutabile, la cui rappresentazione è
 * costituita dalla lunghezza (sempre positiva) delle sue diagonali
 * <em>minore</em> e <em>maggiore</em> (memorizzate in due attributi intero, non
 * modificabili e pubblici, fatto che rende non necessari i <em>getter</em>).
 */
public class PiastrellaRomboidale extends Piastrella {

  /**
   * La diagonale minore, è sempre positiva (e non maggiore della diagonale
   * maggiore).
   */
  public final int minore;

  /**
   * La diagonale maggiore, è sempre positiva (e non minore della diagonale
   * minore).
   */
  public final int maggiore;

  /**
   * Costruisce una piastrella dato il suo <em>costo</em> e la lunghezza delle
   * due <em>diagonali</em>; non è necessario specificare le diagonali in ordine
   * di grandezza.
   *
   * @param prima una delle diagonali.
   * @param seconda l'altra diagonale.
   * @param costoUnitario il costo.
   * @throws IllegalArgumentException se il costo, o una delle diagonali, non
   *         sono positivi.
   */
  public PiastrellaRomboidale(final int prima, final int seconda, final int costoUnitario) {
    super(costoUnitario);
    if (prima <= 0)
      throw new IllegalArgumentException("La prima diagonale dev'essere positiva");
    if (seconda <= 0)
      throw new IllegalArgumentException("La seconda diagonale dev'essere positiva");
    // SOF: rappr
    if (prima < seconda) {
      minore = prima;
      maggiore = seconda;
    } else {
      minore = seconda;
      maggiore = prima;
    }
    // EOF: rappr
  }

  @Override
  public int superficie() {
    return (minore * maggiore) / 2;
  }
}