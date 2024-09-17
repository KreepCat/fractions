package edu.grinnell.csc207.util;

import java.math.BigInteger;

/**
 * Performs arithmatic.
 *
 * @author Alex Pollock
 */
public class EvaluateLine {

  /**
   * Performs arthmatic on inputed elements.
   *
   * @param elements the values inputted.
   * @param bfCalc the calculator being used.
   */
  public static void evaluate(String[] elements, BFCalculator bfCalc) {
    if (elements.length > 1) {
      for (int i = 0; i < elements.length - 1; i++) {
        if (isOperation(elements[i])) {

          BigFraction first = new BigFraction(elements[0]);
          BigFraction second = new BigFraction();

          if (i == 1) {
            first = new BigFraction(elements[0]);
          } else {
            first = bfCalc.get();
          } // else
          second = new BigFraction(elements[i + 1]);

          if (elements[i].charAt(0) == '+') {
            bfCalc.set(first.add(second));
          } // if

          if (elements[i].charAt(0) == '-') {
            bfCalc.set(first.subtract(second));
          } // if

          if (elements[i].charAt(0) == '*') {
            bfCalc.set(first.multiply(second));
          } // if

          if (elements[i].charAt(0) == '/') {
            bfCalc.set(first.divide(second));
          } // if
        } // if
        bfCalc.set(bfCalc.get().simplify());
      } // for
    } else {
      bfCalc.set(new BigFraction(elements[1]));
    } // else
  } // Evaluate(String[], BFCalculator)

  /**
   * Checks if the string is an operation.
   *
   * @param str a string
   * @return returns if the string is an operation
   */
  public static boolean isOperation(String str) {
    if (str.equals("+") || str.equals("-") || str.equals("/") || str.equals("*")) {
      return true;
    } // if
    return false;
  } // String(str)

  /**
   * Checks if the elements are valid for arithmatic.
   *
   * @param elements the value to divide by.
   * @return returns if the elements are valid.
   */
  public static boolean isValid(String[] elements) {
    if (elements[0].length() == 0) {
      return false;
    } // if
    for (int i = 0; i < elements.length; i++) {
      for (int n = 0; n < elements[i].length(); n++) {
        if (!(EvaluateLine.isOperation(elements[i])
            || (elements[i].charAt(n) >= '0' && elements[i].charAt(n) <= '9')
            || elements[i].charAt(n) == '/')) {
          System.err.println("Error: Invalid Line");
          return false;
        } // if
      } // for

      if ((i == elements.length - 1 && EvaluateLine.isOperation(elements[i]))
          || (i == 0 && EvaluateLine.isOperation(elements[i]))) {
        System.err.println("Error: Invalid Line");
        return false;
      } // if

    } // for
    for (int i = 0; i < elements.length - 1; i++) {
      if (isOperation(elements[i]) && isOperation(elements[i + 1])) {
        System.err.println("Error: Two operations in a row");
        return false;
      } // if
      if (!isOperation(elements[i]) && !isOperation(elements[i + 1])) {
        System.err.println("Error: Two integers in a row");
        return false;
      } // if
    } // for

    return true;

  } // isValid(String)

  /**
   * Converts a given string into something the program can read.
   *
   * @param elements the values given.
   * @param reg the stored values.
   * @return the converted values
   */
  public static String[] convert(String[] elements, BFRegisterSet reg) {
    if (elements[0].length() == 0) {
      return elements;
    } // if
    for (int i = 0; i < elements.length; i++) { // replacing a-z w/ frac
      if (elements[i].length() == 1 && !(EvaluateLine.isOperation(elements[i]))
          && (elements[i].charAt(0) >= 'a' && elements[i].charAt(0) <= 'z')) {
        elements[i] = reg.get(elements[i].charAt(0)).toString();
      } // if
    } // for
    for (int i = 0; i < elements.length; i++) {
      for (int n = 0; n < elements[i].length(); n++) {
        if (!(EvaluateLine.isOperation(elements[i]) || elements[i].charAt(n) == '/')) {
          return elements;
        } // if
      } // for
    } // for
    for (int i = 0; i < elements.length; i++) {
      if (!(elements[i].contains("/") || EvaluateLine.isOperation(elements[i]))) {
        BigFraction convert = new BigFraction(new BigInteger(elements[i]), new BigInteger("1"));
        elements[i] = convert.toString();
      } // if
    } // for
    return elements;
  } // convert(String, BFRegisterSet)

} // class EvaluateLine
