package edu.grinnell.csc207.util;

/**
 * Stores and calls fractions.
 *
 * @author Alex Pollock
 */
public class BFRegisterSet {

  /**
   * An array that is used to stored and retrieve fraction values.
   */
  private BigFraction[] registered = new BigFraction['z' - 'a' + 1];


  /**
   * Initializes the register.
   *
   */
  public BFRegisterSet() {
    for (int i = 0; i < 'z' - 'a' + 1; i++) {
      registered[i] = new BigFraction(0, 1);
    } // for
  } // BFRegister()


  /**
   * Stores a value.
   *
   * @param register the char that the fraction is being stored under
   * @param val the fraction being stored
   */
  public void store(char register, BigFraction val) {
    registered[(register - 'a')] = val;
  } // store(char,BigFraction)

  /**
   * Retrives a value.
   *
   * @param register the char corrisponds to the retrived fraction
   * @return the fraction found at register.
   */
  public BigFraction get(char register) {
    return registered[(int) (register - 'a')];
  } // get(char)

} // class BFRegisterSet
