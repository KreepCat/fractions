package edu.grinnell.csc207.util;

import java.math.BigInteger;

/**
 * A simple implementation of arbitrary-precision Fractions.
 *
 * @author Samuel A. Rebelsky
 * @author Alex Pollock, Myles Bohrer-Purnell
 */
public class BigFraction {
  // +------------------+---------------------------------------------
  // | Design Decisions |
  // +------------------+

  /*
   * (1) Denominators are always positive. Therefore, negative fractions are represented with a
   * negative numerator. Similarly, if a fraction has a negative numerator, it is negative.
   *
   * (2) Fractions are not necessarily stored in simplified form. To obtain a fraction in simplified
   * form, one must call the `simplify` method.
   */

  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /** The default numerator when creating fractions. */
  private static final BigInteger DEFAULT_NUMERATOR = BigInteger.valueOf(0);

  /** The default denominator when creating fractions. */
  private static final BigInteger DEFAULT_DENOMINATOR = BigInteger.valueOf(1);

  // +--------+-------------------------------------------------------
  // | Fields |
  // +--------+

  /** The numerator of the fraction. Can be positive, zero or negative. */
  private BigInteger num;

  /** The denominator of the fraction. Must be non-negative. */
  private BigInteger denom;

  // +--------------+-------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new fraction with numerator num and denominator denom.
   *
   * Warning! Not yet stable.
   *
   * @param numerator The numerator of the fraction.
   * @param denominator The denominator of the fraction.
   */
  public BigFraction(BigInteger numerator, BigInteger denominator) {
    this.num = numerator;
    this.denom = denominator;
  } // BigFraction(BigInteger, BigInteger)

  /**
   * Build a new fraction with numerator num and denominator denom.
   *
   * Warning! Not yet stable.
   *
   * @param numerator The numerator of the fraction.
   * @param denominator The denominator of the fraction.
   */
  public BigFraction(int numerator, int denominator) {
    this.num = BigInteger.valueOf(numerator);
    this.denom = BigInteger.valueOf(denominator);
  } // BigFraction(int, int)


  /**
   * Build a new fraction with numerator num
   *
   * Warning! Not yet stable.
   *
   * @param numerator The numerator of the fraction.
   */
  public BigFraction(int numerator) {
    this.num = BigInteger.valueOf(numerator);
    this.denom = BigInteger.valueOf(1);
  } // BigFraction(int)


  /**
   * Build a new fraction with numerator num and denominator denom.
   *
   * @param str A string representation of a fraction.
   */
  public BigFraction(String str) {
    int slashPos = -1;
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '/') {
        slashPos = i;
        break;
      } // if
    } // for
    String numString = str.substring(0, slashPos);
    String denomString = str.substring(slashPos + 1, str.length());
    this.num = new BigInteger(numString);
    this.denom = new BigInteger(denomString);
  } // BigFraction(str)

  /**
   * Build a new default fraction.
   */
  public BigFraction() {
    this.denom = DEFAULT_DENOMINATOR;
    this.num = DEFAULT_NUMERATOR;
  } // BigFraction()


  // +---------+------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Express this fraction as a double.
   *
   * @return the fraction approxiamted as a double.
   */
  public double doubleValue() {
    return this.num.doubleValue() / this.denom.doubleValue();
  } // doubleValue()

  /**
   * Simplify this fraction.
   *
   * @return the fraction simplfied.
   */
  public BigFraction simplify() {
    BigInteger denominator = this.denom;
    BigInteger numerator = this.num;
    BigInteger commonFactor = denominator.gcd(numerator);
    denominator = denominator.divide(commonFactor);
    numerator = numerator.divide(commonFactor);
    return new BigFraction(numerator, denominator);
  } // simplify()

  /**
   * Add another faction to this fraction.
   *
   * @param addend The fraction to add.
   *
   * @return the result of the addition.
   */
  public BigFraction add(BigFraction addend) {
    BigInteger resultNumerator;
    BigInteger resultDenominator;

    // The denominator of the result is the product of this object's
    // denominator and addend's denominator
    resultDenominator = this.denom.multiply(addend.denom);
    // The numerator is more complicated
    resultNumerator = (this.num.multiply(addend.denom)).add(addend.num.multiply(this.denom));

    // Return the computed value
    return new BigFraction(resultNumerator, resultDenominator);
  } // add(BigFraction)

  /**
   * Subtract another faction from this fraction.
   *
   * @param subend The fraction to subtract.
   *
   * @return the result of the subtraction.
   */
  public BigFraction sub(BigFraction subend) {
    BigInteger newNum = subend.num.multiply(BigInteger.valueOf(-1));
    return this.add(new BigFraction(newNum, subend.denom));
  } // sub(BigFraction)


  /**
   * Multiply this fraction by another fraction.
   *
   * @param frac The fraction to multiply by.
   *
   * @return the result of the multiplication.
   */
  public BigFraction multiply(BigFraction frac) {
    BigInteger numerator = this.num.multiply(frac.num);
    BigInteger denominator = this.denom.multiply(frac.denom);

    return new BigFraction(numerator, denominator);
  } // multiply(BigFraction)

  /**
   * Divide this fraction by another fraction.
   *
   * @param frac The fraction to divide by.
   *
   * @return the result of the division.
   */
  public BigFraction divide(BigFraction frac) {
    BigInteger numerator = this.num.multiply(frac.denom);
    BigInteger denominator = this.denom.multiply(frac.num);

    return new BigFraction(numerator, denominator);
  } // divide(BigFraction)

  /**
   * Get the fractional part of a complex fraction.
   *
   * @return the fraction without any integers
   */
  public BigFraction fractional() {
    BigInteger remainder = this.num.mod(this.denom);

    return new BigFraction(remainder, this.denom);
  } // fractional()

  /**
   * Get the denominator of this fraction.
   *
   * @return the denominator
   */
  public BigInteger denominator() {
    return this.denom;
  } // denominator()

  /**
   * Get the numerator of this fraction.
   *
   * @return the numerator
   */
  public BigInteger numerator() {
    return this.num;
  } // numerator()

  /**
   * Convert this fraction to a string for ease of printing.
   *
   * @return a string that represents the fraction.
   */
  public String toString() {
    // Special case: It's zero
    if (this.num.equals(BigInteger.ZERO)) {
      return "0/1";
    } // if it's zero

    // Lump together the string represention of the numerator,
    // a slash, and the string representation of the denominator
    // return this.num.toString().concat("/").concat(this.denom.toString());
    return this.num + "/" + this.denom;
  } // toString()
} // class BigFraction
