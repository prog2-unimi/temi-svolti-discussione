public class PiastrellaRomboidale extends Piastrella {
  public final int minore, maggiore;

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