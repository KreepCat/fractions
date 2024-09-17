package edu.grinnell.csc207.main;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;
import edu.grinnell.csc207.util.BFCalculator;
import edu.grinnell.csc207.util.BFRegisterSet;
import edu.grinnell.csc207.util.BigFraction;
import edu.grinnell.csc207.util.EvaluateLine;


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
      lastLine = eyes.nextLine();
      String[] values = lastLine.split(" ");

      if (lastLine.equals("QUIT")) { // Are we quitting
        return;
      } else if (values[0].equals("STORE") && values.length == 2) { // are we storing
        if (values[1].length() == 1) {
          bfReg.store(values[1].charAt(0), bfCalc.get());
        } // if
      } else if (EvaluateLine.isValid(EvaluateLine.convert(values, bfReg))) {
        values = EvaluateLine.convert(values, bfReg);
        if (values.length > 1) {
          EvaluateLine.evaluate(values, bfCalc);
          bfCalc.set(bfCalc.get().simplify());
        } else {
          EvaluateLine.convert(values, bfReg);
          bfCalc.set(new BigFraction(values[0]).simplify());
        } // else
        if (bfCalc.get().denominator() == BigInteger.valueOf(1)) {
          pen.println(bfCalc.get().numerator());
        } else {
          pen.println(bfCalc.get().simplify());
        } // else

      } // else if

    } // while
    eyes.close();
    pen.close();
  } // main(String[])



} // class InteractiveCalculator
