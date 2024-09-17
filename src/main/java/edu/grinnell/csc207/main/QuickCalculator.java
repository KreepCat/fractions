package edu.grinnell.csc207.main;

import java.io.PrintWriter;
import java.math.BigInteger;
import edu.grinnell.csc207.util.BFCalculator;
import edu.grinnell.csc207.util.BFRegisterSet;
import edu.grinnell.csc207.util.BigFraction;
import edu.grinnell.csc207.util.EvaluateLine;

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
      String[] values = lastLine.split(" ");

      if (values[0].equals("STORE") && values.length == 2) { // are we storing
        if (values[1].length() == 1) {
          bfReg.store(values[1].charAt(0), bfCalc.get());
        } // if
      } else if (EvaluateLine.isValid(EvaluateLine.convert(values, bfReg))) {
        for (int m = 0; m < values.length; m++) {
          pen.print(values[m] + " ");
        } // for
        pen.print("= ");
        values = EvaluateLine.convert(values, bfReg);
        if (values.length > 1) {
          EvaluateLine.evaluate(values, bfCalc);
          bfCalc.set(bfCalc.get());
        } else {
          EvaluateLine.convert(values, bfReg);
          bfCalc.set(new BigFraction(values[0]));
        } // else
        if (bfCalc.get().denominator() == BigInteger.valueOf(1)) {
          pen.println(bfCalc.get().numerator());
        } else {
          pen.println(bfCalc.get());
        } // else
      } // else if
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
