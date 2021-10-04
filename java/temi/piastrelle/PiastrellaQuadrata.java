public class PiastrellaQuadrata extends Piastrella {
  public final int lato;

  public PiastrellaQuadrata(final int lato, final int costoUnitario) {
    super(costoUnitario);
    if (lato <= 0)
     throw new IllegalArgumentException("Il lato dev'essere positivo");
    this.lato = lato;
  }

  @Override
  public int superficie() {
    return lato * lato;
  }
}