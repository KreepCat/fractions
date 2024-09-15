package edu.grinnell.csc207.main;

import java.io.PrintWriter;
import java.math.BigInteger;
import edu.grinnell.csc207.util.BFCalculator;
import edu.grinnell.csc207.util.BFRegisterSet;
import edu.grinnell.csc207.util.BigFraction;

/**
 * Takes in a set of strings and runs the associated operations.
 *
 * @author Alex Pollock
 */
public class QuickCalculator {
  /**
   * A calculator for fractions.
   *
   * @param args Command-line arguments used as statements.
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);

    BFCalculator bfCalc = new BFCalculator();
    BFRegisterSet bfReg = new BFRegisterSet();
    for (int i = 0; i < args.length; i++) {
      String lastLine = args[i];
      boolean isValid = true;
      String[] values = lastLine.split(" ");

      if (values[0].equals("STORE") && values.length == 2) { // are we storing
        if (values[1].length() == 1) {
          bfReg.store(values[1].charAt(0), bfCalc.get());
        } // if
      } else { // examining the input
        for (int n = 0; n < values.length; n++) { // replacing a-z w/ frac
          if (values[n].length() == 1 && !(isOperation(values[n]))
              && (values[n].charAt(0) >= 'a' && values[n].charAt(0) <= 'z')) {
            values[n] = bfReg.get(values[n].charAt(0)).toString();
          } // if
        } // for

        for (int m = 0; m < values.length; m++) {
          for (int n = 0; n < values[m].length(); n++) {
            if (!(isOperation(values[m])
                || (values[m].charAt(n) >= '0' && values[m].charAt(n) <= '9')
                || values[m].charAt(n) == '/')) {
              isValid = false;
            } // if
          } // for
          if ((m == values.length - 1 && isOperation(values[m]))
              || (m == 0 && isOperation(values[m]))) {
            isValid = false;
          } // if
          if (!(values[m].contains("/") || isOperation(values[m])) && isValid) {
            BigFraction convert = new BigFraction(new BigInteger(values[m]), new BigInteger("1"));
            values[m] = convert.toString();
          } // if
        } // for
      } // else

      if (isValid && !values[0].equals("STORE")) {
        pen.print(lastLine + " = ");
        if (values.length > 1) {
          for (int m = 0; m < values.length - 1; m++) {
            if (isOperation(values[m]) && isOperation(values[m + 1])) {
              System.err.println("Error: Two operations in a row");
              isValid = false;
              break;
            } // if
            if (!isOperation(values[m]) && !isOperation(values[m + 1])) {
              System.err.println("Error: Two integers in a row");
              isValid = false;
              break;
            } // if
            if (isOperation(values[m])) {

              BigFraction first = new BigFraction(values[0]);
              BigFraction second = new BigFraction();

              if (m == 1) {
                first = new BigFraction(values[0]);
              } else {
                first = bfCalc.get();
              } // else
              second = new BigFraction(values[m + 1]);

              if (values[m].charAt(0) == '+') {
                bfCalc.set(first.add(second));
              } // if

              if (values[m].charAt(0) == '-') {
                bfCalc.set(first.sub(second));
              } // if

              if (values[m].charAt(0) == '*') {
                bfCalc.set(first.multiply(second));
              } // if

              if (values[m].charAt(0) == '/') {
                bfCalc.set(first.divide(second));
              } // if
            } // if
            bfCalc.set(bfCalc.get().simplify());
          } // for
        } else if (isValid) {
          bfCalc.set(new BigFraction(values[0]));
        } // else if
        if (bfCalc.get().denominator().equals(new BigInteger("1")) && isValid) {
          pen.println(bfCalc.get().numerator());
        } else if (isValid) {
          pen.println(bfCalc.get());
        } // else if


      } else if (!isValid) {
        System.err.println("Error: please enter a valid statement");
      } // else
    } // for
    pen.close();
  } // main(String[])


  /**
   * Checks if the string is an operation.
   *
   * @param str a string
   * @return return if the string is an operation
   */
  public static boolean isOperation(String str) {
    if (str.equals("+") || str.equals("-") || str.equals("/") || str.equals("*")) {
      return true;
    } // if
    return false;
  } // String(str)
} // class QuickCalculator
