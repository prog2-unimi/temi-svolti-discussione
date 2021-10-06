/**
 * Il <em>rivestimento</em> rappresenta una qualunque entità dotata di
 * <em>costo</em> e <em>superficie</em> (comunque tali informazioni siano
 * memorizzate, o calcolate).
 */
public interface Rivestimento {
  /**
   * Restituisce il costo del rivestimento.
   *
   * @return il costo, ha sempre valore positivo.
   */
  int costo();

  /**
   * Restituisce la superficie del rivestimento.
   *
   * @return la superficie, ha sempre valore positivo.
   */
  int superficie();
}