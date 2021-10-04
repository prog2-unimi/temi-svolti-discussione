public class PiastrellaTriangolare extends Piastrella {
  public final int base, altezza;

  public PiastrellaTriangolare(final int base, final int altezza, final int costoUnitario) {
    super(costoUnitario);
    if (base <= 0)
      throw new IllegalArgumentException("La base dev'essere positiva");
    if (altezza <= 0)
      throw new IllegalArgumentException("L'altezza dev'essere positiva");
    this.base = base;
    this.altezza = altezza;
  }

  @Override
  public int superficie() {
    return (base * altezza) / 2;
  }
}