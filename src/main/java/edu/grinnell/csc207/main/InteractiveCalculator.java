package edu.grinnell.csc207.main;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;
import edu.grinnell.csc207.util.BFCalculator;
import edu.grinnell.csc207.util.BFRegisterSet;
import edu.grinnell.csc207.util.BigFraction;


/**
 * Runs the REPL loop for calculating fractions.
 *
 * @author Alex Pollock
 */
public class InteractiveCalculator {
  /**
   * A calculator for fractions.
   *
   * @param args Command-line arguments;
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);
    Scanner eyes = new Scanner(System.in);
    String lastLine = "";
    BFCalculator bfCalc = new BFCalculator();
    BFRegisterSet bfReg = new BFRegisterSet();


    while (!lastLine.equals("QUIT")) {

      boolean isValid = true;
      lastLine = eyes.nextLine();
      String[] values = lastLine.split(" ");


      if (lastLine.equals("QUIT")) { // Are we quitting
        return;
      } else if (values[0].equals("STORE") && values.length == 2) { // are we storing
        if (values[1].length() == 1) {
          bfReg.store(values[1].charAt(0), bfCalc.get());
        } // if
      } else { // examining the input
        for (int i = 0; i < values.length; i++) { // replacing a-z w/ frac
          if (values[i].length() == 1 && !(isOperation(values[i]))
              && (values[i].charAt(0) >= 'a' && values[i].charAt(0) <= 'z')) {
            values[i] = bfReg.get(values[i].charAt(0)).toString();
          } // if
        } // for

        for (int i = 0; i < values.length; i++) {
          for (int n = 0; n < values[i].length(); n++) {
            if (!(isOperation(values[i])
                || (values[i].charAt(n) >= '0' && values[i].charAt(n) <= '9')
                || values[i].charAt(n) == '/')) {
              isValid = false;
            } // if
          } // for
          if ((i == values.length - 1 && isOperation(values[i]))
              || (i == 0 && isOperation(values[i]))) {
            isValid = false;
          } // if
          if (!(values[i].contains("/") || isOperation(values[i])) && isValid) {
            BigFraction convert = new BigFraction(new BigInteger(values[i]), new BigInteger("1"));
            values[i] = convert.toString();
          } // if
        } // for
      } // else

      if (isValid && !values[0].equals("STORE")) {
        if (values.length > 1) {
          for (int i = 0; i < values.length - 1; i++) {
            if (isOperation(values[i]) && isOperation(values[i + 1])) {
              System.err.println("Error: Two operations in a row");
              isValid = false;
              break;
            } // if
            if (!isOperation(values[i]) && !isOperation(values[i + 1])) {
              System.err.println("Error: Two integers in a row");
              isValid = false;
              break;
            } // if
            if (isOperation(values[i])) {

              BigFraction first = new BigFraction(values[0]);
              BigFraction second = new BigFraction();

              if (i == 1) {
                first = new BigFraction(values[0]);
              } else {
                first = bfCalc.get();
              } // else
              second = new BigFraction(values[i + 1]);

              if (values[i].charAt(0) == '+') {
                bfCalc.set(first.add(second));
              } // if

              if (values[i].charAt(0) == '-') {
                bfCalc.set(first.sub(second));
              } // if

              if (values[i].charAt(0) == '*') {
                bfCalc.set(first.multiply(second));
              } // if

              if (values[i].charAt(0) == '/') {
                bfCalc.set(first.divide(second));
              } // if
            } // if
            bfCalc.set(bfCalc.get().simplify());
          } // for
        } else if (isValid) {
          bfCalc.set(new BigFraction(values[0]));
        } // else if
        if (bfCalc.get().denominator().equals(new BigInteger("1")) && isValid) {
          pen.println("Result: " + bfCalc.get().numerator());
        } else if (isValid) {
          pen.println("Result: " + bfCalc.get());
        } // else if


      } else if (!isValid) {
        System.err.println("Error: please enter a valid statement");
      } // else if


    } // while
    eyes.close();
    pen.close();
  } // main(String[])

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

} // class InteractiveCalculator
