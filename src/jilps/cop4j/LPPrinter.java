package jilps.cop4j;

import jilps.cop4j.algebra.Cop4JVectorEntry;
import jilps.cop4j.algebra.Cop4JMatrix;
import jilps.cop4j.algebra.Cop4JVector;

import java.io.PrintStream;

/**
 * @author Sebastian Riedel
 */
public class LPPrinter {

  void print(LP lp, PrintStream out) {
    //cost function
    out.print(lp.isMax() ? "max: " : "min: ");
    int index = 0;

    Cop4JMatrix A = lp.getConstraintMatrix();
    Cop4JVector c = lp.getCosts();
    Cop4JVector l = lp.getColumnLowerBounds();
    Cop4JVector u = lp.getColumnUpperBounds();
    Cop4JVector a = lp.getRowLowerBounds();
    Cop4JVector b = lp.getRowUpperBounds();

    for (Cop4JVectorEntry entry : c.getNonZeroValues()) {
      if (index++ == 0) {
        printFirstTerm(lp.getColumnName(entry.getIndex()), entry.getValue(), out);
      } else {
        printTerm(lp.getColumnName(entry.getIndex()), entry.getValue(), out);
      }
    }

    out.println();
    out.println("/* Constraints */");
    for (int row = 0; row < A.getNumRows(); ++row) {
      double lb = a.getValue(row);
      double ub = b.getValue(row);
      out.print(lb);
      out.print(" <= ");
      index = 0;
      for (Cop4JVectorEntry entry : A.getRow(row).getNonZeroValues()) {
        if (index++ == 0)
          printFirstTerm(lp.getColumnName(entry.getIndex()), entry.getValue(), out);
        else
          printTerm(lp.getColumnName(entry.getIndex()), entry.getValue(), out);
      }
      out.print(" <= ");
      out.print(ub);
      out.println();
    }

    out.println("/* Bounds */");    
    for (int column = 0; column < A.getNumCols(); ++column) {
      double lb = l.getValue(column);
      double ub = u.getValue(column);
      out.print(lb);
      out.print(" <= ");
      index = 0;
      out.print(lp.getColumnName(column));
      out.print(" <= ");
      out.print(ub);
      out.println();
    }


  }

  private void printTerm(String name, double value, PrintStream out) {
    out.print(value < 0 ? " - " : " + ");
    double abs = Math.abs(value);
    if (abs != 1) out.print(abs + " ");
    out.print(name);

  }

  private void printFirstTerm(String name, double value, PrintStream out) {
    out.print(value < 0 ? "-" : "");
    double abs = Math.abs(value);
    if (abs != 1) out.print(abs + " ");
    out.print(name);
  }

}
