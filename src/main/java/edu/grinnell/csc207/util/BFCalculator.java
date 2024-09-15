package edu.grinnell.csc207.util;

/**
 * Manipulates fractions.
 *
 * @author Alex Pollock
 */
public class BFCalculator {

  /**
   * Stores the last computed value.
   *
   * @return the last fraction approxiamted.
   */
  private BigFraction lastValue = new BigFraction();

  /**
   * Get the last computed value.
   *
   * @return the last fraction approxiamted.
   */
  public BigFraction get() {
    return lastValue;
  } // get()

  /**
   * Set the last computed value.
   *
   * @param frac the last value computed,
   */
  public void set(BigFraction frac) {
    lastValue = frac;
  } // get()

  /**
   * Adds val to the last computed value.
   *
   * @param val the value to add.
   */
  public void add(BigFraction val) {
    lastValue = lastValue.add(val);
  } // add(BigFraction)

  /**
   * Subtracts val to the last computed value.
   *
   * @param val the value to subtract.
   */
  public void subtract(BigFraction val) {
    lastValue = lastValue.sub(val);
  } // subtract(BigFraction)

  /**
   * Multiplies val by the last computed value.
   *
   * @param val the value to multiply by.
   */
  public void multiply(BigFraction val) {
    lastValue = lastValue.multiply(val);
  } // multiply(BigFraction)

  /**
   * Divides the last computed value by val.
   *
   * @param val the value to divide by.
   */
  public void divide(BigFraction val) {
    lastValue = lastValue.divide(val);
  } // divide(BigFraction)

  /**
   * Resets the last computed value to 0.
   */
  public void clear() {
    lastValue = new BigFraction(0, 0);
  } // clear()

} // class BFCalculator
